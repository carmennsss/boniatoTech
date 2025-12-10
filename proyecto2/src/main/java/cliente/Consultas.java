package cliente;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class Consultas {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		System.out.println("Introduce tu usuario:");
		String usuario = sc.nextLine();
		System.out.println("Introduce tu contraseña");
		String contrasenia = sc.nextLine();
		FTPClient cliente = InicioSesion.iniciarSesion(usuario, contrasenia);

		if (!cliente.isConnected()) {
			return;
		}
		System.out.println("¿Que directorio quieres leer?");
		String directorio = sc.nextLine();
		try {
			if (cliente.changeWorkingDirectory(directorio)) {
				System.out.println("Dir ACTUAL : " + cliente.printWorkingDirectory());
				imprimirFicheros(cliente);
			} else {
				System.out.println("DIRECTORIO NO EXISTE");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void imprimirFicheros(FTPClient cliente) {
		try {
			FTPFile[] files = cliente.listFiles();
			System.out.println("Ficheros en el directorio actual:" + files.length);
			String tipos[] = { "Fichero", "Directorio", "Enlace simb." };
			for (int i = 0; i < files.length; i++) {
				System.out.println("\t" + files[i].getName() + " => " + tipos[files[i].getType()]);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
