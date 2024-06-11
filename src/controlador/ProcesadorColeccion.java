package controlador;

import com.google.gson.stream.JsonReader;

import modelo.ProcesarColeccionDAO;
import persistencias.Carta;
import persistencias.Coleccion;

import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/*
 * Clase que se encarga de la carga en BBDD de la coleccion
 */
public class ProcesadorColeccion extends Thread {

	private static String nombreCSV;
	private static String nombreColeccion;
	private static ArrayList<Carta> cartas;
	private static Coleccion coleccion;

	public ProcesadorColeccion(String nombreCSV, String nombreColeccion) {
		super();
		this.nombreCSV = nombreCSV;
		this.nombreColeccion = nombreColeccion;
		this.cartas = new ArrayList<Carta>();
		this.coleccion = new Coleccion(nombreColeccion);
	}

	// Metodo para extraer el valor numerico del coste de mana
	// ejemplo "mana_cost":"{2}{W}{W}"
	private static int procesarManaCost(String manaCost) {
		int totalCost = 0;

		String[] mana = manaCost.split("\\{");

		for (String simbolo : mana) {
			if (simbolo.isEmpty())
				continue;
			simbolo = simbolo.replace("}", "");

			switch (simbolo) {
			case "W":
				totalCost++;
				break;
			case "U":
				totalCost++;
				break;
			case "B":
				totalCost++;
				break;
			case "R":
				totalCost++;
				break;
			case "G":
				totalCost++;
				break;
			default:
				try {
					totalCost += Integer.parseInt(simbolo);
				} catch (NumberFormatException e) {
					totalCost = 0;
				}
				break;
			}
		}
		return totalCost;
	}

