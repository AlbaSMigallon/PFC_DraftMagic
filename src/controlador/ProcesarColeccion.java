package controlador;

import com.google.gson.stream.JsonReader;

import modelo.GestionBBDD;
import persistencias.Carta;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ProcesarColeccion extends Thread {
	private static String rutaJSON;
	private static String rutaCSV;
	private static String nombreColeccion;
	private static ArrayList<Carta> cartas;

	public ProcesarColeccion(String rutaJSON, String rutaCSV, String nombreColeccion) {
		super();
		this.rutaJSON = rutaJSON;
		this.rutaCSV = rutaCSV;
		this.nombreColeccion = nombreColeccion;
		this.cartas = new ArrayList<Carta>();
	}

	// Metodo para extraer el valor numerico del coste de mana
	// ejemplo "mana_cost":"{2}{W}{W}"
	private static int procesarManaCost(String manaCost) {
		int totalCost = 0;
		Pattern pattern = Pattern.compile("\\{(\\d+)\\}"); // Busca numeros dentro de {}
		Matcher matcher = pattern.matcher(manaCost);
		while (matcher.find()) {
			totalCost += Integer.parseInt(matcher.group(1));
		}
		return totalCost;
	}

	private static void cargaJson(String coleccion) {
		System.out.println("Iniciamos carga JSON");
		try (JsonReader reader = new JsonReader(
				new InputStreamReader(new FileInputStream("archivos_datos/all-cards-20240103101329.json")))) {
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
				if (set.equals(coleccion) && idioma.equals("es")) {
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

	private static void descargarPNG(String coleccion) {
		System.out.println("Iniciamos descarga PNG de cartas");
		try {
			// crear carpeta cartas por coleccion
			File carpeta = new File(
					"C:\\Users\\Alba\\Documents\\EFA\\2_DAM\\0_PROYECTO\\Proyecto\\PFC_DraftMagic_AlbaSanchezMigallonArias\\"
							+ coleccion + "_PNG_cartas");
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

	private static void cargaCSV(String nombreCSV) {
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

				// Agregar carta a estructura datos
				cartas.add(carta);
				System.out.println(carta.getNombreOriginal());
				System.out.println("----------------------------------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		SessionFactory sessionFactory = null;
		try {
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			sessionFactory = configuration.buildSessionFactory();

			// procesar CSV
			cargaCSV("card-ratings-2024-05-12.csv");
			// procesarJSON
			cargaJson("ltr");

			GestionBBDD gBD = GestionBBDD.getInstance();
			gBD.insertarCartasEnBDD(cartas);
			// descargarPNG("ltr");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (sessionFactory != null) {
				sessionFactory.close();
			}
		}
	}

}
