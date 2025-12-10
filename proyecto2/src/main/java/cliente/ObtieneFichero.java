package cliente;

import java.io.Serializable;

public class ObtieneFichero implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private byte[] contenido;

    public ObtieneFichero(byte[] contenido) {
        this.contenido = contenido;
    }

    public byte[] getContenidoFichero() {
        return contenido;
    }
}