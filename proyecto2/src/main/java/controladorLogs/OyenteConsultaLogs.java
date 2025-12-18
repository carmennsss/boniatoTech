package controladorLogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import vista.VistaLogs;

public class OyenteConsultaLogs implements ActionListener {
    private VistaLogs vistaLogs;
    private ControladorLogs controladorLogs;

    public OyenteConsultaLogs(VistaLogs vistaLogs, ControladorLogs controladorLogs) {
        this.vistaLogs = vistaLogs;
        this.controladorLogs = controladorLogs;
    }

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
