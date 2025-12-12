/**
 * Clase sesion
 * 
 * @author Grupo 1
 * 12/12/2025
 */
package modelo;

import java.util.ArrayList;

public class MoSesion {
	private int numSesion;
	private ArrayList<String> asientosOcupados;
	
	// Constructor
	public MoSesion(int numSesion) {
		this.numSesion = numSesion;
		this.asientosOcupados = new ArrayList<>();
	}
	
	// GETTERS
	
	public int getNumSesion() {
		return numSesion;
	}
	public ArrayList<String> getAsientosOcupados() {
		return asientosOcupados;
	}
}
