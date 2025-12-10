package cliente;

import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;

public class Registro {

	public static void main(String[] args) {
		FTPClient cliente = new FTPClient();
		String servFTP = "localhost";
		Scanner sc = new Scanner(System.in);

		System.out.println("Introduce tu usuario:");
		String usuario = sc.nextLine();
		System.out.println("Introduce tu contraseña");
		String contrasenia = sc.nextLine();
		try {
			cliente.connect(servFTP);
			
		} catch (IOException ioe) {

		}
	}

}
