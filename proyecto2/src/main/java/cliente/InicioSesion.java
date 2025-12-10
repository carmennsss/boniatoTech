package cliente;

import java.io.*;
import java.util.Scanner;

import org.apache.commons.net.ftp.*;

public class InicioSesion {
	public static FTPClient iniciarSesion(String usuario, String contrasenia) {
		FTPClient cliente = new FTPClient();
		String servFTP = "ftp.rediris.es";
		
		try {
			cliente.connect(servFTP);
			boolean login = cliente.login(usuario, contrasenia);
			if (login) {
				System.out.println("Login correcto...");
				System.out.println(cliente.getReplyString());
			} else {
				System.out.println("Login incorrecto...");
				cliente.disconnect();
				System.exit(1);
			}
		} catch (IOException ioe) {

		}
		return cliente;

	}

}
