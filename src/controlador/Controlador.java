package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import modelo.MazoDAO;
import modelo.ProcesarColeccionDAO;
import modelo.UsuarioDAO;
import persistencias.Carta;
import persistencias.Coleccion;
import persistencias.Mazo;
import persistencias.Usuario;
import vista.Vista;

/*
 * Clase que controla la Vista 
 */
public class Controlador implements ActionListener, ListSelectionListener {
	private Vista vista;
	private ProcesarColeccionDAO coleccionDAO;
	private UsuarioDAO usuarioDAO;
	private Login login;
	private Usuario usuarioActual;
	private JugadorUsuario jugadorUsuario;
	private Draft draft;
	private Coleccion coleccionUso;
	private ProcesadorColeccion procesarColeccion;
	private Sobre sobre;
	private Carta cartaSeleccionada;
	private int seleccion;

	/*
	 * Constructor de la clase Controlador
	 */
	public Controlador(Vista vista) throws InterruptedException {
		this.vista = vista;
		this.usuarioDAO = new UsuarioDAO();
		this.coleccionDAO = new ProcesarColeccionDAO();
		this.login = new Login(usuarioDAO);
		this.seleccion = 0;

		// Agregar action listeners
		this.vista.btnLogin.addActionListener(this);
		this.vista.btnMostrarRegistro.addActionListener(this);
		this.vista.btnRegistrarUsuario.addActionListener(this);
		this.vista.btnVolverALogin.addActionListener(this);
		this.vista.btnCarta1.addActionListener(this);
		this.vista.btnCarta2.addActionListener(this);
		this.vista.btnCarta3.addActionListener(this);
		this.vista.btnCarta4.addActionListener(this);
		this.vista.btnCarta5.addActionListener(this);
		this.vista.btnCarta6.addActionListener(this);
		this.vista.btnCarta7.addActionListener(this);
		this.vista.btnCarta8.addActionListener(this);
		this.vista.btnCarta9.addActionListener(this);
		this.vista.btnCarta10.addActionListener(this);
		this.vista.btnCarta11.addActionListener(this);
		this.vista.btnCarta12.addActionListener(this);
		this.vista.btnCarta13.addActionListener(this);
		this.vista.btnCarta14.addActionListener(this);
		this.vista.btnPickear.addActionListener(this);
		this.vista.listCartas.addListSelectionListener(this);
		this.vista.btnSeleccionarCartaParaMazo.addActionListener(this);
		this.vista.btnMazoConsultarCarta.addActionListener(this);
		this.vista.btnMazoBorrarCarta.addActionListener(this);
		this.vista.btnMazoFinalizarDraft.addActionListener(this);
		this.vista.btnVolverInfoCarta.addActionListener(this);
	}

