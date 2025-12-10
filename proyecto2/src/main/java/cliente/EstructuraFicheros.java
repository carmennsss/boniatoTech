import java.io.Serializable;

public class EstructuraFicheros implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nombre;      // nombre del fichero o carpeta
    private String ruta;        // ruta completa
    private boolean directorio; // true si es directorio
    private long tamano;        // tamaño en bytes (0 si es carpeta)
    private EstructuraFicheros[] lista; // hijos (solo si es directorio)
    private int numFicheros;    // número de elementos en la lista

    // Constructor para fichero
    public EstructuraFicheros(String nombre, String ruta, long tamano) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.directorio = false;
        this.tamano = tamano;
    }

    // Constructor para directorio
    public EstructuraFicheros(String nombre, String ruta, EstructuraFicheros[] lista, int numFicheros) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.directorio = true;
        this.lista = lista;
        this.numFicheros = numFicheros;
    }

    // Getters
    public String getName() { return nombre; }
    public String getPath() { return ruta; }
    public boolean isDir() { return directorio; }
    public long getTamano() { return tamano; }
    public EstructuraFicheros[] getLista() { return lista; }
    public int getNumFich() { return numFicheros; }  // número de elementos

    @Override
    public String toString() {
        if (directorio) {
            return nombre + " <DIR>";
        } else {
            return nombre + " (" + tamano + " bytes)";
        }
    }
}