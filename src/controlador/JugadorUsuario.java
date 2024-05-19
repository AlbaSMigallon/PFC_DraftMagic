package controlador;

import java.util.ArrayList;

import persistencias.Mazo;
import persistencias.Usuario;

public class JugadorUsuario extends Jugador {
	private Usuario usuario;

	public JugadorUsuario(Usuario usuario) {
		super();
		// TODO Auto-generated constructor stub
		this.sobres = new ArrayList<Sobre>();
		this.mazo = new ArrayList<Mazo>();
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
