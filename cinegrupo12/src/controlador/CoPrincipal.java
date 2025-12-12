/**
 * Clase controlador principal
 * 
 * @author Grupo 1
 * 10/12/2025
 */
package controlador;

import modelo.MoPrincipal;
import modelo.MoVentana;
import vista.ViPrincipal;

public class CoPrincipal {
	private MoPrincipal modelo;
	private ViPrincipal vista;
	private MoVentana modeloVista;

	// constructor
	public CoPrincipal() {
		this.modeloVista = new MoVentana();
		modelo = new MoPrincipal();
		vista = new ViPrincipal(modeloVista, modelo);

		comenzar();
	}

	// Metodo para activar la visibilidad de la ventana y la activacion de eventos
	// de las teclas y los botones
	private void comenzar() {
		vista.hacerVisible(true);
		activarEventoTecla();
		activarEventoBotones();
	}

	// Activacion de los eventos de las teclas
	private void activarEventoTecla() {
		vista.getPanelesCont().get(0).getCajas().get(0).addKeyListener(new OyenteTecla(this.modelo, this.vista));
	}

	// Activacion de los eventos de los botones
	private void activarEventoBotones() {
		OyenteClick oyente = new OyenteClick(this.modelo, this.vista, this.modeloVista);
		for (int i = 0; i < vista.getPanelesSala().size(); i++) {
			for (int j = 0; j < modeloVista.getNumAsientos(); j++) {
				// Activacion de los botones asientos
				vista.getPanelesSala().get(i).getAsientos().get(j).addActionListener(oyente);
			}
			// Activacion del boton acciones(comprar)
			vista.getPanelesSala().get(i).getAcciones().get(0).addActionListener(oyente);
		}
		// Activacion del boton para el formulario
		vista.getPanelesForm().get(0).getBoton().addActionListener(oyente);
	}
}
