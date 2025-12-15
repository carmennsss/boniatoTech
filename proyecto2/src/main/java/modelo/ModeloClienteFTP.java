package modelo;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class ModeloClienteFTP {
    private FTPClient cliente;
    private String servidor = "13.62.51.110";
    private int puerto = 21;
    private String user="";
    private String pass="";
    
    public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public ModeloClienteFTP() {
        cliente = new FTPClient();
    }

    public void establecerConexion() throws IOException {
        if (!cliente.isConnected()) {
            cliente.enterLocalPassiveMode();
            cliente.connect(servidor, puerto);
            System.out.println(cliente.getReplyString());
        }
    }

    public FTPClient getCliente() {
        return cliente;
    }

    public void desconectar() {
        try {
            if (cliente.isConnected()) {
                cliente.logout();
                cliente.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
