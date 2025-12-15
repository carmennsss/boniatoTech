package modelo;

public class MoView {
    private String tablaActual;
    private int filaSeleccionada;

    public MoView() {
        this.tablaActual = "especies";
        this.filaSeleccionada = -1;
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
}
