package controlador;

import java.util.ArrayList;

import persistencias.Usuario;
import persistencias.Carta;

/*
 * Clase de jugador que utiliza la aplicacion
 */
public class JugadorUsuario extends Jugador {
	private Usuario usuario;
	protected ArrayList<Carta> mazoConstruido = new ArrayList<Carta>();

	public JugadorUsuario(Usuario usuario) {
		super();
		// TODO Auto-generated constructor stub
		this.sobres = new ArrayList<Sobre>();
		this.mazo = new ArrayList<Carta>();
		this.usuario = usuario;
		this.mazoConstruido = mazoConstruido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void agregarSobre(Sobre sobre) {
		this.sobres.add(sobre);
	}

	public ArrayList<Carta> getMazoConstruido() {
		return mazoConstruido;
	}

	public void setMazoConstruido(ArrayList<Carta> mazoConstruido) {
		this.mazoConstruido = mazoConstruido;
	}

}
