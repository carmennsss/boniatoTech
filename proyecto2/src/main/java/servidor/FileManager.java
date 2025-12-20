package servidor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import modelo.MoTextos;

/**
 * Gestor de archivos que utiliza FTP para realizar operaciones de
 * transferencia.
 * Encapsula la librería FTPClient de Apache Commons Net.
 */
public class FileManager {

	/** Cliente FTP para las operaciones de transferencia. */
	private FTPClient ftpClient;

	/** Dirección del servidor FTP. */
	private String servidor;

	/** Puerto del servidor FTP. */
	private int puerto;

	/** Nombre de usuario para la conexión FTP. */
	private String usuario;

	/** Contraseña para la conexión FTP. */
	private String contrasena;

	/**
	 * Constructor que inicializa el gestor de archivos FTP.
	 *
	 * @param servidor   Dirección del servidor FTP.
	 * @param puerto     Puerto del servidor FTP.
	 * @param usuario    Nombre de usuario para la conexión.
	 * @param contrasena Contraseña para la conexión.
	 */
	public FileManager(String servidor, int puerto, String usuario, String contrasena) {
		ftpClient = new FTPClient();
		this.servidor = servidor;
		this.puerto = puerto;
		this.usuario = usuario;
		this.contrasena = contrasena;
	}

