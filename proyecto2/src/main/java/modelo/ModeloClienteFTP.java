package modelo;

import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class ModeloClienteFTP {
    private FTPClient cliente;
    private String servidor = "13.62.51.110";
    private int puerto = 21;

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
