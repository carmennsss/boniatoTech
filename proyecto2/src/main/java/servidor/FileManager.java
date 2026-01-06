/*
* @author Daniel - BoniatoTech
* @version 1.0
*/

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
 * Gestor de archivos que utiliza el protocolo FTP para realizar operaciones de 
 * transferencia y administración de ficheros en un servidor remoto.
 * Encapsula la lógica de la librería Apache Commons Net para facilitar 
 * operaciones de subida, descarga, borrado y renombrado.
 */
public class FileManager {

	/** Cliente FTP para gestionar la comunicación con el servidor. */
	private FTPClient ftpClient;

	/** Dirección IP o nombre de dominio del servidor FTP. */
	private String servidor;

	/** Puerto de escucha del servicio FTP (habitualmente 21). */
	private int puerto;

	/** Credencial de usuario para la autenticación. */
	private String usuario;

	/** Credencial de contraseña para la autenticación. */
	private String contrasena;

	/**
	 * Constructor principal que inicializa las credenciales y el cliente FTP.
	 *
	 * @param servidor   Dirección del servidor remoto.
	 * @param puerto     Puerto de conexión.
	 * @param usuario    Nombre de usuario para el acceso.
	 * @param contrasena Contraseña asociada al usuario.
	 */
	public FileManager(String servidor, int puerto, String usuario, String contrasena) {
		this.ftpClient = new FTPClient();
		this.servidor = servidor;
		this.puerto = puerto;
		this.usuario = usuario;
		this.contrasena = contrasena;
	}