	/**
	 * Borra un archivo del servidor FTP.
	 *
	 * @param select     Archivo FTP a borrar.
	 * @param rutaActual Directorio donde se encuentra el archivo.
	 */
	public void borrarArchivo(FTPFile select, String rutaActual) {
		String rutaCompleta;
		if (!this.conectar()) {
			JOptionPane.showMessageDialog(null, "Could not connect to the server", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		rutaCompleta = rutaActual;
		if (!rutaActual.endsWith("/")) {
			rutaCompleta += "/";
		}
		rutaCompleta += select.getName();
		int confirmacion = JOptionPane.showConfirmDialog(null, MoTextos.msg_confirm_delete_file);
		if (confirmacion == JOptionPane.OK_OPTION) {
			try {
				if (!ftpClient.deleteFile(rutaCompleta)) {
					JOptionPane.showMessageDialog(null, select.getName() + " => " + MoTextos.msg_could_not_delete);
				}
			} catch (IOException el) {
				el.printStackTrace();
			}

		}
		this.desconectar();
	}

	/**
	 * Elimina una carpeta del servidor FTP.
	 *
	 * @param nombreCarpeta Nombre de la carpeta a borrar.
	 * @param rutaActual    Ruta donde se encuentra la carpeta.
	 */
	public void borrarCarpeta(String nombreCarpeta, String rutaActual) {

		String rutaCompleta;

		if (!this.conectar()) {
			JOptionPane.showMessageDialog(null, "Could not connect to the server", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		rutaCompleta = rutaActual;
		if (!rutaCompleta.endsWith("/")) {
			rutaCompleta += "/";
		}
		rutaCompleta += nombreCarpeta;

		int confirmacion = JOptionPane.showConfirmDialog(null, MoTextos.msg_confirm_delete_folder,
				"Confirm deletion", JOptionPane.OK_CANCEL_OPTION);

		if (confirmacion == JOptionPane.OK_OPTION) {
			try {
				boolean borrada = ftpClient.removeDirectory(rutaCompleta);

				if (!borrada) {
					JOptionPane.showMessageDialog(null,
							nombreCarpeta + " => " + MoTextos.msg_could_not_delete + "\n"
									+ MoTextos.msg_folder_not_empty,
							"Error", JOptionPane.ERROR_MESSAGE);
				}

			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error deleting the folder:\n" + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		this.desconectar();
	}

	/**
	 * Establece la conexión y realiza el login con el servidor FTP.
	 *
	 * @return true si la conexión y login fueron exitosos, false en caso contrario.
	 */
	public boolean conectar() {
		try {
			this.ftpClient.connect(this.servidor, this.puerto);
			return this.ftpClient.login(this.usuario, this.contrasena);
		} catch (IOException e) {
			System.out.println("Could not connect to the server");
			return false;
		}
	}

	/**
	 * Crea un nuevo directorio en el servidor FTP.
	 *
	 * @param nombreCarpeta Nombre de la nueva carpeta.
	 * @param rutaActual    Ruta donde se creará la carpeta.
	 */
	public void crearCarpeta(String nombreCarpeta, String rutaActual) {
		String rutaCompleta;
		try {
			if (!this.conectar()) {
				JOptionPane.showMessageDialog(null, "Could not connect to the server", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			rutaCompleta = rutaActual;
			if (!rutaActual.endsWith("/")) {
				rutaCompleta += "/";
			}
			if (ftpClient.makeDirectory(rutaCompleta + nombreCarpeta)) {
				JOptionPane.showMessageDialog(null, "Folder created successfully.", "",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, nombreCarpeta + " => " + MoTextos.msg_could_not_create, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException el) {
			el.printStackTrace();
		}
		this.desconectar();
	}

	/**
	 * Descarga un archivo del servidor FTP al sistema local.
	 *
	 * @param select     Archivo FTP seleccionado para descarga.
	 * @param rutaLocal  Ruta del directorio local destino.
	 * @param rutaActual Ruta del directorio actual en el FTP.
	 */
	public void descargarArchivo(FTPFile select, String rutaLocal, String rutaActual) {
		BufferedOutputStream out;
		File archivoLocal;
		String rutaCompleta;
		boolean exito = false;
		try {
			if (!this.conectar()) {
				JOptionPane.showMessageDialog(null, "Could not connect to the server", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			archivoLocal = new File(rutaLocal + File.separator + select.getName());
			out = new BufferedOutputStream(new FileOutputStream(archivoLocal));
			rutaCompleta = rutaActual;
			if (!rutaCompleta.endsWith("/")) {
				rutaCompleta += "/";
			}
			rutaCompleta += select.getName();
			exito = this.ftpClient.retrieveFile(rutaCompleta, out);
			out.close();
			if (exito) {
				JOptionPane.showMessageDialog(null, "File downloaded successfully.", "",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Error downloading the file.", "Error",
						JOptionPane.ERROR_MESSAGE);
				archivoLocal.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Input/output error: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		this.desconectar();
	}

	/**
	 * Cierra la sesión y desconecta del servidor FTP.
	 */
	public void desconectar() {
		try {
			if (this.ftpClient.isConnected()) {
				this.ftpClient.logout();
				this.ftpClient.disconnect();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene el nombre de usuario configurado para la conexión FTP.
	 *
	 * @return El nombre de usuario.
	 */
	public String getUserName() {
		return usuario;
	}

	/**
	 * Lista los archivos y carpetas en un directorio del servidor FTP.
	 *
	 * @param ruta Ruta del directorio a listar.
	 * @return Array de objetos FTPFile con la información de los archivos.
	 */
	public FTPFile[] listarArchivos(String ruta) {
		try {
			return this.ftpClient.listFiles(ruta);
		} catch (IOException e) {
			e.printStackTrace();
			return new FTPFile[0];
		}
	}

	/**
	 * Renombra un archivo o carpeta en el servidor FTP.
	 *
	 * @param archivoSeleccionado Archivo FTP a renombrar.
	 * @param nuevoNombre         Nuevo nombre para el archivo.
	 * @param rutaActual          Ruta donde se encuentra el archivo.
	 */
	public void renombrar(FTPFile archivoSeleccionado, String nuevoNombre, String rutaActual) {
		if (!this.conectar()) {
			JOptionPane.showMessageDialog(null, "Could not connect to the server", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String rutaVieja = rutaActual;
		if (!rutaVieja.endsWith("/")) {
			rutaVieja += "/";
		}
		rutaVieja += archivoSeleccionado.getName();

		String rutaNueva = rutaActual;
		if (!rutaNueva.endsWith("/")) {
			rutaNueva += "/";
		}
		rutaNueva += nuevoNombre;

		int confirmacion = JOptionPane.showConfirmDialog(null,
				"Do you want to rename the file '" + archivoSeleccionado.getName() + "' to '" + nuevoNombre + "'?",
				"Confirm Rename", JOptionPane.OK_CANCEL_OPTION);

		if (confirmacion == JOptionPane.OK_OPTION) {
			try {
				boolean exito = ftpClient.rename(rutaVieja, rutaNueva);
				if (exito) {
					JOptionPane.showMessageDialog(null, "File renamed successfully.", "",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Could not rename the file.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error renaming the file: " + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		this.desconectar();
	}

	/**
	 * Sube un archivo local al servidor FTP.
	 *
	 * @param archivo       Ruta absoluta del archivo local.
	 * @param nombreArchivo Nombre del archivo.
	 * @param rutaActual    Directorio destino en el servidor FTP.
	 */
	public void subirArchivo(String archivo, String nombreArchivo, String rutaActual) {
		BufferedInputStream in;
		String rutaCompleta;
		try {
			in = new BufferedInputStream(new FileInputStream(archivo));
			if (!this.conectar()) {
				JOptionPane.showMessageDialog(null, "Could not connect to the server", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			rutaCompleta = rutaActual;
			if (!rutaActual.endsWith("/")) {
				rutaCompleta += "/";
			}
			rutaCompleta += nombreArchivo;

			if (this.ftpClient.storeFile(rutaCompleta, in)) {
				JOptionPane.showMessageDialog(null, "File uploaded successfully.", "",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Error uploading the file.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Input/output error: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		this.desconectar();
	}

}
