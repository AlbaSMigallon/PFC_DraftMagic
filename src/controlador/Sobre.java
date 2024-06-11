package controlador;

import java.util.ArrayList;

import persistencias.Carta;

/*
 * Clase Sobre
 */
public class Sobre {
	private int numSobre;
	private ArrayList<Carta> cartasSobre;
	private boolean esFinSobre;

	public Sobre(int numSobre) {
		super();
		this.numSobre = numSobre;
		this.cartasSobre = new ArrayList<Carta>();
		this.esFinSobre = false;
	}

	public int getNumSobre() {
		return numSobre;
	}

	public void setNumSobre(int numSobre) {
		this.numSobre = numSobre;
	}

	public ArrayList<Carta> getCartasSobre() {
		return cartasSobre;
	}

	public void setCartasSobre(ArrayList<Carta> cartasSobre) {
		this.cartasSobre = cartasSobre;
	}

	public boolean isEsFinSobre() {
		return esFinSobre;
	}

	public void setEsFinSobre(boolean esFinSobre) {
		this.esFinSobre = esFinSobre;
	}

	public void agregarCarta(Carta carta) {
		if (this.cartasSobre.size() < 14) {
			this.cartasSobre.add(carta);
		}
	}

	public void eliminarCarta(Carta cartaSeleccionada) {
		cartasSobre.remove(cartaSeleccionada);
	}

}
