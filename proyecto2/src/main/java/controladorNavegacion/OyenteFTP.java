package controladorNavegacion;

import controladorPrincipal.CoPrincipal;

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
import vista.VistaLogin;
import vista.VistaCRUD;
import vista.VistaAdmin;
import vista.VistaGeneralCorreo;
import vista.VistaGestorArchivos;
import vista.VistaLogs;
import vista.VistaMenuPrincipal;
import vista.VistaRegistroUsuarios;
import controladorLogs.ControladorLogs;
import controladorLogs.GestionLogs;

/**
 * Oyente principal que maneja la l�gica de navegaci�n y eventos generales de la
 * aplicaci�n.
 * Gestiona el inicio de sesi�n, la navegaci�n entre men�s y llamadas a otros
 * controladores.
 */
public class OyenteFTP implements ActionListener {
	/** Vista de login. */
	private VistaLogin vistaLogin;

	/** Vista CRUD. */
	private VistaCRUD vistaCRUD;

	/** Modelo del cliente FTP. */
	private ModeloClienteFTP modelo;

	/** Modelo de vista para estado compartido. */
	private MoView modeloVista;

	/** Controlador principal de la aplicaci�n. */
	private CoPrincipal controladorPrincipal;

	/** Vista del gestor de archivos. */
	private VistaGestorArchivos vistaArchivo;

	/** Vista del men� principal. */
	private VistaMenuPrincipal vistaMenuPrincipal;

	/** Vista de administraci�n. */
	private VistaAdmin vistaAdmin;

	/** Vista de registro de usuarios. */
	private VistaRegistroUsuarios vistaUsuarios;

	/** Vista general de correo. */
	private VistaGeneralCorreo vistaGeneralCorreo;

	/** Vista de logs. */
	private VistaLogs vistaLogs;

	/** Modelo de base de datos. */
	private ModeloBaseDatos modeloBaseDatos;

	/** Controlador de logs. */
	private ControladorLogs controladorLogs;

