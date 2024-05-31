package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Executors;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import modelo.ProcesarColeccionDAO;
import modelo.UsuarioDAO;
import persistencias.Carta;
import persistencias.Usuario;
import vista.Vista;

public class Controlador implements ActionListener {
    private Vista vista;
    private ProcesarColeccionDAO coleccionDAO;
    private UsuarioDAO usuarioDAO;
    private Login login;
    private Usuario usuarioActual;
    private JugadorUsuario jugadorUsuario;
    private Draft draft;

    private Sobre sobre;
    private Carta cartaSeleccionada;
    private int seleccion;

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
        this.vista.btnIniciarDraft.addActionListener(this);
        
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
    }

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
        } else if (e.getSource() == vista.btnIniciarDraft) { // Maneja el evento de iniciar draft
            iniciarDraft();
        }
    	
    	if(e.getSource() == vista.btnCarta1) {
    		ampliarCarta(sobre.getCartasSobre().get(0));
    	}
    	if(e.getSource() == vista.btnCarta2) {
    		ampliarCarta(sobre.getCartasSobre().get(1));
    	}
    	if(e.getSource() == vista.btnCarta3) {
    		ampliarCarta(sobre.getCartasSobre().get(2));
    	}
    	if(e.getSource() == vista.btnCarta4) {
    		ampliarCarta(sobre.getCartasSobre().get(3));
    	}
    	if(e.getSource() == vista.btnCarta5) {
    		ampliarCarta(sobre.getCartasSobre().get(4));
    	}
    	if(e.getSource() == vista.btnCarta6) {
    		ampliarCarta(sobre.getCartasSobre().get(5));
    	}
    	if(e.getSource() == vista.btnCarta7) {
    		ampliarCarta(sobre.getCartasSobre().get(6));
    	}
    	if(e.getSource() == vista.btnCarta8) {
    		ampliarCarta(sobre.getCartasSobre().get(7));
    	}
    	if(e.getSource() == vista.btnCarta9) {
    		ampliarCarta(sobre.getCartasSobre().get(8));
    	}
    	if(e.getSource() == vista.btnCarta10) {
    		ampliarCarta(sobre.getCartasSobre().get(9));
    	}
    	if(e.getSource() == vista.btnCarta11) {
    		ampliarCarta(sobre.getCartasSobre().get(10));
    	}
    	if(e.getSource() == vista.btnCarta12) {
    		ampliarCarta(sobre.getCartasSobre().get(11));
    	}
    	if(e.getSource() == vista.btnCarta13) {
    		ampliarCarta(sobre.getCartasSobre().get(12));
    	}
    	if(e.getSource() == vista.btnCarta14) {
    		ampliarCarta(sobre.getCartasSobre().get(13));
    	}
    	
    	if(e.getSource() == vista.btnPickear) {
    		pickearCarta();
    	}
    }

    private void handleLogin() {
    	String nombreUsuario = vista.tfUsuarioLogin.getText();
        String contrasena = new String(vista.pfContrasenaLogin.getPassword());
        usuarioActual = login.autenticarUsuario(nombreUsuario, contrasena);

        if (usuarioActual == null) {
            int respuesta = javax.swing.JOptionPane.showConfirmDialog(vista, "Usuario no encontrado. ¿Deseas crear un nuevo usuario?");
            if (respuesta == javax.swing.JOptionPane.YES_OPTION) {
                mostrarPanelRegistroUsuario();
            }
        } else {

        	// Cargar la BBDD
            try {

				cargarBBDD("ltr");
				vista.jPanelDraft.setVisible(true);
				vista.panelEsperaCargaColeccion.setVisible(false);
	            iniciarDraft();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }

    private void handleCrearUsuario() {
        String nombreUsuario = vista.tfUsuarioRegistro.getText();
        String contrasena = new String(vista.pfContrasenaRegistro.getPassword());

        // Verificar si el nombre de usuario ya existe
        if (usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario) != null) {
            javax.swing.JOptionPane.showMessageDialog(vista, "El nombre de usuario ya existe. Por favor, elija otro.");
        } else {
            // Crear el nuevo usuario
            login.crearNuevoUsuario(nombreUsuario, contrasena);
            usuarioActual = usuarioDAO.obtenerUsuarioPorNombre(nombreUsuario); // Obtener el usuario creado
            javax.swing.JOptionPane.showMessageDialog(vista, "Usuario creado exitosamente");
            mostrarPanelLogin();
        }
    }

    private void mostrarPanelRegistroUsuario() {
    	vista.jPanelRegistroUsuario.setVisible(true);
    	vista.jPanelLogin.setVisible(false);
    }

    private void mostrarPanelLogin() {
    	vista.jPanelRegistroUsuario.setVisible(false);
    	vista.jPanelLogin.setVisible(true);
    }
    
    private void mostrarEspera() {
    	ImageIcon imagen = new ImageIcon("resources/"+"loading.gif");
    	Icon icon = new ImageIcon(imagen.getImage().getScaledInstance(vista.lblEspera.getWidth(), vista.lblEspera.getHeight(), Image.SCALE_DEFAULT));
    	vista.lblEspera.setIcon(icon);
    	vista.panelEsperaCargaColeccion.setVisible(true);
    }
    
    private void loginCorrecto() {
    	javax.swing.JOptionPane.showMessageDialog(vista, "Login exitoso");
        // Proceso de carga de bbdd si no hay datos y pantalla de carga
    	vista.jPanelRegistroUsuario.setVisible(false);
    	vista.jPanelLogin.setVisible(false);
    }

    private void cargarBBDD(String nombreColeccion) throws InterruptedException {
        boolean esColeccion = coleccionDAO.selectColeccion(nombreColeccion);
        if (!esColeccion) {
            System.out.println("Iniciamos carga de coleccion");
            ProcesadorColeccion procesarColeccion = new ProcesadorColeccion("card-ratings-2024-05-12.csv", nombreColeccion);
        	mostrarEspera();
        	vista.jPanelRegistroUsuario.setVisible(false);
        	vista.jPanelLogin.setVisible(false);
        	javax.swing.JOptionPane.showMessageDialog(vista, "Cargando coleccion");

            procesarColeccion.run();
            //procesarColeccion.join();

        } else {
        	loginCorrecto();
            System.out.println("Coleccion ya existente en BBDD");
        }
    }

    private void iniciarDraft() {
    	
        ArrayList<Carta> todasLasCartas = obtenerTodasLasCartas(); // Método para obtener todas las cartas de la colección
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
            System.out.println("Sobres del Jugador Bot " + (i+1)+ ":");
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
        sobre = jugadorUsuario.getSobres().get(draft.getRonda()-1);
        abrirSobre(sobre);
    }

    private void abrirSobre(Sobre sobre) {
    	vista.btnCarta1.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(0)));
    	if(sobre.getCartasSobre().size() > 1) {
    		vista.btnCarta2.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(1)));
    		vista.btnCarta2.setEnabled(true);
    	}else {
    		vista.btnCarta2.setEnabled(false);
    		vista.btnCarta2.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 2) {
    		vista.btnCarta3.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(2)));
    		vista.btnCarta3.setEnabled(true);
    	}else {
    		vista.btnCarta3.setEnabled(false);
    		vista.btnCarta3.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 3) {
    		vista.btnCarta4.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(3)));
    		vista.btnCarta4.setEnabled(true);
    	}else {
    		vista.btnCarta4.setEnabled(false);
    		vista.btnCarta4.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 4) {
    		vista.btnCarta5.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(4)));
    		vista.btnCarta5.setEnabled(true);
    	}else {
    		vista.btnCarta5.setEnabled(false);
    		vista.btnCarta5.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 5) {
    		vista.btnCarta6.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(5)));
    		vista.btnCarta6.setEnabled(true);
    	}else {
    		vista.btnCarta6.setEnabled(false);
    		vista.btnCarta6.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 6) {
    		vista.btnCarta7.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(6)));
    		vista.btnCarta7.setEnabled(true);
    	}else {
    		vista.btnCarta7.setEnabled(false);
    		vista.btnCarta7.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 7) {
    		vista.btnCarta8.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(7)));
    		vista.btnCarta8.setEnabled(true);
    	}else {
    		vista.btnCarta8.setEnabled(false);
    		vista.btnCarta8.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 8) {
    		vista.btnCarta9.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(8)));
    		vista.btnCarta9.setEnabled(true);
    	}else {
    		vista.btnCarta9.setEnabled(false);
    		vista.btnCarta9.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 9) {
    		vista.btnCarta10.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(9)));
    		vista.btnCarta10.setEnabled(true);
    	}else {
    		vista.btnCarta10.setEnabled(false);
    		vista.btnCarta10.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 10) {
    		vista.btnCarta11.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(10)));
    		vista.btnCarta11.setEnabled(true);
    	}else {
    		vista.btnCarta11.setEnabled(false);
    		vista.btnCarta11.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 11) {
    		vista.btnCarta12.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(11)));
    		vista.btnCarta12.setEnabled(true);
    	}else {
    		vista.btnCarta12.setEnabled(false);
    		vista.btnCarta12.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 12) {
    		vista.btnCarta13.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(12)));
    		vista.btnCarta13.setEnabled(true);
    	}else {
    		vista.btnCarta13.setEnabled(false);
    		vista.btnCarta13.setIcon(null);
    	}
    	if(sobre.getCartasSobre().size() > 13) {
    		vista.btnCarta14.setIcon(obtenerImagenCarta(sobre.getCartasSobre().get(13)));
    		vista.btnCarta14.setEnabled(true);
    	}else {
    		vista.btnCarta14.setEnabled(false);
    		vista.btnCarta14.setIcon(null);
    	}

    	
    	vista.lblSobre.setText("Num Sobre "+draft.getRonda());
    	
    }
    
    private Icon obtenerImagenCarta(Carta carta) {
    	ImageIcon imagen = new ImageIcon("ltr_PNG_cartas/"+carta.getNombreOriginal()+".png");
    	Icon icon = new ImageIcon(imagen.getImage().getScaledInstance(vista.btnCarta1.getWidth(), vista.btnCarta1.getHeight(), Image.SCALE_DEFAULT));
    	
    	return icon;
    }
    
    private void ampliarCarta(Carta carta) {
    	ImageIcon imagen = new ImageIcon("ltr_PNG_cartas/"+carta.getNombreOriginal()+".png");
    	Icon icon = new ImageIcon(imagen.getImage().getScaledInstance(vista.lblCartaSeleccionada.getWidth(), vista.lblCartaSeleccionada.getHeight(), Image.SCALE_DEFAULT));
    	vista.lblCartaSeleccionada.setIcon(icon);
    	
    	vista.lblAlsa.setText("ALSA "+carta.getAlsa());
    	vista.lblATA.setText("ATA "+carta.getAta());
    	vista.lblWR.setText("WR "+carta.getGpWr());
    	
    	vista.btnPickear.setEnabled(true);
    	
    	cartaSeleccionada = carta;
    	

    }
    
    private void pickearCarta() {
    
    	jugadorUsuario.getMazo().add(cartaSeleccionada);
    	sobre.eliminarCarta(cartaSeleccionada);
    	vista.lblCartaSeleccionada.setIcon(null);
    	vista.lblPickeos.setText("Num total Pickeos " + jugadorUsuario.getMazo().size());
    	
    	for (int i = 0; i < draft.getSobresRonda().size(); i++) {
    		if(seleccion != i) {
    			pickeoBot(i);
    		}
    	}
    	if (seleccion >= 7) {
    		this.seleccion = 0;
    	}else {
        	this.seleccion++;
    	}

    	if(jugadorUsuario.getSobres().get(draft.getRonda()-1).getCartasSobre().size() == 0) {   		
    		this.seleccion = 0;
    		draft.setRonda(draft.getRonda()+1);
    		if (draft.getRonda() > 3) {
    			javax.swing.JOptionPane.showMessageDialog(vista, "Draft terminado. Accediendo a construcción de mazos");
    		}else {
    			javax.swing.JOptionPane.showMessageDialog(vista, "Ronda terminada. Abriendo siguiente sobre");
    			draft.annadirSobresRonda();
    			sobre = jugadorUsuario.getSobres().get(draft.getRonda()-1);
    			abrirSobre(sobre);
    		}		
    		
    	}else {
    		sobre = draft.getSobresRonda().get(seleccion);
    		abrirSobre(sobre);
    	}
    	
    	vista.btnPickear.setEnabled(false);
    	
    }
    
    private void pickeoBot(int index) {
    	
		Carta cartaSeleccionada = new Carta();
		
		for(Carta carta : draft.getSobresRonda().get(index).getCartasSobre()) {
			
			if(cartaSeleccionada.getGpWr() == null) {
				cartaSeleccionada = carta;
			}else {
				if (carta.getGpWr().longValue() > cartaSeleccionada.getGpWr().longValue()) {
					cartaSeleccionada = carta;
				}
			}
		}
		draft.getSobresRonda().get(index).getCartasSobre().remove(cartaSeleccionada);
    }
    
    private ArrayList<Carta> obtenerTodasLasCartas() {
        // Implementar lógica para obtener todas las cartas, por ejemplo, desde una base de datos o archivo
        // Este es un ejemplo de cómo podrías cargar las cartas. Por ahora, retorna una lista vacía.
    	ArrayList <Carta> cartasColeccion = coleccionDAO.obtenerCartasColeccion("ltr");
        return cartasColeccion;
    }
}
