package vista;

import javax.swing.*;

import controlador.Controlador;

import java.awt.*;

public class Vista extends JFrame {
    public static final long serialVersionUID = 1L;
    public JPanel contentPane;
    public JPanel jPanelLogin, panelEsperaCargaColeccion;
    public JPanel jPanelRegistroUsuario;
    public JPanel jPanelDraft;
    public JTextField tfUsuarioLogin;
    public JPasswordField pfContrasenaLogin;
    public JButton btnLogin;
    public JButton btnMostrarRegistro;
    public JButton btnCarta1, btnCarta2, btnCarta3, btnCarta4, btnCarta5, btnCarta6, btnCarta7, btnCarta8, btnCarta9, btnCarta10, btnCarta11, 
    btnCarta12, btnCarta13, btnCarta14, btnPickear;
    public JLabel lblCartaSeleccionada, lblSobre, lblPickeos, lblATA, lblAlsa, lblWR, lblEspera, lblEsperaRetro;
    public JTextField tfUsuarioRegistro;
    public JPasswordField pfContrasenaRegistro;
    public JButton btnRegistrarUsuario;
    public JButton btnVolverALogin;
    public JButton btnIniciarDraft;
    public JLabel jLabelLogo;
    public JLabel lblUsuarioLogin;
    public JLabel lblContrasenaLogin;
    public JLabel lblUsuarioRegistro;
    public JLabel lblContrasenaRegistro;
    
    
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
        
        jPanelDraft = new JPanel();
        jPanelDraft.setBounds(0, 0, screenSize.width, screenSize.height);
        jPanelDraft.setLayout(null);
        jPanelDraft.setVisible(false);
        contentPane.add(jPanelDraft);

        try {
            ImageIcon logoConclaveIcon = new ImageIcon("resources/logo_jugador.png");
            jLabelLogo = new JLabel(logoConclaveIcon);
        } catch (Exception e) {
            e.printStackTrace();
            jLabelLogo = new JLabel("Error cargando el logo");
        }
        
        
       

        jLabelLogo.setBounds((screenSize.width - 200) / 2, 50, 200, 200);
        jPanelLogin.add(jLabelLogo);
        //jPanelRegistroUsuario.add(jLabelLogo);

        lblUsuarioLogin = new JLabel("Usuario:");
        lblUsuarioLogin.setBounds((screenSize.width - 400) / 2, 300, 400, 25);
        jPanelLogin.add(lblUsuarioLogin);

        tfUsuarioLogin = new JTextField();
        tfUsuarioLogin.setBounds((screenSize.width - 400) / 2, 330, 400, 30);
        jPanelLogin.add(tfUsuarioLogin);

        lblContrasenaLogin = new JLabel("Contraseña:");
        lblContrasenaLogin.setBounds((screenSize.width - 400) / 2, 370, 400, 25);
        jPanelLogin.add(lblContrasenaLogin);

        pfContrasenaLogin = new JPasswordField();
        pfContrasenaLogin.setBounds((screenSize.width - 400) / 2, 400, 400, 30);
        jPanelLogin.add(pfContrasenaLogin);

        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds((screenSize.width - 200) / 2, 450, 200, 40);
        jPanelLogin.add(btnLogin);

        btnMostrarRegistro = new JButton("¿No tienes una cuenta? ¡Regístrate aquí!");
        btnMostrarRegistro.setBounds((screenSize.width - 300) / 2, 500, 300, 30);
        jPanelLogin.add(btnMostrarRegistro);

        lblUsuarioRegistro = new JLabel("Indique el nombre de usuario:");
        lblUsuarioRegistro.setBounds((screenSize.width - 400) / 2, 300, 400, 25);
        jPanelRegistroUsuario.add(lblUsuarioRegistro);

        tfUsuarioRegistro = new JTextField();
        tfUsuarioRegistro.setBounds((screenSize.width - 400) / 2, 330, 400, 30);
        jPanelRegistroUsuario.add(tfUsuarioRegistro);

        lblContrasenaRegistro = new JLabel("Introduzca la contraseña:");
        lblContrasenaRegistro.setBounds((screenSize.width - 400) / 2, 370, 400, 25);
        jPanelRegistroUsuario.add(lblContrasenaRegistro);

        pfContrasenaRegistro = new JPasswordField();
        pfContrasenaRegistro.setBounds((screenSize.width - 400) / 2, 400, 400, 30);
        jPanelRegistroUsuario.add(pfContrasenaRegistro);

        btnRegistrarUsuario = new JButton("Registrar Usuario");
        btnRegistrarUsuario.setBounds((screenSize.width - 200) / 2, 450, 200, 40);
        jPanelRegistroUsuario.add(btnRegistrarUsuario);

