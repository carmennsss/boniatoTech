/**
 * Clase panel formulario
 * 
 * @author Carmen
 * 10/12/2025
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.MoVentana;

public class ViPanelForm extends JPanel {
	private ArrayList<JLabel> labels;
	private ArrayList<JTextField> cajas;
	private ArrayList<JComboBox<String>> combo;
	private MoVentana modelo;
	private JButton boton;

	// Constructor
	public ViPanelForm(MoVentana modelo) {
		this.labels = new ArrayList<>();
		this.combo = new ArrayList<>();
		this.cajas = new ArrayList<>();
		this.modelo = modelo;

		setBackground(new Color(45, 35, 50));
		propiedadesPanel();
	}

	// Caracteristicas principales para el panel
	private void propiedadesPanel() {
		setLayout(new BorderLayout(modelo.getTamanios().get(0), modelo.getTamanios().get(0)));
		crearCombo();
		crearCantidadCajas();
		crearBoton(modelo.getNombreFormulario().get(modelo.getNombreFormulario().size() - 1));
	}

	// Metodo que crea la estructura de formulario (labels y cajas)
	private void crearCantidadCajas() {
		JPanel panelCampos = new JPanel(new GridLayout(modelo.getNombreFormulario().size() - 2, 2,
				modelo.getTamanios().get(0), modelo.getTamanios().get(0)));
		panelCampos.setBackground(new Color(60, 50, 80));

		for (int i = 0; i < modelo.getNombreFormulario().size() - 2; i++) {
			crearLabels(panelCampos, modelo.getNombreFormulario().get(i));
			crearCajas(panelCampos);
		}
		add(panelCampos, BorderLayout.CENTER);
	}

	// Metodo que crea labels con una misma estructura
	private void crearLabels(JPanel panel, String texto) {
		JLabel label = new JLabel(texto);
		label.setForeground(Color.WHITE);
		label.setPreferredSize(new Dimension(modelo.getTamanios().get(3), modelo.getTamanios().get(0)));
		labels.add(label);
		panel.add(this.labels.get(this.labels.size() - 1));
	}

	// Metodo que crea cajas con una misma estructura
	private void crearCajas(JPanel panel) {
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(modelo.getTamanios().get(3), modelo.getTamanios().get(0)));
		textField.setBackground(new Color(90, 70, 110));
		textField.setForeground(Color.WHITE);
		cajas.add(textField);
		panel.add(this.cajas.get(this.cajas.size() - 1));
	}

	// Metodo para crear el combo
	private void crearCombo() {
		JPanel panelCombo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelCombo.setBackground(new Color(50, 40, 70));
		JComboBox<String> comboBox = new JComboBox<>();

		for (int i = 1; i <= modelo.getNumSesiones(); i++) {
			comboBox.addItem(modelo.getTextoCombo().get(0) + " " + i);
		}
		comboBox.setPreferredSize(new Dimension(modelo.getTamanios().get(2), modelo.getTamanios().get(0)));
		comboBox.setBackground(new Color(110, 90, 140));
		comboBox.setForeground(Color.WHITE);
		combo.add(comboBox);

		panelCombo.add(this.combo.get(this.combo.size() - 1));
		add(panelCombo, BorderLayout.NORTH);
	}

	// Metodo que crea un boton
	private void crearBoton(String texto) {
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBoton.setBackground(new Color(45, 35, 50));
		boton = new JButton(texto);
		boton.setPreferredSize(new Dimension(modelo.getTamanios().get(3), modelo.getTamanios().get(4)));
		boton.setBackground(new Color(150, 40, 60));
		boton.setForeground(Color.WHITE);
		panelBoton.add(boton);
		add(panelBoton, BorderLayout.SOUTH);
	}

	// GETTERS Y SETTERS
	public ArrayList<JTextField> getCajas() {
		return cajas;
	}

	public ArrayList<JComboBox<String>> getCombo() {
		return combo;
	}

	public JButton getBoton() {
		return boton;
	}
}