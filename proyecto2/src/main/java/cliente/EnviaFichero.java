package cliente;

import java.io.Serializable;

public class EnviaFichero implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private byte[] contenido;
    private String nombre;
    private String directorioDestino;  // donde se va a guardar en el servidor

    public EnviaFichero(byte[] contenido, String nombre, String directorioDestino) {
        this.contenido = contenido;
        this.nombre = nombre;
        this.directorioDestino = directorioDestino;
    }

    public byte[] getContenidoFichero() {
        return contenido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDirectorioDestino() {
        return directorioDestino;
    }
}