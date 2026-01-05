package controladorPrincipal;

import controladorCRUD.*;
import controladorRoles.*;
import controladorWhitelist.*;
import controladorUsuarios.*;
import controladorArchivos.OyenteArchivos;
import controladorNavegacion.*;

import modelo.*;
import vista.*;

import javax.swing.*;

import controladorCorreos.ControladorCorreos;
import controladorLogs.ControladorLogs;

/**
 * Controlador Principal de la aplicaci�n.
 * Coordina la inicializaci�n de vistas, modelos y otros controladores
 * espec�ficos.
 * Act�a como punto central para la gesti�n de eventos y la navegaci�n entre
 * m�dulos.
 */
public class CoPrincipal {
    /** Modelo de base de datos para operaciones de persistencia. */
    private ModeloBaseDatos bd;

    /** Modelo de vista para gesti�n de datos de interfaz. */
    private MoView modeloVista;

    /** Vista de login de la aplicaci�n. */
    private VistaLogin vistaLogin;

    /** Vista principal de CRUD para entidades del zool�gico. */
    private VistaCRUD vistaCRUD;

    /** Vista de formulario para crear/editar entidades. */
    private ViFormulario viFormulario;

    /** Vista para crear nuevos roles. */
    private ViCrearRol viCrearRol;

    /** Vista para asignar roles a usuarios. */
    private VistaAsignarRol vistaAsignarRol;

    /** Vista de gesti�n de whitelist. */
    private VistaWhitelist vistaWhitelist;

    /** Vista del gestor de archivos FTP. */
    private VistaGestorArchivos vistaArchivo;

    /** Vista del men� principal de la aplicaci�n. */
    private VistaMenuPrincipal vistaMenuPrincipal;

    /** Vista de administraci�n del sistema. */
    private VistaAdmin vistaAdmin;

    /** Vista de registro de usuarios. */
    private VistaRegistroUsuarios vistaUsuarios;

    /** Vista para eliminar usuarios. */
    private VistaEliminarUsuarios vistaEliminarUsuarios;

    /** Vista general de correo electr�nico. */
    private VistaGeneralCorreo vistaGeneralCorreo;

    /** Vista de logs del sistema. */
    private VistaLogs vistaLogs;

    /** Indica si se est� editando una entidad. */
    private boolean editando;

    /** Modelo del cliente FTP. */
    private ModeloClienteFTP modeloFTP;

    /** Oyente de eventos de whitelist. */
    private OyenteWhitelist oyenteWhitelist;

    /** Controlador de operaciones CRUD. */
    private ControladorCRUD controladorCRUD;

    /** Controlador de gesti�n de roles. */
    private ControladorRoles controladorRoles;

    /** Controlador de whitelist. */
    private ControladorWhitelist controladorWhitelist;

    /** Controlador de gesti�n de usuarios. */
    private ControladorUsuarios controladorUsuarios;

    /** Controlador de correo electr�nico. */
    private ControladorCorreos controladorCorreos;

    /** Controlador de logs del sistema. */
    private ControladorLogs controladorLogs;

    /**
     * Constructor principal.
     * Inicializa la base de datos, el cliente FTP, las vistas y los controladores
     * secundarios.
     */
    public CoPrincipal() {
        this.bd = new ModeloBaseDatos();
        this.modeloVista = new MoView();
        this.vistaLogin = new VistaLogin();
        this.vistaCRUD = new VistaCRUD();
        this.viFormulario = new ViFormulario();
        this.viCrearRol = new ViCrearRol();
        this.vistaAsignarRol = new VistaAsignarRol();
        this.vistaWhitelist = new VistaWhitelist();
        this.editando = false;
        this.controladorCRUD = new ControladorCRUD(this, bd, vistaCRUD, viFormulario, modeloVista);
        this.modeloFTP = new ModeloClienteFTP();
        this.vistaMenuPrincipal = new VistaMenuPrincipal(modeloFTP);
        this.vistaArchivo = new VistaGestorArchivos();
        OyenteArchivos oyenteArchivos = new OyenteArchivos(vistaArchivo, modeloFTP, bd, vistaMenuPrincipal);
        vistaArchivo.setControlador(oyenteArchivos);
        this.vistaAdmin = new VistaAdmin(vistaMenuPrincipal);
        this.controladorRoles = new ControladorRoles(this, bd, viCrearRol, vistaAsignarRol, vistaCRUD, modeloFTP,
                vistaAdmin, modeloVista);
        this.controladorWhitelist = new ControladorWhitelist(vistaWhitelist, bd, modeloVista, vistaAdmin);
        this.vistaUsuarios = new VistaRegistroUsuarios(vistaAdmin, modeloFTP, bd);
        this.controladorUsuarios = new ControladorUsuarios(vistaAdmin, vistaUsuarios);
        this.vistaEliminarUsuarios = new VistaEliminarUsuarios(vistaUsuarios, modeloFTP, bd);
        this.vistaLogs = new VistaLogs(vistaAdmin);
        this.controladorLogs = new ControladorLogs(ModeloBaseDatos.getConexion(), vistaLogs);

        vistaLogin.setVisible(true);
        asignarEventos();
    }

