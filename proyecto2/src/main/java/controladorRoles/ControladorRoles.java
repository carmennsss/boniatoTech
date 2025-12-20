package controladorRoles;

import controladorPrincipal.CoPrincipal;
import controladorCRUD.OyenteTablaCRUD;

import javax.swing.JOptionPane;

import modelo.*;
import vista.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controlador para la gestión de roles y permisos.
 * Maneja la creación de roles, asignación de permisos a roles y asignación de
 * permisos a usuarios.
 */
public class ControladorRoles {
    /** Modelo de base de datos para operaciones de persistencia. */
    private ModeloBaseDatos bd;

    /** Vista para crear roles. */
    private ViCrearRol viCrearRol;

    /** Vista para asignar roles a usuarios. */
    private VistaAsignarRol vistaAsignarRol;

    /** Vista CRUD principal. */
    private VistaCRUD vistaCRUD;

    /** Modelo del cliente FTP. */
    private ModeloClienteFTP modeloFTP;

    /** Modelo de vista para gestión de datos. */
    private MoView modeloVista;

    /** Controlador principal de la aplicación. */
    private CoPrincipal coPrincipal;

    /** Vista de administración. */
    private VistaAdmin vistaAdmin;

    /**
     * Constructor del controlador de roles.
     *
     * @param coPrincipal     Controlador principal.
     * @param bd              Modelo de base de datos.
     * @param viCrearRol      Vista de crear rol.
     * @param vistaAsignarRol Vista de asignar rol.
     * @param vistaCRUD       Vista CRUD.
     * @param modeloFTP       Modelo del cliente FTP.
     * @param vistaAdmin      Vista de administración.
     * @param modeloVista     Modelo de vista.
     */
    public ControladorRoles(CoPrincipal coPrincipal, ModeloBaseDatos bd, ViCrearRol viCrearRol,
            VistaAsignarRol vistaAsignarRol, VistaCRUD vistaCRUD, ModeloClienteFTP modeloFTP,
            VistaAdmin vistaAdmin, MoView modeloVista) {
        this.coPrincipal = coPrincipal;
        this.bd = bd;
        this.viCrearRol = viCrearRol;
        this.vistaAsignarRol = vistaAsignarRol;
        this.vistaCRUD = vistaCRUD;
        this.modeloFTP = modeloFTP;
        this.vistaAdmin = vistaAdmin;
        this.modeloVista = modeloVista;
    }

    /**
     * Abre la vista de asignación de roles.
     * Rellena el combo de roles y la tabla de usuarios, luego muestra la vista.
     */
    public void abrirAsignarRoles() {
        rellenarComboRoles();
        rellenarTablaUsuarios();
        vistaAsignarRol.setVisible(true);
        vistaAdmin.setVisible(false);
    }

    /**
     * Abre la vista de creación de roles.
     * Oculta la vista de administración y muestra la vista de crear rol.
     */
    public void abrirCrearRol() {
        rellenarVentanaCrearRol();
        viCrearRol.hacerVisible();
        vistaAdmin.setVisible(false);
    }

    /**
     * Actualiza el estado de un permiso para un rol específico.
     *
     * @param rolNombre     El nombre del rol.
     * @param permisoNombre El nombre del permiso.
     * @param isChecked     true si el permiso debe ser asignado, false si debe ser
     * revocado.
     */
    public void actualizarPermiso(String rolNombre, String permisoNombre, boolean isChecked) {
        String sql;
        ArrayList<String> params = new ArrayList<>();
        params.add(rolNombre);
        params.add(permisoNombre);

        if (isChecked) {
            sql = "INSERT INTO roles_permisos (roles_id, permisos_id) " +
                    "VALUES ((SELECT id_roles FROM roles WHERE nombre_roles = ?), " +
                    "(SELECT id_permisos FROM permisos WHERE nombre_permisos = ?));";
        } else {
            sql = "DELETE FROM roles_permisos WHERE roles_id = (SELECT id_roles FROM roles WHERE nombre_roles = ?) " +
                    "AND permisos_id = (SELECT id_permisos FROM permisos WHERE nombre_permisos = ?);";
        }
        bd.ejecutarActualizacion(sql, params);
    }

