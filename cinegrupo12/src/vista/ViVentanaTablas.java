/**
 * Clase vista tabla
 * 
 * @author Grupo 1
 * 10/12/2025
 */
package vista;

import javax.swing.*;
import modelo.MoVentana;
import java.awt.*;

public class ViVentanaTablas extends JFrame {
	private ViPanelTabla panelTabla;
	private MoVentana modelo;

	// Constructor
	public ViVentanaTablas(MoVentana modelo) {
		this.modelo = modelo;
		propiedadesVentana();
		crearPanelTabla();
	}

	// Caracteristicas principales de la ventana
	private void propiedadesVentana() {
		this.setLayout(new BorderLayout());
		this.setResizable(true);
		// Tamanio minimo
		this.setMinimumSize(new Dimension(modelo.getTamanios().get(1), modelo.getTamanios().get(2)));
	}

	// Llama a panel tabla para agregar la tabla a la ventana
	private void crearPanelTabla() {
		panelTabla = new ViPanelTabla(modelo);
		add(panelTabla, BorderLayout.CENTER);
		
		this.pack();
		this.setLocationRelativeTo(null);
	}

	// Hace visible la ventana
	public void hacerVisible(boolean b) {
		this.setVisible(b);
	}

	// GETTERS

	public ViPanelTabla getPanelTabla() {
		return panelTabla;
	}
}
