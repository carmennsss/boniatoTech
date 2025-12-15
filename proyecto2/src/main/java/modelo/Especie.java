package modelo;

public class Especie {
    private int especie_id;
    private String nombre_especies;

    public Especie(int especie_id, String nombre_especies) {
        this.especie_id = especie_id;
        this.nombre_especies = nombre_especies;
    }

    public int getEspecie_id() {
        return especie_id;
    }

    public void setEspecie_id(int especie_id) {
        this.especie_id = especie_id;
    }

    public String getNombre_especies() {
        return nombre_especies;
    }

    public void setNombre_especies(String nombre_especies) {
        this.nombre_especies = nombre_especies;
    }

    @Override
    public String toString() {
        return nombre_especies;
    }
}
