/**

 * Clase modelo ventana
 * 
 * @author Grupo 1
 * 10/12/2025
 */
package modelo;

import java.util.ArrayList;

public class MoVentana {
	private ArrayList<String> nombreFormulario;
	private ArrayList<Integer> tamanios;
	private ArrayList<String> textoBotones;
	private ArrayList<String> textoCombo;
	private ArrayList<String> textosTab;
	private String[] titulos;
	private int numAsientos = 30;
	private int numBotones = 1;
	private int numSesiones = 4;

	// Constructor
	public MoVentana() {
		this.nombreFormulario = new ArrayList<>();
		this.tamanios = new ArrayList<>();
		this.textoBotones = new ArrayList<>();
		this.titulos = new String[4];
		this.textoCombo = new ArrayList<>();
		this.textosTab = new ArrayList<>();

		// Llamadas para agregar los datos
		aniadirNombresForm();
		aniadirTamanios();
		agregarTextoCombo();
		ponerTextoBotones();
		agregarTitulosTabla();
		ponerTextosTab();
	}

	private void agregarTitulosTabla() {
		this.titulos[0] = "Nombre";
		this.titulos[1] = "Correo";
		this.titulos[2] = "Butacas compradas";
		this.titulos[3] = "Sesion";
	}

	private void agregarTextoCombo() {
		this.textoCombo.add("Sesion ");
	}

	private void ponerTextoBotones() {
		textoBotones.add("Comprar");
		textoBotones.add("Salir");
	}

	private void aniadirTamanios() {
		this.tamanios.add(25);
		this.tamanios.add(400);
		this.tamanios.add(300);
		this.tamanios.add(120);
		this.tamanios.add(40);
		this.tamanios.add(800);
		this.tamanios.add(500);
	}

	private void aniadirNombresForm() {
		this.nombreFormulario.add("Nombre: ");
		this.nombreFormulario.add("Correo: ");
		this.nombreFormulario.add("Contraseña: ");
		this.nombreFormulario.add("AGREGAR");
	}

	private void ponerTextosTab() {
		this.textosTab.add("Formulario");
		this.textosTab.add("Admin");
	}

	// GETTERS
	public ArrayList<String> getNombreFormulario() {
		return nombreFormulario;
	}

	public ArrayList<String> getTextoCombo() {
		return textoCombo;
	}

	public ArrayList<Integer> getTamanios() {
		return tamanios;
	}

	public ArrayList<String> getTextosTab() {
		return textosTab;
	}

	public ArrayList<String> getTextoBotones() {
		return textoBotones;
	}

	public int getNumAsientos() {
		return numAsientos;
	}

	public int getNumBotones() {
		return numBotones;
	}

	public int getNumSesiones() {
		return numSesiones;
	}

	public String[] getTitulos() {
		return titulos;
	}
}
