package modelo;

import java.util.ArrayList;

public class MoView {
    private String tablaActual;
    private int filaSeleccionada;
    private ArrayList<String> correoSeleccionados;

    public MoView() {
        this.tablaActual = "especies";
        this.filaSeleccionada = -1;
        this.correoSeleccionados = new ArrayList<>();
    }

    public boolean buscarCorreo(String correo) {
        if (!this.correoSeleccionados.contains(correo)) {
            this.correoSeleccionados.add(correo);
            return true;
        }
        this.correoSeleccionados.remove(correo);
        return false;
    }

    public String getTablaActual() {
        return tablaActual;
    }

    public void setTablaActual(String tablaActual) {
        this.tablaActual = tablaActual;
    }

    public int getFilaSeleccionada() {
        return filaSeleccionada;
    }

    public void setFilaSeleccionada(int filaSeleccionada) {
        this.filaSeleccionada = filaSeleccionada;
    }

    public ArrayList<String> getCorreoSeleccionados() {
        return correoSeleccionados;
    }

    public void setCorreoSeleccionados(ArrayList<String> correoSeleccionados) {
        this.correoSeleccionados = correoSeleccionados;
    }
}
