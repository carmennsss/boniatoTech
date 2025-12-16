package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import modelo.ModeloClienteFTP;


public class VistaAdmin extends JFrame{
	
	VistaMenuPrincipal menu;
	JButton botonCrearUsuario;
	JButton botonCrearRoles;
	JButton botonAsignarRoles;
	JButton botonVolver;
	
	public VistaAdmin(VistaMenuPrincipal menu) {
		this.menu=menu;
		this.setTitle("Administrator menu");
		this.setLayout(new BorderLayout());
		
		botonCrearUsuario = new JButton("Administrate users");
		botonCrearRoles = new JButton("Administrate roles");
		botonAsignarRoles = new JButton("Asign roles");
		botonVolver = new JButton("Main Menu");
		
		JPanel layout = new JPanel(new GridLayout(5, 2, 10, 10));
		layout.setBorder(new EmptyBorder(20, 20, 20, 20));
		
		accionBotonVolver(botonVolver);
		
		layout.add(botonCrearUsuario);
		layout.add(botonCrearRoles);
		layout.add(botonAsignarRoles);
		layout.add(botonVolver);
		
		this.add(layout);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public JButton getBotonCrearUsuario() {
		return botonCrearUsuario;
	}

	public void setBotonCrearUsuario(JButton botonCrearUsuario) {
		this.botonCrearUsuario = botonCrearUsuario;
	}

	public JButton getBotonCrearRoles() {
		return botonCrearRoles;
	}

	public void setBotonCrearRoles(JButton botonCrearRoles) {
		this.botonCrearRoles = botonCrearRoles;
	}

	public JButton getBotonAsignarRoles() {
		return botonAsignarRoles;
	}

	public void setBotonAsignarRoles(JButton botonAsignarRoles) {
		this.botonAsignarRoles = botonAsignarRoles;
	}

	public JButton getBotonVolver() {
		return botonVolver;
	}

	public void setBotonVolver(JButton botonVolver) {
		this.botonVolver = botonVolver;
	}

	public void hacerVisible() {
		this.setVisible(true);
	}
	
	public void accionBotonVolver(JButton boton) {
    	boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	menu.hacerVisible();
            	VistaAdmin.this.setVisible(false);
            }
    	});

    }
	

	
}
