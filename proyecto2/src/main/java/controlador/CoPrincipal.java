package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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

    public CoPrincipal() {
        this.bd = new ModeloBaseDatos();
        this.modeloVista = new MoView();
        this.vista = new ViMain();
        if (bd.getConexion() == null) {
            vista.mostrarMensajeError("No se pudo conectar a la base de datos");
            System.exit(1);
        }

        this.modeloFTP = new ModeloClienteFTP();
        this.vistaMenuPrincipal = new VistaMenuPrincipal(modeloFTP, vista);
        this.vistaArchivo = new VistaGestorArchivos(modeloFTP, vistaMenuPrincipal);
        this.vistaAdmin = new VistaAdmin(vistaMenuPrincipal);
        this.vistaUsuarios = new VistaRegistroUsuarios(vistaAdmin);
        OyenteLogin oyLogin = new OyenteLogin(modeloVista, vista, modeloFTP, this, vistaArchivo, vistaMenuPrincipal,
                vistaAdmin,
                vistaUsuarios);
        OyenteTablaRoles oyTablaRoles = new OyenteTablaRoles(this, vista, modeloVista);
        vista.getPanelLogin().getBotones().get(0).addActionListener(oyLogin);
        vistaMenuPrincipal.getBotonCRUD().addActionListener(oyLogin);
        vistaMenuPrincipal.getBotonFileManager().addActionListener(oyLogin);
        vistaMenuPrincipal.getBotonAdmin().addActionListener(oyLogin);
        vistaAdmin.getBotonCrearUsuario().addActionListener(oyLogin);
        vista.getViCrearRol().getBotones().get(0).addActionListener(oyLogin);
        vistaAdmin.getBotonCrearRoles().addActionListener(oyLogin);
        vistaAdmin.getBotonAsignarRoles().addActionListener(oyLogin);
        vista.getViAsignarRol().getBotones().get(0).addActionListener(oyLogin);
        vista.getViAsignarRol().getBotones().get(1).addActionListener(oyLogin);
        vista.getViAsignarRol().getTabla().getTabla().addMouseListener(oyTablaRoles);
        vista.getViAsignarRol().getBotones().get(2).addActionListener(oyLogin);
        vista.hacerVisible();
        asignarEventosCRUD();
    }

    public void rellenarVentanaCrearRol() {
        DefaultTableModel modeloTabla = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 1;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex > 1) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        ArrayList<String> listaNombresPermisos = new ArrayList<>();

        String sqlPermisos = "SELECT * FROM permisos ORDER BY nombre_permisos;";
        ResultSet rsPermisos = bd.getConsulta(sqlPermisos);

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

        String sqlDatos = "SELECT r.nombre_roles, r.descripcion_roles, p.nombre_permisos, " +
                "(SELECT COUNT(*) FROM roles_permisos rp WHERE rp.roles_id = r.id_roles AND rp.permisos_id = p.id_permisos) AS activo "
                +
                "FROM roles r, permisos p " +
                "ORDER BY r.nombre_roles, p.nombre_permisos;";

        ResultSet rsDatos = bd.getConsulta(sqlDatos);

        try {
            String ultimoRolProcesado = "";
            Object[] filaActual = null;

            while (rsDatos.next()) {
                String rolLeido = rsDatos.getString("nombre_roles");
                String descLeida = rsDatos.getString("descripcion_roles");
                String permisoLeido = rsDatos.getString("nombre_permisos");
                boolean esActivo = rsDatos.getInt("activo") == 1;

                if (!rolLeido.equals(ultimoRolProcesado)) {
                    if (filaActual != null) {
                        modeloTabla.addRow(filaActual);
                    }

                    filaActual = new Object[2 + listaNombresPermisos.size()];
                    filaActual[0] = rolLeido;
                    filaActual[1] = descLeida;

                    for (int i = 2; i < filaActual.length; i++) {
                        filaActual[i] = Boolean.FALSE;
                    }

                    ultimoRolProcesado = rolLeido;
                }

                int indicePermiso = listaNombresPermisos.indexOf(permisoLeido);

                if (indicePermiso != -1) {
                    filaActual[indicePermiso + 2] = esActivo;
                }
            }

            if (filaActual != null) {
                modeloTabla.addRow(filaActual);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        OyenteTabla oyentePermisos = new OyenteTabla(this, vista, modeloVista);
        oyentePermisos.setListaNombresPermisos(listaNombresPermisos);
        modeloTabla.addTableModelListener(oyentePermisos);

        vista.getViCrearRol().getPanelTabla().getTabla().setModel(modeloTabla);
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
                    modeloTabla
                            .addRow(new Object[] { usuario.getUsername(), usuario.getCorreo(), "No tiene roles" });
                } else {
                    modeloTabla
                            .addRow(new Object[] { usuario.getUsername(), usuario.getCorreo(), rs.getString("roles") });
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

    public void agregarRol() {
        String nombre = vista.getViCrearRol().getTextFieldNombre().getText();
        String descripcion = vista.getViCrearRol().getTextFieldDescripcion().getText();
        String sql = "INSERT INTO roles (nombre_roles, descripcion_roles) VALUES (?, ?);";
        ArrayList<String> valores = new ArrayList<>();
        valores.add(nombre);
        valores.add(descripcion);
        bd.ejecutarActualizacion(sql, valores);
        modeloFTP.crearRol(nombre);
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
        System.out.println("Permiso " + (isChecked ? "añadido" : "eliminado") + " -> Rol: " + rolNombre + ", Permiso: "
                + permisoNombre);

        String permisoFTP = "";
        if (permisoNombre.contains("BORRADO")) {
            permisoFTP = "FileDelete";
        } else if (permisoNombre.contains("ESCRI")) {
            permisoFTP = "FileWrite";
        } else if (permisoNombre.contains("LEE")) {
            permisoFTP = "FileRead";
        }

        if (!permisoFTP.isEmpty()) {
            modeloFTP.asignarPermiso(rolNombre, "C:\\xampp\\htdocs", permisoFTP, isChecked);
        }
    }

    private void asignarEventosCRUD() {
        OyenteBot oyB = new OyenteBot(this, vista, modeloVista, vistaMenuPrincipal);
        OyenteTabla oyT = new OyenteTabla(this, vista, modeloVista);

        for (JButton btn : vista.getPanelMenu().getBotones()) {
            btn.addActionListener(oyB);
        }

        for (JButton btn : vista.getPanelAcciones().getBotones()) {
            btn.addActionListener(oyB);
        }

        vista.getPanelTabla().getTabla().addMouseListener(oyT);
    }

    public void rellenarTabla(String tabla) {
        if (tabla.isEmpty()) {
            vista.getPanelTabla().setModelo(new DefaultTableModel());
            return;
        }
        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ResultSet rs = bd.getTabla(tabla);

        try {
            if (rs != null) {
                int totalColumnas = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= totalColumnas; i++) {
                    modelo.addColumn(rs.getMetaData().getColumnName(i));
                }

                while (rs.next()) {
                    String[] row = new String[totalColumnas];
                    for (int i = 1; i <= totalColumnas; i++) {
                        Object obj = rs.getObject(i);
                        row[i - 1] = (obj != null) ? obj.toString() : "";
                    }
                    modelo.addRow(row);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        vista.getPanelTabla().setModelo(modelo);
    }

    public void mostrarFormularioNuevo() {
        editando = false;
        String tabla = modeloVista.getTablaActual();
        ArrayList<String> columnas = bd.getNombresColumnas(tabla);

        if (!columnas.isEmpty()) {
            columnas.remove(0);
        }

        vista.getVentanaFormulario().crearFormulario(columnas);

        if (tabla.equals("animales")) {
            vista.getVentanaFormulario().agregarComboEspecies("especie_id", bd.getEspecies());
            vista.getVentanaFormulario().agregarComboCuidadores("cuidador_id", bd.getCuidadores());
            vista.getVentanaFormulario().agregarComboTipos("tipo", Arrays.asList("Male", "Female"));
        }

        OyenteFormulario oyF = new OyenteFormulario(this, vista);
        vista.getVentanaFormulario().getBotones().get(0).addActionListener(oyF);
        vista.getVentanaFormulario().getBotones().get(1).addActionListener(oyF);

        vista.getVentanaFormulario().hacerVisible();
    }

    public void mostrarFormularioActualizar() {
        editando = true;
        int fila = vista.getPanelTabla().getTabla().getSelectedRow();
        if (fila == -1)
            return;

        String tabla = modeloVista.getTablaActual();
        ArrayList<String> columnas = bd.getNombresColumnas(tabla);

        if (!columnas.isEmpty()) {
            columnas.remove(0);
        }

        vista.getVentanaFormulario().crearFormulario(columnas);

        if (tabla.equals("animales")) {
            vista.getVentanaFormulario().agregarComboEspecies("especie_id", bd.getEspecies());
            vista.getVentanaFormulario().agregarComboCuidadores("cuidador_id", bd.getCuidadores());
            vista.getVentanaFormulario().agregarComboTipos("tipo", Arrays.asList("Male", "Female"));
        }

        int totalColumnas = vista.getPanelTabla().getTabla().getColumnCount();
        String[] valores = new String[totalColumnas - 1];
        for (int i = 1; i < totalColumnas; i++) {
            Object val = vista.getPanelTabla().getTabla().getValueAt(fila, i);
            valores[i - 1] = val != null ? val.toString() : "";
        }

        vista.getVentanaFormulario().rellenarDatos(valores);

        OyenteFormulario oyF = new OyenteFormulario(this, vista);
        vista.getVentanaFormulario().getBotones().get(0).addActionListener(oyF);
        vista.getVentanaFormulario().getBotones().get(1).addActionListener(oyF);

        vista.getVentanaFormulario().hacerVisible();
    }

    public void guardarNuevo() {
        String tabla = modeloVista.getTablaActual();
        ArrayList<String> columnas = bd.getNombresColumnas(tabla);
        columnas.remove(0);

        String[] valores = vista.getVentanaFormulario().obtenerValores();

        String consulta = "INSERT INTO " + tabla + " (";
        String clausulaValores = "VALUES (";

        for (int i = 0; i < columnas.size(); i++) {
            consulta += columnas.get(i);
            clausulaValores += "?";
            if (i < columnas.size() - 1) {
                consulta += ", ";
                clausulaValores += ", ";
            }
        }
        consulta += ") " + clausulaValores + ")";

        ArrayList<String> parametros = new ArrayList<>(Arrays.asList(valores));

        if (bd.ejecutarActualizacion(consulta, parametros) > 0) {
            vista.mostrarMensajeExito("Guardado correctamente");
            vista.getVentanaFormulario().setVisible(false);
            rellenarTabla(tabla);
        } else {
            vista.mostrarMensajeError("Error al guardar");
        }
    }

    public void guardarActualizar() {
        int fila = vista.getPanelTabla().getTabla().getSelectedRow();
        String tabla = modeloVista.getTablaActual();
        Object id = vista.getPanelTabla().getTabla().getValueAt(fila, 0);
        String idCol = vista.getPanelTabla().getTabla().getColumnName(0);

        ArrayList<String> columnas = bd.getNombresColumnas(tabla);
        columnas.remove(0);

        String[] valores = vista.getVentanaFormulario().obtenerValores();

        String consulta = "UPDATE " + tabla + " SET ";
        for (int i = 0; i < columnas.size(); i++) {
            consulta += columnas.get(i) + " = ?";
            if (i < columnas.size() - 1)
                consulta += ", ";
        }
        consulta += " WHERE " + idCol + " = ?";

        ArrayList<String> parametros = new ArrayList<>(Arrays.asList(valores));
        parametros.add(id.toString());

        if (bd.ejecutarActualizacion(consulta, parametros) > 0) {
            vista.mostrarMensajeExito("Actualizado correctamente");
            vista.getVentanaFormulario().setVisible(false);
            rellenarTabla(tabla);
        } else {
            vista.mostrarMensajeError("Error al actualizar");
        }
    }

    public void eliminarRegistro() {
        int fila = vista.getPanelTabla().getTabla().getSelectedRow();
        if (fila == -1)
            return;

        if (vista.mostrarConfirmacion("¿Estás seguro de eliminar este registro?")) {
            String tabla = modeloVista.getTablaActual();
            Object id = vista.getPanelTabla().getTabla().getValueAt(fila, 0);
            String idCol = vista.getPanelTabla().getTabla().getColumnName(0);

            String consulta = "DELETE FROM " + tabla + " WHERE " + idCol + " = ?";

            ArrayList<String> parametros = new ArrayList<>();
            parametros.add(id.toString());

            if (bd.ejecutarActualizacion(consulta, parametros) > 0) {
                vista.mostrarMensajeExito("Eliminado correctamente");
                rellenarTabla(tabla);
            } else {
                vista.mostrarMensajeError("Error al eliminar");
            }
        }
    }

    public ViMain getVista() {
        return vista;
    }

    public MoView getModeloVista() {
        return modeloVista;
    }

    public boolean isEditando() {
        return editando;
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
