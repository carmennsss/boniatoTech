package com.example.demo.service;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class ClienteService {
	private FTPClient cliente;

	public void iniciarSesion(String usuario, String contrasenia) {
		this.cliente = new FTPClient();
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
	}

	public void crearConsulta() {
		try {
			if (!cliente.isConnected()) {
				return;
			}
			FTPFile[] files;

			files = cliente.listFiles();

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
