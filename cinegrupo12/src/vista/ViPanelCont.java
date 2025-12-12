/**
 * Clase panel contrasenia
 * 
 * @author Carmen
 * 10/12/2025
 */
package vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import modelo.MoVentana;

public class ViPanelCont extends JPanel {
	private ArrayList<JTextField> cajas;
	private ArrayList<JLabel> labels;
	private MoVentana modelo;

	// Constructor
	public ViPanelCont(MoVentana modelo) {
		this.cajas = new ArrayList<>();
		this.labels = new ArrayList<>();
		this.modelo = modelo;

		propiedadesPanel();
	}

	// Caracteristicas principales para el panel
	private void propiedadesPanel() {
		this.setLayout(new FlowLayout(FlowLayout.CENTER, modelo.getTamanios().get(0), modelo.getTamanios().get(0)));
		this.setBackground(new Color(40, 40, 40));
		crearPanelPrincipal();
	}

	private void crearPanelPrincipal() {
		// PANEL PARA ORGANIZAR CAMPOS
		JPanel panelCampos = new JPanel(new GridLayout(2, 1, modelo.getTamanios().get(0), modelo.getTamanios().get(0)));
		panelCampos.setBackground(new Color(50, 50, 50));

		// Label para poner "contraseña"
		JLabel label = new JLabel(modelo.getNombreFormulario().get(2));
		label.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(JLabel.CENTER);

		// Caja de texto
		JTextField textField = new JTextField();
		textField.setPreferredSize(new Dimension(modelo.getTamanios().get(3), modelo.getTamanios().get(0)));
		textField.setBackground(new Color(70, 70, 70));
		textField.setForeground(Color.WHITE);
		textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		this.labels.add(label);
		this.cajas.add(textField);

		panelCampos.add(this.labels.get(this.labels.size() - 1));
		panelCampos.add(this.cajas.get(this.cajas.size() - 1));
		this.add(panelCampos);
	}

	// GETTERS

	public ArrayList<JTextField> getCajas() {
		return cajas;
	}
}
