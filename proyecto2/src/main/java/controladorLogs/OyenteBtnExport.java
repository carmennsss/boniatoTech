package controladorLogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import vista.VistaLogs;

public class OyenteBtnExport implements ActionListener {
    
    private GestionLogs gestionLogs;
    private VistaLogs vistaLogs; // Necesitamos referencia a la vista

    public OyenteBtnExport(GestionLogs gestionLogs, VistaLogs vistaLogs) {
        this.gestionLogs = gestionLogs;
        this.vistaLogs = vistaLogs;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 1. Pedir a la vista que elija el archivo
        File archivoDestino = vistaLogs.seleccionarArchivoGuardar();

        if (archivoDestino != null) {
            // 2. Mandar al modelo a procesar los datos y guardar
            boolean exito = gestionLogs.exportLogs(archivoDestino);

            // 3. Actualizar la vista con el resultado
            if (exito) {
                vistaLogs.mostrarMensaje("Logs exported successfully at:\n" + archivoDestino.getAbsolutePath());
            } else {
                vistaLogs.mostrarError("Ocurred an error.");
            }
        }
    }
}