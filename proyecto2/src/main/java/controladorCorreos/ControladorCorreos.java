package controladorCorreos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import controladorLogs.GestionLogs;
import modelo.Correo;
import modelo.Log;
import modelo.ModeloBaseDatos;
import vista.VistaGeneralCorreo;
import vista.VistaMenuPrincipal;

/**
 * Controlador principal para la gestión de correos electrónicos.
 * Maneja la recepción (POP3/IMAP), envío, eliminación y visualización de
 * correos.
 */
public class ControladorCorreos {

	private String CORREO;
	private String PASSWORD_APLICACION;
	private static final String HOST = "pop.gmail.com";
	private static final String HOSTIMAP = "imap.gmail.com";
	private ArrayList<Correo> correos = new ArrayList<>();
	private VistaGeneralCorreo vistaGeneral;
	private static GestionCorreos gestion;
	private Thread hiloRecepcion;
	private ModeloBaseDatos db;
	private VistaMenuPrincipal vistaMenuPrincipal;
	private ArrayList<Correo> listaDescargada;
	private ArrayList<Correo> listaDescargadaAnterior;

	public ControladorCorreos(String CORREO, VistaGeneralCorreo vistaGeneral, ModeloBaseDatos bd,
			VistaMenuPrincipal vistaMenu) {
		this.vistaGeneral = vistaGeneral;
		this.vistaMenuPrincipal = vistaMenu;
		this.db = bd;
		this.CORREO = CORREO;
		this.PASSWORD_APLICACION = obtenerClaveCorreoPorUsuario(CORREO);
		gestion = new GestionCorreos();
		configurarVistaGeneral();

	}