	/*
	 * metodo que carga el JSON de todas las cartas
	 */
	private static void cargaJson() {
		System.out.println("Iniciamos carga JSON");
		try (JsonReader reader = new JsonReader(
				new InputStreamReader(new FileInputStream("archivos_datos/all-cards-20240103101329.json")))) {// el
																												// archivo
																												// JSON
																												// es el
																												// mismo
																												// para
																												// todas
																												// las
																												// cartas
			reader.beginArray();
			while (reader.hasNext()) {
				reader.beginObject();
				String tipo = "";
				String set = "";
				String cardName = "";
				String printedName = "";
				String idioma = "";
				String manaCost = "";
				String rarity = "";
				String png = "";
				String color = "";
				String typeLine = "";

				while (reader.hasNext()) {
					String key = reader.nextName();

					if (key.equals("set")) {
						set = reader.nextString();
					} else if (key.equals("name")) {
						cardName = reader.nextString();
					} else if (key.equals("printed_name")) {
						printedName = reader.nextString();
					} else if (key.equals("lang")) {
						idioma = reader.nextString();
					} else if (key.equals("mana_cost")) {
						manaCost = reader.nextString();
					} else if (key.equals("rarity")) {
						rarity = reader.nextString();
					} else if (key.equals("image_uris")) {
						reader.beginObject();
						while (reader.hasNext()) {
							String imgKey = reader.nextName();
							if (imgKey.equals("png")) {
								png = reader.nextString();
							} else {
								reader.skipValue();
							}
						}
						reader.endObject();
					} else if (key.equals("color_identity")) {
						reader.beginArray();
						StringBuilder colorBuilder = new StringBuilder();
						while (reader.hasNext()) {
							colorBuilder.append(reader.nextString());
						}
						reader.endArray();
						color = colorBuilder.toString();
						if (color.equals("")) {
							color = "COLORLESS";
						}
					} else if (key.equals("type_line")) {
						typeLine = reader.nextString();
						// Extraer el tipo antes del guion
						if (typeLine.contains(" — ")) {
							tipo = typeLine.split(" — ")[0].trim();
						} else {
							tipo = typeLine.trim();
						}

						// Clasificar el tipo en base a las reglas
						if (tipo.contains("Creature")) {
							tipo = "Creature";
						} else if (tipo.equals("Sorcery") || tipo.equals("Enchantment") || tipo.equals("Instant")
								|| tipo.equals("Legendary Enchantment") || tipo.equals("Legendary Instant")) {
							tipo = "Instant";
						} else if (tipo.equals("Legendary Artifact")) {
							tipo = "Artifact";
						}
					} else {
						reader.skipValue();
					}
				}
				reader.endObject();
				if (set.equals(coleccion.getNombre()) && idioma.equals("es")) {
					try {
						for (int i = 0; i < cartas.size(); i++) {
							Carta carta = cartas.get(i);
							if (carta.getNombreOriginal().equals(cardName)
									|| carta.getNombreOriginal().equals(printedName)) {
								carta.setNombreEspaniol(printedName);
								carta.setCoste(procesarManaCost(manaCost));// procesar el mana cost
								carta.setRareza(rarity);
								carta.setPng(png);
								carta.setColor(color);
								carta.setTipo(tipo);
								carta.setColeccion(coleccion);
								i = cartas.size();// como ya ha encontrado la carta se sale del for
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			System.out.println("Finalizamos la entrada de cartas");
			reader.endArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Borrar direcctorio de imagenes para controlar si se han quedado cortado el
	 * proceso de descarga en cargas anteriores
	 */
	public static boolean deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			// Si es un directorio, recorrer todos los archivos y directorios dentro
			File[] children = dir.listFiles();
			if (children != null) {
				for (File child : children) {
					boolean success = deleteDirectory(child);
					if (!success) {
						return false;
					}
				}
			}
		}
		// Finalmente, borrar el archivo o directorio actual
		return dir.delete();
	}

	/*
	 * Descargar pngs de las cartas de la coleccion
	 */
	private static void descargarPNG() {
		System.out.println("Iniciamos descarga PNG de cartas");
		String nombreColeecion = coleccion.getNombre();
		try {
			// crear carpeta cartas por coleccion
			File carpeta = new File((coleccion.getNombre()) + "_PNG_cartas");
			if (carpeta.exists()) {
				boolean result = deleteDirectory(carpeta);
				if (result) {
					System.out.println("Carpeta borrada.");
				} else {
					System.out.println("No se pudo borrar la carpeta.");
				}
			}
			if (!carpeta.exists()) {
				carpeta.mkdirs();
			}

			for (int i = 0; i < cartas.size(); i++) {
				Carta carta = cartas.get(i);
				String urlImagen = carta.getPng();
				// nombre de png cartaOriginal
				String nombreArchivo = carta.getNombreOriginal() + ".png";
				File archivoImagen = new File(carpeta, nombreArchivo);

				URL url = new URL(urlImagen);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				try (BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
						FileOutputStream out = new FileOutputStream(archivoImagen)) {

					byte[] buffer = new byte[1024];
					int bytesRead;
					while ((bytesRead = in.read(buffer)) != -1) {
						out.write(buffer, 0, bytesRead);
					}
				}
				System.out.println(
						"Imagen de " + carta.getNombreOriginal() + " descargada en " + archivoImagen.getAbsolutePath());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Carga de CSV con las estadisticas de la coleccion
	 */
	private static void cargaCSV() {
		System.out.println("Iniciamos carga CSV");
		String linea;
		String separador = ";";
		cartas = new ArrayList<Carta>();

		try (BufferedReader br = new BufferedReader(new FileReader("archivos_datos/" + nombreCSV))) {
			linea = br.readLine();
			String[] encabezados = linea.split(separador);

			int indiceName = -1;
			for (int i = 0; i < encabezados.length; i++) {
				if (encabezados[i].equals("Name")) {
					indiceName = i;
					break;
				}
			}

			while ((linea = br.readLine()) != null) {
				String[] datos = linea.split(separador);

				String nombreCartaCSV = datos[0];
				String seen = datos[3];
				String alsa = datos[4];
				String picked = datos[5];
				String ata = datos[6];
				String gp = datos[7];
				String gpWr = datos[8];

				// Eliminar comillas dobles de las cadenas
				nombreCartaCSV = nombreCartaCSV.replace("\"", "");
				seen = seen.replaceAll("\"", "");
				alsa = alsa.replaceAll("\"", "");
				picked = picked.replaceAll("\"", "");
				ata = ata.replaceAll("\"", "");
				gp = gp.replaceAll("\"", "");
				gpWr = gpWr.replaceAll("\"", "");

				// Quitar % al final de las cadenas gp y gpWr
				gp = gp.replaceAll("%", "");
				gpWr = gpWr.replaceAll("%", "");

				// Crear objeto Carta
				Carta carta = new Carta();
				carta.setNombreOriginal(nombreCartaCSV);
				carta.setSeen(Integer.parseInt(seen));
				carta.setAlsa(new BigDecimal(alsa));
				carta.setPicked(Integer.parseInt(picked));
				carta.setAta(new BigDecimal(ata));
				carta.setGp(Integer.parseInt(gp));
				carta.setGpWr(new BigDecimal(gpWr));
				carta.setColeccion(coleccion);

				// Agregar carta a estructura datos
				cartas.add(carta);
				System.out.println(carta.getNombreOriginal());
				System.out.println("----------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Metodo run para iniciar la carga de la coleccion
	 */
	public void run() {
		SessionFactory sessionFactory = null;
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			sessionFactory = configuration.buildSessionFactory();

			this.coleccion.setNombre(this.nombreColeccion);
			// procesar CSV
			cargaCSV();
			// procesarJSON
			cargaJson();

			ProcesarColeccionDAO coleccionDAO = new ProcesarColeccionDAO();
			descargarPNG();
			coleccionDAO.borrarColeccionesPorNombre(this.nombreColeccion);
			System.out.println("Insert tabla coleccion");
			coleccion.setInsertado(1);
			coleccionDAO.insertarColeccion(coleccion);
			coleccionDAO.insertarCartasEnBDD(cartas);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sessionFactory != null) {
				sessionFactory.close();
			}
		}
	}

	public static Coleccion getColeccion() {
		return coleccion;
	}

	public static void setColeccion(Coleccion coleccion) {
		ProcesadorColeccion.coleccion = coleccion;
	}

}