	/**
	 * Constructor del oyente principal.
	 *
	 * @param modeloVista        Modelo de vista para estado compartido.
	 * @param vistaLogin         Vista de login.
	 * @param vistaCRUD          Vista CRUD.
	 * @param modelo             Modelo del cliente FTP.
	 * @param ctrl               Controlador principal.
	 * @param vistaArchivo       Vista del gestor de archivos.
	 * @param vistaMenuPrincipal Vista del men� principal.
	 * @param vistaAdmin         Vista de administraci�n.
	 * @param vistaUsuarios      Vista de registro de usuarios.
	 * @param vistaLogs          Vista de logs.
	 * @param modeloBaseDatos    Modelo de base de datos.
	 */
	public OyenteFTP(MoView modeloVista, VistaLogin vistaLogin, VistaCRUD vistaCRUD, ModeloClienteFTP modelo,
			CoPrincipal ctrl,
			VistaGestorArchivos vistaArchivo, VistaMenuPrincipal vistaMenuPrincipal, VistaAdmin vistaAdmin,
			VistaRegistroUsuarios vistaUsuarios, VistaLogs vistaLogs, ModeloBaseDatos modeloBaseDatos) {
		this.vistaLogin = vistaLogin;
		this.vistaCRUD = vistaCRUD;
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

	/**
	 * Maneja los eventos de los botones del men� principal, login y panel
	 * administrativo.
	 *
	 * @param e El evento de acci�n detectado.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == vistaLogin.getBotones().get(0)) {
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
		} else if (source == controladorPrincipal.getViCrearRol().getBotones().get(0)) {
			controladorPrincipal.getControladorRoles().mostrarDialogoAgregarRol();
		} else if (source == controladorPrincipal.getVistaAsignarRol().getBotones().get(1)) {
			manejarDesasignar();
		} else if (source == controladorPrincipal.getVistaAsignarRol().getBotones().get(0)) {
			controladorPrincipal.getControladorRoles().asignarRolAUsuarios(
					modeloVista.getCorreoSeleccionados(),
					(Rol) controladorPrincipal.getVistaAsignarRol().getComboRoles().getSelectedItem());
		} else if (source == vistaAdmin.getBotonAsignarRoles()) {
			abrirAsignarRoles();
		} else if (source == vistaAdmin.getBotonVolver()) {
			volverMenuPrincipal();
		} else if (source == controladorPrincipal.getViCrearRol().getBotones().get(1)
				|| source == controladorPrincipal.getVistaAsignarRol().getBotones().get(2)) {
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

	/**
	 * Abre la vista de administraci�n de usuarios.
	 */
	private void abrirAdministrarUsuarios() {
		vistaUsuarios.hacerVisible();
		vistaAdmin.setVisible(false);
	}

	/**
	 * Abre la vista de asignaci�n de roles.
	 */
	private void abrirAsignarRoles() {
		controladorPrincipal.getControladorRoles().rellenarComboRoles();
		controladorPrincipal.getControladorRoles().rellenarTablaUsuarios();
		controladorPrincipal.getVistaAsignarRol().setVisible(true);
		vistaAdmin.setVisible(false);
	}

	/**
	 * Abre la vista de correos electr�nicos.
	 */
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

	/**
	 * Abre la vista de creaci�n de roles.
	 */
	private void abrirCrearRol() {
		controladorPrincipal.getControladorRoles().rellenarVentanaCrearRol();
		controladorPrincipal.getViCrearRol().hacerVisible();
		vistaAdmin.setVisible(false);
	}

	/**
	 * Abre el gestor de archivos FTP.
	 */
	private void abrirFileManager() {
		vistaArchivo.hacerVisible();
		vistaMenuPrincipal.setVisible(false);
	}

	/**
	 * Abre la vista de logs.
	 */
	private void abrirLogs() {
		vistaAdmin.setVisible(false);
		if (controladorLogs == null) {
			controladorLogs = new ControladorLogs(ModeloBaseDatos.getConexion(), vistaLogs);
		}
		controladorLogs.mostrar();
	}

	/**
	 * Abre la vista de gesti�n de datos (CRUD).
	 */
	private void abrirManageData() {
		vistaCRUD.setVisible(false);
		vistaLogin.setVisible(true);
		vistaLogin.setVisible(false);
		vistaCRUD.setVisible(true);
		vistaMenuPrincipal.setVisible(false);
		controladorPrincipal.getControladorCRUD().rellenarTabla("animales");
	}

	/**
	 * Abre la vista de gesti�n de whitelist.
	 */
	private void abrirWhitelist() {
		controladorPrincipal.getControladorWhitelist().rellenarTablaWhitelist();
		controladorPrincipal.getVistaWhitelist().hacerVisible();
		vistaAdmin.setVisible(false);
	}

	/**
	 * Maneja la acci�n de desasignar rol si la vista est� activa.
	 */
	private void manejarDesasignar() {
		if (controladorPrincipal.getVistaAsignarRol().isVisible()) {
			controladorPrincipal.getControladorRoles().desasignarRol(
					modeloVista.getCorreoSeleccionados(),
					(Rol) controladorPrincipal.getVistaAsignarRol().getComboRoles().getSelectedItem());
		}
	}

	/**
	 * Realiza el proceso de inicio de sesi�n.
	 * Valida campos, conecta al servidor FTP y verifica credenciales.
	 */
	private void login() {
		if (vistaLogin.getCajas().get(0).getText().trim().isEmpty()
				|| vistaLogin.getCajas().get(1).getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(vistaLogin, MoTextos.msg_fill_all_fields,
					MoTextos.msg_error_title,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String usuario = vistaLogin.getCajas().get(0).getText();
		String contrasenia = vistaLogin.getCajas().get(1).getText();
		modelo.setUser(usuario);
		modelo.setPass(contrasenia);
		// vistaArchivo.inicializarFileManager();

		try {
			modelo.establecerConexion();
			if (modelo.getCliente().login(usuario, contrasenia)) {
				GestionLogs.writeLog(
						new Log("Login, correct credentials", modeloBaseDatos.obtenerEmailPorUsuario(usuario), true));
				vistaMenuPrincipal.hacerVisible();
				vistaLogin.setVisible(false);
			} else {
				GestionLogs.writeLog(new Log("Login, incorrect credentials", "", false));
				JOptionPane.showMessageDialog(vistaLogin, MoTextos.msg_incorrect_creds,
						MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(vistaLogin,
					MoTextos.msg_connection_error + ": " + ex.getMessage(), MoTextos.msg_error_title,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Cierra la sesi�n del usuario y vuelve a la pantalla de login.
	 */
	private void logOut() {
		modelo.desconectar();
		vistaMenuPrincipal.setVisible(false);
		vistaLogin.setVisible(true);
		vistaLogin.getCajas().get(0).setText("");
		vistaLogin.getCajas().get(1).setText("");
		vistaCRUD.setVisible(false);
	}

	/**
	 * Maneja la acci�n de volver desde las vistas de roles.
	 */
	private void manejarVolver() {
		if (controladorPrincipal.getViCrearRol().isVisible() || controladorPrincipal.getVistaAsignarRol().isVisible()) {
			volverAdminDesdeRoles();
		}
	}

	/**
	 * Verifica si el usuario tiene permisos de administrador.
	 * Si es administrador, abre el panel de administraci�n.
	 */
	private void verificarAdministrador() {
		String sql = "SELECT u.* FROM usuarios u JOIN usuarios_roles ur ON u.email = ur.email_usuario WHERE u.email = ? AND ur.roles_id = 3;";
		String email = modeloBaseDatos.obtenerEmailPorUsuario(modelo.getUser());
		if (modeloBaseDatos.existeRegistro(sql, new ArrayList<String>(Arrays.asList(email)))) {
			vistaAdmin.hacerVisible();
			vistaMenuPrincipal.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(vistaLogin, MoTextos.msg_not_admin,
					MoTextos.msg_error_title,
					JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Vuelve al panel de administraci�n desde la gesti�n de roles.
	 */
	private void volverAdminDesdeRoles() {
		controladorPrincipal.getViCrearRol().setVisible(false);
		controladorPrincipal.getVistaAsignarRol().setVisible(false);
		vistaAdmin.hacerVisible();
	}

	/**
	 * Vuelve al men� principal desde el panel de administraci�n.
	 */
	private void volverMenuPrincipal() {
		vistaAdmin.setVisible(false);
		vistaMenuPrincipal.hacerVisible();
	}

	// --- GETTERS Y SETTERS ---

	public VistaLogin getVistaLogin() {
		return vistaLogin;
	}

	public void setVistaLogin(VistaLogin vistaLogin) {
		this.vistaLogin = vistaLogin;
	}

	public MoView getModeloVista() {
		return modeloVista;
	}

	public ControladorLogs getControladorLogs() {
		return controladorLogs;
	}

	public void setControladorLogs(ControladorLogs controladorLogs) {
		this.controladorLogs = controladorLogs;
	}
}