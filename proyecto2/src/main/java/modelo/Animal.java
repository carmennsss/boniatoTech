package modelo;

public class Animal {
    private int animal_id;
    private String nombre_animales;
    private String tipo; // "male", "female"
    private int especie_id;
    private int cuidador_id;

    public Animal(int animal_id, String nombre_animales, String tipo, int especie_id, int cuidador_id) {
        this.animal_id = animal_id;
        this.nombre_animales = nombre_animales;
        this.tipo = tipo;
        this.especie_id = especie_id;
        this.cuidador_id = cuidador_id;
    }

    public int getAnimal_id() {
        return animal_id;
    }

    public void setAnimal_id(int animal_id) {
        this.animal_id = animal_id;
    }

    public String getNombre_animales() {
        return nombre_animales;
    }

    public void setNombre_animales(String nombre_animales) {
        this.nombre_animales = nombre_animales;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getEspecie_id() {
        return especie_id;
    }

    public void setEspecie_id(int especie_id) {
        this.especie_id = especie_id;
    }

    public int getCuidador_id() {
        return cuidador_id;
    }

    public void setCuidador_id(int cuidador_id) {
        this.cuidador_id = cuidador_id;
    }
}
