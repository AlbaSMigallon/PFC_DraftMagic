package controlador;

import java.util.ArrayList;

import persistencias.Carta;
import persistencias.Mazo;

/*
 * Clase abstracta de jugador. Heredan JugadorUsuario y JugadorBot
 */
public abstract class Jugador {
	protected ArrayList<Sobre> sobres;
	protected ArrayList<Carta> mazo;

	public ArrayList<Sobre> getSobres() {
		return sobres;
	}

	public void setSobres(ArrayList<Sobre> sobres) {
		this.sobres = sobres;
	}

	public ArrayList<Carta> getMazo() {
		return mazo;
	}

	public void setMazo(ArrayList<Carta> mazo) {
		this.mazo = mazo;
	}

}
