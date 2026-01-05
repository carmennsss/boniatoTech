/*
* @author Carmen - BoniatoTech
* @version 1.0
*/
package controladorPrincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import modelo.MoTextos;

/**
 * Oyente para el cambio de idioma a trav�s del ComboBox en la interfaz.
 * Detecta cuando el usuario selecciona un nuevo idioma y actualiza todas las
 * vistas.
 */
public class OyenteIdioma implements ActionListener {

	/** Controlador principal de la aplicaci�n. */
	private CoPrincipal coPrincipal;

	/**
	 * Constructor del oyente de idioma.
	 *
	 * @param coPrincipal Controlador principal de la aplicaci�n.
	 */
	public OyenteIdioma(CoPrincipal coPrincipal) {
		this.coPrincipal = coPrincipal;
	}

	/**
	 * Detecta la selecci�n de un nuevo idioma y actualiza la configuraci�n glo
	 * al.
	 *
	 * @param e El evento de acci�n.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox<String> combo = (JComboBox<String>) e.getSource();
		int index = combo.getSelectedIndex();

		// Actualiza el �ndice del idioma en el modelo de textos
		MoTextos.setIdioma(index);

		// Notifica al controlador principal para refrescar todas las vistas
		coPrincipal.actualizarIdiomaGlobal();
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene el controlador principal asociado.
	 * 
	 * @return El objeto CoPrincipal.
	 */
	public CoPrincipal getCoPrincipal() {
		return coPrincipal;
	}

	/**
	 * Establece el controlador principal asociado.
	 * 
	 * @param coPrincipal El nuevo controlador principal.
	 */
	public void setCoPrincipal(CoPrincipal coPrincipal) {
		this.coPrincipal = coPrincipal;
	}
}