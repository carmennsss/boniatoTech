/**
 * Clase modelo principal
 * 
 * @author Grupo 1
 * 10/12/2025
 */
package modelo;

import java.util.ArrayList;

public class MoPrincipal {
	private String contrasenia;
	private ArrayList<MoCliente> clientes;
	private ArrayList<MoSesion> sesiones;
	private ArrayList<String> infoAux;
	private int numSalas = 4;
	private int salaActual = 0;

	// Constructor
	public MoPrincipal() {
		// Array para guardar la informacion momentaneamente
		this.infoAux = new ArrayList<>();
		
		this.clientes = new ArrayList<>();
		this.sesiones = new ArrayList<>();
		this.contrasenia = "mariaenMiami";
		
		inicializarSesiones();
	}
	
	// Se inicializan sesiones dependiendo del numero de sesiones que se quiera
	private void inicializarSesiones() {
		for (int i=0; i<numSalas; i++) {
			this.sesiones.add(new MoSesion(i));
		}
	}
	
	// GETTERS Y SETTERS

	public ArrayList<MoSesion> getSesiones() {
		return sesiones;
	}

	public void setSesiones(ArrayList<MoSesion> sesiones) {
		this.sesiones = sesiones;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public ArrayList<MoCliente> getClientes() {
		return clientes;
	}

	public ArrayList<String> getInfoAux() {
		return infoAux;
	}

	public void setInfoAux() {
		this.infoAux.clear();
	}

	public int getSalaActual() {
		return salaActual;
	}

	public void setSalaActual(int salaActual) {
		this.salaActual = salaActual;
	}
}
