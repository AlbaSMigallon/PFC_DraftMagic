package controlador;

import java.util.ArrayList;

import persistencias.Mazo;

public abstract class Jugador {
	protected ArrayList<Sobre> sobres;
	protected ArrayList<Mazo> mazo;

	public ArrayList<Sobre> getSobres() {
		return sobres;
	}

	public void setSobres(ArrayList<Sobre> sobres) {
		this.sobres = sobres;
	}

	public ArrayList<Mazo> getMazo() {
		return mazo;
	}

	public void setMazo(ArrayList<Mazo> mazo) {
		this.mazo = mazo;
	}

}
