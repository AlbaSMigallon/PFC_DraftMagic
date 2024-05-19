package controlador;

import java.util.ArrayList;

import persistencias.Mazo;
import persistencias.Usuario;

public class JugadorBot extends Jugador {
	private int numBot;

	public JugadorBot(int numNot) {
		super();
		// TODO Auto-generated constructor stub
		this.sobres = new ArrayList<Sobre>();
		this.mazo = new ArrayList<Mazo>();
		this.numBot = numBot;
	}

	public int getNumBot() {
		return numBot;
	}

	public void setNumBot(int numBot) {
		this.numBot = numBot;
	}

}
