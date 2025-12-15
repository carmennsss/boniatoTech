package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import modelo.ModeloClienteFTP;
import vista.ViMain;

public class OyenteLogin implements ActionListener {
	private ViMain viMain;
	private ModeloClienteFTP modelo;
	private CoPrincipal controladorPrincipal;

	public OyenteLogin(ViMain viMain, ModeloClienteFTP modelo, CoPrincipal ctrl) {
		this.viMain = viMain;
		this.modelo = modelo;
		this.controladorPrincipal = ctrl;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (viMain.getPanelLogin().getCajas().get(0).getText().trim().isEmpty()
				|| viMain.getPanelLogin().getCajas().get(1).getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(viMain, "Por favor, rellene todos los campos", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String usuario = viMain.getPanelLogin().getCajas().get(0).getText();
		String contrasenia = viMain.getPanelLogin().getCajas().get(1).getText();

		try {
			modelo.establecerConexion();
			if (modelo.getCliente().login(usuario, contrasenia)) {
				viMain.mostrarCRUD();

				controladorPrincipal.rellenarTabla("animales");

			} else {
				JOptionPane.showMessageDialog(viMain, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
				System.out.println(modelo.getCliente().getReplyString());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(viMain, "Error de conexión: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
