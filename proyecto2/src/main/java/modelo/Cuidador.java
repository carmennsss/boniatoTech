package modelo;

public class Cuidador {
    private int cuidador_id;
    private String nombre_cuidadores;
    private String direccion_cuidadores;

    public Cuidador(int cuidador_id, String nombre_cuidadores, String direccion_cuidadores) {
        this.cuidador_id = cuidador_id;
        this.nombre_cuidadores = nombre_cuidadores;
        this.direccion_cuidadores = direccion_cuidadores;
    }

    public int getCuidador_id() {
        return cuidador_id;
    }

    public void setCuidador_id(int cuidador_id) {
        this.cuidador_id = cuidador_id;
    }

    public String getNombre_cuidadores() {
        return nombre_cuidadores;
    }

    public void setNombre_cuidadores(String nombre_cuidadores) {
        this.nombre_cuidadores = nombre_cuidadores;
    }

    public String getDireccion_cuidadores() {
        return direccion_cuidadores;
    }

    public void setDireccion_cuidadores(String direccion_cuidadores) {
        this.direccion_cuidadores = direccion_cuidadores;
    }

    @Override
    public String toString() {
        return nombre_cuidadores;
    }
}
