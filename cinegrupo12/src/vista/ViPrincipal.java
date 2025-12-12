/**
 * Clase vista principal
 *
 * @author Grupo 1
 * 10/12/2025
 */
package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import modelo.MoPrincipal;
import modelo.MoVentana;

public class ViPrincipal extends JFrame {
	private MoVentana modelo;
	private MoPrincipal modeloPrin;
	private ViVentanaTablas tabla;
	private ArrayList<ViPanelForm> panelesForm;
	private ArrayList<ViPanelCont> panelesCont;
	private JTabbedPane tabbedPane;
	private ArrayList<ViPanelSala> panelesSala;

	// Constructor
	public ViPrincipal(MoVentana modelo, MoPrincipal modeloPrin) {
		this.modelo = modelo;
		this.modeloPrin = modeloPrin;
		this.tabla = new ViVentanaTablas(modelo);
		this.panelesForm = new ArrayList<>();
		this.panelesSala = new ArrayList<>();
		this.panelesCont = new ArrayList<>();
		tabbedPane = new JTabbedPane();

		// Aplicando colores personalizados al JTabbedPane
		tabbedPane.setBackground(new Color(40, 40, 40));
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setOpaque(true);
		tabbedPane.setBorder(null);

		this.setSize(modelo.getTamanios().get(6), modelo.getTamanios().get(1));
		propiedadesVentana();
	}

	// Propiedades básicas de la ventana
	private void propiedadesVentana() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		// Tamanio minimo
		this.setMinimumSize(new Dimension(modelo.getTamanios().get(6), modelo.getTamanios().get(1)));

		setLayout(new BorderLayout());
		crearTabFormulario();
		crearTabContrasenia();
		crearTabSalas();
		this.add(tabbedPane, BorderLayout.CENTER);
	}

	// Método para hacer visible la tabla
	public void mostrarTabla() {
		tabla.hacerVisible(true);
	}

	// Método para crear la pestaña del formulario
	private void crearTabFormulario() {
		this.panelesForm.add(new ViPanelForm(this.modelo));
		tabbedPane.addTab(modelo.getTextosTab().get(0), this.panelesForm.get(this.panelesForm.size() - 1));
	}

	// Método para crear la pestaña del admin
	private void crearTabContrasenia() {
		this.panelesCont.add(new ViPanelCont(this.modelo));
		tabbedPane.addTab(modelo.getTextosTab().get(1), this.panelesCont.get(this.panelesCont.size() - 1));
	}

	// Método para crear las pestañas de las salas
	private void crearTabSalas() {
		for (int i = 0; i < modelo.getNumSesiones(); i++) {
			this.panelesSala.add(new ViPanelSala(modelo));
		}
	}

	// Hace visible la ventana
	public void hacerVisible(boolean b) {
		this.setVisible(b);
	}

	// Borra los botones verdes seleccionados, es decir, los devuelve a su color original
	public void borrarSeleccion(int panel) {
		for (int i = 0; i < getPanelesSala().get(panel).getAsientos().size(); i++) {
			if (getPanelesSala().get(panel).getAsientos().get(i).getBackground().equals(Color.GREEN.darker())) {
				getPanelesSala().get(panel).getAsientos().get(i).setBackground(UIManager.getColor("Button.background"));
			}
		}
	}

	/*
	 * Método para cambiar los asientos seleccionados (con el fondo verde) a fondo
	 * rojo, desactivarlos y retornar un array que contenga los números de los
	 * asientos comprados
	 */
	public ArrayList<String> asientoOcupado(int panel) {
		ArrayList<String> asientosOcupados = new ArrayList<>();
		for (int i = 0; i < getPanelesSala().get(panel).getAsientos().size(); i++) {
			if (getPanelesSala().get(panel).getAsientos().get(i).getBackground().equals(Color.GREEN.darker())) {
				asientosOcupados.add(getPanelesSala().get(panel).getAsientos().get(i).getText());
			}
		}
		return asientosOcupados;
	}
	
	// Metodo para poner en rojo los botones que ya se encuentren comprados en la sesion
	public void ponerRojo(int panel) {
		for (int i = 0; i < getPanelesSala().get(panel).getAsientos().size(); i++) {
			if (modeloPrin.getSesiones().get(panel).getAsientosOcupados().contains(getPanelesSala().get(panel).getAsientos().get(i).getText())) {
				getPanelesSala().get(panel).getAsientos().get(i).setBackground(Color.RED.darker());
				getPanelesSala().get(panel).getAsientos().get(i).setEnabled(false);
			}
		}
	}

	// Habilita el tab del formulario
	public void mostrarFormulario() {
		tabbedPane.insertTab(modelo.getTextosTab().get(0), null, this.getPanelesForm().get(0), null, 0);
	}

	// Método para eliminar cualquier tab
	public void eliminarTab(int indice) {
		this.tabbedPane.remove(indice);
	}

	// Método para habilitar los tab de las sesiones
	public void mostrarTab(int indice) {
		this.tabbedPane.add(modelo.getTextoCombo().get(0) + (indice + 1), this.getPanelesSala().get(indice));
	}

	// GETTERS
	public ArrayList<ViPanelCont> getPanelesCont() {
		return panelesCont;
	}

	public ArrayList<ViPanelSala> getPanelesSala() {
		return panelesSala;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public ArrayList<ViPanelForm> getPanelesForm() {
		return panelesForm;
	}

	public ViVentanaTablas getTabla() {
		return tabla;
	}
}