    /**
     * Actualiza los textos de todas las vistas seg�n el idioma seleccionado
     * globalmente.
     */
    public void actualizarIdiomaGlobal() {
        if (vistaWhitelist != null)
            vistaWhitelist.actualizarTextos();
        if (vistaLogin != null)
            vistaLogin.actualizarTextos();
        if (viCrearRol != null)
            viCrearRol.actualizarTextos();
        if (vistaAsignarRol != null)
            vistaAsignarRol.actualizarTextos();
        if (viFormulario != null)
            viFormulario.actualizarTextos();
        if (vistaCRUD != null)
            vistaCRUD.actualizarTextos();
        if (vistaMenuPrincipal != null)
            vistaMenuPrincipal.actualizarTextos();
        if (vistaArchivo != null)
            vistaArchivo.actualizarTextos();
        if (vistaAdmin != null)
            vistaAdmin.actualizarTextos();
        if (vistaUsuarios != null)
            vistaUsuarios.actualizarTextos();
        if (vistaEliminarUsuarios != null)
            vistaEliminarUsuarios.actualizarTextos();
        if (vistaGeneralCorreo != null)
            vistaGeneralCorreo.actualizarTextos();
        if (vistaLogs != null)
            vistaLogs.actualizarTextos();
    }

    /**
     * Inicializa los componentes relacionados con el correo electr�nico una vez
     * logueado el usuario.
     * Crea la vista de correo y su controlador asociado.
     */
    public void instanciarCorreos() {
        this.vistaGeneralCorreo = new VistaGeneralCorreo(bd.obtenerEmailPorUsuario(modeloFTP.getUser()));
        this.controladorCorreos = new ControladorCorreos(bd.obtenerEmailPorUsuario(modeloFTP.getUser()),
                vistaGeneralCorreo, bd, vistaMenuPrincipal);
        new controladorLogs.GestionLogs();
    }