	/**
	 * Elimina un archivo específico del servidor FTP tras una confirmación del usuario.
	 *
	 * @param select     El objeto FTPFile que representa el archivo a borrar.
	 * @param rutaActual El directorio remoto donde se encuentra el archivo.
	 */
	public void borrarArchivo(FTPFile select, String rutaActual) {
		String rutaCompleta;
		if (!this.conectar()) {
			JOptionPane.showMessageDialog(null, MoTextos.msg_connection_error, MoTextos.msg_error_title,
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
	 * Elimina un directorio del servidor. Solo tendrá éxito si el directorio está vacío.
	 *
	 * @param nombreCarpeta Nombre de la carpeta a eliminar.
	 * @param rutaActual    Ruta del directorio padre en el servidor.
	 */
	public void borrarCarpeta(String nombreCarpeta, String rutaActual) {
		String rutaCompleta;
		if (!this.conectar()) {
			JOptionPane.showMessageDialog(null, MoTextos.msg_connection_error, MoTextos.msg_error_title,
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		rutaCompleta = rutaActual;
		if (!rutaCompleta.endsWith("/")) {
			rutaCompleta += "/";
		}
		rutaCompleta += nombreCarpeta;

		int confirmacion = JOptionPane.showConfirmDialog(null, MoTextos.msg_confirm_delete_folder,
				MoTextos.msg_confirm_title, JOptionPane.OK_CANCEL_OPTION);

		if (confirmacion == JOptionPane.OK_OPTION) {
			try {
				boolean borrada = ftpClient.removeDirectory(rutaCompleta);
				if (!borrada) {
					JOptionPane.showMessageDialog(null,
							nombreCarpeta + " => " + MoTextos.msg_could_not_delete + "\n"
									+ MoTextos.msg_folder_not_empty,
							MoTextos.msg_error_title, JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.desconectar();
	}

	/**
	 * Establece la conexión física con el servidor y realiza la autenticación.
	 *
	 * @return true si la conexión y el login fueron exitosos; false en caso contrario.
	 */
	public boolean conectar() {
		try {
			this.ftpClient.connect(this.servidor, this.puerto);
			return this.ftpClient.login(this.usuario, this.contrasena);
		} catch (IOException e) {
			System.err.println("Error de conexión: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Crea un nuevo directorio en la ruta especificada del servidor.
	 *
	 * @param nombreCarpeta Nombre que se le asignará a la nueva carpeta.
	 * @param rutaActual    Ruta remota donde se creará el directorio.
	 */
	public void crearCarpeta(String nombreCarpeta, String rutaActual) {
		String rutaCompleta;
		try {
			if (!this.conectar()) {
				JOptionPane.showMessageDialog(null, MoTextos.msg_connection_error, MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			rutaCompleta = rutaActual;
			if (!rutaActual.endsWith("/")) {
				rutaCompleta += "/";
			}
			if (ftpClient.makeDirectory(rutaCompleta + nombreCarpeta)) {
				JOptionPane.showMessageDialog(null, MoTextos.msg_folder_created, MoTextos.msg_success_title,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, nombreCarpeta + " => " + MoTextos.msg_could_not_create, 
						MoTextos.msg_error_title, JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException el) {
			el.printStackTrace();
		}
		this.desconectar();
	}

	/**
	 * Descarga un archivo del servidor remoto al sistema de archivos local.
	 *
	 * @param select     Archivo FTP origen.
	 * @param rutaLocal  Directorio de destino en el PC del usuario.
	 * @param rutaActual Directorio de origen en el servidor FTP.
	 */
	public void descargarArchivo(FTPFile select, String rutaLocal, String rutaActual) {
		BufferedOutputStream out;
		File archivoLocal;
		String rutaCompleta;
		boolean exito = false;
		try {
			if (!this.conectar()) {
				JOptionPane.showMessageDialog(null, MoTextos.msg_connection_error, MoTextos.msg_error_title,
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
				JOptionPane.showMessageDialog(null, MoTextos.msg_download_success, MoTextos.msg_success_title,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, MoTextos.msg_download_error, MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
				archivoLocal.delete();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.desconectar();
	}

	/**
	 * Cierra la sesión activa del usuario y desconecta el cliente del servidor de forma segura.
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
	 * Obtiene un listado de los archivos y carpetas contenidos en una ruta remota.
	 *
	 * @param ruta Directorio a explorar.
	 * @return Un array de objetos FTPFile con la información del contenido.
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
	 * Cambia el nombre de un archivo o directorio en el servidor remoto.
	 *
	 * @param archivoSeleccionado Objeto que representa el archivo actual.
	 * @param nuevoNombre         Nombre de destino.
	 * @param rutaActual          Ruta remota donde reside el archivo.
	 */
	public void renombrar(FTPFile archivoSeleccionado, String nuevoNombre, String rutaActual) {
		if (!this.conectar()) {
			JOptionPane.showMessageDialog(null, MoTextos.msg_connection_error, MoTextos.msg_error_title, 
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		String rutaVieja = rutaActual + (rutaActual.endsWith("/") ? "" : "/") + archivoSeleccionado.getName();
		String rutaNueva = rutaActual + (rutaActual.endsWith("/") ? "" : "/") + nuevoNombre;

		try {
			if (ftpClient.rename(rutaVieja, rutaNueva)) {
				JOptionPane.showMessageDialog(null, MoTextos.msg_updated_ok, MoTextos.msg_success_title,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, MoTextos.msg_update_error, MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.desconectar();
	}

	/**
	 * Transfiere un archivo desde el sistema local al servidor remoto.
	 *
	 * @param archivo       Ruta absoluta del archivo local a subir.
	 * @param nombreArchivo Nombre que tendrá el archivo en el servidor.
	 * @param rutaActual    Ruta remota de destino.
	 */
	public void subirArchivo(String archivo, String nombreArchivo, String rutaActual) {
		BufferedInputStream in;
		String rutaCompleta;
		try {
			in = new BufferedInputStream(new FileInputStream(archivo));
			if (!this.conectar()) {
				JOptionPane.showMessageDialog(null, MoTextos.msg_connection_error, MoTextos.msg_error_title,
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			rutaCompleta = rutaActual + (rutaActual.endsWith("/") ? "" : "/") + nombreArchivo;
			if (this.ftpClient.storeFile(rutaCompleta, in)) {
				JOptionPane.showMessageDialog(null, MoTextos.msg_upload_success, MoTextos.msg_success_title,
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, MoTextos.msg_upload_error, MoTextos.msg_error_title, 
						JOptionPane.ERROR_MESSAGE);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.desconectar();
	}

	// --- GETTERS Y SETTERS ---

	/**
	 * Obtiene el nombre de usuario configurado para la conexión.
	 * @return El nombre de usuario (String).
	 */
	public String getUserName() {
		return usuario;
	}

	/**
	 * Obtiene la instancia del cliente FTP de Apache Commons.
	 * @return El objeto FTPClient utilizado internamente.
	 */
	public FTPClient getFtpClient() {
		return ftpClient;
	}

	/**
	 * Establece una nueva instancia para el cliente FTP.
	 * @param ftpClient El nuevo objeto FTPClient.
	 */
	public void setFtpClient(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	/**
	 * Obtiene la dirección o host del servidor FTP.
	 * @return La dirección del servidor.
	 */
	public String getServidor() {
		return servidor;
	}

	/**
	 * Establece la dirección o host del servidor FTP.
	 * @param servidor El host del servidor remoto.
	 */
	public void setServidor(String servidor) {
		this.servidor = servidor;
	}

	/**
	 * Obtiene el puerto de conexión al servidor FTP.
	 * @return El puerto configurado (int).
	 */
	public int getPuerto() {
		return puerto;
	}

	/**
	 * Establece el puerto de conexión al servidor FTP.
	 * @param puerto El nuevo número de puerto.
	 */
	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	/**
	 * Obtiene el nombre del usuario configurado para la autenticación.
	 * @return El nombre de usuario.
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * Establece el nombre de usuario para la autenticación en el servidor.
	 * @param usuario El nuevo nombre de usuario.
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Obtiene la contraseña configurada para la autenticación.
	 * @return La contraseña (String).
	 */
	public String getContrasena() {
		return contrasena;
	}

	/**
	 * Establece la contraseña para la autenticación en el servidor.
	 * @param contrasena La nueva contraseña.
	 */
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
}