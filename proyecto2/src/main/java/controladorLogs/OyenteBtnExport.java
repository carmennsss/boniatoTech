package controladorLogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import vista.VistaLogs;

/**
 * Oyente para el botón de exportación de logs.
 * Gestiona la selección del archivo destino y la ejecución de la exportación.
 */
public class OyenteBtnExport implements ActionListener {

    /** Gestor de logs para operaciones de exportación. */
    private GestionLogs gestionLogs;

    /** Vista de logs. */
    private VistaLogs vistaLogs;

    /**
     * Constructor del oyente.
     *
     * @param gestionLogs Gestor de logs.
     * @param vistaLogs   Vista de logs.
     */
    public OyenteBtnExport(GestionLogs gestionLogs, VistaLogs vistaLogs) {
        this.gestionLogs = gestionLogs;
        this.vistaLogs = vistaLogs;
    }

    /**
     * Abre el selector de archivos y exporta los logs a formato CSV si se
     * selecciona un destino válido.
     * Muestra mensajes de éxito o error según el resultado.
     *
     * @param e Evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        File archivoDestino = vistaLogs.seleccionarArchivoGuardar();

        if (archivoDestino != null) {

            boolean exito = gestionLogs.exportLogs(archivoDestino);

            if (exito) {
                vistaLogs.mostrarMensaje("Logs exported successfully at:\n" + archivoDestino.getAbsolutePath());
            } else {
                vistaLogs.mostrarError("An error occurred during export.");
            }
        }
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Obtiene el gestor de logs.
     * @return El objeto GestionLogs.
     */
    public GestionLogs getGestionLogs() {
        return gestionLogs;
    }

    /**
     * Establece el gestor de logs.
     * @param gestionLogs El nuevo gestor de logs.
     */
    public void setGestionLogs(GestionLogs gestionLogs) {
        this.gestionLogs = gestionLogs;
    }

    /**
     * Obtiene la vista de logs.
     * @return El objeto VistaLogs.
     */
    public VistaLogs getVistaLogs() {
        return vistaLogs;
    }

    /**
     * Establece la vista de logs.
     * @param vistaLogs La nueva vista de logs.
     */
    public void setVistaLogs(VistaLogs vistaLogs) {
        this.vistaLogs = vistaLogs;
    }
}