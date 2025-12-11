package servidor;

import java.io.*;
import java.net.*;
import cliente.EstructuraFicheros;
import cliente.PideFichero;
import cliente.EnviaFichero;
import cliente.ObtieneFichero;

public class HiloServidor extends Thread {
	
	Socket socket;
	ObjectOutputStream outObjeto;
	ObjectInputStream inObjeto;
	EstructuraFicheros NF;
	
	// Constructor
	public HiloServidor(Socket s, EstructuraFicheros nF) throws IOException {
		this.socket = s;
		this.NF = nF;
		inObjeto = new ObjectInputStream(socket.getInputStream());
		outObjeto = new ObjectOutputStream(socket.getOutputStream());
		
	}
	
	public void run() {
		try {
			outObjeto.writeObject(NF);
			
			while (true) {
				Object peticion = inObjeto.readObject();
				
				if (peticion instanceof PideFichero) {
					PideFichero fichero = (PideFichero) peticion;
					EnviarFichero(fichero);
				}
				
				if (peticion instanceof EnviaFichero) {
					EnviaFichero fic = (EnviaFichero) peticion;
					File d = new File(fic.getDirectorioDestino());
					File f1 = new File(d, fic.getNombre());
					
					FileOutputStream fos = new FileOutputStream(f1); 
					fos.write(fic.getContenidoFichero());
					fos.close();
					
					EstructuraFicheros n = new EstructuraFicheros(fic.getDirectorioDestino());
					outObjeto.writeObject(n);
				}
			}
		} catch (IOException e) {
			try {
				inObjeto.close();
				outObjeto.close();
				socket.close();
				System.out.println("Cerrado cliente");
			} catch (IOException ee) {
				ee.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void EnviarFichero(PideFichero fich) {
		File fichero = new File(fich.getNombreFichero());
		FileInputStream fileIn = null;
		
		try {
			fileIn = new FileInputStream(fichero);
			long bytes = fichero.length();
			byte[] buff = new byte[(int) bytes];
			int i, j = 0;
			
			while ((i = fileIn.read()) != -1) {
				buff[j] = (byte) i;
				j++;
			}
			fileIn.close();
			Object ff = new ObtieneFichero(buff);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
