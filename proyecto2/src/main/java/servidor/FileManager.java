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

public class FileManager {

	private FTPClient ftpClient;
	private String servidor;
	private int puerto;
	private String usuario;
	private String contrasena;

	public FileManager(String servidor, int puerto, String usuario, String contrasena) {
		ftpClient = new FTPClient();
		this.servidor = servidor;
		this.puerto = puerto;
		this.usuario = usuario;
		this.contrasena = contrasena;
	}

	public void subirArchivo(String archivo, String nombreArchivo, String rutaActual) {
		BufferedInputStream in;
		String rutaCompleta;
		try {
			in = new BufferedInputStream(new FileInputStream(archivo));
			if (!this.conectar()) {
				JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			rutaCompleta = rutaActual;
			if (!rutaActual.endsWith("/")) {
				rutaCompleta += "/";
			}
			rutaCompleta += nombreArchivo;

			if (this.ftpClient.storeFile(rutaCompleta, in)) {
				JOptionPane.showMessageDialog(null, "Archivo subido correctamente.", "",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Error al subir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error de entrada/salida: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		this.desconectar();
	}

	public void descargarArchivo(FTPFile select, String rutaLocal, String rutaActual) {
		BufferedOutputStream out;
		File archivoLocal;
		String rutaCompleta;
		boolean exito = false;
		try {
			if (!this.conectar()) {
				JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor", "Error",
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
				JOptionPane.showMessageDialog(null, "Archivo descargado correctamente.", "",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Error al descargar el archivo.", "Error",
						JOptionPane.ERROR_MESSAGE);
				archivoLocal.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error de entrada/salida: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}

		this.desconectar();
	}

	public void borrarArchivo(FTPFile select, String rutaActual) {
		String rutaCompleta;
		if (!this.conectar()) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		rutaCompleta = rutaActual;
		if (!rutaActual.endsWith("/")) {
			rutaCompleta += "/";
		}
		rutaCompleta += select.getName();
		int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar el archivo seleccionado?");
		if (confirmacion == JOptionPane.OK_OPTION) {
			try {
				if (!ftpClient.deleteFile(rutaCompleta)) {
					JOptionPane.showMessageDialog(null, select.getName() + " => No se ha podido eliminar ...");
				}
			} catch (IOException el) {
				el.printStackTrace();
			}

		}
		this.desconectar();
	}

	public void crearCarpeta(String nombreCarpeta, String rutaActual) {
		String rutaCompleta;
		try {
			if (!this.conectar()) {
				JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			rutaCompleta = rutaActual;
			if (!rutaActual.endsWith("/")) {
				rutaCompleta += "/";
			}
			if (ftpClient.makeDirectory(rutaCompleta + nombreCarpeta)) {
				JOptionPane.showMessageDialog(null, "Carpeta creada correctamente.", "",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, nombreCarpeta + " => No se ha podido crear ...", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (IOException el) {
			el.printStackTrace();
		}
		this.desconectar();
	}

	public void borrarCarpeta(String nombreCarpeta, String rutaActual) {

		String rutaCompleta;

		if (!this.conectar()) {
			JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		rutaCompleta = rutaActual;
		if (!rutaCompleta.endsWith("/")) {
			rutaCompleta += "/";
		}
		rutaCompleta += nombreCarpeta;

		int confirmacion = JOptionPane.showConfirmDialog(null, "¿Desea eliminar la carpeta seleccionada?",
				"Confirmar eliminación", JOptionPane.OK_CANCEL_OPTION);

		if (confirmacion == JOptionPane.OK_OPTION) {
			try {
				boolean borrada = ftpClient.removeDirectory(rutaCompleta);

				if (!borrada) {
					JOptionPane.showMessageDialog(null,
							nombreCarpeta + " => No se ha podido eliminar.\n" + "La carpeta podría no estar vacía.",
							"Error", JOptionPane.ERROR_MESSAGE);
				}

			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al eliminar la carpeta:\n" + e.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		this.desconectar();
	}

	public FTPFile[] listarArchivos(String ruta) {
		try {
			return this.ftpClient.listFiles(ruta);
		} catch (IOException e) {
			e.printStackTrace();
			return new FTPFile[0];
		}
	}

	public boolean conectar() {
		try {
			this.ftpClient.connect(this.servidor, this.puerto);
			return this.ftpClient.login(this.usuario, this.contrasena);
		} catch (IOException e) {
			System.out.println("No se pudo conectar al servidor");
			return false;
		}
	}

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

}