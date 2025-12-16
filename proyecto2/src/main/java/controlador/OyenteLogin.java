package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import modelo.ModeloClienteFTP;
import vista.ViMain;
import vista.VistaGestorArchivos;
import vista.VistaMenuPrincipal;

public class OyenteLogin implements ActionListener {
	private ViMain viMain;
	private ModeloClienteFTP modelo;
	private CoPrincipal controladorPrincipal;
	private VistaGestorArchivos vistaArchivo;
	private VistaMenuPrincipal vistaMenuPrincipal;

	public OyenteLogin(ViMain viMain, ModeloClienteFTP modelo, CoPrincipal ctrl, VistaGestorArchivos vistaArchivo,
			VistaMenuPrincipal vistaMenuPrincipal) {
		this.viMain = viMain;
		this.modelo = modelo;
		this.controladorPrincipal = ctrl;
		this.vistaArchivo = vistaArchivo;
		this.vistaMenuPrincipal = vistaMenuPrincipal;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if (button.getText().equalsIgnoreCase("Enter")) {
			login();
		} else if (button.getText().equalsIgnoreCase("File Manager")) {
			vistaArchivo.hacerVisible();
			vistaMenuPrincipal.setVisible(false);
		} else if (button.getText().equalsIgnoreCase("Manage Data")) {
			viMain.hacerVisible();
			viMain.mostrarCRUD();
			vistaMenuPrincipal.setVisible(false);
			controladorPrincipal.rellenarTabla("animales");
		}
	}

	private void login() {
		if (viMain.getPanelLogin().getCajas().get(0).getText().trim().isEmpty()
				|| viMain.getPanelLogin().getCajas().get(1).getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(viMain.getPanelLogin(), "Por favor, rellene todos los campos", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String usuario = viMain.getPanelLogin().getCajas().get(0).getText();
		String contrasenia = viMain.getPanelLogin().getCajas().get(1).getText();
		modelo.setUser(usuario);
		modelo.setPass(contrasenia);
		vistaArchivo.inicializarFileManager();
		try {
			modelo.establecerConexion();
			if (modelo.getCliente().login(usuario, contrasenia)) {

				vistaMenuPrincipal.hacerVisible();
				viMain.setVisible(false);

			} else {
				JOptionPane.showMessageDialog(viMain.getPanelLogin(), "Credenciales incorrectas", "Error",
						JOptionPane.ERROR_MESSAGE);
				System.out.println(modelo.getCliente().getReplyString());
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(viMain.getPanelLogin(), "Error de conexión: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
