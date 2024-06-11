package controlador;

import java.util.ArrayList;

import persistencias.Carta;

/*
 * Clase para la fase draft. Crea los sobres y crea los bots
 */
public class Draft {
	private ArrayList<Carta> cartas;
	private ArrayList<JugadorBot> jugadoresBot;
	private ArrayList<Sobre> sobresRonda;
	private JugadorUsuario jugadorUsuario;
	private boolean esFinDraft;
	private int ronda;

	public Draft(ArrayList<Carta> cartas, JugadorUsuario jugadorUsuario) {
		super();
		this.cartas = cartas;
		this.jugadorUsuario = jugadorUsuario;
		this.esFinDraft = false;
		this.jugadoresBot = new ArrayList<JugadorBot>();
		this.sobresRonda = new ArrayList<Sobre>();
		this.ronda = 1;
		crearJugadoresBot();
		asignarSobres();

	}

	public ArrayList<Carta> getCartas() {
		return cartas;
	}

	public void setCartas(ArrayList<Carta> cartas) {
		this.cartas = cartas;
	}

	public ArrayList<JugadorBot> getJugadoresBot() {
		return jugadoresBot;
	}

	public void setJugadoresBot(ArrayList<JugadorBot> jugadoresBot) {
		this.jugadoresBot = jugadoresBot;
	}

	public ArrayList<Sobre> getSobresRonda() {
		return sobresRonda;
	}

	public void setSobresRonda(ArrayList<Sobre> sobresRonda) {
		this.sobresRonda = sobresRonda;
	}

	public JugadorUsuario getJugadorUsuario() {
		return jugadorUsuario;
	}

	public void setJugadorUsuario(JugadorUsuario jugadorUsuario) {
		this.jugadorUsuario = jugadorUsuario;
	}

	public boolean isEsFinDraft() {
		return esFinDraft;
	}

	public void setEsFinDraft(boolean esFinDraft) {
		this.esFinDraft = esFinDraft;
	}

	public int getRonda() {
		return ronda;
	}

	public void setRonda(int ronda) {
		this.ronda = ronda;
	}

	private void crearJugadoresBot() {
		// se crean 7 jugadores contrincantes para hacer un total de 8 en el draft
		for (int i = 0; i < 7; i++) {
			JugadorBot jugadorBot = new JugadorBot(i + 1);
			jugadoresBot.add(jugadorBot);
		}
	}

	private void asignarSobres() {
		GeneradorSobres generadorSobres = new GeneradorSobres(cartas);

		// asignar 3 sobres al jugador usuario
		for (int i = 0; i < 3; i++) {
			jugadorUsuario.agregarSobre(generadorSobres.generarSobre());
		}

		// asignar 3 sobres a cada jugador bot
		for (JugadorBot jugadorBot : jugadoresBot) {
			for (int i = 0; i < 3; i++) {
				jugadorBot.agregarSobre(generadorSobres.generarSobre());
			}
		}
	}

	public void annadirSobresRonda() {
		sobresRonda = new ArrayList<Sobre>();
		sobresRonda.add(jugadorUsuario.getSobres().get(this.getRonda() - 1));
		for (JugadorBot bot : jugadoresBot) {
			sobresRonda.add(bot.getSobres().get(this.getRonda() - 1));
		}
	}

}
