package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import modelo.MoVentana;

public class ViPanelSala extends JPanel {
	private ArrayList<JButton> asientos;
	private ArrayList<JButton> acciones;
	private MoVentana modelo;

	// Constructor
	public ViPanelSala(MoVentana modelo) {
		this.modelo = modelo;
		asientos = new ArrayList<>();
		acciones = new ArrayList<>();
		setBackground(new Color(50, 40, 60));
		crearPanelPrincipal();
		crearAsientos();
		crearBotones();
	}

	// Metodo para la creacion de los botones con acciones(Boton de comprar)
	private void crearBotones() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setBackground(new Color(70, 55, 85));
		for (int i = 0; i < modelo.getNumBotones(); i++) {
			JButton boton = new JButton(modelo.getTextoBotones().get(i));
			boton.setBackground(new Color(100, 80, 120));
			boton.setForeground(Color.WHITE);
			acciones.add(boton);
			panel.add(boton);
		}
		this.add(panel);
	}

	// Metodo para la creacion de botones que son asientos
	private void crearAsientos() {
		this.setLayout(new GridLayout(modelo.getNumAsientos() / 5, 5, modelo.getTamanios().get(0),
				modelo.getTamanios().get(0)));
		for (int i = 0; i < modelo.getNumAsientos(); i++) {
			JButton asiento = new JButton(String.valueOf(i + 1));
			asiento.setPreferredSize(new Dimension(modelo.getTamanios().get(4), modelo.getTamanios().get(0)));
			asientos.add(asiento);
			add(asiento);
		}
	}

	// Tamanios del panel principal
	private void crearPanelPrincipal() {
		setLayout(new GridLayout(2, 1, modelo.getTamanios().get(0), modelo.getTamanios().get(0)));
	}

	// GETTERS
	public ArrayList<JButton> getAsientos() {
		return asientos;
	}

	public ArrayList<JButton> getAcciones() {
		return acciones;
	}
}