    /**
     * Agrega un nuevo rol a la base de datos con el nombre y descripción
     * proporcionados en la vista.
     */
    public void agregarRol() {
        String nombre = viCrearRol.getTextFieldNombre().getText();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(viCrearRol, MoTextos.msg_role_name_empty);
            return;
        }
        String descripcion = viCrearRol.getTextFieldDescripcion().getText();
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(viCrearRol, MoTextos.msg_role_desc_empty);
            return;
        }

        String sql = "INSERT INTO roles (nombre_roles, descripcion_roles) VALUES (?, ?);";
        ArrayList<String> valores = new ArrayList<>();
        valores.add(nombre);
        valores.add(descripcion);
        bd.ejecutarActualizacion(sql, valores);

        rellenarVentanaCrearRol();
    }

    /**
     * Asigna el rol seleccionado a los usuarios marcados.
     * * @param correos Lista de correos de usuarios seleccionados.
     * @param rol      Rol a asignar.
     */
    public void asignarRolAUsuarios(ArrayList<String> correos, Rol rol) {
        asignarRol(true, correos, rol);
        limpiarSeleccionRoles();
    }

    /**
     * Desasigna el rol seleccionado de los usuarios marcados.
     * * @param correos Lista de correos de usuarios seleccionados.
     * @param rol      Rol a desasignar.
     */
    public void desasignarRol(ArrayList<String> correos, Rol rol) {
        asignarRol(false, correos, rol);
        limpiarSeleccionRoles();
    }

    /**
     * Limpia la selección de usuarios y actualiza la tabla de asignación de roles.
     */
    public void limpiarSeleccionRoles() {
        modeloVista.getCorreoSeleccionados().clear();
        vistaAsignarRol.getTabla().deseleccionarFilas();
        rellenarTablaUsuarios();
    }

    /**
     * Muestra el diálogo para agregar un nuevo rol y lo procesa.
     */
    public void mostrarDialogoAgregarRol() {
        if (viCrearRol.mostrarAgregarRol() == 0) {
            agregarRol();
        }
    }

    /**
     * Rellena el combobox de selección de roles con los roles disponibles en la
     * base de datos.
     */
    public void rellenarComboRoles() {
        String sql = "SELECT * FROM roles;";
        ResultSet rs = bd.getConsulta(sql);
        vistaAsignarRol.getComboRoles().removeAllItems();
        try {
            while (rs.next()) {
                Rol rol = new Rol(rs.getInt("id_roles"), rs.getString("nombre_roles"),
                        rs.getString("descripcion_roles"));
                vistaAsignarRol.getComboRoles().addItem(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Rellena la tabla de usuarios con su información y roles asignados.
     */
    public void rellenarTablaUsuarios() {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn(MoTextos.col_user);
        modeloTabla.addColumn(MoTextos.col_email);
        modeloTabla.addColumn(MoTextos.col_roles);

        String sql = "SELECT usuarios.nombre_usuario, usuarios.email AS correo, GROUP_CONCAT(roles.nombre_roles SEPARATOR ', ') AS roles FROM usuarios LEFT JOIN usuarios_roles ON usuarios.email = usuarios_roles.email_usuario LEFT JOIN roles ON roles.id_roles = usuarios_roles.roles_id GROUP BY usuarios.nombre_usuario, usuarios.email;";
        ResultSet rs = bd.getConsulta(sql);

        try {
            while (rs.next()) {
                User usuario = new User(rs.getString("nombre_usuario"), rs.getString("correo"));
                if (rs.getString("roles") == null || rs.getString("roles").isEmpty()
                        || rs.getString("roles").equalsIgnoreCase("NULL")) {
                    modeloTabla.addRow(new Object[] { usuario.getNombre(), usuario.getCorreo(), MoTextos.msg_no_roles });
                } else {
                    modeloTabla.addRow(new Object[] { usuario.getNombre(), usuario.getCorreo(), rs.getString("roles") });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        vistaAsignarRol.getTabla().setModelo(modeloTabla);
    }

    /**
     * Rellena la tabla de creación de roles con los roles existentes y sus
     * permisos.
     * Configura el modelo de la tabla para permitir la edición de permisos
     * (checkboxes).
     */
    public void rellenarVentanaCrearRol() {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                try {
                    Object idObj = getValueAt(row, 0);
                    int idRol = -1;
                    if (idObj instanceof Integer) {
                        idRol = (Integer) idObj;
                    } else if (idObj instanceof String) {
                        idRol = Integer.parseInt((String) idObj);
                    }
                    if (idRol == 3) {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
                return column > 2;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex > 2) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        ArrayList<String> listaNombresPermisos = new ArrayList<>();
        String sqlPermisos = "SELECT * FROM permisos ORDER BY nombre_permisos;";
        ResultSet rsPermisos = bd.getConsulta(sqlPermisos);

        modeloTabla.addColumn(MoTextos.col_id);
        modeloTabla.addColumn(MoTextos.col_role);
        modeloTabla.addColumn(MoTextos.col_desc);

        try {
            while (rsPermisos.next()) {
                String nombreP = rsPermisos.getString("nombre_permisos");
                listaNombresPermisos.add(nombreP);
                modeloTabla.addColumn(nombreP);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String sqlDatos = "SELECT r.id_roles, r.nombre_roles, r.descripcion_roles, p.nombre_permisos, " +
                "(SELECT COUNT(*) FROM roles_permisos rp WHERE rp.roles_id = r.id_roles AND rp.permisos_id = p.id_permisos) AS activo " +
                "FROM roles r, permisos p ORDER BY r.nombre_roles, p.nombre_permisos;";

        ResultSet rsDatos = bd.getConsulta(sqlDatos);

        try {
            int ultimoRolId = -1;
            Object[] filaActual = null;

            while (rsDatos.next()) {
                int idRol = rsDatos.getInt("id_roles");
                String rolLeido = rsDatos.getString("nombre_roles");
                String descLeida = rsDatos.getString("descripcion_roles");
                String permisoLeido = rsDatos.getString("nombre_permisos");
                boolean estaActivo = rsDatos.getInt("activo") == 1;

                if (idRol != ultimoRolId) {
                    if (filaActual != null) {
                        modeloTabla.addRow(filaActual);
                    }
                    filaActual = new Object[3 + listaNombresPermisos.size()];
                    filaActual[0] = idRol;
                    filaActual[1] = rolLeido;
                    filaActual[2] = descLeida;

                    for (int i = 3; i < filaActual.length; i++) {
                        filaActual[i] = false;
                    }
                    ultimoRolId = idRol;
                }

                int indicePermiso = listaNombresPermisos.indexOf(permisoLeido);
                if (indicePermiso != -1) {
                    filaActual[indicePermiso + 3] = estaActivo;
                }
            }
            if (filaActual != null) {
                modeloTabla.addRow(filaActual);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        OyenteTablaCRUD oyentePermisos = new OyenteTablaCRUD(coPrincipal, vistaCRUD, modeloVista);
        oyentePermisos.setListaNombresPermisos(listaNombresPermisos);
        modeloTabla.addTableModelListener(oyentePermisos);

        viCrearRol.getPanelTabla().getTabla().setModel(modeloTabla);
        viCrearRol.getPanelTabla().getTabla().getColumnModel().getColumn(0).setMinWidth(0);
        viCrearRol.getPanelTabla().getTabla().getColumnModel().getColumn(0).setMaxWidth(0);
        viCrearRol.getPanelTabla().getTabla().getColumnModel().getColumn(0).setWidth(0);
    }

    /**
     * Vuelve al panel de administración desde la gestión de roles.
     * Oculta las vistas de crear y asignar roles.
     */
    public void volverAdminDesdeRoles() {
        viCrearRol.setVisible(false);
        vistaAsignarRol.setVisible(false);
        vistaAdmin.hacerVisible();
    }

    /**
     * Asigna o desasigna un rol a una lista de usuarios seleccionados.
     *
     * @param asignar             true para asignar el rol, false para desasignar.
     * @param correoSeleccionados Lista de correos electrónicos de los usuarios.
     * @param rol                 El rol a asignar o desasignar.
     */
    public void asignarRol(boolean asignar, ArrayList<String> correoSeleccionados, Rol rol) {
        String sql = "";
        String accion = "";
        if (asignar) {
            accion = "asignado";
            sql = "INSERT INTO usuarios_roles (email_usuario, roles_id) VALUES (?, ?);";
        } else {
            accion = "desasignado";
            sql = "DELETE FROM usuarios_roles WHERE email_usuario = ? AND roles_id = ?;";
        }
        for (String correo : correoSeleccionados) {
            String sqlComprobar = "SELECT * FROM usuarios_roles WHERE email_usuario = ? AND roles_id = ?;";
            if (!bd.existeRegistro(sqlComprobar,
                    new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))))
                    && accion.equalsIgnoreCase("asignado")) {
                bd.ejecutarActualizacion(sql,
                        new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))));
            } else if (bd.existeRegistro(sqlComprobar,
                    new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))))
                    && accion.equalsIgnoreCase("desasignado")) {
                bd.ejecutarActualizacion(sql,
                        new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))));
            } else {
                JOptionPane.showMessageDialog(viCrearRol,
                        "The user " + correo + " already has the role " + rol.getNombre_roles() + " " + accion);
            }
        }
    }
}