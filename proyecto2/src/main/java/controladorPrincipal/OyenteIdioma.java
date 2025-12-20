package controladorPrincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import modelo.MoTextos;

/**
 * Oyente para el cambio de idioma a través del ComboBox en la interfaz.
 * Detecta cuando el usuario selecciona un nuevo idioma y actualiza todas las
 * vistas.
 */
public class OyenteIdioma implements ActionListener {

    /** Controlador principal de la aplicación. */
    private CoPrincipal coPrincipal;

    /**
     * Constructor del oyente de idioma.
     *
     * @param coPrincipal Controlador principal de la aplicación.
     */
    public OyenteIdioma(CoPrincipal coPrincipal) {
        this.coPrincipal = coPrincipal;
    }

    /**
     * Detecta la selección de un nuevo idioma y actualiza la configuración global.
     *
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<?> combo = (JComboBox<?>) e.getSource();
        int index = combo.getSelectedIndex();

        MoTextos.setIdioma(index);

        coPrincipal.actualizarIdiomaGlobal();

        System.out.println("Language switched to: " + (index == 0 ? "English" : "Spanish"));
    }
}
