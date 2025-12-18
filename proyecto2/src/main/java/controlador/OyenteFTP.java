package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import modelo.Log;
import modelo.MoTextos;
import modelo.MoView;
import modelo.ModeloBaseDatos;
import modelo.ModeloClienteFTP;
import modelo.Rol;
import vista.ViMain;
import vista.VistaAdmin;
import vista.VistaGeneralCorreo;
import vista.VistaGestorArchivos;
import vista.VistaLogs;
import vista.VistaMenuPrincipal;
import vista.VistaRegistroUsuarios;
import controladorLogs.ControladorLogs;
import controladorLogs.GestionLogs;

public class OyenteFTP implements ActionListener {
	private ViMain viMain;
	private ModeloClienteFTP modelo;
	private MoView modeloVista;
	private CoPrincipal controladorPrincipal;
	private VistaGestorArchivos vistaArchivo;
	private VistaMenuPrincipal vistaMenuPrincipal;
	private VistaAdmin vistaAdmin;
	private VistaRegistroUsuarios vistaUsuarios;
	private VistaGeneralCorreo vistaGeneralCorreo;
	private VistaLogs vistaLogs;
	private ModeloBaseDatos modeloBaseDatos;
	private ControladorLogs controladorLogs;

	public OyenteFTP(MoView modeloVista, ViMain viMain, ModeloClienteFTP modelo, CoPrincipal ctrl,
			VistaGestorArchivos vistaArchivo, VistaMenuPrincipal vistaMenuPrincipal, VistaAdmin vistaAdmin,
			VistaRegistroUsuarios vistaUsuarios, VistaLogs vistaLogs, ModeloBaseDatos modeloBaseDatos) {
		this.viMain = viMain;
		this.modelo = modelo;
		this.controladorPrincipal = ctrl;
		this.vistaArchivo = vistaArchivo;
		this.modeloVista = modeloVista;
		this.vistaMenuPrincipal = vistaMenuPrincipal;
		this.vistaAdmin = vistaAdmin;
		this.vistaUsuarios = vistaUsuarios;
		this.vistaLogs = vistaLogs;
		this.modeloBaseDatos = modeloBaseDatos;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == viMain.getPanelLogin().getBotones().get(0)) {
			login();
			controladorPrincipal.instanciarCorreos();
		} else if (source == vistaMenuPrincipal.getBotonFileManager()) {
			abrirFileManager();
		} else if (source == vistaMenuPrincipal.getBotonCRUD()) {
			abrirManageData();
		} else if (source == vistaMenuPrincipal.getBotonAdmin()) {
			verificarAdministrador();
		} else if (source == vistaAdmin.getBotonCrearUsuario()) {
			abrirAdministrarUsuarios();
		} else if (source == vistaAdmin.getBotonCrearRoles()) {
			abrirCrearRol();
		} else if (source == viMain.getViCrearRol().getBotones().get(0)) {
			agregarRol();
		} else if (source == viMain.getViAsignarRol().getBotones().get(1)) {
			manejarDesasignar();
		} else if (source == viMain.getViAsignarRol().getBotones().get(0)) {
			asignarRol();
		} else if (source == vistaAdmin.getBotonAsignarRoles()) {
			abrirAsignarRoles();
		} else if (source == vistaAdmin.getBotonVolver()) {
			volverMenuPrincipal();
		} else if (source == viMain.getViCrearRol().getBotones().get(1)
				|| source == viMain.getViAsignarRol().getBotones().get(2)) {
			manejarVolver();
		} else if (source == vistaAdmin.getBotonLogs()) {
			abrirLogs();
		} else if (source == vistaAdmin.getBotonWhitelist()) {
			abrirWhitelist();
		} else if (source == vistaMenuPrincipal.getBotonCerrarSesion()) {
			logOut();
		} else if (source == vistaMenuPrincipal.getBotonCorreo()) {
			abrirCorreo();
		}
	}

	private void abrirLogs() {
		vistaAdmin.setVisible(false);
		if (controladorLogs == null) {
			controladorLogs = new ControladorLogs(ModeloBaseDatos.getConexion(), vistaLogs);
		}
		controladorLogs.mostrar();
	}

	private void abrirCorreo() {

		VistaGeneralCorreo vistaCorr = controladorPrincipal.getVistaGeneralCorreo();

		if (vistaCorr != null) {
			vistaMenuPrincipal.setVisible(false);
			vistaCorr.hacerVisible();
			controladorPrincipal.getControladorCorreos().cargarCorreos();
		} else {
			JOptionPane.showMessageDialog(null, MoTextos.msg_err_mail_init);
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

	private void volverMenuDesdeCorreos() {
		controladorPrincipal.getVistaGeneralCorreo().setVisible(false);
		vistaMenuPrincipal.hacerVisible();
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
			JOptionPane.showMessageDialog(viMain.getPanelLogin(), MoTextos.msg_not_admin,
					MoTextos.msg_error_title,
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

	private void abrirWhitelist() {
		controladorPrincipal.getControladorWhitelist().rellenarTablaWhitelist();
		viMain.getViWhitelist().hacerVisible();
		vistaAdmin.setVisible(false);
	}

	private void manejarDesasignar() {
		if (viMain.getViAsignarRol().isVisible()) {
			desasignarRol();
		}
	}

	private void manejarVolver() {
		if (viMain.getViCrearRol().isVisible() || viMain.getViAsignarRol().isVisible()) {
			volverAdminDesdeRoles();
		}
	}

	private void login() {
		if (viMain.getPanelLogin().getCajas().get(0).getText().trim().isEmpty()
				|| viMain.getPanelLogin().getCajas().get(1).getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(viMain.getPanelLogin(), MoTextos.msg_fill_all_fields,
					MoTextos.msg_error_title,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String usuario = viMain.getPanelLogin().getCajas().get(0).getText();
		String contrasenia = viMain.getPanelLogin().getCajas().get(1).getText();
		modelo.setUser(usuario);
		modelo.setPass(contrasenia);

		try {
			modelo.establecerConexion();
			if (modelo.getCliente().login(usuario, contrasenia)) {
				GestionLogs.writeLog(
						new Log("Login, correct credentials", modeloBaseDatos.obtenerEmailPorUsuario(usuario), true));
				vistaMenuPrincipal.hacerVisible();
				viMain.setVisible(false);

			} else {
				GestionLogs.writeLog(new Log("Login, incorrect credentials", "", false));
				JOptionPane.showMessageDialog(viMain.getPanelLogin(), MoTextos.msg_incorrect_creds,
						MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
				System.out.println(modelo.getCliente().getReplyString());
			}
		} catch (org.apache.commons.net.ftp.FTPConnectionClosedException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(viMain.getPanelLogin(),
					MoTextos.msg_connection_error + "\n" + e.getMessage(),
					MoTextos.msg_error_title,
					JOptionPane.ERROR_MESSAGE);
			try {
				modelo.desconectar();
			} catch (Exception ex) {
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(viMain.getPanelLogin(),
					MoTextos.msg_connection_error + ": " + ex.getMessage(), MoTextos.msg_error_title,
					JOptionPane.ERROR_MESSAGE);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(viMain.getPanelLogin(),
					MoTextos.msg_unexpected_error_prefix + ex.getMessage(),
					MoTextos.msg_error_title,
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
