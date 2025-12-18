package controladorCorreos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.mail.Flags;
import javax.swing.SwingUtilities;

import modelo.Correo;
import modelo.ModeloBaseDatos;
import vista.VistaGeneralCorreo;

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

	public ControladorCorreos(String CORREO, VistaGeneralCorreo vistaGeneral, ModeloBaseDatos bd) {
		this.vistaGeneral = vistaGeneral;
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
				ArrayList<Correo> listaDescargada = obtenerCorreos();

				// Una vez descargados, actualizamos la tabla en el hilo de Swing
				SwingUtilities.invokeLater(() -> {
					this.correos.clear();
					this.correos.addAll(listaDescargada); // Actualizamos la lista local
					vistaGeneral.cargarCorreos(this.correos);

					vistaGeneral.getBtnRefrescar().setEnabled(true);

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

	// Metodo para detener el hilo cuando se cierre la ventana
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
				.addActionListener(new OyenteBotonEnviar(vistaGeneral.getCorreo(), PASSWORD_APLICACION));
		vistaGeneral.getEmailTabla()
				.addMouseListener(new OyenteTabla(vistaGeneral.getEmailTabla(), correos, this, CORREO));
		vistaGeneral.getBtnRefrescar().addActionListener(new OyenteRefrescarCorreo(this));
	}

	// ELIMINAR
	public void eliminarCorreoSeleccionado(Correo correo) {
		try {

			gestion.eliminarCorreoPOP3(HOST, "recent:" + CORREO, PASSWORD_APLICACION, correo);

			correos.remove(correo);
			vistaGeneral.cargarCorreos(correos);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// LEIDO
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

	// NO LEIDO
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

	// ACTUALIZAR
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

}