	/*
	 * Metodo que controla los eventos de Vista
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == vista.btnLogin) {
			handleLogin();
		} else if (e.getSource() == vista.btnMostrarRegistro) {
			mostrarPanelRegistroUsuario();
		} else if (e.getSource() == vista.btnRegistrarUsuario) {
			handleCrearUsuario();
		} else if (e.getSource() == vista.btnVolverALogin) {
			mostrarPanelLogin();
		}

		if (e.getSource() == vista.btnCarta1) {
			ampliarCarta(sobre.getCartasSobre().get(0));
		}
		if (e.getSource() == vista.btnCarta2) {
			ampliarCarta(sobre.getCartasSobre().get(1));
		}
		if (e.getSource() == vista.btnCarta3) {
			ampliarCarta(sobre.getCartasSobre().get(2));
		}
		if (e.getSource() == vista.btnCarta4) {
			ampliarCarta(sobre.getCartasSobre().get(3));
		}
		if (e.getSource() == vista.btnCarta5) {
			ampliarCarta(sobre.getCartasSobre().get(4));
		}
		if (e.getSource() == vista.btnCarta6) {
			ampliarCarta(sobre.getCartasSobre().get(5));
		}
		if (e.getSource() == vista.btnCarta7) {
			ampliarCarta(sobre.getCartasSobre().get(6));
		}
		if (e.getSource() == vista.btnCarta8) {
			ampliarCarta(sobre.getCartasSobre().get(7));
		}
		if (e.getSource() == vista.btnCarta9) {
			ampliarCarta(sobre.getCartasSobre().get(8));
		}
		if (e.getSource() == vista.btnCarta10) {
			ampliarCarta(sobre.getCartasSobre().get(9));
		}
		if (e.getSource() == vista.btnCarta11) {
			ampliarCarta(sobre.getCartasSobre().get(10));
		}
		if (e.getSource() == vista.btnCarta12) {
			ampliarCarta(sobre.getCartasSobre().get(11));
		}
		if (e.getSource() == vista.btnCarta13) {
			ampliarCarta(sobre.getCartasSobre().get(12));
		}
		if (e.getSource() == vista.btnCarta14) {
			ampliarCarta(sobre.getCartasSobre().get(13));
		}

		if (e.getSource() == vista.btnPickear) {
			pickearCarta();
			vista.lblALSA.setText("");
			vista.lblATA.setText("");
			vista.lblWR.setText("");
		}

		if (e.getSource() == vista.btnSeleccionarCartaParaMazo) {
			anadirMazo();
		}

		if (e.getSource() == vista.btnMazoConsultarCarta) {
			consultarCartaMazo();
		}

		if (e.getSource() == vista.btnMazoBorrarCarta) {
			borrarMazo();
		}

		if (e.getSource() == vista.btnMazoFinalizarDraft) {
			finDraft();
		}

		if (e.getSource() == vista.btnVolverInfoCarta) {
			volverConstruccionMazo();
		}
	}

	/*
	 * Metodo que cambia las cartas de Mazo
	 */
	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == vista.listCartas) {

			if (vista.listCartas.getSelectedIndex() != -1) {
				cambiarImagenMazo();
			}
		}
	}

	/*
	 * Metodo para mostrar paneles de fase crecion de mazo
	 */
	private void volverConstruccionMazo() {
		vista.panelCreacionMazoInfoCarta.setVisible(false);
		vista.btnMazoConsultarCarta.setVisible(true);
		vista.btnMazoBorrarCarta.setVisible(true);
		vista.btnMazoFinalizarDraft.setVisible(true);
	}

	/*
	 * Metodo para consultar la informacion de la carta en fase construccion de mazo
	 * cuando esta aniadida a mazo de creacion
	 */
	private void consultarCartaMazo() {

		if (vista.listMazo.getSelectedIndex() != -1) {
			DefaultListModel<String> listModelMazo = (DefaultListModel<String>) vista.listMazo.getModel();
			String nombre = listModelMazo.get(vista.listMazo.getSelectedIndex());
			Carta cartaSeleccion = new Carta();
			for (Carta carta : jugadorUsuario.getMazoConstruido()) {

				if (nombre.equals(carta.getNombreEspaniol())) {
					cartaSeleccion = carta;
				}
			}

			vista.lblCartaMazoInfoCarta.setIcon(obtenerImagenCarta(cartaSeleccion));

			vista.lblAlsaCartaMazoInfoCarta.setText("ALSA: " + cartaSeleccion.getAlsa());
			vista.lblATACartaMazoInfoCarta.setText("ATA: " + cartaSeleccion.getAta());
			vista.lblWRCartaMazoInfoCarta.setText("WR: " + cartaSeleccion.getGpWr());

			vista.panelCreacionMazoInfoCarta.setVisible(true);

			vista.btnMazoConsultarCarta.setVisible(false);
			vista.btnMazoBorrarCarta.setVisible(false);
			vista.btnMazoFinalizarDraft.setVisible(false);
		}

	}

	/*
	 * Metodo para mostrar la informacion de carta en fase de creacion de mazo
	 * cuando la carta esta en mazo de draft
	 */
	private void cambiarImagenMazo() {
		DefaultListModel<String> listModelCartas = (DefaultListModel<String>) vista.listCartas.getModel();
		String nombre = listModelCartas.get(vista.listCartas.getSelectedIndex());
		Carta cartaSeleccion = new Carta();
		for (Carta carta : jugadorUsuario.getMazo()) {

			if (nombre.equals(carta.getNombreEspaniol())) {
				cartaSeleccion = carta;
			}
		}

		vista.lblCartaMazo.setIcon(obtenerImagenCarta(cartaSeleccion));

		vista.lblAlsaCartaMazo.setText("ALSA: " + cartaSeleccion.getAlsa());
		vista.lblATACartaMazo.setText("ATA: " + cartaSeleccion.getAta());
		vista.lblWRCartaMazo.setText("WR: " + cartaSeleccion.getGpWr());
	}

	/*
	 * Metodo de creacion de usuario en login
	 */
	private void handleCrearUsuario() {
		String nombreUsuario = vista.tfUsuarioRegistro.getText().trim(); // Eliminar espacios en blanco al principio y
																			// al final
		String contrasena = new String(vista.pfContrasenaRegistro.getPassword()).trim(); // Eliminar espacios en blanco
																							// al principio y al final

		if (nombreUsuario.isEmpty() || contrasena.isEmpty()) {
			javax.swing.JOptionPane.showMessageDialog(vista,
					"El nombre de usuario y la contraseña no pueden estar vacíos.");
		} else {
			if (usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario) != null) {
				javax.swing.JOptionPane.showMessageDialog(vista,
						"El nombre de usuario ya existe. Por favor, elija otro.");
			} else {
				login.crearNuevoUsuario(nombreUsuario, contrasena);
				usuarioActual = usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario);
				javax.swing.JOptionPane.showMessageDialog(vista, "Usuario creado exitosamente");
				mostrarPanelLogin();
			}
		}
	}

	/*
	 * Metodo de login
	 */
	private void handleLogin() {
		String nombreUsuario = vista.tfUsuarioLogin.getText();
		String contrasena = new String(vista.pfContrasenaLogin.getPassword());
		usuarioActual = login.autenticarUsuario(nombreUsuario, contrasena);

		if (usuarioActual == null) {
			int respuesta = javax.swing.JOptionPane.showConfirmDialog(vista,
					"Usuario no encontrado. ¿Deseas crear un nuevo usuario?");
			if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
				mostrarPanelRegistroUsuario();
			}
		} else {
			if (usuarioActual.getContrasenia().equals(contrasena)) {
				// Contrasenia correcta
				try {
					cargarBBDD("ltr");
					coleccionUso = procesarColeccion.getColeccion();
					vista.jPanelDraft.setVisible(true);
					vista.panelEsperaCargaColeccion.setVisible(false);
					iniciarDraft();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				// Contraseniaa incorrecta
				javax.swing.JOptionPane.showMessageDialog(vista,
						"Contraseña incorrecta para el usuario " + nombreUsuario);
			}
		}
	}

	/*
	 * Muestra panel de registro de usuario
	 */
	private void mostrarPanelRegistroUsuario() {
		vista.jPanelRegistroUsuario.setVisible(true);

		vista.jPanelLogin.setVisible(false);
		vista.tfUsuarioLogin.setText("");
		vista.pfContrasenaLogin.setText("");
	}

	/*
	 * Muestra panel de login
	 */
	private void mostrarPanelLogin() {
		vista.jPanelRegistroUsuario.setVisible(false);
		vista.jPanelLogin.setVisible(true);
		vista.tfUsuarioRegistro.setText("");
		vista.pfContrasenaRegistro.setText("");
	}

	/*
	 * Muestra panel de espera mientras se carga la coleccion
	 */
	private void mostrarEspera() {
		ImageIcon imagen = new ImageIcon("resources/esperando.png");
		Image imagenEscalada = imagen.getImage().getScaledInstance(vista.lblEspera.getWidth(),
				vista.lblEspera.getHeight(), Image.SCALE_DEFAULT);
		ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
		vista.lblEspera.setIcon(iconoEscalado);
		vista.panelEsperaCargaColeccion.setVisible(true);
	}

	/*
	 * Mensaje y cambio de paneles si el login es exitoso
	 */
	private void loginCorrecto() {
		javax.swing.JOptionPane.showMessageDialog(vista, "Login exitoso");
		// Proceso de carga de bbdd si no hay datos y pantalla de carga
		vista.jPanelRegistroUsuario.setVisible(false);
		vista.jPanelLogin.setVisible(false);
	}

	/*
	 * Carga de BBDD de los datos de cartas y coleccion. Comprueba promiero si
	 * existe coleccion
	 */
	private void cargarBBDD(String nombreColeccion) throws InterruptedException {
		List<Coleccion> colecciones = coleccionDAO.selectColeccion(nombreColeccion);
		boolean existeColeccion = false;

		for (Coleccion coleccion : colecciones) {
			if (coleccion.getNombre().equals(nombreColeccion)) {
				existeColeccion = true;
				coleccionUso = coleccion;
			}
		}

		if (!existeColeccion) {
			System.out.println("Iniciamos carga de coleccion");
			procesarColeccion = new ProcesadorColeccion("card-ratings-2024-05-12.csv", nombreColeccion);
			mostrarEspera();
			vista.jPanelRegistroUsuario.setVisible(false);
			vista.jPanelLogin.setVisible(false);
			javax.swing.JOptionPane.showMessageDialog(vista, "Cargando coleccion");

			procesarColeccion.run();
			// procesarColeccion.join();

		} else {
			loginCorrecto();
			System.out.println("Coleccion ya existente en BBDD");
		}
	}

	/*
	 * Metodo para iniciar fase de draft
	 */
	private void iniciarDraft() {

		ArrayList<Carta> todasLasCartas = obtenerTodasLasCartas(); // Metodo para obtener todas las cartas de la
																	// coleccion
		jugadorUsuario = new JugadorUsuario(usuarioActual);

		draft = new Draft(todasLasCartas, jugadorUsuario);

		// Mostrar sobres del jugador usuario
		ArrayList<Sobre> sobresJugadorUsuario = jugadorUsuario.getSobres();
		for (int i = 0; i < sobresJugadorUsuario.size(); i++) {
			Sobre sobre = sobresJugadorUsuario.get(i);
			System.out.println("Sobre número: " + sobre.getNumSobre());
			ArrayList<Carta> cartasSobre = sobre.getCartasSobre();
			for (int j = 0; j < cartasSobre.size(); j++) {
				Carta carta = cartasSobre.get(j);
				System.out.println("Carta: " + carta.getNombreOriginal() + " Rareza: " + carta.getRareza());
			}
		}

		// Mostrar sobres de cada jugador bot
		ArrayList<JugadorBot> jugadoresBot = draft.getJugadoresBot();
		for (int i = 0; i < jugadoresBot.size(); i++) {
			JugadorBot jugadorBot = jugadoresBot.get(i);
			System.out.println("Sobres del Jugador Bot " + (i + 1) + ":");
			ArrayList<Sobre> sobresJugadorBot = jugadorBot.getSobres();
			for (int j = 0; j < sobresJugadorBot.size(); j++) {
				Sobre sobre = sobresJugadorBot.get(j);
				System.out.println("Sobre número: " + sobre.getNumSobre());
				ArrayList<Carta> cartasSobre = sobre.getCartasSobre();
				for (int k = 0; k < cartasSobre.size(); k++) {
					Carta carta = cartasSobre.get(k);
					System.out.println("Carta: " + carta.getNombreOriginal() + " Rareza: " + carta.getRareza());
				}
			}
		}
		draft.annadirSobresRonda();
		sobre = jugadorUsuario.getSobres().get(draft.getRonda() - 1);
		abrirSobre(sobre);
	}

	/*
	 * Metodo que se encarga de abrir los sobres de la fase de draft y de la gestion
	 * de los botones de las cartas
	 */
	private void abrirSobre(Sobre sobre) {
		vista.btnCarta1.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(0)));
		if (sobre.getCartasSobre().size() > 1) {
			vista.btnCarta2.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(1)));
			vista.btnCarta2.setEnabled(true);
		} else {
			vista.btnCarta2.setEnabled(false);
			vista.btnCarta2.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 2) {
			vista.btnCarta3.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(2)));
			vista.btnCarta3.setEnabled(true);
		} else {
			vista.btnCarta3.setEnabled(false);
			vista.btnCarta3.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 3) {
			vista.btnCarta4.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(3)));
			vista.btnCarta4.setEnabled(true);
		} else {
			vista.btnCarta4.setEnabled(false);
			vista.btnCarta4.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 4) {
			vista.btnCarta5.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(4)));
			vista.btnCarta5.setEnabled(true);
		} else {
			vista.btnCarta5.setEnabled(false);
			vista.btnCarta5.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 5) {
			vista.btnCarta6.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(5)));
			vista.btnCarta6.setEnabled(true);
		} else {
			vista.btnCarta6.setEnabled(false);
			vista.btnCarta6.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 6) {
			vista.btnCarta7.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(6)));
			vista.btnCarta7.setEnabled(true);
		} else {
			vista.btnCarta7.setEnabled(false);
			vista.btnCarta7.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 7) {
			vista.btnCarta8.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(7)));
			vista.btnCarta8.setEnabled(true);
		} else {
			vista.btnCarta8.setEnabled(false);
			vista.btnCarta8.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 8) {
			vista.btnCarta9.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(8)));
			vista.btnCarta9.setEnabled(true);
		} else {
			vista.btnCarta9.setEnabled(false);
			vista.btnCarta9.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 9) {
			vista.btnCarta10.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(9)));
			vista.btnCarta10.setEnabled(true);
		} else {
			vista.btnCarta10.setEnabled(false);
			vista.btnCarta10.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 10) {
			vista.btnCarta11.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(10)));
			vista.btnCarta11.setEnabled(true);
		} else {
			vista.btnCarta11.setEnabled(false);
			vista.btnCarta11.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 11) {
			vista.btnCarta12.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(11)));
			vista.btnCarta12.setEnabled(true);
		} else {
			vista.btnCarta12.setEnabled(false);
			vista.btnCarta12.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 12) {
			vista.btnCarta13.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(12)));
			vista.btnCarta13.setEnabled(true);
		} else {
			vista.btnCarta13.setEnabled(false);
			vista.btnCarta13.setIcon(null);
		}
		if (sobre.getCartasSobre().size() > 13) {
			vista.btnCarta14.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(13)));
			vista.btnCarta14.setEnabled(true);
		} else {
			vista.btnCarta14.setEnabled(false);
			vista.btnCarta14.setIcon(null);
		}

		vista.lblSobre.setText("SOBRE:  " + draft.getRonda());

	}

	/*
	 * Metodo utulizado para pintar la imagend de las cartas y ponerlas a la escala
	 * del componente
	 */
	private Icon obtenerImagenCarta(Carta carta) {
		// ImageIcon imagen = new
		// ImageIcon("ltr_PNG_cartas/"+carta.getNombreOriginal()+".png");
		// Icon icon = new
		// ImageIcon(imagen.getImage().getScaledInstance(vista.btnCarta1.getWidth(),
		// vista.btnCarta1.getHeight(), Image.SCALE_DEFAULT));
		ImageIcon imagen = new ImageIcon("ltr_PNG_cartas/" + carta.getNombreOriginal() + ".png");
		Image imagenEscalada = imagen.getImage().getScaledInstance(vista.btnCarta1.getWidth(),
				vista.btnCarta1.getHeight(), Image.SCALE_SMOOTH);
		Icon icono = new ImageIcon(imagenEscalada);
		return icono;
	}

	/*
	 * Metodo que se encarga de ampliar la carta en la fase de draft
	 */
	private void ampliarCarta(Carta carta) {
		ImageIcon imagen = new ImageIcon("ltr_PNG_cartas/" + carta.getNombreOriginal() + ".png");
		Icon icon = new ImageIcon(imagen.getImage().getScaledInstance(vista.lblCartaSeleccionada.getWidth(),
				vista.lblCartaSeleccionada.getHeight(), Image.SCALE_DEFAULT));
		vista.lblCartaSeleccionada.setIcon(icon);

		vista.lblALSA.setText("ALSA: " + carta.getAlsa());
		vista.lblATA.setText("ATA: " + carta.getAta());
		vista.lblWR.setText("WR: " + carta.getGpWr());

		vista.btnPickear.setEnabled(true);

		cartaSeleccionada = carta;

	}

	/*
	 * Metodo que se encarga del pickeo de la carta en fase de draft y del cambio de
	 * ronda comprobando fin de sobre o fin de draft
	 */
	private void pickearCarta() {

		jugadorUsuario.getMazo().add(cartaSeleccionada);
		sobre.eliminarCarta(cartaSeleccionada);
		vista.lblCartaSeleccionada.setIcon(null);
		vista.lblPickeos.setText("TOTAL PICKEOS: " + jugadorUsuario.getMazo().size());

		for (int i = 0; i < draft.getSobresRonda().size(); i++) {
			if (seleccion != i) {
				pickeoBot(i);
			}
		}
		if (seleccion >= 7) {
			this.seleccion = 0;
		} else {
			this.seleccion++;
		}

		if (jugadorUsuario.getSobres().get(draft.getRonda() - 1).getCartasSobre().size() == 0) {
			this.seleccion = 0;
			draft.setRonda(draft.getRonda() + 1);
			if (draft.getRonda() > 3) {
				javax.swing.JOptionPane.showMessageDialog(vista,
						"Draft terminado. Accediendo a construcción de mazos.");
				construccionMazo();
			} else {
				javax.swing.JOptionPane.showMessageDialog(vista, "Ronda terminada. Abriendo siguiente sobre.");
				draft.annadirSobresRonda();
				sobre = jugadorUsuario.getSobres().get(draft.getRonda() - 1);
				abrirSobre(sobre);
			}

		} else {
			sobre = draft.getSobresRonda().get(seleccion);
			abrirSobre(sobre);
		}

		vista.btnPickear.setEnabled(false);

	}

	/*
	 * Metodo de contruccion de mazo de draft y de mazo de creacion que aniade las
	 * cartas a los componentes list que se monstraran en la fase de creacion de
	 * mazo
	 */
	private void construccionMazo() {
		DefaultListModel<String> listModelCartas = (DefaultListModel<String>) vista.listCartas.getModel();
		DefaultListModel<String> listModelMazo = (DefaultListModel<String>) vista.listMazo.getModel();
		for (Carta carta : jugadorUsuario.getMazo()) {
			listModelCartas.addElement(carta.getNombreEspaniol());
		}

		vista.listMazo.setModel(listModelMazo);

		vista.panelCreacionMazo.setVisible(true);
		vista.jPanelDraft.setVisible(false);

	}

	/*
	 * Metodo que se encarga de la gestion de los pickeos de los bots
	 */
	private void pickeoBot(int index) {

		Carta cartaSeleccionada = new Carta();

		for (Carta carta : draft.getSobresRonda().get(index).getCartasSobre()) {

			if (cartaSeleccionada.getGpWr() == null) {
				cartaSeleccionada = carta;
			} else {
				if (carta.getGpWr().longValue() > cartaSeleccionada.getGpWr().longValue()) {
					cartaSeleccionada = carta;
				}
			}
		}
		draft.getSobresRonda().get(index).getCartasSobre().remove(cartaSeleccionada);
	}

	/*
	 * Metodo para obtener todas las cartaas de la coleccion de BBDD
	 */
	private ArrayList<Carta> obtenerTodasLasCartas() {
		ArrayList<Carta> cartasColeccion = coleccionDAO.obtenerCartasColeccion("ltr");
		return cartasColeccion;
	}

	/*
	 * Metodo para aniadir carta del mazo de draft a mazo de creacion y gestionar su
	 * cambio de un componente list a otro
	 */
	private void anadirMazo() {
		String nombre = vista.listModelCartas.get(vista.listCartas.getSelectedIndex());
		Carta cartaSeleccionada = new Carta();

		for (Carta carta : jugadorUsuario.getMazo()) {

			if (nombre.equals(carta.getNombreEspaniol())) {
				cartaSeleccionada = carta;
			}
		}

		jugadorUsuario.getMazoConstruido().add(cartaSeleccionada);
		jugadorUsuario.getMazo().remove(cartaSeleccionada);

		DefaultListModel<String> listModelCartas = new DefaultListModel<String>();

		for (Carta carta : jugadorUsuario.getMazo()) {
			listModelCartas.addElement(carta.getNombreEspaniol());

		}

		vista.listModelCartas = listModelCartas;
		vista.listCartas.setModel(listModelCartas);

		vista.listCartas.setSelectedIndex(0);

		DefaultListModel<String> listModelMazo = new DefaultListModel<String>();

		for (Carta carta : jugadorUsuario.getMazoConstruido()) {
			listModelMazo.addElement(carta.getNombreEspaniol());
		}

		vista.listModelMazo = listModelMazo;
		vista.listMazo.setModel(listModelMazo);

		vista.lblTotalCartasMazo.setText("TOTAL: " + jugadorUsuario.getMazoConstruido().size());
		vista.lblCurvaMana.setText("CURVA DE MANA: " + obtenerCurvaMana());

		actualizarColores();
		actualizarTipos();
	}

	/*
	 * Metodo que borra la carta del mazo de creacion y la lleva al mazo de draft
	 */
	private void borrarMazo() {
		String nombre = vista.listModelMazo.get(vista.listMazo.getSelectedIndex());
		Carta cartaSeleccionada = new Carta();

		for (Carta carta : jugadorUsuario.getMazoConstruido()) {

			if (nombre.equals(carta.getNombreEspaniol())) {
				cartaSeleccionada = carta;
			}
		}

		jugadorUsuario.getMazo().add(cartaSeleccionada);
		jugadorUsuario.getMazoConstruido().remove(cartaSeleccionada);

		DefaultListModel<String> listModelMazo = new DefaultListModel<String>();

		for (Carta carta : jugadorUsuario.getMazoConstruido()) {
			listModelMazo.addElement(carta.getNombreEspaniol());

		}

		vista.listModelMazo = listModelMazo;
		vista.listMazo.setModel(listModelMazo);

		vista.listCartas.setSelectedIndex(0);

		DefaultListModel<String> listModelCartas = new DefaultListModel<String>();

		for (Carta carta : jugadorUsuario.getMazo()) {
			listModelCartas.addElement(carta.getNombreEspaniol());
		}

		vista.listModelCartas = listModelCartas;
		vista.listCartas.setModel(listModelCartas);

		vista.lblTotalCartasMazo.setText("TOTAL: " + jugadorUsuario.getMazoConstruido().size());
		vista.lblCurvaMana.setText("CURVA DE MANA: " + obtenerCurvaMana());
		//Actualizar la informacion de mazo
		actualizarColores();
		actualizarTipos();
	}
	/*
	 * Metodo que se encarga de actualizar los colores de las cartas en la informacion mostrada en la fase de creacion
	 */
	private void actualizarColores() {
		String[] columnNames = { "COLOR", "VALOR" };
		DefaultTableModel coloresModel = new DefaultTableModel(columnNames, 0);

		int blackColor = 0;
		int whiteColor = 0;
		int redColor = 0;
		int blueColor = 0;
		int greenColor = 0;
		int multicolor = 0;
		int colorless = 0;

		for (Carta carta : jugadorUsuario.getMazoConstruido()) {

			switch (carta.getColor()) {
			case "W":
				whiteColor++;
				break;
			case "U":
				blueColor++;
				break;
			case "B":
				blackColor++;
				break;
			case "R":
				redColor++;
				break;
			case "G":
				greenColor++;
				break;
			case "COLORLESS":
				colorless++;
				break;
			default:
				multicolor++;
				break;
			}
		}
		coloresModel.addRow(new Object[] { "NEGRO", Integer.toString(blackColor) });
		coloresModel.addRow(new Object[] { "BLANCO", Integer.toString(whiteColor) });
		coloresModel.addRow(new Object[] { "ROJO", Integer.toString(redColor) });
		coloresModel.addRow(new Object[] { "AZUL", Integer.toString(blueColor) });
		coloresModel.addRow(new Object[] { "VERDE", Integer.toString(greenColor) });
		coloresModel.addRow(new Object[] { "MULTICOLOR", Integer.toString(multicolor) });
		coloresModel.addRow(new Object[] { "SIN COLOR", Integer.toString(colorless) });

		vista.tableColor.setModel(coloresModel);
	}
	/*
	 * Metodo que se encarga de actualizar la informacion de tipos de carta de creacion de mazo 
	 */
	private void actualizarTipos() {
		String[] columnNames = { "TIPO", "CANTIDAD" };
		DefaultTableModel tiposModel = new DefaultTableModel(columnNames, 0);

		int criaturas = 0;
		int artefactos = 0;
		int instantaneos = 0;
		int tierras = 0;

		for (Carta carta : jugadorUsuario.getMazoConstruido()) {

			switch (carta.getTipo()) {
			case "Creature":
				criaturas++;
				break;
			case "Instant":
				instantaneos++;
				break;
			case "Artifact":
				artefactos++;
				break;
			default:
				tierras++;
				break;
			}
		}
		tiposModel.addRow(new Object[] { "CRIATURAS", Integer.toString(criaturas) });
		tiposModel.addRow(new Object[] { "ARTEFACTOS", Integer.toString(artefactos) });
		tiposModel.addRow(new Object[] { "INSTANTANEOS", Integer.toString(instantaneos) });

		vista.tableType.setModel(tiposModel);
	}
	/*
	 * Metodoq ue se encarga de obtener la curva de mana del mazo creado
	 */
	private String obtenerCurvaMana() {

		double curvaMana = 0;

		for (Carta carta : jugadorUsuario.getMazoConstruido()) {
			curvaMana += carta.getCoste();
		}

		double curvaManaAux = curvaMana / jugadorUsuario.getMazoConstruido().size();
		int aux = (int) (curvaManaAux * 100);
		curvaMana = aux / 100.0;

		return Double.toString(curvaMana);

	}
	/*
	 * metodo que gestiona el final de la cracion de mazo
	 */
	private void finDraft() {
		int respuesta = javax.swing.JOptionPane.showConfirmDialog(vista, "Va a finalizar el draft. ¿Esta seguro?");
		if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
			// iniciar insert de mazo y actualizacion usuario
			// usuario
			usuarioDAO.updateUsuario(usuarioActual);
			// mazo
			Mazo mazoInsert = prepararInsertMazo();
			MazoDAO mazoDao = new MazoDAO();
			mazoDao.insertMazo(mazoInsert);
			// reseteamos la informacion
			vista.jPanelDraft.setVisible(true);
			vista.panelCreacionMazo.setVisible(false);
			ArrayList<Carta> resetMazo = new ArrayList<Carta>();
			jugadorUsuario.setMazo(resetMazo);
			jugadorUsuario.setMazoConstruido(resetMazo);

			DefaultListModel<String> listModelReset = new DefaultListModel<String>();

			// resetear componentes para volver a hacer draft tras este
			vista.lblPickeos.setText("");
			vista.listModelCartas = listModelReset;
			vista.listModelMazo = listModelReset;
			// reiniciar draft
			iniciarDraft();
		}
	}
	/*
	 * Metodo para preparar el objeto del insert del mazo creado 
	 */
	private Mazo prepararInsertMazo() {
		Mazo mazoInsert = new Mazo();
		BigDecimal winrateTotal = BigDecimal.ZERO; // Inicializar en 0
		int w = 0;
		int r = 0;
		int b = 0;
		int u = 0;
		int g = 0;
		int multiColor = 0;
		int colorLess = 0;
		int criaturas = 0;
		int artefactos = 0;
		int instantaneos = 0;
		int comunes = 0;
		int infrecuentes = 0;
		int raras = 0;
		int legendarias = 0;

		// sumar todos los gpWr
		for (int i = 0; i < jugadorUsuario.getMazoConstruido().size(); i++) {
			winrateTotal = winrateTotal.add(jugadorUsuario.getMazoConstruido().get(i).getGpWr());
			if (jugadorUsuario.getMazoConstruido().get(i).getColor().equalsIgnoreCase("B")) {
				b++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getColor().equalsIgnoreCase("W")) {
				w++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getColor().equalsIgnoreCase("U")) {
				u++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getColor().equalsIgnoreCase("G")) {
				g++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getColor().equalsIgnoreCase("R")) {
				r++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getColor().equalsIgnoreCase("COLORLESS")) {
				colorLess++;
			} else {
				multiColor++;
			}
			if (jugadorUsuario.getMazoConstruido().get(i).getTipo().equalsIgnoreCase("Creature")) {
				criaturas++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getTipo().equalsIgnoreCase("Artifact")) {
				artefactos++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getTipo().equalsIgnoreCase("Instant")) {
				instantaneos++;
			}

			if (jugadorUsuario.getMazoConstruido().get(i).getRareza().equalsIgnoreCase("common")) {
				comunes++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getRareza().equalsIgnoreCase("uncommon")) {
				infrecuentes++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getRareza().equalsIgnoreCase("rare")) {
				raras++;
			} else if (jugadorUsuario.getMazoConstruido().get(i).getRareza().equalsIgnoreCase("mythic")) {
				legendarias++;
			}

		}

		// calcular el winrate media
		BigDecimal winratePromedio = winrateTotal.divide(BigDecimal.valueOf(jugadorUsuario.getMazoConstruido().size()),
				2, RoundingMode.HALF_UP);
		// seteas mazoInsert
		mazoInsert.setUsuario(usuarioActual);
		mazoInsert.setFechaCreacion(new Timestamp(System.currentTimeMillis()));
		mazoInsert.setCantidadCartas(jugadorUsuario.getMazoConstruido().size());
		mazoInsert.setWinrate(winratePromedio);
		mazoInsert.setCurvaMana(new BigDecimal(obtenerCurvaMana()));
		mazoInsert.setColorB(b);
		mazoInsert.setColorW(w);
		mazoInsert.setColorU(u);
		mazoInsert.setColorR(r);
		mazoInsert.setColorG(g);
		mazoInsert.setColorless(colorLess);
		mazoInsert.setMultiColor(multiColor);
		mazoInsert.setArtefactos(artefactos);
		mazoInsert.setCriaturas(criaturas);
		mazoInsert.setInstantaneos(instantaneos);
		mazoInsert.setComunes(comunes);
		mazoInsert.setInfrecuentes(infrecuentes);
		mazoInsert.setRaras(raras);
		mazoInsert.setLegendarias(legendarias);
		mazoInsert.setColeccion(coleccionUso);
		return mazoInsert;

	}

}
