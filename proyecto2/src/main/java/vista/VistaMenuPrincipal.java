package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controlador.CoPrincipal;
import modelo.ModeloClienteFTP;


public class VistaMenuPrincipal extends JFrame{
	ModeloClienteFTP client;
	JButton botonCRUD;
	JButton botonFileManager;
	JButton botonCerrarSesion;
	ViMain vista;
	public VistaMenuPrincipal(ModeloClienteFTP client, ViMain vista) {
		this.client=client;
		this.vista=vista;
		this.setTitle("Main Menu");
		this.setLayout(new BorderLayout());
		
		JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
		JPanel layoutOpciones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel layoutBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		layoutBotones.setLayout(new BoxLayout(layoutBotones, BoxLayout.Y_AXIS));
		layoutBotones.setBorder(new EmptyBorder(0, 0, 20, 0));
		
		JLabel text = new JLabel("Welcome, please select what do you want to do");
		Font fuenteText = new Font("Arial", Font.BOLD, 30);
		botonCRUD = new JButton("CRUD");
		botonFileManager = new JButton("File Manager");
		botonCerrarSesion =  new JButton("Log out");
		botonCRUD.setPreferredSize(new Dimension(300, 120)); 
		botonFileManager.setPreferredSize(new Dimension(300, 120));
		text.setFont(fuenteText);
		
		accionBotonCerrarSesion(botonCerrarSesion);
		
		contenedor.add(text);
		layoutOpciones.add(botonCRUD);
		layoutOpciones.add(botonFileManager);
		layoutBotones.add(layoutOpciones);
		layoutBotones.add(botonCerrarSesion);
		contenedor.add(layoutBotones);
		
		this.add(contenedor);
		this.setSize(1000, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(false);
	}
	
	public void accionBotonCerrarSesion(JButton boton) {
		boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	client.desconectar();
            	VistaMenuPrincipal.this.setVisible(false);
            	vista.setVisible(true);
                vista.getPanelLogin().getCajas().get(0).setText("");
                vista.getPanelLogin().getCajas().get(1).setText("");
                vista.mostrarLogin();
            }
    	});
	}
	
	public JButton getBotonFileManager() {
		return botonFileManager;
	}



	public void setBotonFileManager(JButton botonFileManager) {
		this.botonFileManager = botonFileManager;
	}



	public JButton getBotonCRUD() {
		return botonCRUD;
	}



	public void setBotonCRUD(JButton botonCRUD) {
		this.botonCRUD = botonCRUD;
	}



	public void hacerVisible() {
		setVisible(true);
	}
	
}
