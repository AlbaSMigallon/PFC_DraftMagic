package vista;

import javax.swing.*;

import controlador.Controlador;

import java.awt.*;

public class Vista extends JFrame {
	public static final long serialVersionUID = 1L;
	public JPanel contentPane;
	public JPanel jPanelLogin, panelEsperaCargaColeccion, panelCreacionMazo, panelCreacionMazoInfoCarta,
			jPanelRegistroUsuario, jPanelDraft;

	public JTextField tfUsuarioLogin;
	public JPasswordField pfContrasenaLogin;
	//
	public JTextField tfUsuarioRegistro;
	public JPasswordField pfContrasenaRegistro;
	public JButton btnRegistrarUsuario;
	public JButton btnVolverALogin;
	public JButton btnIniciarDraft;
	//
	public JButton btnLogin, btnMostrarRegistro, btnSeleccionarCartaParaMazo, btnMazoBorrarCarta, btnMazoConsultarCarta,
			btnMazoFinalizarDraft;
	public JButton btnCarta1, btnCarta2, btnCarta3, btnCarta4, btnCarta5, btnCarta6, btnCarta7, btnCarta8, btnCarta9,
			btnCarta10, btnCarta11, btnCarta12, btnCarta13, btnCarta14, btnPickear, btnVolverInfoCarta;
	public JLabel lblCartaSeleccionada, lblSobre, lblPickeos, lblATA, lblALSA, lblWR, lblEspera, lblEsperaRetro,
			lblCartaMazo, lblAlsaCartaMazo, lblATACartaMazo, lblWRCartaMazo, lblCartaMazoInfoCarta,
			lblAlsaCartaMazoInfoCarta, lblATACartaMazoInfoCarta, lblWRCartaMazoInfoCarta, jLabelLogo, lblUsuarioLogin,
			lblContrasenaLogin, lblUsuarioRegistro, lblContrasenaRegistro, lblTotalCartasMazo, lblCurvaMana, lblType;
	public JList listCartas, listMazo;
	public DefaultListModel<String> listModelCartas, listModelMazo;
	public JScrollPane listScrollCartas, listScrollMazo;

	public JTable tableColor, tableCurvaMana, tableType;
	private JLabel lblColor;
	private JLabel lblSeparador;
	private JLabel lblTitulo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					Controlador controlador = new Controlador(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Vista() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width, screenSize.height);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// LOGIN Y REGISTRO DE USUARIO

		jPanelLogin = new JPanel();
		jPanelLogin.setBounds(0, 0, screenSize.width, screenSize.height);
		jPanelLogin.setLayout(null);
		jPanelLogin.setVisible(true);
		contentPane.add(jPanelLogin);

		jPanelRegistroUsuario = new JPanel();
		jPanelRegistroUsuario.setBounds(0, 0, screenSize.width, screenSize.height);
		jPanelRegistroUsuario.setLayout(null);
		jPanelRegistroUsuario.setVisible(false);
		contentPane.add(jPanelRegistroUsuario);

