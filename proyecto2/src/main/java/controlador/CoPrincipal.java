package controlador;

import modelo.*;
import vista.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import modelo.MoView;
import modelo.ModeloBaseDatos;

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

    public CoPrincipal() {
        this.bd = new ModeloBaseDatos();
        this.modeloVista = new MoView();
        this.vista = new ViMain();
        if (bd.getConexion() == null) {
            vista.mostrarMensajeError("No se pudo conectar a la base de datos");
            System.exit(1);
        }

        modelo.ModeloClienteFTP modeloFTP = new modelo.ModeloClienteFTP();
        this.vistaMenuPrincipal = new VistaMenuPrincipal(modeloFTP,vista);
        this.vistaArchivo = new VistaGestorArchivos(modeloFTP, vistaMenuPrincipal);
        this.vistaAdmin = new VistaAdmin(vistaMenuPrincipal);
        this.vistaUsuarios = new VistaRegistroUsuarios(vistaAdmin,modeloFTP);
        OyenteLogin oyLogin = new OyenteLogin(vista, modeloFTP, this, vistaArchivo, vistaMenuPrincipal, vistaAdmin, vistaUsuarios);
        vista.getPanelLogin().getBotones().get(0).addActionListener(oyLogin);
        vistaMenuPrincipal.getBotonCRUD().addActionListener(oyLogin);
        vistaMenuPrincipal.getBotonFileManager().addActionListener(oyLogin);
        vistaMenuPrincipal.getBotonAdmin().addActionListener(oyLogin);
        vistaAdmin.getBotonCrearUsuario().addActionListener(oyLogin);
        vista.hacerVisible();
        asignarEventosCRUD();
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
}
