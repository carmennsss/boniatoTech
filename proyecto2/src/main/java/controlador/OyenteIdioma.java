package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import modelo.MoTextos;

public class OyenteIdioma implements ActionListener {

    private CoPrincipal coPrincipal;

    public OyenteIdioma(CoPrincipal coPrincipal) {
        this.coPrincipal = coPrincipal;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox<?> combo = (JComboBox<?>) e.getSource();
        int index = combo.getSelectedIndex();

        // 0 = English, 1 = Spanish
        MoTextos.setIdioma(index);

        // Update all views
        coPrincipal.actualizarIdiomaGlobal();

        // Verify output for debugging if needed
        System.out.println("Language switched to: " + (index == 0 ? "English" : "Spanish"));
    }
}
