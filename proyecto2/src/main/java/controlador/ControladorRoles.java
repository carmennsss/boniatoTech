package controlador;

import modelo.*;
import vista.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ControladorRoles {
    private ModeloBaseDatos bd;
    private ViMain vista;
    private ModeloClienteFTP modeloFTP;
    private MoView modeloVista;
    private CoPrincipal coPrincipal;

    public ControladorRoles(CoPrincipal coPrincipal, ModeloBaseDatos bd, ViMain vista, ModeloClienteFTP modeloFTP,
            MoView modeloVista) {
        this.coPrincipal = coPrincipal;
        this.bd = bd;
        this.vista = vista;
        this.modeloFTP = modeloFTP;
        this.modeloVista = modeloVista;
    }

    // --- MÉTODOS DE VISTA ---

    public void rellenarVentanaCrearRol() {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                try {
                    // Obtener el ID del rol (columna 0)
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

        modeloTabla.addColumn("ID"); // Nueva columna ID
        modeloTabla.addColumn("Rol");
        modeloTabla.addColumn("Descripción");

        try {
            while (rsPermisos.next()) {
                String nombreP = rsPermisos.getString("nombre_permisos");
                listaNombresPermisos.add(nombreP);
                modeloTabla.addColumn(nombreP);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Modificamos la query para obtener tambien el id_roles
        String sqlDatos = "SELECT r.id_roles, r.nombre_roles, r.descripcion_roles, p.nombre_permisos, " +
                "(SELECT COUNT(*) FROM roles_permisos rp WHERE rp.roles_id = r.id_roles AND rp.permisos_id = p.id_permisos) AS activo "
                +
                "FROM roles r, permisos p " +
                "ORDER BY r.nombre_roles, p.nombre_permisos;";

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

                    // Tamaño: ID + Rol + Desc + listaPermisos
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
        OyenteTabla oyentePermisos = new OyenteTabla(coPrincipal, vista, modeloVista);
        oyentePermisos.setListaNombresPermisos(listaNombresPermisos);
        modeloTabla.addTableModelListener(oyentePermisos);

        vista.getViCrearRol().getPanelTabla().getTabla().setModel(modeloTabla);

        // Ocultar la columna ID (Opcional, pero recomendado para UI limpia)
        // vista.getViCrearRol().getPanelTabla().getTabla().getColumnModel().getColumn(0).setMinWidth(0);
        // vista.getViCrearRol().getPanelTabla().getTabla().getColumnModel().getColumn(0).setMaxWidth(0);
        // vista.getViCrearRol().getPanelTabla().getTabla().getColumnModel().getColumn(0).setWidth(0);
        // De momento la dejo visible para debug si el usuario quiere, o la oculto?
        // El usuario dijo "NO LO PUEDES MIRAR POR EL ID", no dijo que lo mostrara.
        // Pero no tengo acceso directo a la tabla aquí facil sin getters largos, y
        // arriba ya hago getTabla().
        // Voy a ocultarla para que se vea igual que antes.
        vista.getViCrearRol().getPanelTabla().getTabla().getColumnModel().getColumn(0).setMinWidth(0);
        vista.getViCrearRol().getPanelTabla().getTabla().getColumnModel().getColumn(0).setMaxWidth(0);
        vista.getViCrearRol().getPanelTabla().getTabla().getColumnModel().getColumn(0).setWidth(0);
    }

    public void rellenarTablaUsuarios() {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modeloTabla.addColumn("Usuario");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Roles");

        String sql = "SELECT usuarios.nombre_usuario, usuarios.email AS correo, GROUP_CONCAT(roles.nombre_roles SEPARATOR ', ') AS roles FROM usuarios LEFT JOIN usuarios_roles ON usuarios.email = usuarios_roles.email_usuario LEFT JOIN roles ON roles.id_roles = usuarios_roles.roles_id GROUP BY usuarios.nombre_usuario, usuarios.email;";
        ResultSet rs = bd.getConsulta(sql);

        try {
            while (rs.next()) {
                User usuario = new User(rs.getString("nombre_usuario"), rs.getString("correo"));
                if (rs.getString("roles") == null || rs.getString("roles").isEmpty()
                        || rs.getString("roles").equalsIgnoreCase("NULL")) {
                    modeloTabla.addRow(new Object[] { usuario.getNombre(), usuario.getCorreo(), "No tiene roles" });
                } else {
                    modeloTabla
                            .addRow(new Object[] { usuario.getNombre(), usuario.getCorreo(), rs.getString("roles") });
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        vista.getViAsignarRol().getTabla().setModelo(modeloTabla);
    }

    public void rellenarComboRoles() {
        String sql = "SELECT * FROM roles;";
        ResultSet rs = bd.getConsulta(sql);
        vista.getViAsignarRol().getComboRoles().removeAllItems();
        try {
            while (rs.next()) {
                Rol rol = new Rol(rs.getInt("id_roles"), rs.getString("nombre_roles"),
                        rs.getString("descripcion_roles"));
                vista.getViAsignarRol().getComboRoles().addItem(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- MÉTODOS DE ACCIÓN ---

    public void agregarRol() {
        String nombre = vista.getViCrearRol().getTextFieldNombre().getText();
        if (nombre.isEmpty()) {
            vista.mostrarMensajeError("El nombre del rol no puede estar vacío");
            return;
        }
        String descripcion = vista.getViCrearRol().getTextFieldDescripcion().getText();
        if (descripcion.isEmpty()) {
            vista.mostrarMensajeError("La descripción del rol no puede estar vacía");
            return;
        }

        String sql = "INSERT INTO roles (nombre_roles, descripcion_roles) VALUES (?, ?);";
        ArrayList<String> valores = new ArrayList<>();
        valores.add(nombre);
        valores.add(descripcion);
        bd.ejecutarActualizacion(sql, valores);
        // modeloFTP.crearRol(nombre);
        rellenarVentanaCrearRol();
    }

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

        // String permisoFTP = "";
        // if (permisoNombre.contains("BORRADO")) {
        // permisoFTP = "FileDelete";
        // } else if (permisoNombre.contains("ESCRI")) {
        // permisoFTP = "FileWrite";
        // } else if (permisoNombre.contains("LEE")) {
        // permisoFTP = "FileRead";
        // }

        // if (!permisoFTP.isEmpty()) {
        // modeloFTP.asignarPermiso(rolNombre, "C:\\xampp\\htdocs", permisoFTP,
        // isChecked);
        // }
    }

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
                vista.mostrarMensajeError(
                        "El usuario " + correo + " ya tiene el rol " + rol.getNombre_roles() + " " + accion);
            }
        }
    }
}
