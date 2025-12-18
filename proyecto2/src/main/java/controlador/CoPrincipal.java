package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;

import controladorCorreos.ControladorCorreos;

public class CoPrincipal {
    private ModeloBaseDatos bd;
    private MoView modeloVista;
    private ViMain vista;
    private VistaGestorArchivos vistaArchivo;
    private VistaMenuPrincipal vistaMenuPrincipal;
    private VistaAdmin vistaAdmin;
    private VistaRegistroUsuarios vistaUsuarios;
    private VistaEliminarUsuarios vistaEliminarUsuarios;
    private VistaGeneralCorreo vistaGeneralCorreo;
    private boolean editando;
    private ModeloClienteFTP modeloFTP;

    private OyenteWhitelist oyenteWhitelist;
    private ControladorCRUD controladorCRUD;
    private ControladorRoles controladorRoles;
    private ControladorWhitelist controladorWhitelist;
    private ControladorCorreos controladorCorreos;

    public CoPrincipal() {
        this.bd = new ModeloBaseDatos();
        this.modeloVista = new MoView();
        this.vista = new ViMain();
        this.editando = false;
        this.controladorCRUD = new ControladorCRUD(this, bd, vista, modeloVista);
        this.modeloFTP = new ModeloClienteFTP();
        this.controladorRoles = new ControladorRoles(this, bd, vista, modeloFTP, modeloVista);
        this.vistaMenuPrincipal = new VistaMenuPrincipal(modeloFTP, vista);
        this.vistaArchivo = new VistaGestorArchivos();
        OyenteArchivos oyenteArchivos = new OyenteArchivos(vistaArchivo, modeloFTP, bd, vistaMenuPrincipal);
        vistaArchivo.setControlador(oyenteArchivos);
        this.vistaAdmin = new VistaAdmin(vistaMenuPrincipal);
        this.controladorWhitelist = new ControladorWhitelist(vista, bd, modeloVista, vistaAdmin);
        this.vistaUsuarios = new VistaRegistroUsuarios(vistaAdmin, modeloFTP, bd);
        this.vistaEliminarUsuarios = new VistaEliminarUsuarios(vistaUsuarios, modeloFTP, bd);

        vista.hacerVisible();
        asignarEventos();

    }

    private void asignarEventos() {
        OyenteFTP oyFTP = new OyenteFTP(modeloVista, vista, modeloFTP, this, vistaArchivo, vistaMenuPrincipal,
                vistaAdmin,
                vistaUsuarios, bd);
        OyenteTablaRoles oyTablaRoles = new OyenteTablaRoles(this, vista, modeloVista);
        OyenteCRUD oyCRUD = new OyenteCRUD(this, vista, modeloVista, vistaMenuPrincipal);
        controladorCRUD.setOyente(oyCRUD);
        OyenteTablaCRUD oyT = new OyenteTablaCRUD(this, vista, modeloVista);

        this.oyenteWhitelist = new OyenteWhitelist(controladorWhitelist, vista.getViWhitelist());

        OyenteUsuario oyU = new OyenteUsuario(vistaUsuarios, vistaEliminarUsuarios, bd, modeloFTP);
        vistaUsuarios.getAniadir().addActionListener(oyU);
        vistaUsuarios.getEliminar().addActionListener(oyU);
        vistaUsuarios.getVolver().addActionListener(oyU);
        vistaEliminarUsuarios.getBtnEliminar().addActionListener(oyU);
        vistaEliminarUsuarios.getBtnVolver().addActionListener(oyU);
        JButton[] botonesLogin = {
                vista.getPanelLogin().getBotones().get(0),
                vistaMenuPrincipal.getBotonCRUD(),
                vistaMenuPrincipal.getBotonFileManager(),
                vistaMenuPrincipal.getBotonCorreo(),
                vistaMenuPrincipal.getBotonAdmin(),
                vistaMenuPrincipal.getBotonCerrarSesion(),
                vistaAdmin.getBotonCrearUsuario(),
                vista.getViCrearRol().getBotones().get(0),
                vista.getViCrearRol().getBotones().get(1),
                vistaAdmin.getBotonCrearRoles(),
                vistaAdmin.getBotonAsignarRoles(),
                vistaAdmin.getBotonWhitelist(),
                vista.getViAsignarRol().getBotones().get(0),
                vista.getViAsignarRol().getBotones().get(1),
                vista.getViAsignarRol().getBotones().get(2),
                vistaAdmin.getBotonVolver(),

        };

        for (JButton btn : botonesLogin) {
            btn.addActionListener(oyFTP);
        }

        // Register OyenteWhitelist
        vista.getViWhitelist().getBotones().get(0).addActionListener(oyenteWhitelist); // Add
        vista.getViWhitelist().getBotones().get(1).addActionListener(oyenteWhitelist); // Remove
        vista.getViWhitelist().getBotones().get(2).addActionListener(oyenteWhitelist); // Back
        vista.getViWhitelist().getTabla().getTabla().addMouseListener(oyenteWhitelist); // Table Click

        vista.getViAsignarRol().getTabla().getTabla().addMouseListener(oyTablaRoles);

        for (JButton btn : vista.getPanelMenu().getBotones()) {
            btn.addActionListener(oyCRUD);
        }

        for (JButton btn : vista.getPanelAcciones().getBotones()) {
            btn.addActionListener(oyCRUD);
        }

        vista.getPanelTabla().getTabla().addMouseListener(oyT);
    }

    public void instanciarCorreos() {
    	this.vistaGeneralCorreo = new VistaGeneralCorreo(bd.obtenerEmailPorUsuario(modeloFTP.getUser()));
        this.controladorCorreos = new ControladorCorreos(bd.obtenerEmailPorUsuario(modeloFTP.getUser()), vistaGeneralCorreo, bd, vistaMenuPrincipal);
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

    public ControladorWhitelist getControladorWhitelist() {
        return controladorWhitelist;
    }

    public ControladorCorreos getControladorCorreos() {
        return controladorCorreos;
    }

    public void setControladorCorreos(ControladorCorreos controladorCorreos) {
        this.controladorCorreos = controladorCorreos;
    }

    public VistaGeneralCorreo getVistaGeneralCorreo() {
        return this.vistaGeneralCorreo;
    }
}
