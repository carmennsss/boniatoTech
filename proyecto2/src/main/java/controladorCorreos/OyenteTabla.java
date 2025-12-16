package controladorCorreos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JTable;
import modelo.Correo;
import vista.VistaCorreoBase;

public class OyenteTabla implements MouseListener {
    
    private JTable emailTabla;
    private ArrayList<Correo> correos;
    private ControladorCorreos controladorCorreos;

    public OyenteTabla(JTable emailTabla, ArrayList<Correo> correos, ControladorCorreos controladorCorreos) {
        this.emailTabla = emailTabla;
        this.correos = correos;
        this.controladorCorreos = controladorCorreos;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    	if (e.getClickCount() == 2) {

            int filaSeleccionada = emailTabla.getSelectedRow(); 
            

            if (filaSeleccionada != -1 && !correos.isEmpty()) {
            	
                
                Correo correoSeleccionado = correos.get(filaSeleccionada);
                
                VistaCorreoBase vistaLectura = new VistaCorreoBase(correoSeleccionado);
                vistaLectura.getBotonEliminar().addActionListener(new OyenteBotonEliminar(correoSeleccionado, controladorCorreos, vistaLectura));
                vistaLectura.getBotonLeido().addActionListener(new OyenteBotonLeido(correoSeleccionado, controladorCorreos));
                vistaLectura.setVisible(true);
            }
        }
    }

    // M�todos vac�os obligatorios de la interfaz
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}