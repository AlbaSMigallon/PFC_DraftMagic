package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.GestionBBDD;
import vista.Vista;

public class Controlador implements ActionListener {
	public Vista vista;
	private static GestionBBDD gBD;
	
	public Controlador(Vista vista) {
		this.vista = vista;
		Controlador.gBD = GestionBBDD.getInstance();
		//this.vista.btnAceptarInicio.addActionListener(this);
		cargarBBDD("ltr");
		


	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	private void cargarBBDD(String nombreColeccion) {
		boolean esColeccion=gBD.selectColeccion(nombreColeccion);
		if(!esColeccion) {
			//no hay coleccion en BBDD y hay que crearla
			System.out.println("Iniciamos carga de coleccion");
			ProcesarColeccion procesarColeccion= new ProcesarColeccion("card-ratings-2024-05-12.csv", nombreColeccion);
			procesarColeccion.run();
			
		}else {
			System.out.println("Coleccion ya existente en BBDD");
		}
	}

}
