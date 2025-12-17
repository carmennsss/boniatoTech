package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.MoView;
import modelo.ModeloClienteFTP;
import modelo.Rol;
import vista.ViMain;
import vista.VistaAdmin;
import vista.VistaGestorArchivos;
import vista.VistaMenuPrincipal;
import vista.VistaRegistroUsuarios;

public class OyenteFTP implements ActionListener {
	private ViMain viMain;
	private ModeloClienteFTP modelo;
	private MoView modeloVista;
	private CoPrincipal controladorPrincipal;
	private VistaGestorArchivos vistaArchivo;
	private VistaMenuPrincipal vistaMenuPrincipal;
	private VistaAdmin vistaAdmin;
	private VistaRegistroUsuarios vistaUsuarios;

	public OyenteFTP(MoView modeloVista, ViMain viMain, ModeloClienteFTP modelo, CoPrincipal ctrl,
			VistaGestorArchivos vistaArchivo,
			VistaMenuPrincipal vistaMenuPrincipal, VistaAdmin vistaAdmin, VistaRegistroUsuarios vistaUsuarios) {
		this.viMain = viMain;
		this.modelo = modelo;
		this.controladorPrincipal = ctrl;
		this.vistaArchivo = vistaArchivo;
		this.modeloVista = modeloVista;
		this.vistaMenuPrincipal = vistaMenuPrincipal;
		this.vistaAdmin = vistaAdmin;
		this.vistaUsuarios = vistaUsuarios;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String command = button.getText();

		switch (command.toLowerCase()) {
			case "enter":
				login();
				break;
			case "file manager":
				abrirFileManager();
				break;
			case "manage data":
				abrirManageData();
				break;
			case "administrate":
				verificarAdministrador();
				break;
			case "administrate users":
				abrirAdministrarUsuarios();
				break;
			case "administrate roles":
				abrirCrearRol();
				break;
			case "agregar rol":
				agregarRol();
				break;
			case "desasignar":
				desasignarRol();
				break;
			case "asignar":
				asignarRol();
				break;
			case "asign roles":
				abrirAsignarRoles();
				break;
			default:
				break;
		}
	}

	private void abrirFileManager() {
		vistaArchivo.hacerVisible();
		vistaMenuPrincipal.setVisible(false);
	}

	private void abrirManageData() {
		viMain.hacerVisible();
		viMain.mostrarCRUD();
		vistaMenuPrincipal.setVisible(false);
		controladorPrincipal.getControladorCRUD().rellenarTabla("animales");
	}

	private void verificarAdministrador() {
		if (modelo.getUser().equalsIgnoreCase("admin")) {
			vistaAdmin.hacerVisible();
			vistaMenuPrincipal.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(viMain.getPanelLogin(), "You are not the administrator", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void abrirAdministrarUsuarios() {
		vistaUsuarios.hacerVisible();
		vistaAdmin.setVisible(false);
	}

	private void abrirCrearRol() {
		controladorPrincipal.getControladorRoles().rellenarVentanaCrearRol();
		viMain.getViCrearRol().hacerVisible();
	}

	private void agregarRol() {
		if (viMain.getViCrearRol().mostrarAgregarRol() == 0) {
			controladorPrincipal.getControladorRoles().agregarRol();
		}
	}

	private void desasignarRol() {
		controladorPrincipal.getControladorRoles().asignarRol(false, modeloVista.getCorreoSeleccionados(),
				(Rol) viMain.getViAsignarRol().getComboRoles().getSelectedItem());
		limpiarSeleccionRoles();
	}

	private void asignarRol() {
		controladorPrincipal.getControladorRoles().asignarRol(true, modeloVista.getCorreoSeleccionados(),
				(Rol) viMain.getViAsignarRol().getComboRoles().getSelectedItem());
		limpiarSeleccionRoles();
	}

	private void limpiarSeleccionRoles() {
		modeloVista.getCorreoSeleccionados().clear();
		viMain.getViAsignarRol().getTabla().deseleccionarFilas();
		controladorPrincipal.getControladorRoles().rellenarTablaUsuarios();
	}

	private void abrirAsignarRoles() {
		controladorPrincipal.getControladorRoles().rellenarComboRoles();
		controladorPrincipal.getControladorRoles().rellenarTablaUsuarios();
		viMain.getViAsignarRol().setVisible(true);
		vistaAdmin.setVisible(false);
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
