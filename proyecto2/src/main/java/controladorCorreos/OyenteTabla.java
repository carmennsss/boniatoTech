package controladorCorreos;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JTable;

import modelo.Correo;
import vista.VistaCorreoBase;

public class OyenteTabla implements MouseListener {
	
	private JTable emailTabla;
	private ArrayList<Correo> correos;

	public OyenteTabla(JTable emailTabla, ArrayList<Correo> correos) {
		this.emailTabla = emailTabla;
		this.correos = correos;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int fila = emailTabla.rowAtPoint(e.getPoint());
		Correo correo = correos.get(fila);
		VistaCorreoBase vistaBase = new VistaCorreoBase(correo);
		vistaBase.setVisible(true);
		

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
