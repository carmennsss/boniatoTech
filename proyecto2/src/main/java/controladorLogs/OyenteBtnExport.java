package controladorLogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import vista.VistaLogs;

public class OyenteBtnExport implements ActionListener {

    private GestionLogs gestionLogs;
    private VistaLogs vistaLogs;

    public OyenteBtnExport(GestionLogs gestionLogs, VistaLogs vistaLogs) {
        this.gestionLogs = gestionLogs;
        this.vistaLogs = vistaLogs;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        File archivoDestino = vistaLogs.seleccionarArchivoGuardar();

        if (archivoDestino != null) {

            boolean exito = gestionLogs.exportLogs(archivoDestino);

            if (exito) {
                vistaLogs.mostrarMensaje("Logs exported successfully at:\n" + archivoDestino.getAbsolutePath());
            } else {
                vistaLogs.mostrarError("Ocurred an error.");
            }
        }
    }
}