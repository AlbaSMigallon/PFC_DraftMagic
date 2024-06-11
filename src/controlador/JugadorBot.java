package controlador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import persistencias.Carta;
import persistencias.Mazo;
import persistencias.Usuario;

/*
 * Clase que extiende de Jugador. Se encarga del los bots
 */
public class JugadorBot extends Jugador {
	private int numBot;
	private String color1;
	private String color2;

	public JugadorBot(int numNot) {
		super();
		// TODO Auto-generated constructor stub
		this.sobres = new ArrayList<Sobre>();
		this.mazo = new ArrayList<Carta>();
		// this.numBot = numBot;
	}

	public int getNumBot() {
		return numBot;
	}

	public void setNumBot(int numBot) {
		this.numBot = numBot;
	}

	public void agregarSobre(Sobre sobre) {
		this.sobres.add(sobre);
	}

	/*
	 * Metodo para seleccionar carta
	 */
	public void seleccionarCartas() {
		if (this.sobres.isEmpty()) {
			return;
		}

		Sobre primerSobre = this.sobres.get(0);

		// Seleccionar cartas hasta que el mazo tenga 12 cartas
		while (mazo.size() < 12 && !primerSobre.getCartasSobre().isEmpty()) {
			Carta mejorCarta = seleccionarMejorCarta(primerSobre);
			if (mejorCarta != null) {
				mazo.add(mejorCarta);
				primerSobre.getCartasSobre().remove(mejorCarta);
			}
		}

		// Determinar los dos colores principales
		determinarColores();

		// Seleccionar cartas basadas en los colores preferidos
		while (!primerSobre.getCartasSobre().isEmpty()) {
			Carta mejorCarta = seleccionarCartaConColores(primerSobre);
			if (mejorCarta != null) {
				mazo.add(mejorCarta);
				primerSobre.getCartasSobre().remove(mejorCarta);
			}
		}
	}

	/*
	 * Metodo para seleccionar carta con mas WR
	 */
	private Carta seleccionarMejorCarta(Sobre sobre) {
		Carta mejorCarta = null;
		for (int i = 0; i < sobre.getCartasSobre().size(); i++) {
			Carta carta = sobre.getCartasSobre().get(i);
			if (mejorCarta == null || carta.getGpWr().compareTo(mejorCarta.getGpWr()) > 0) {
				mejorCarta = carta;
			}
		}
		return mejorCarta;
	}

	/*
	 * Metodo para determinar color del mazo del bot
	 */
	private void determinarColores() {
		Map<String, Integer> colorCount = new HashMap<>();
		for (int i = 0; i < mazo.size(); i++) {
			Carta carta = mazo.get(i);
			String color = carta.getColor();
			colorCount.put(color, colorCount.getOrDefault(color, 0) + 1);
		}

		color1 = null;
		color2 = null;
		int maxCount1 = 0;
		int maxCount2 = 0;

		for (Map.Entry<String, Integer> entry : colorCount.entrySet()) {
			String color = entry.getKey();
			int count = entry.getValue();
			if (count > maxCount1) {
				maxCount2 = maxCount1;
				color2 = color1;
				maxCount1 = count;
				color1 = color;
			} else if (count > maxCount2) {
				maxCount2 = count;
				color2 = color;
			}
		}
	}

	/*
	 * Metodo principal para selecciond de carta de bot
	 */
	private Carta seleccionarCartaConColores(Sobre sobre) {
		Carta mejorCarta = null;
		for (int i = 0; i < sobre.getCartasSobre().size(); i++) {
			Carta carta = sobre.getCartasSobre().get(i);
			if (carta.getColor().equals(color1) || carta.getColor().equals(color2)) {
				if (mejorCarta == null || carta.getGpWr().compareTo(mejorCarta.getGpWr()) > 0) {
					mejorCarta = carta;
				}
			}
		}

		if (mejorCarta == null) {
			// No hay cartas de los colores preferidos. seleccionar la de mayor gpWr
			mejorCarta = seleccionarMejorCarta(sobre);
		}

		return mejorCarta;
	}
}