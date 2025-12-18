package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTable;
import vista.VistaWhitelist;

public class OyenteWhitelist implements ActionListener, MouseListener {

    private VistaWhitelist vista;
    private ControladorWhitelist controller;

    public OyenteWhitelist(ControladorWhitelist controller, VistaWhitelist vista) {
        this.controller = controller;
        this.vista = vista;
    }

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
