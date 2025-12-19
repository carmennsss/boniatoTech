package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import vista.VistaWhitelist;

/**
 * Oyente que maneja los eventos de usuario en la vista de Whitelist (clics en
 * botones y tabla).
 */
public class OyenteWhitelist implements ActionListener, MouseListener {

    private VistaWhitelist vista;
    private ControladorWhitelist controller;

    /**
     * Constructor del oyente de whitelist.
     *
     * @param controller Controlador de whitelist.
     * @param vista      Vista de whitelist.
     */
    public OyenteWhitelist(ControladorWhitelist controller, VistaWhitelist vista) {
        this.controller = controller;
        this.vista = vista;
    }

    /**
     * Maneja los eventos de acción (clics en botones).
     *
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == vista.getBotones().get(0)) {
            controller.anadirUsuario();
        } else if (source == vista.getBotones().get(1)) {
            controller.desasignarUsuarios();
        } else if (source == vista.getBotones().get(2)) {
            controller.volver();
        }
    }

    /**
     * Maneja los clics del ratón en la tabla para seleccionar usuarios.
     *
     * @param e El evento de ratón.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JTable) {
            JTable tabla = (JTable) e.getSource();
            int fila = tabla.rowAtPoint(e.getPoint());
            if (fila != -1) {
                String email = (String) tabla.getValueAt(fila, 0);
                controller.seleccionarUsuario(email, fila);
                tabla.repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
