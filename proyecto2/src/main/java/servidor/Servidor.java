package servidor;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;

import cliente.EstructuraFicheros;

public class Servidor {

	static Integer PUERTO = 44441;
	static public EstructuraFicheros NF;
	static ServerSocket servidor;

	public static void main(String[] args) {

		String Directorio = "";

		JFileChooser f = new JFileChooser();
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.setDialogTitle("SELECCIONA EL DIRECTORIO DONDE ESTAN LOS FICHEROS");
		int returnVal = f.showDialog(f, "Seleccionar");
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File file = f.getSelectedFile();
			Directorio = file.getAbsolutePath();

		}

		// si no se selecciona nada salir

		if (Directorio.equals("")) {

			System.out.println("Debe seleccionar un directorio");
			System.exit(1);
		}

		try {
			servidor = new ServerSocket(PUERTO);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Servidor Iniciado en Puerto" + PUERTO);

		while (true) {
			try {

				Socket cliente = servidor.accept();
				System.out.println("Bienvenido al cliente");
				NF = new EstructuraFicheros(Directorio);
				HiloServidor hilo = new HiloServidor(cliente, NF);
				hilo.start();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}
	}
}
