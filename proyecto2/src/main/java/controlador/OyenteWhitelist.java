package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JTable;

public class OyenteWhitelist implements ActionListener, MouseListener {

    private ControladorWhitelist controller;

    public OyenteWhitelist(ControladorWhitelist controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        String comando = btn.getText().toLowerCase();

        switch (comando) {
            case "añadir":
                controller.anadirUsuario();
                break;
            case "desasignar":
                controller.desasignarUsuarios();
                break;
            case "volver":
                controller.volver();
                break;
            default:
                break;
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
