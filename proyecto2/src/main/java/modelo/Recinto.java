package modelo;

public class Recinto {
    private int recinto_id;
    private String nombre_recintos;
    private String direccion_recintos;
    private int cantidad_origen;
    private int cantidad_destino;

    public Recinto(int recinto_id, String nombre_recintos, String direccion_recintos, int cantidad_origen,
            int cantidad_destino) {
        this.recinto_id = recinto_id;
        this.nombre_recintos = nombre_recintos;
        this.direccion_recintos = direccion_recintos;
        this.cantidad_origen = cantidad_origen;
        this.cantidad_destino = cantidad_destino;
    }

    public int getRecinto_id() {
        return recinto_id;
    }

    public void setRecinto_id(int recinto_id) {
        this.recinto_id = recinto_id;
    }

    public String getNombre_recintos() {
        return nombre_recintos;
    }

    public void setNombre_recintos(String nombre_recintos) {
        this.nombre_recintos = nombre_recintos;
    }

    public String getDireccion_recintos() {
        return direccion_recintos;
    }

    public void setDireccion_recintos(String direccion_recintos) {
        this.direccion_recintos = direccion_recintos;
    }

    public int getCantidad_origen() {
        return cantidad_origen;
    }

    public void setCantidad_origen(int cantidad_origen) {
        this.cantidad_origen = cantidad_origen;
    }

    public int getCantidad_destino() {
        return cantidad_destino;
    }

    public void setCantidad_destino(int cantidad_destino) {
        this.cantidad_destino = cantidad_destino;
    }

    @Override
    public String toString() {
        return nombre_recintos;
    }
}
