package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;

public class CoPrincipal {
    private ModeloBaseDatos bd;
    private MoView modeloVista;
    private ViMain vista;
    private VistaGestorArchivos vistaArchivo;
    private VistaMenuPrincipal vistaMenuPrincipal;
    private VistaAdmin vistaAdmin;
    private VistaRegistroUsuarios vistaUsuarios;
    private boolean editando;
    private ModeloClienteFTP modeloFTP;

    private ControladorCRUD controladorCRUD;
    private ControladorRoles controladorRoles;

    public CoPrincipal() {
        this.bd = new ModeloBaseDatos();
        this.modeloVista = new MoView();
        this.vista = new ViMain();
        this.editando = false;
        this.controladorCRUD = new ControladorCRUD(this, bd, vista, modeloVista);

        this.modeloFTP = new ModeloClienteFTP();
        this.controladorRoles = new ControladorRoles(this, bd, vista, modeloFTP, modeloVista);
        this.vistaMenuPrincipal = new VistaMenuPrincipal(modeloFTP, vista);
        this.vistaArchivo = new VistaGestorArchivos(modeloFTP, vistaMenuPrincipal);
        this.vistaAdmin = new VistaAdmin(vistaMenuPrincipal);
        this.vistaUsuarios = new VistaRegistroUsuarios(vistaAdmin, modeloFTP, bd);
        vista.hacerVisible();
        asignarEventos();

    }

    private void asignarEventos() {
        OyenteLogin oyLogin = new OyenteLogin(modeloVista, vista, modeloFTP, this, vistaArchivo, vistaMenuPrincipal,
                vistaAdmin,
                vistaUsuarios);
        OyenteTablaRoles oyTablaRoles = new OyenteTablaRoles(this, vista, modeloVista);
        OyenteBot oyB = new OyenteBot(this, vista, modeloVista, vistaMenuPrincipal);
        OyenteTabla oyT = new OyenteTabla(this, vista, modeloVista);

        JButton[] botonesLogin = {
                vista.getPanelLogin().getBotones().get(0),
                vistaMenuPrincipal.getBotonCRUD(),
                vistaMenuPrincipal.getBotonFileManager(),
                vistaMenuPrincipal.getBotonAdmin(),
                vistaAdmin.getBotonCrearUsuario(),
                vista.getViCrearRol().getBotones().get(0),
                vistaAdmin.getBotonCrearRoles(),
                vistaAdmin.getBotonAsignarRoles(),
                vista.getViAsignarRol().getBotones().get(0),
                vista.getViAsignarRol().getBotones().get(1),
                vista.getViAsignarRol().getBotones().get(2)
        };

        for (JButton btn : botonesLogin) {
            btn.addActionListener(oyLogin);
        }

        vista.getViAsignarRol().getTabla().getTabla().addMouseListener(oyTablaRoles);

        for (JButton btn : vista.getPanelMenu().getBotones()) {
            btn.addActionListener(oyB);
        }

        for (JButton btn : vista.getPanelAcciones().getBotones()) {
            btn.addActionListener(oyB);
        }

        vista.getPanelTabla().getTabla().addMouseListener(oyT);
    }

    public void setEditando(boolean editando) {
        this.editando = editando;
    }

    public boolean isEditando() {
        return editando;
    }

    public ControladorCRUD getControladorCRUD() {
        return controladorCRUD;
    }

    public ControladorRoles getControladorRoles() {
        return controladorRoles;
    }
}
