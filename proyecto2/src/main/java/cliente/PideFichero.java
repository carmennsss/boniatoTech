package cliente;
import java.io.Serializable;

public class PideFichero implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String rutaCompleta;

    public PideFichero(String rutaCompleta) {
        this.rutaCompleta = rutaCompleta;
    }

    public String getRutaCompleta() {
        return rutaCompleta;
    }
}