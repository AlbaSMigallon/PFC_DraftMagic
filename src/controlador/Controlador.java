package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vista.Vista;

public class Controlador implements ActionListener {
	public Vista vista;
	
	public Controlador(Vista vista) {
		this.vista = vista;
		//this.vista.btnAceptarInicio.addActionListener(this);
		


	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
