package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import modelo.MoView;
import modelo.ModeloBaseDatos;
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
	private ModeloBaseDatos modeloBaseDatos;

	public OyenteFTP(MoView modeloVista, ViMain viMain, ModeloClienteFTP modelo, CoPrincipal ctrl,
			VistaGestorArchivos vistaArchivo,
			VistaMenuPrincipal vistaMenuPrincipal, VistaAdmin vistaAdmin, VistaRegistroUsuarios vistaUsuarios,
			ModeloBaseDatos modeloBaseDatos) {
		this.viMain = viMain;
		this.modelo = modelo;
		this.controladorPrincipal = ctrl;
		this.vistaArchivo = vistaArchivo;
		this.modeloVista = modeloVista;
		this.vistaMenuPrincipal = vistaMenuPrincipal;
		this.vistaAdmin = vistaAdmin;
		this.vistaUsuarios = vistaUsuarios;
		this.modeloBaseDatos = modeloBaseDatos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String command = button.getText();

		switch (command.toLowerCase()) {
			case "log in":
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
			case "manage users":
				abrirAdministrarUsuarios();
				break;
			case "manage roles":
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
			case "assign roles":
				abrirAsignarRoles();
				break;
			case "main menu":
				volverMenuPrincipal();
				break;
			case "back":
				volverAdminDesdeUsuarios();
				break;
			case "volver":
				volverAdminDesdeRoles();
				break;
			case "log out":
				logOut();
				break;
			default:
				break;
		}
	}
	
	private void logOut() {
		modelo.desconectar();
		vistaMenuPrincipal.setVisible(false);
		viMain.setVisible(true);
		viMain.getPanelLogin().getCajas().get(0).setText("");
		viMain.getPanelLogin().getCajas().get(1).setText("");
		viMain.mostrarLogin();
		
	}

	private void volverMenuPrincipal() {
		vistaAdmin.setVisible(false);
		vistaMenuPrincipal.hacerVisible();
	}

	private void volverAdminDesdeUsuarios() {
		vistaUsuarios.setVisible(false);
		vistaAdmin.hacerVisible();
	}

	private void volverAdminDesdeRoles() {
		viMain.getViCrearRol().setVisible(false);
		viMain.getViAsignarRol().setVisible(false);
		vistaAdmin.hacerVisible();
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
		String sql = "SELECT u.* FROM usuarios u JOIN usuarios_roles ur ON u.email = ur.email_usuario WHERE u.email = ? AND ur.roles_id = 3;";
		String email = modeloBaseDatos.obtenerEmailPorUsuario(modelo.getUser());
		boolean existe = false;
		existe = modeloBaseDatos.existeRegistro(sql, new ArrayList<String>(Arrays.asList(email)));
		if (existe) {
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
		vistaAdmin.setVisible(false);
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
		} catch (org.apache.commons.net.ftp.FTPConnectionClosedException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(viMain.getPanelLogin(),
					"La conexión con el servidor se ha cerrado inesperadamente.\nPor favor, inténtelo de nuevo.",
					"Error de Conexión",
					JOptionPane.ERROR_MESSAGE);
			try {
				modelo.desconectar();
			} catch (Exception ex) {
				// Ignorar errores al desconectar si ya estaba cerrado
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(viMain.getPanelLogin(), "Error de conexión: " + ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(viMain.getPanelLogin(), "Ocurrió un error inesperado: " + ex.getMessage(),
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
