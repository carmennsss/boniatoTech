/**
 * Clase cliente
 * 
 * @author Grupo 1
 * 10/12/2025
 */
package modelo;

import java.util.ArrayList;

public class MoCliente {
	private String nombre;
	private String correo;
	private ArrayList<String> butacasCompradas;
	private int sesion;

	// Constructor
	public MoCliente(String nombre, String correo, ArrayList<String> butacasCompradas, int sesion) {
		this.nombre = nombre;
		this.correo = correo;
		this.butacasCompradas = butacasCompradas;
		this.sesion = sesion;
	}

	// GETTERS

	public String getNombre() {
		return nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public ArrayList<String> getButacasCompradas() {
		return butacasCompradas;
	}

	public int getSesion() {
		return sesion;
	}
}
