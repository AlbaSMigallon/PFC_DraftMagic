package controlador;

import modelo.UsuarioDAO;
import persistencias.Usuario;

/*
 * Clase de gestion de Login de la aplicacion
 */
public class Login {
	private UsuarioDAO usuarioDAO;

	public Login(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	public Usuario autenticarUsuario(String nombreUsuario, String contrasena) {
		Usuario usuario = usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario);

		if (usuario != null) {

			return usuario;

		} else {
			return null; // Devuelve null si el usuario no es encontrado
		}
	}

	public Usuario crearNuevoUsuario(String nombreUsuario, String contrasena) {
		Usuario nuevoUsuario = new Usuario();
		nuevoUsuario.setNombre(nombreUsuario);
		nuevoUsuario.setContrasenia(contrasena);

		usuarioDAO.guardarUsuario(nuevoUsuario);

		return nuevoUsuario; // Devuelve el usuario creado
	}

}
