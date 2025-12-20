package controladorLogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vista.VistaLogs;

/**
 * Oyente para los botones de consulta de logs.
 * Permite filtrar/ordenar los logs por acciones, usuarios, fechas o resultados.
 */
public class OyenteConsultaLogs implements ActionListener {
    /** Vista de logs. */
    private VistaLogs vistaLogs;

    /** Controlador de logs. */
    private ControladorLogs controladorLogs;

    /**
     * Constructor del oyente.
     *
     * @param vistaLogs       Vista de logs.
     * @param controladorLogs Controlador de logs.
     */
    public OyenteConsultaLogs(VistaLogs vistaLogs, ControladorLogs controladorLogs) {
        this.vistaLogs = vistaLogs;
        this.controladorLogs = controladorLogs;
    }

    /**
     * Identifica qué botón se pulsó y solicita al controlador la carga de logs con
     * el criterio correspondiente.
     *
     * @param e Evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton boton = (JButton) e.getSource();

        if (vistaLogs.getBotonesConsultas().get(0).equals(boton)) {
            String consulta = "actions";
            controladorLogs.cargarLogs(consulta);
        } else if (vistaLogs.getBotonesConsultas().get(1).equals(boton)) {
            String consulta = "users";
            controladorLogs.cargarLogs(consulta);
        } else if (vistaLogs.getBotonesConsultas().get(2).equals(boton)) {
            String consulta = "dates";
            controladorLogs.cargarLogs(consulta);
        } else if (vistaLogs.getBotonesConsultas().get(3).equals(boton)) {
            String consulta = "results";
            controladorLogs.cargarLogs(consulta);
        }
    }
}
