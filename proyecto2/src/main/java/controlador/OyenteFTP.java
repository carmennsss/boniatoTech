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

/**
 * Oyente principal que maneja la lógica de navegación y eventos generales de la
 * aplicación.
 * Gestiona el inicio de sesión, la navegación entre menús y llamadas a otros
 * controladores.
 */
public class OyenteFTP implements ActionListener {
	private ViMain viMain;
	private ModeloClienteFTP modelo;
	private MoView modeloVista;
	private CoPrincipal controladorPrincipal;
	private VistaGestorArchivos vistaArchivo;
	private VistaMenuPrincipal vistaMenuPrincipal;
	private VistaAdmin vistaAdmin;
	private VistaRegistroUsuarios vistaUsuarios;
	private VistaLogs vistaLogs;
	private ModeloBaseDatos modeloBaseDatos;
	private ControladorLogs controladorLogs;

	/**
	 * Constructor del oyente principal.
	 *
	 * @param modeloVista        Modelo de vista para estado compartido.
	 * @param viMain             Vista principal.
	 * @param modelo             Modelo del cliente FTP.
	 * @param ctrl               Controlador principal.
	 * @param vistaArchivo       Vista del gestor de archivos.
	 * @param vistaMenuPrincipal Vista del menú principal.
	 * @param vistaAdmin         Vista de administración.
	 * @param vistaUsuarios      Vista de registro de usuarios.
	 * @param vistaLogs          Vista de logs.
	 * @param modeloBaseDatos    Modelo de base de datos.
	 */
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

	/**
	 * Maneja los eventos de los botones del menú principal, login y panel
	 * administrativo.
	 *
	 * @param e El evento de acción detectado.
	 */
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
	 * Abre la vista de correos electrónicos.
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
	 * Cierra la sesión del usuario y vuelve a la pantalla de login.
	 */
	private void logOut() {
		modelo.desconectar();
		vistaMenuPrincipal.setVisible(false);
		viMain.setVisible(true);
		viMain.getPanelLogin().getCajas().get(0).setText("");
		viMain.getPanelLogin().getCajas().get(1).setText("");
		viMain.mostrarLogin();

	}

	/**
	 * Vuelve al menú principal desde el panel de administración.
	 */
	private void volverMenuPrincipal() {
		vistaAdmin.setVisible(false);
		vistaMenuPrincipal.hacerVisible();
	}

	/**
	 * Vuelve al panel de administración desde la gestión de roles.
	 */
	private void volverAdminDesdeRoles() {
		viMain.getViCrearRol().setVisible(false);
		viMain.getViAsignarRol().setVisible(false);
		vistaAdmin.hacerVisible();
	}

	/**
	 * Abre el gestor de archivos FTP.
	 */
	private void abrirFileManager() {
		vistaArchivo.hacerVisible();
		vistaMenuPrincipal.setVisible(false);
	}

	/**
	 * Abre la vista de gestión de datos (CRUD).
	 */
	private void abrirManageData() {
		viMain.hacerVisible();
		viMain.mostrarCRUD();
		vistaMenuPrincipal.setVisible(false);
		controladorPrincipal.getControladorCRUD().rellenarTabla("animales");
	}

	/**
	 * Verifica si el usuario tiene permisos de administrador.
	 * Si es administrador, abre el panel de administración.
	 */
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

	/**
	 * Abre la vista de administración de usuarios.
	 */
	private void abrirAdministrarUsuarios() {
		vistaUsuarios.hacerVisible();
		vistaAdmin.setVisible(false);
	}

	/**
	 * Abre la vista de creación de roles.
	 */
	private void abrirCrearRol() {
		controladorPrincipal.getControladorRoles().rellenarVentanaCrearRol();
		viMain.getViCrearRol().hacerVisible();
		vistaAdmin.setVisible(false);
	}

	/**
	 * Muestra el diálogo para agregar un nuevo rol.
	 */
	private void agregarRol() {
		if (viMain.getViCrearRol().mostrarAgregarRol() == 0) {
			controladorPrincipal.getControladorRoles().agregarRol();
		}
	}

	/**
	 * Desasigna el rol seleccionado de los usuarios marcados.
	 */
	private void desasignarRol() {
		controladorPrincipal.getControladorRoles().asignarRol(false, modeloVista.getCorreoSeleccionados(),
				(Rol) viMain.getViAsignarRol().getComboRoles().getSelectedItem());
		limpiarSeleccionRoles();
	}

	/**
	 * Asigna el rol seleccionado a los usuarios marcados.
	 */
	private void asignarRol() {
		controladorPrincipal.getControladorRoles().asignarRol(true, modeloVista.getCorreoSeleccionados(),
				(Rol) viMain.getViAsignarRol().getComboRoles().getSelectedItem());
		limpiarSeleccionRoles();
	}

	/**
	 * Limpia la selección de usuarios y actualiza la tabla.
	 */
	private void limpiarSeleccionRoles() {
		modeloVista.getCorreoSeleccionados().clear();
		viMain.getViAsignarRol().getTabla().deseleccionarFilas();
		controladorPrincipal.getControladorRoles().rellenarTablaUsuarios();
	}

	/**
	 * Abre la vista de asignación de roles.
	 */
	private void abrirAsignarRoles() {
		controladorPrincipal.getControladorRoles().rellenarComboRoles();
		controladorPrincipal.getControladorRoles().rellenarTablaUsuarios();
		viMain.getViAsignarRol().setVisible(true);
		vistaAdmin.setVisible(false);
	}

	/**
	 * Abre la vista de gestión de whitelist.
	 */
	private void abrirWhitelist() {
		controladorPrincipal.getControladorWhitelist().rellenarTablaWhitelist();
		viMain.getViWhitelist().hacerVisible();
		vistaAdmin.setVisible(false);
	}

	/**
	 * Maneja la acción de desasignar rol si la vista está activa.
	 */
	private void manejarDesasignar() {
		if (viMain.getViAsignarRol().isVisible()) {
			desasignarRol();
		}
	}

	/**
	 * Maneja la acción de volver desde las vistas de roles.
	 */
	private void manejarVolver() {
		if (viMain.getViCrearRol().isVisible() || viMain.getViAsignarRol().isVisible()) {
			volverAdminDesdeRoles();
		}
	}

	/**
	 * Realiza el proceso de inicio de sesión.
	 * Valida campos, conecta al servidor FTP y verifica credenciales.
	 */
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
		vistaArchivo.inicializarFileManager();

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