	/**
	 * Obtiene la contraseña de aplicación de Gmail almacenada en la base de datos
	 * para un usuario.
	 *
	 * @param correo El correo del usuario.
	 * @return La clave de aplicación o null si no se encuentra.
	 */
	private String obtenerClaveCorreoPorUsuario(String correo) {
		String contrasenaAplicacion = null;
		try {
			String sql = "SELECT clave_correo FROM usuarios WHERE email = ?";
			Connection conexion = db.getConexion();

			PreparedStatement ps = conexion.prepareStatement(sql);

			ps.setString(1, correo);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				contrasenaAplicacion = rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contrasenaAplicacion;
	}

	/**
	 * Inicia el proceso de carga de correos en un hilo secundario.
	 * Actualiza la interfaz gráfica una vez descargados los mensajes.
	 */
	public void cargarCorreos() {

		vistaGeneral.getBtnRefrescar().setEnabled(false);

		// Guardamos cantidad ANTES del refresco
		int cantidadAnterior = this.correos.size();

		new Thread(() -> {
			try {
				System.out.println("Conectando con Gmail...");
				listaDescargada = obtenerCorreos();

				SwingUtilities.invokeLater(() -> {
					int cantidadActual = listaDescargada.size();
					this.correos.clear();
					this.correos.addAll(listaDescargada);
					vistaGeneral.cargarCorreos(this.correos);
					vistaGeneral.getBtnRefrescar().setEnabled(true);
					if (cantidadActual > cantidadAnterior && cantidadAnterior != 0) {

						GestionLogs.writeLog(new Log("MAIL_RECEIVED", CORREO, true));
					}
					if (hiloRecepcion == null || !hiloRecepcion.isAlive()) {

						HiloRecepcionCorreos hilo = new HiloRecepcionCorreos(gestion, HOST, "recent:" + CORREO,
								PASSWORD_APLICACION, vistaGeneral, this);
						hiloRecepcion = new Thread(hilo, "Hilo-Recepcion-Correos");
						hiloRecepcion.start();
					}
				});

			} catch (Exception e) {
				SwingUtilities.invokeLater(() -> vistaGeneral.getBtnRefrescar().setEnabled(true));
				e.printStackTrace();
			}
		}).start();

	}

	/**
	 * Detiene el hilo de recepción de correos si está en ejecución.
	 */
	public void detenerHiloRecepcion() {
		if (hiloRecepcion != null && hiloRecepcion.isAlive()) {
			hiloRecepcion.interrupt();
		}
	}

	/**
	 * Obtiene la lista de correos del servidor mediante POP3.
	 *
	 * @return Lista de correos recibidos.
	 */
	public ArrayList<Correo> obtenerCorreos() {
		ArrayList<Correo> listaCorreos = gestion.recibirCorreosPOP3(HOST, HOSTIMAP, "recent:" + CORREO,
				PASSWORD_APLICACION);

		return listaCorreos;
	}

	/**
	 * Configura los escuchadores de los botones y la tabla en la vista general.
	 */
	private void configurarVistaGeneral() {
		vistaGeneral.getBotonEnviarCorreo()
				.addActionListener(new OyenteBotonEnviar(vistaGeneral.getCorreo(), PASSWORD_APLICACION, this));
		vistaGeneral.getEmailTabla()
				.addMouseListener(new OyenteTabla(vistaGeneral.getEmailTabla(), correos, this, CORREO));
		vistaGeneral.getBtnRefrescar().addActionListener(new OyenteRefrescarCorreo(this));
		vistaGeneral.getBtnVolver().addActionListener(new OyenteBotonVolver(vistaGeneral, vistaMenuPrincipal, this));
	}

	/**
	 * Elimina un correo seleccionado tanto de la lista local como del servidor
	 * POP3.
	 *
	 * @param correo El correo a eliminar.
	 */
	public void eliminarCorreoSeleccionado(Correo correo) {
		try {

			gestion.eliminarCorreoPOP3(HOST, "recent:" + CORREO, PASSWORD_APLICACION, correo);

			correos.remove(correo);
			vistaGeneral.cargarCorreos(correos);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Marca un correo como leído en el servidor IMAP y actualiza la vista.
	 *
	 * @param correo El correo a marcar como leído.
	 */
	public synchronized void marcarCorreoLeido(Correo correo) {
		try {
			String idParaMarcar = correo.getMessageId();

			gestion.marcarLeidoIMAP(HOSTIMAP, CORREO, PASSWORD_APLICACION, idParaMarcar);

			correo.setLeido(true);
			javax.swing.SwingUtilities.invokeLater(() -> {
				vistaGeneral.cargarCorreos(correos);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Marca un correo como no leído en el servidor IMAP y actualiza la vista.
	 *
	 * @param correo El correo a marcar como no leído.
	 */
	public synchronized void marcarCorreoNoLeido(Correo correo) {
		try {
			String idParaMarcar = correo.getMessageId();

			gestion.marcarNoLeidoIMAP(HOSTIMAP, CORREO, PASSWORD_APLICACION, idParaMarcar);

			correo.setLeido(false);
			javax.swing.SwingUtilities.invokeLater(() -> {
				vistaGeneral.cargarCorreos(correos);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Actualiza la lista de correos local con nuevos correos recibidos desde el
	 * hilo de recepción.
	 *
	 * @param nuevosCorreos Lista de nuevos correos.
	 */
	public synchronized void actualizarListaDesdeHilo(ArrayList<Correo> nuevosCorreos) {
		this.correos.clear();
		this.correos.addAll(nuevosCorreos);

		vistaGeneral.cargarCorreos(this.correos);

		System.out.println("Lista de correos sincronizada. Total: " + this.correos.size());
	}

	/**
	 * Devuelve la lista actual de correos almacenados localmente.
	 * 
	 * @return Lista de correos.
	 */
	public ArrayList<Correo> getListaCorreosActual() {
		return this.correos;
	}

	/**
	 * Obtiene la contraseña de aplicación utilizada para la autenticación.
	 * 
	 * @return Contraseña de aplicación.
	 */
	public String getPasswordAplicacion() {
		return PASSWORD_APLICACION;
	}

	/**
	 * Comprueba si un receptor está en la lista blanca o es un usuario registrado.
	 *
	 * @param receptor Email del receptor.
	 * @return true si es válido, false en caso contrario.
	 */
	public boolean comprobarReceptorWhiteList(String receptor) {
		ArrayList<String> whitelist = new ArrayList<>();
		try {
			Connection conexion = db.getConexion();

			Statement sentencia = conexion.createStatement();

			String sql = "SELECT email AS correo FROM usuarios UNION SELECT correo FROM whitelist";

			ResultSet rs = sentencia.executeQuery(sql);

			while (rs.next()) {
				whitelist.add(rs.getString(1));
			}

			if (whitelist.contains(receptor)) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Obtiene el correo electrónico del usuario actual.
	 * 
	 * @return Dirección de correo.
	 */
	public String getCORREO() {
		return CORREO;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 * 
	 * @param cORREO Dirección de correo.
	 */
	public void setCORREO(String cORREO) {
		CORREO = cORREO;
	}

}