        btnVolverALogin = new JButton("Volver al inicio de sesión");
        btnVolverALogin.setBounds((screenSize.width - 300) / 2, 500, 300, 30);
        jPanelRegistroUsuario.add(btnVolverALogin);

        btnIniciarDraft = new JButton("Iniciar Draft");
        btnIniciarDraft.setBounds((screenSize.width - 200) / 2, 550, 200, 40);
        jPanelRegistroUsuario.add(btnIniciarDraft);
        
        // ESPERA CARGA COLECCION
        
        panelEsperaCargaColeccion = new JPanel();
        panelEsperaCargaColeccion.setBounds(0, 0, screenSize.width, screenSize.height);
        panelEsperaCargaColeccion.setLayout(null);
        panelEsperaCargaColeccion.setVisible(false);
        contentPane.add(panelEsperaCargaColeccion);
        
        lblEspera = new JLabel("Espera");
        lblEspera.setBounds(521, 647, 285, 390);
        panelEsperaCargaColeccion.add(lblEspera);
        
        lblEsperaRetro = new JLabel("Cargando colección");
        lblEsperaRetro.setBounds(521, 647, 285, 390);
        panelEsperaCargaColeccion.add(lblEsperaRetro);
        
        
        // FASE DRAFT
        
        btnCarta1 = new JButton("Carta 1");
        btnCarta1.setBounds(77, 61, 190, 265);
        jPanelDraft.add(btnCarta1);
        
        btnCarta2 = new JButton("Carta 2");
        btnCarta2.setBounds(334, 61, 190, 265);
        jPanelDraft.add(btnCarta2);
        
        btnCarta3 = new JButton("Carta 3");
        btnCarta3.setBounds(591, 61, 190, 265);
        jPanelDraft.add(btnCarta3);
        
        btnCarta4 = new JButton("Carta 4");
        btnCarta4.setBounds(842, 61, 190, 265);
        jPanelDraft.add(btnCarta4);
        
        btnCarta5 = new JButton("Carta 5");
        btnCarta5.setBounds(1096, 61, 190, 265);
        jPanelDraft.add(btnCarta5);
        
        btnCarta6 = new JButton("Carta 6");
        btnCarta6.setBounds(1353, 61, 190, 265);
        jPanelDraft.add(btnCarta6);
        
        btnCarta7 = new JButton("Carta 7");
        btnCarta7.setBounds(1611, 61, 190, 265);
        jPanelDraft.add(btnCarta7);
        
        btnCarta8 = new JButton("Carta 8");
        btnCarta8.setBounds(77, 358, 190, 265);
        jPanelDraft.add(btnCarta8);
        
        btnCarta9 = new JButton("Carta 9");
        btnCarta9.setBounds(334, 358, 190, 265);
        jPanelDraft.add(btnCarta9);
        
        btnCarta10 = new JButton("Carta 10");
        btnCarta10.setBounds(591, 358, 190, 265);
        jPanelDraft.add(btnCarta10);
        
        btnCarta11 = new JButton("Carta 11");
        btnCarta11.setBounds(842, 358, 190, 265);
        jPanelDraft.add(btnCarta11);
        
        btnCarta12 = new JButton("Carta 12");
        btnCarta12.setBounds(1096, 358, 190, 265);
        jPanelDraft.add(btnCarta12);
        
        btnCarta13 = new JButton("Carta 13");
        btnCarta13.setBounds(1353, 358, 190, 265);
        jPanelDraft.add(btnCarta13);

        btnCarta14 = new JButton("Carta 14");
        btnCarta14.setBounds(1611, 358, 190, 265);
        jPanelDraft.add(btnCarta14);
        
        lblCartaSeleccionada = new JLabel("Carta seleccionada");
        lblCartaSeleccionada.setBounds(521, 647, 285, 390);
        jPanelDraft.add(lblCartaSeleccionada);
        
        lblAlsa = new JLabel("ALSA ");
        lblAlsa.setBounds(866, 728, 285, 48);
        jPanelDraft.add(lblAlsa );

        lblATA = new JLabel("ATA ");
        lblATA.setBounds(866, 646, 285, 48);
        jPanelDraft.add(lblATA);
        
        lblWR = new JLabel("WR ");
        lblWR.setBounds(866, 799,  285, 48);
        jPanelDraft.add(lblWR);
        
        lblSobre = new JLabel("Num Sobre ");
        lblSobre.setBounds(77, 873,  285, 48);
        jPanelDraft.add(lblSobre);
        
        lblPickeos = new JLabel("Num total de pickeos 0");
        lblPickeos.setBounds(77, 932,  285, 48);
        jPanelDraft.add(lblPickeos);
        
        btnPickear = new JButton("Pickear carta");
        btnPickear.setBounds(1648, 953, 200, 40);
        jPanelDraft.add(btnPickear);
        
        
        
        
        
    }
}
