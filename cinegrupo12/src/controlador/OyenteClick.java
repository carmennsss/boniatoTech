/**
* Clase oyente click
*
* @author Grupo 1
* 10/12/2025
*/
package controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.UIManager;
import modelo.MoCliente;
import modelo.MoPrincipal;
import modelo.MoVentana;
import vista.ViPrincipal;

public class OyenteClick implements ActionListener {
	private MoPrincipal modelo;
	private ViPrincipal vista;
	private MoVentana modeloVista;
	private int salaActual;

	// Constructor
	public OyenteClick(MoPrincipal modelo, ViPrincipal vista, MoVentana modeloVista) {
		this.modelo = modelo;
		this.vista = vista;
		this.modeloVista = modeloVista;
		this.salaActual = modelo.getSalaActual();
	}

	// Metodo de eventos para pulsar botones
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton boton = new JButton();
		boton = (JButton) e.getSource();

		// Se impedira continuar si alguno de los datos del formulario estan vacios
		if (boton.getText().equals(modeloVista.getNombreFormulario().get(3))) {
			int i = 0;
			boolean vacio = false;
			do {
				if (vista.getPanelesForm().get(0).getCajas().get(i).getText().isEmpty()) {
					vacio = true;
				}
				i++;
			} while (i < vista.getPanelesForm().get(0).getCajas().size() && !vacio);

			if (!vacio) {
				// Guardado de los datos inscritos en el formulario
				this.salaActual = vista.getPanelesForm().get(0).getCombo().get(0).getSelectedIndex();
				for (i = 0; i < vista.getPanelesForm().get(0).getCajas().size(); i++) {
					modelo.getInfoAux().add(vista.getPanelesForm().get(0).getCajas().get(i).getText());
					vista.getPanelesForm().get(0).getCajas().get(i).setText("");
				}
				// Se habilita el tab de la sesion elegida y se deshabilita el del formulario
				vista.mostrarTab(this.salaActual);
				vista.getTabbedPane().setSelectedIndex(2);
				vista.eliminarTab(0);
			}

		// Se comprobara la sesion y el boton de la misma
		} else if (boton == vista.getPanelesSala().get(this.salaActual).getAcciones().get(0)) {
			comprobarBoton(this.salaActual, boton);
			
		} else {
			/*
			 * Si los botones no contienen los textos anteriores, serán los asientos Se
			 * comprueba si el fondo es verde para volver al color predeterminado, si no, el
			 * fondo de los botones sera verde (Seleccionar y deseleccionar el asiento)
			 */
			if (vista.getPanelesSala().get(salaActual).getAsientos().contains(boton)) {
				if (boton.getBackground().equals(Color.GREEN.darker())) {
					boton.setBackground(UIManager.getColor("Button.background"));
				} else {
					boton.setBackground(Color.GREEN.darker());
				}
			}
		}
	}

	/*
	 * Metodo para comprobar el boton, si es 'Salir', borrara los asientos
	 * seleccionados, si es 'Comprar' dependiendo si hay botones seleccionados o no,
	 * comprara
	 */
	private void comprobarBoton(int panel, JButton boton) {
		ArrayList<String> asientosOcupados;
		if (boton.getText().equals(modeloVista.getTextoBotones().get(0))) { // Si es 'Comprar'
			asientosOcupados = vista.asientoOcupado(panel);
			comprobarCompra(asientosOcupados, boton);
			this.vista.ponerRojo(panel);
		} else { // Si es 'Salir'
			vista.borrarSeleccion(panel);
			boton.setText(modeloVista.getTextoBotones().get(0));
			regenerarFormulario();
		}
	}

	/*
	 * Metodo para la comprobacion de los asientos que se van a comprar. Si se han
	 * seleccionado asientos, se agregara el cliente; si no, el boton comprar se
	 * hara como un boton de salida para poder volver al menu formulario
	 */
	private void comprobarCompra(ArrayList<String> asientosOcupados, JButton boton) {
		if (!asientosOcupados.isEmpty()) {
			agregarCliente(asientosOcupados);
			regenerarFormulario();
		} else {
			boton.setText(modeloVista.getTextoBotones().get(1));
		}
	}

	/*
	 * Metodo para la regeneracion del tab formulario y la eliminacion del tab de la
	 * sesion
	 */
	private void regenerarFormulario() {
		vista.mostrarFormulario();
		vista.getTabbedPane().setSelectedIndex(0);
		vista.eliminarTab(2);
	}

	/*
	 * Metodo para agregar la informacion de la compra(nombre, correo, sesion y
	 * asientos) a la tabla
	 */
	private void agregarCliente(ArrayList<String> asientosOcupados) {
		// Se añaden para la sesion
		for (int i=0; i<asientosOcupados.size(); i++) {
			modelo.getSesiones().get(this.salaActual).getAsientosOcupados().add(asientosOcupados.get(i));
		}
		
		// Se añaden para el cliente
		modelo.getClientes().add(new MoCliente(modelo.getInfoAux().get(0), modelo.getInfoAux().get(1), asientosOcupados,
				(this.salaActual + 1)));
		vista.getTabla().getPanelTabla().agregarFilaPersonal(modelo.getClientes().get(modelo.getClientes().size() - 1));
		modelo.setInfoAux();
	}
}