/**
 * Clase oyente tecla
 * 
 * @author Carmen
 * 10/12/2025
 */
package controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

import modelo.MoPrincipal;
import vista.ViPrincipal;

public class OyenteTecla implements KeyListener {
	private MoPrincipal modelo;
	private ViPrincipal vista;

	// Constructor
	public OyenteTecla(MoPrincipal modelo, ViPrincipal vista) {
		this.modelo = modelo;
		this.vista = vista;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// NO SE UTILIZA
	}

	@Override
	public void keyPressed(KeyEvent e) {
		comprobarContrasenia(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// NO SE UTILIZA
	}

	// Metodo que comprueba si el texto introducido es la contrasenia
	private void comprobarContrasenia(KeyEvent e) {
		JTextField caja = new JTextField();
		caja = (JTextField) e.getSource();

		// Si se pulsa enter (codigo 10), comprueba si es igual
		if (e.getKeyCode() == 10 && caja.getText().equals(modelo.getContrasenia())) {
			caja.setText("");
			vista.mostrarTabla(); // Si lo es, muestra la tabla
		}
	}
}