		ImageIcon loginBackgroundIcon = new ImageIcon("resources/fondo.png");
		Image loginBackgroundImg = loginBackgroundIcon.getImage().getScaledInstance(screenSize.width, screenSize.height,
				Image.SCALE_SMOOTH);
		loginBackgroundIcon = new ImageIcon(loginBackgroundImg);
		JLabel loginBackgroundLabel = new JLabel(loginBackgroundIcon);
		loginBackgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);
		jPanelLogin.add(loginBackgroundLabel);

		ImageIcon registerBackgroundIcon = new ImageIcon("resources/fondo.png");
		Image registerBackgroundImg = registerBackgroundIcon.getImage().getScaledInstance(screenSize.width,
				screenSize.height, Image.SCALE_SMOOTH);
		registerBackgroundIcon = new ImageIcon(registerBackgroundImg);
		JLabel registerBackgroundLabel = new JLabel(registerBackgroundIcon);
		registerBackgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);
		jPanelRegistroUsuario.add(registerBackgroundLabel);

		ImageIcon logoConclaveIcon = new ImageIcon("resources/logo.png");
		Image newimg = logoConclaveIcon.getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
		logoConclaveIcon = new ImageIcon(newimg);
		
		lblTitulo = new JLabel("DRAFT MAGIC");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Cambria Math", Font.BOLD, 40));
		lblTitulo.setBounds(761, 411, 400, 79);
		jPanelLogin.add(lblTitulo);

		jLabelLogo = new JLabel(logoConclaveIcon);
		jLabelLogo.setBounds(761, 94, 400, 400);
		jPanelLogin.add(jLabelLogo);

		lblUsuarioLogin = new JLabel("Usuario:");
		lblUsuarioLogin.setFont(new Font("Cambria Math", Font.BOLD, 20));
		lblUsuarioLogin.setBounds(761, 535, 400, 25);
		jPanelLogin.add(lblUsuarioLogin);

		tfUsuarioLogin = new JTextField();
		tfUsuarioLogin.setFont(new Font("Cambria Math", Font.PLAIN, 20));
		tfUsuarioLogin.setBounds(761, 565, 400, 30);
		jPanelLogin.add(tfUsuarioLogin);

		lblContrasenaLogin = new JLabel("Contraseña:");
		lblContrasenaLogin.setFont(new Font("Cambria Math", Font.BOLD, 20));
		lblContrasenaLogin.setBounds(761, 605, 400, 25);
		jPanelLogin.add(lblContrasenaLogin);

		pfContrasenaLogin = new JPasswordField();
		pfContrasenaLogin.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pfContrasenaLogin.setBounds(761, 635, 400, 30);
		jPanelLogin.add(pfContrasenaLogin);

		btnLogin = new JButton("Iniciar Sesión");
		btnLogin.setFont(new Font("Cambria Math", Font.BOLD, 20));
		btnLogin.setBounds(761, 685, 400, 40);
		jPanelLogin.add(btnLogin);

		btnMostrarRegistro = new JButton("¿No tienes una cuenta? ¡Regístrate aquí!");
		btnMostrarRegistro.setFont(new Font("Cambria Math", Font.BOLD, 20));
		btnMostrarRegistro.setBounds(761, 736, 400, 40);
		jPanelLogin.add(btnMostrarRegistro);

	
		lblUsuarioRegistro = new JLabel("Indique el nombre de usuario:");
		lblUsuarioRegistro.setBounds((screenSize.width - 400) / 2, 300, 400, 25);
		lblUsuarioRegistro.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelRegistroUsuario.add(lblUsuarioRegistro);

		tfUsuarioRegistro = new JTextField();
		tfUsuarioRegistro.setBounds((screenSize.width - 400) / 2, 330, 400, 30);
		tfUsuarioRegistro.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelRegistroUsuario.add(tfUsuarioRegistro);

		lblContrasenaRegistro = new JLabel("Introduzca la contraseña:");
		lblContrasenaRegistro.setBounds((screenSize.width - 400) / 2, 370, 400, 25);
		lblContrasenaRegistro.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelRegistroUsuario.add(lblContrasenaRegistro);

		pfContrasenaRegistro = new JPasswordField();
		pfContrasenaRegistro.setBounds((screenSize.width - 400) / 2, 400, 400, 30);
		pfContrasenaRegistro.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelRegistroUsuario.add(pfContrasenaRegistro);

		btnRegistrarUsuario = new JButton("Registrar Usuario");
		btnRegistrarUsuario.setBounds((screenSize.width - 400) / 2, 450, 400, 40);
		btnRegistrarUsuario.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelRegistroUsuario.add(btnRegistrarUsuario);
		;
		btnVolverALogin = new JButton("Volver al inicio de sesión");
		btnVolverALogin.setBounds((screenSize.width - 400) / 2, 500, 400, 40);
		btnVolverALogin.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelRegistroUsuario.add(btnVolverALogin);

		if (btnVolverALogin != null) {
			btnVolverALogin.addActionListener(e -> {

				jPanelRegistroUsuario.setVisible(false);
				jPanelLogin.setVisible(true);
			});
		}

		jPanelLogin.setComponentZOrder(loginBackgroundLabel, jPanelLogin.getComponentCount() - 1);
		jPanelRegistroUsuario.setComponentZOrder(registerBackgroundLabel,
				jPanelRegistroUsuario.getComponentCount() - 1);

		setVisible(true);

		// ESPERA CARGA COLECCION
		panelEsperaCargaColeccion = new JPanel();
		panelEsperaCargaColeccion.setBounds(0, 0, screenSize.width, screenSize.height);
		panelEsperaCargaColeccion.setLayout(null);
		panelEsperaCargaColeccion.setBackground(Color.WHITE); 
		panelEsperaCargaColeccion.setVisible(false); 
		contentPane.add(panelEsperaCargaColeccion);

		
		ImageIcon esperaIcon = new ImageIcon("resources/esperando.png");
		Image imagenEspera = esperaIcon.getImage().getScaledInstance(esperaIcon.getIconWidth() / 2,
				esperaIcon.getIconHeight() / 2, Image.SCALE_DEFAULT);
		ImageIcon esperaEscalada = new ImageIcon(imagenEspera);
		lblEspera = new JLabel(esperaEscalada);
		lblEspera.setBounds((screenSize.width - esperaEscalada.getIconWidth()) / 2,
				(screenSize.height - esperaEscalada.getIconHeight()) / 2, esperaEscalada.getIconWidth(),
				esperaEscalada.getIconHeight());
		panelEsperaCargaColeccion.add(lblEspera);

		
		lblEsperaRetro = new JLabel("Cargando colección. Espere.");
		lblEsperaRetro.setFont(new Font("Cambria Math", Font.BOLD, 20));
		lblEsperaRetro.setBounds((screenSize.width - 300) / 2, (screenSize.height + esperaEscalada.getIconHeight()) / 2,
				300, 30);
		lblEsperaRetro.setHorizontalAlignment(SwingConstants.CENTER); 
		panelEsperaCargaColeccion.add(lblEsperaRetro);

		// DRAFT
		jPanelDraft = new JPanel();
		jPanelDraft.setBounds(0, 0, screenSize.width, screenSize.height);
		jPanelDraft.setLayout(null);
		jPanelDraft.setVisible(false);
		contentPane.add(jPanelDraft);

		btnCarta1 = new JButton("");
		btnCarta1.setBounds(77, 33, 190, 265);
		jPanelDraft.add(btnCarta1);

		btnCarta2 = new JButton("");
		btnCarta2.setBounds(334, 33, 190, 265);
		jPanelDraft.add(btnCarta2);

		btnCarta3 = new JButton("");
		btnCarta3.setBounds(591, 33, 190, 265);
		jPanelDraft.add(btnCarta3);

		btnCarta4 = new JButton("");
		btnCarta4.setBounds(842, 33, 190, 265);
		jPanelDraft.add(btnCarta4);

		btnCarta5 = new JButton("");
		btnCarta5.setBounds(1096, 33, 190, 265);
		jPanelDraft.add(btnCarta5);

		btnCarta6 = new JButton("");
		btnCarta6.setBounds(1353, 33, 190, 265);
		jPanelDraft.add(btnCarta6);

		btnCarta7 = new JButton("");
		btnCarta7.setBounds(1611, 33, 190, 265);
		jPanelDraft.add(btnCarta7);

		btnCarta8 = new JButton("");
		btnCarta8.setBounds(77, 330, 190, 265);
		jPanelDraft.add(btnCarta8);

		btnCarta9 = new JButton("");
		btnCarta9.setBounds(334, 330, 190, 265);
		jPanelDraft.add(btnCarta9);

		btnCarta10 = new JButton("");
		btnCarta10.setBounds(591, 330, 190, 265);
		jPanelDraft.add(btnCarta10);

		btnCarta11 = new JButton("");
		btnCarta11.setBounds(842, 330, 190, 265);
		jPanelDraft.add(btnCarta11);

		btnCarta12 = new JButton("");
		btnCarta12.setBounds(1096, 330, 190, 265);
		jPanelDraft.add(btnCarta12);

		btnCarta13 = new JButton("");
		btnCarta13.setBounds(1353, 330, 190, 265);
		jPanelDraft.add(btnCarta13);

		btnCarta14 = new JButton("");
		btnCarta14.setBounds(1611, 330, 190, 265);
		jPanelDraft.add(btnCarta14);

		lblCartaSeleccionada = new JLabel("");
		lblCartaSeleccionada.setBounds(591, 632, 285, 390);
		jPanelDraft.add(lblCartaSeleccionada);

		lblALSA = new JLabel("");
		lblALSA.setBounds(937, 792, 285, 48);
		lblALSA.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelDraft.add(lblALSA);

		lblATA = new JLabel("");
		lblATA.setBounds(937, 710, 285, 48);
		lblATA.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelDraft.add(lblATA);

		lblWR = new JLabel("");
		lblWR.setBounds(937, 863, 285, 48);
		lblWR.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelDraft.add(lblWR);

		lblSobre = new JLabel("");
		lblSobre.setBounds(77, 915, 285, 48);
		lblSobre.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelDraft.add(lblSobre);

		lblPickeos = new JLabel("TOTAL PICKEOS: ");
		lblPickeos.setBounds(77, 974, 285, 48);
		lblPickeos.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelDraft.add(lblPickeos);

		btnPickear = new JButton("PICKEAR CARTA");
		btnPickear.setBounds(937, 982, 200, 40);
		btnPickear.setFont(new Font("Cambria Math", Font.BOLD, 20));
		jPanelDraft.add(btnPickear);

		Color whiteColor = Color.WHITE;
		jPanelDraft.setBackground(whiteColor);

		// CREACION DE MAZO - INFORMACION CARTA

		panelCreacionMazoInfoCarta = new JPanel();
		panelCreacionMazoInfoCarta.setBounds(1067, 456, 851, 599);
		contentPane.add(panelCreacionMazoInfoCarta);
		panelCreacionMazoInfoCarta.setLayout(null);
		panelCreacionMazoInfoCarta.setVisible(false);

		lblCartaMazoInfoCarta = new JLabel("");
		lblCartaMazoInfoCarta.setBounds(97, 126, 285, 390);
		lblCartaMazoInfoCarta.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazoInfoCarta.add(lblCartaMazoInfoCarta);

		lblAlsaCartaMazoInfoCarta = new JLabel("ALSA:  ");
		lblAlsaCartaMazoInfoCarta.setBounds(448, 209, 285, 48);
		lblAlsaCartaMazoInfoCarta.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazoInfoCarta.add(lblAlsaCartaMazoInfoCarta);

		lblATACartaMazoInfoCarta = new JLabel("ATA: ");
		lblATACartaMazoInfoCarta.setBounds(448, 257, 285, 48);
		lblATACartaMazoInfoCarta.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazoInfoCarta.add(lblATACartaMazoInfoCarta);

		lblWRCartaMazoInfoCarta = new JLabel("WR:  ");
		lblWRCartaMazoInfoCarta.setBounds(448, 305, 285, 48);
		lblWRCartaMazoInfoCarta.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazoInfoCarta.add(lblWRCartaMazoInfoCarta);

		btnVolverInfoCarta = new JButton("VOLVER");
		btnVolverInfoCarta.setBounds(448, 468, 285, 48);
		btnVolverInfoCarta.setFont(new Font("Cambria Math", Font.BOLD, 20));

		panelCreacionMazoInfoCarta.add(btnVolverInfoCarta);

		// CREACION DE MAZO

		panelCreacionMazo = new JPanel();
		panelCreacionMazo.setBounds(0, 0, 1920, 1062);
		panelCreacionMazo.setVisible(false);
		contentPane.add(panelCreacionMazo);
		panelCreacionMazo.setLayout(null);

		JLabel lblCreacion = new JLabel("CREACIÓN");
		lblCreacion.setForeground(new Color(55, 55, 55));
		lblCreacion.setFont(new Font("Cambria Math", Font.BOLD, 34));
		lblCreacion.setBounds(208, 50, 259, 48);
		panelCreacionMazo.add(lblCreacion);

		JLabel lblMazo = new JLabel("MAZO");
		lblMazo.setForeground(new Color(55, 55, 55));
		lblMazo.setFont(new Font("Cambria Math", Font.BOLD, 34));
		lblMazo.setBounds(1093, 50, 157, 48);
		panelCreacionMazo.add(lblMazo);

		lblColor = new JLabel("Color: ");
		lblColor.setFont(new Font("Cambria Math", Font.BOLD, 24));
		lblColor.setBounds(1093, 138, 157, 48);
		panelCreacionMazo.add(lblColor);

		lblType = new JLabel("Tipo: ");
		lblType.setFont(new Font("Cambria Math", Font.BOLD, 24));
		lblType.setBounds(1501, 138, 157, 48);
		panelCreacionMazo.add(lblType);

		
		lblCartaMazo = new JLabel("");
		lblCartaMazo.setBounds(208, 152, 285, 390);
		lblCartaMazo.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(lblCartaMazo);

		lblAlsaCartaMazo = new JLabel("ALSA ");
		lblAlsaCartaMazo.setBounds(545, 257, 285, 48);
		lblAlsaCartaMazo.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(lblAlsaCartaMazo);

		lblATACartaMazo = new JLabel("ATA ");
		lblATACartaMazo.setBounds(545, 305, 285, 48);
		lblATACartaMazo.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(lblATACartaMazo);

		lblWRCartaMazo = new JLabel("WR ");
		lblWRCartaMazo.setBounds(545, 357, 285, 48);
		lblWRCartaMazo.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(lblWRCartaMazo);

		listModelCartas = new DefaultListModel<>();

		lblSeparador = new JLabel("");
		lblSeparador.setForeground(new Color(128, 128, 128));
		lblSeparador.setBackground(new Color(128, 128, 128));
		lblSeparador.setBounds(961, 0, 6, 1062);
		panelCreacionMazo.add(lblSeparador);
		listCartas = new JList(listModelCartas);
		listCartas.setBounds(105, 609, 335, 350);
		listScrollCartas = new JScrollPane(listCartas);
		listCartas.setFont(new Font("Cambria Math", Font.BOLD, 16));
		listScrollCartas.setBounds(208, 609, 335, 350);
		panelCreacionMazo.add(listScrollCartas);

		listModelMazo = new DefaultListModel<>();
		listMazo = new JList(listModelMazo);
		listMazo.setBounds(1093, 609, 335, 350);
		listMazo.setFont(new Font("Cambria Math", Font.BOLD, 16));
		listScrollMazo = new JScrollPane(listMazo);
		listScrollMazo.setBounds(1093, 609, 335, 350);
		panelCreacionMazo.add(listScrollMazo);

		btnSeleccionarCartaParaMazo = new JButton("SELECCIONAR");
		btnSeleccionarCartaParaMazo.setBounds(604, 923, 173, 37);
		btnSeleccionarCartaParaMazo.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(btnSeleccionarCartaParaMazo);

		btnMazoBorrarCarta = new JButton("BORRAR");
		btnMazoBorrarCarta.setBounds(1485, 875, 173, 37);
		btnMazoBorrarCarta.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(btnMazoBorrarCarta);

		btnMazoConsultarCarta = new JButton("CONSULTAR");
		btnMazoConsultarCarta.setBounds(1485, 827, 173, 37);
		btnMazoConsultarCarta.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(btnMazoConsultarCarta);

		btnMazoFinalizarDraft = new JButton("FINALIZAR");
		btnMazoFinalizarDraft.setBounds(1485, 923, 173, 37);
		btnMazoFinalizarDraft.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(btnMazoFinalizarDraft);

		lblTotalCartasMazo = new JLabel("TOTAL:");
		lblTotalCartasMazo.setBounds(1093, 371, 101, 48);
		lblTotalCartasMazo.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(lblTotalCartasMazo);

	
		String[] columnNames1 = { "COLOR", "VALOR" };
		Object[][] data1 = { { "NEGRO", "" }, { "BLANCO", "" }, { "ROJO", "" }, { "AZUL", "" }, { "VERDE", "" },
				{ "MULTICOLOR", "" }, { "SIN COLOR", "" } };

		tableColor = new JTable(data1, columnNames1);
		tableColor.setBounds(1093, 197, 225, 112);
		tableColor.setFont(new Font("Cambria Math", Font.BOLD, 14));
		panelCreacionMazo.add(tableColor);
		

		String[] columnNames2 = { "TIPO", "CAMTIDAD" };
		Object[][] data2 = { { "CRIATURA", "" }, { "ARTEFACTO", "" }, { "INSTANTANEO", "" }};

		tableType = new JTable(data2, columnNames2);
		tableType.setBounds(1501, 197, 225, 112);
		tableType.setFont(new Font("Cambria Math", Font.BOLD, 14));
		panelCreacionMazo.add(tableType);

		lblCurvaMana = new JLabel("CURVA DE MANA:");
		lblCurvaMana.setBounds(1292, 347, 259, 96);
		lblCurvaMana.setFont(new Font("Cambria Math", Font.BOLD, 20));
		panelCreacionMazo.add(lblCurvaMana);

		panelCreacionMazo.setBackground(Color.WHITE);
		panelCreacionMazoInfoCarta.setBackground(Color.WHITE);

	}
}