    /**
     * Asigna los eventos a distintas vistas (con tablas, botones, etc.).
     */
    private void asignarEventos() {
        OyenteFTP oyFTP = new OyenteFTP(modeloVista, vistaLogin, vistaCRUD, modeloFTP, this, vistaArchivo,
                vistaMenuPrincipal, vistaAdmin, vistaUsuarios, vistaLogs, bd);
        OyenteTablaRoles oyTablaRoles = new OyenteTablaRoles(this, vistaAsignarRol, modeloVista);
        OyenteCRUD oyCRUD = new OyenteCRUD(this, vistaCRUD, viFormulario, modeloVista, vistaMenuPrincipal);
        controladorCRUD.setOyente(oyCRUD);
        OyenteTablaCRUD oyT = new OyenteTablaCRUD(this, vistaCRUD, modeloVista);

        this.oyenteWhitelist = new OyenteWhitelist(controladorWhitelist, vistaWhitelist);

        OyenteUsuario oyU = new OyenteUsuario(vistaUsuarios, vistaEliminarUsuarios, bd, modeloFTP);
        vistaUsuarios.getAniadir().addActionListener(oyU);
        vistaUsuarios.getEliminar().addActionListener(oyU);
        vistaUsuarios.getVolver().addActionListener(oyU);
        vistaEliminarUsuarios.getBtnEliminar().addActionListener(oyU);
        vistaEliminarUsuarios.getBtnVolver().addActionListener(oyU);

        JButton[] botonesLogin = {
                vistaLogin.getBotones().get(0),
                vistaMenuPrincipal.getBotonCRUD(),
                vistaMenuPrincipal.getBotonFileManager(),
                vistaMenuPrincipal.getBotonCorreo(),
                vistaMenuPrincipal.getBotonAdmin(),
                vistaMenuPrincipal.getBotonCerrarSesion(),
                vistaAdmin.getBotonCrearUsuario(),
                viCrearRol.getBotones().get(0),
                viCrearRol.getBotones().get(1),
                vistaAdmin.getBotonCrearRoles(),
                vistaAdmin.getBotonAsignarRoles(),
                vistaAdmin.getBotonLogs(),
                vistaAdmin.getBotonWhitelist(),
                vistaAsignarRol.getBotones().get(0),
                vistaAsignarRol.getBotones().get(1),
                vistaAsignarRol.getBotones().get(2),
                vistaAdmin.getBotonVolver(),
        };

        for (JButton btn : botonesLogin) {
            btn.addActionListener(oyFTP);
        }

        vistaWhitelist.getBotones().get(0).addActionListener(oyenteWhitelist);
        vistaWhitelist.getBotones().get(1).addActionListener(oyenteWhitelist);
        vistaWhitelist.getBotones().get(2).addActionListener(oyenteWhitelist);
        vistaWhitelist.getTabla().getTabla().addMouseListener(oyenteWhitelist);

        vistaAsignarRol.getTabla().getTabla().addMouseListener(oyTablaRoles);

        for (JButton btn : vistaCRUD.getPanelMenu().getBotones()) {
            btn.addActionListener(oyCRUD);
        }

        for (JButton btn : vistaCRUD.getPanelAcciones().getBotones()) {
            btn.addActionListener(oyCRUD);
        }

        vistaCRUD.getPanelTabla().getTabla().addMouseListener(oyT);

        OyenteIdioma oyIdioma = new OyenteIdioma(this);
        vistaLogin.getComboIdiomas().addActionListener(oyIdioma);
        vistaMenuPrincipal.getComboIdiomas().addActionListener(oyIdioma);
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Obtiene el controlador de operaciones CRUD.
     * 
     * @return Controlador CRUD.
     */
    public ControladorCRUD getControladorCRUD() {
        return controladorCRUD;
    }

    /**
     * Obtiene el controlador de correos.
     * 
     * @return Controlador de correos.
     */
    public ControladorCorreos getControladorCorreos() {
        return controladorCorreos;
    }

    /**
     * Obtiene el controlador de gesti�n de roles.
     * 
     * @return Controlador de roles.
     */
    public ControladorRoles getControladorRoles() {
        return controladorRoles;
    }

    /**
     * Obtiene el controlador de gesti�n de usuarios.
     * 
     * @return Controlador de usuarios.
     */
    public ControladorUsuarios getControladorUsuarios() {
        return controladorUsuarios;
    }

    /**
     * Obtiene el controlador de whitelist.
     * 
     * @return Controlador de whitelist.
     */
    public ControladorWhitelist getControladorWhitelist() {
        return controladorWhitelist;
    }

    /**
     * Obtiene la vista de asignar rol.
     * 
     * @return Vista de asignar rol.
     */
    public VistaAsignarRol getVistaAsignarRol() {
        return vistaAsignarRol;
    }

    /**
     * Obtiene la vista CRUD.
     * 
     * @return Vista CRUD.
     */
    public VistaCRUD getVistaCRUD() {
        return vistaCRUD;
    }

    /**
     * Obtiene la vista de crear rol.
     * 
     * @return Vista de crear rol.
     */
    public ViCrearRol getViCrearRol() {
        return viCrearRol;
    }

    /**
     * Obtiene la vista general de correo.
     * 
     * @return Vista general de correo.
     */
    public VistaGeneralCorreo getVistaGeneralCorreo() {
        return this.vistaGeneralCorreo;
    }

    /**
     * Obtiene la vista de formulario.
     * 
     * @return Vista de formulario.
     */
    public ViFormulario getViFormulario() {
        return viFormulario;
    }

    /**
     * Obtiene la vista de login.
     * 
     * @return Vista de login.
     */
    public VistaLogin getVistaLogin() {
        return vistaLogin;
    }

    /**
     * Obtiene la vista de whitelist.
     * 
     * @return Vista de whitelist.
     */
    public VistaWhitelist getVistaWhitelist() {
        return vistaWhitelist;
    }

    /**
     * Verifica si se est� editando una entidad.
     * 
     * @return true si se est� editando, false en caso contrario.
     */
    public boolean isEditando() {
        return editando;
    }

    /**
     * Establece el controlador de correos.
     * 
     * @param controladorCorreos Controlador de correos a establecer.
     */
    public void setControladorCorreos(ControladorCorreos controladorCorreos) {
        this.controladorCorreos = controladorCorreos;
    }

    /**
     * Establece el estado de edici�n.
     * 
     * @param editando true si se est� editando, false en caso contrario.
     */
    public void setEditando(boolean editando) {
        this.editando = editando;
    }
}