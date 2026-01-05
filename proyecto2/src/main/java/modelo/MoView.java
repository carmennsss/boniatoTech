package modelo;

import java.util.ArrayList;

/**
 * Modelo de la vista (ViewModel) que almacena el estado temporal de la interfaz
 * de usuario.
 * Gestiona selecciones de tablas, filas y listas de correos seleccionados, 
 * actuando como un puente de estado entre controladores.
 */
public class MoView {
    /** Nombre de la tabla actualmente activa en la vista CRUD. */
    private String tablaActual;

    /** Índice de la fila seleccionada en una tabla. */
    private int filaSeleccionada;

    /** Lista de correos seleccionados en la bandeja de entrada para gestión de roles. */
    private ArrayList<String> correoSeleccionados;

    /** Lista de correos seleccionados para la whitelist (borrado/gestión). */
    private ArrayList<String> correosWhitelist;

    /**
     * Constructor que inicializa el estado de la vista con valores por defecto.
     * La tabla por defecto es "especies" y el índice de fila se inicializa en -1.
     */
    public MoView() {
        this.tablaActual = "especies";
        this.filaSeleccionada = -1;
        this.correoSeleccionados = new ArrayList<>();
        this.correosWhitelist = new ArrayList<>();
    }

    /**
     * Gestiona la selección o deselección de un correo electrónico.
     * Si el correo ya está en la lista, lo elimina. Si no, lo añade.
     *
     * @param correo El correo electrónico a seleccionar o deseleccionar.
     * @return true si el correo fue añadido, false si fue eliminado de la lista.
     */
    public boolean buscarCorreo(String correo) {
        if (!this.correoSeleccionados.contains(correo)) {
            this.correoSeleccionados.add(correo);
            return true;
        }
        this.correoSeleccionados.remove(correo);
        return false;
    }

    /**
     * Gestiona la selección o deselección de un correo en la sección de whitelist.
     * Si el correo ya está en la lista, lo elimina. Si no, lo añade.
     *
     * @param correo El correo electrónico a gestionar.
     * @return true si fue añadido satisfactoriamente, false si fue eliminado.
     */
    public boolean buscarCorreoWhitelist(String correo) {
        if (!this.correosWhitelist.contains(correo)) {
            this.correosWhitelist.add(correo);
            return true;
        }
        this.correosWhitelist.remove(correo);
        return false;
    }

    // --- GETTERS Y SETTERS AL FINAL ---

    /**
     * Obtiene la lista de correos seleccionados en la bandeja de entrada.
     *
     * @return Lista de correos electrónicos seleccionados.
     */
    public ArrayList<String> getCorreoSeleccionados() {
        return correoSeleccionados;
    }

    /**
     * Obtiene la lista de correos seleccionados para la gestión de whitelist.
     *
     * @return Lista de correos marcados para borrado o gestión.
     */
    public ArrayList<String> getCorreosWhitelist() {
        return correosWhitelist;
    }

    /**
     * Obtiene el índice de la fila seleccionada actualmente en la tabla activa.
     *
     * @return El índice de la fila (entero).
     */
    public int getFilaSeleccionada() {
        return filaSeleccionada;
    }

    /**
     * Obtiene el nombre de la tabla actualmente activa en la interfaz CRUD.
     *
     * @return Nombre de la tabla como cadena de texto.
     */
    public String getTablaActual() {
        return tablaActual;
    }

    /**
     * Establece la lista de correos seleccionados en la bandeja de entrada.
     *
     * @param correoSeleccionados Nueva lista de correos seleccionados.
     */
    public void setCorreoSeleccionados(ArrayList<String> correoSeleccionados) {
        this.correoSeleccionados = correoSeleccionados;
    }

    /**
     * Establece la lista de correos de la whitelist seleccionados.
     *
     * @param correosWhitelist Nueva lista de correos para la gestión de whitelist.
     */
    public void setCorreosWhitelist(ArrayList<String> correosWhitelist) {
        this.correosWhitelist = correosWhitelist;
    }

    /**
     * Establece el índice de la fila que se ha seleccionado en la vista.
     *
     * @param filaSeleccionada El nuevo índice de fila seleccionada.
     */
    public void setFilaSeleccionada(int filaSeleccionada) {
        this.filaSeleccionada = filaSeleccionada;
    }

    /**
     * Establece el nombre de la tabla actual para la vista CRUD.
     *
     * @param tablaActual El nombre de la nueva tabla activa.
     */
    public void setTablaActual(String tablaActual) {
        this.tablaActual = tablaActual;
    }
}