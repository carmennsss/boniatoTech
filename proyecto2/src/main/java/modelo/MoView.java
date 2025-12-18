package modelo;

import java.util.ArrayList;

/**
 * Modelo de la vista (ViewModel) que almacena el estado temporal de la interfaz
 * de usuario.
 * Gestiona selecciones de tablas, filas y listas de correos seleccionados.
 */
public class MoView {
    private String tablaActual;
    private int filaSeleccionada;
    private ArrayList<String> correoSeleccionados;
    private ArrayList<String> correosWhitelist;

    /**
     * Constructor que inicializa el estado de la vista con valores por defecto.
     * La tabla por defecto es "especies" y no hay fila seleccionada (-1).
     */
    public MoView() {
        this.tablaActual = "especies";
        this.filaSeleccionada = -1;
        this.correoSeleccionados = new ArrayList<>();
        this.correosWhitelist = new ArrayList<>();
    }

    /**
     * Gestiona la selección/deselección de un correo.
     * Si el correo ya está en la lista, lo elimina. Si no, lo añade.
     *
     * @param correo El correo a seleccionar o deseleccionar.
     * @return true si el correo fue añadido, false si fue eliminado.
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
     * Gestiona la selección/deselección de un correo en la whitelist.
     * Si el correo ya está en la lista, lo elimina. Si no, lo añade.
     *
     * @param correo El correo a seleccionar o deseleccionar.
     * @return true si fue añadido, false si fue eliminado.
     */
    public boolean buscarCorreoWhitelist(String correo) {
        if (!this.correosWhitelist.contains(correo)) {
            this.correosWhitelist.add(correo);
            return true;
        }
        this.correosWhitelist.remove(correo);
        return false;
    }

    /**
     * Obtiene la lista de correos seleccionados para la whitelist
     * (borrado/gestión).
     *
     * @return Lista de correos.
     */
    public ArrayList<String> getCorreosWhitelist() {
        return correosWhitelist;
    }

    /**
     * Establece la lista de correos de la whitelist seleccionados.
     *
     * @param correosWhitelist Nueva lista de correos.
     */
    public void setCorreosWhitelist(ArrayList<String> correosWhitelist) {
        this.correosWhitelist = correosWhitelist;
    }

    /**
     * Obtiene el nombre de la tabla actualmente activa en la vista CRUD.
     *
     * @return Nombre de la tabla.
     */
    public String getTablaActual() {
        return tablaActual;
    }

    /**
     * Establece la tabla actual.
     *
     * @param tablaActual El nombre de la nueva tabla.
     */
    public void setTablaActual(String tablaActual) {
        this.tablaActual = tablaActual;
    }

    /**
     * Obtiene el índice de la fila seleccionada en una tabla.
     *
     * @return El índice de la fila.
     */
    public int getFilaSeleccionada() {
        return filaSeleccionada;
    }

    /**
     * Establece el índice de la fila seleccionada.
     *
     * @param filaSeleccionada El nuevo índice.
     */
    public void setFilaSeleccionada(int filaSeleccionada) {
        this.filaSeleccionada = filaSeleccionada;
    }

    /**
     * Obtiene la lista de correos seleccionados en la bandeja de entrada.
     *
     * @return Lista de correos seleccionados.
     */
    public ArrayList<String> getCorreoSeleccionados() {
        return correoSeleccionados;
    }

    /**
     * Establece la lista de correos seleccionados en la bandeja de entrada.
     *
     * @param correoSeleccionados Nueva lista de correos.
     */
    public void setCorreoSeleccionados(ArrayList<String> correoSeleccionados) {
        this.correoSeleccionados = correoSeleccionados;
    }
}
