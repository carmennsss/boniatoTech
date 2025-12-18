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

	public void cargarCorreos() {
		vistaGeneral.getBtnRefrescar().setEnabled(false);

		new Thread(() -> {
			try {
				System.out.println("Conectando con Gmail...");
				listaDescargada = obtenerCorreos();

				SwingUtilities.invokeLater(() -> {
					listaDescargadaAnterior = this.correos;
					this.correos.clear();
					this.correos.addAll(listaDescargada);
					vistaGeneral.cargarCorreos(this.correos);

					vistaGeneral.getBtnRefrescar().setEnabled(true);

					// Comparo lista anterior con la actual
					if (listaDescargadaAnterior.size() < listaDescargada.size()
							&& listaDescargadaAnterior.size() != 0) {

						controladorLogs.GestionLogs.writeLog(new Log("MAIL_RECEIVED", CORREO, true));

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
			}
		}).start();

	}

	public void detenerHiloRecepcion() {
		if (hiloRecepcion != null && hiloRecepcion.isAlive()) {
			hiloRecepcion.interrupt();
		}
	}

	public ArrayList<Correo> obtenerCorreos() {
		ArrayList<Correo> listaCorreos = gestion.recibirCorreosPOP3(HOST, HOSTIMAP, "recent:" + CORREO,
				PASSWORD_APLICACION);

		return listaCorreos;
	}

	private void configurarVistaGeneral() {
		vistaGeneral.getBotonEnviarCorreo()
				.addActionListener(new OyenteBotonEnviar(vistaGeneral.getCorreo(), PASSWORD_APLICACION, this));
		vistaGeneral.getEmailTabla()
				.addMouseListener(new OyenteTabla(vistaGeneral.getEmailTabla(), correos, this, CORREO));
		vistaGeneral.getBtnRefrescar().addActionListener(new OyenteRefrescarCorreo(this));
		vistaGeneral.getBtnVolver().addActionListener(new OyenteBotonVolver(vistaGeneral, vistaMenuPrincipal, this));
	}

	public void eliminarCorreoSeleccionado(Correo correo) {
		try {

			gestion.eliminarCorreoPOP3(HOST, "recent:" + CORREO, PASSWORD_APLICACION, correo);

			correos.remove(correo);
			vistaGeneral.cargarCorreos(correos);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

	public synchronized void actualizarListaDesdeHilo(ArrayList<Correo> nuevosCorreos) {
		this.correos.clear();
		this.correos.addAll(nuevosCorreos);

		vistaGeneral.cargarCorreos(this.correos);

		System.out.println("Lista de correos sincronizada. Total: " + this.correos.size());
	}

	public ArrayList<Correo> getListaCorreosActual() {
		return this.correos;
	}

	public String getPasswordAplicacion() {
		return PASSWORD_APLICACION;
	}

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

	public String getCORREO() {
		return CORREO;
	}

	public void setCORREO(String cORREO) {
		CORREO = cORREO;
	}

}