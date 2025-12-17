package controlador;

import modelo.Animal;
import modelo.ModeloBaseDatos;
import modelo.MoView;
import modelo.Recinto;
import modelo.Especie;
import modelo.Cuidador;
import vista.ViMain;
import vista.Estilos; // Import needed for OyenteBot refactoring if needed, but logic remains here

import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ControladorCRUD {
    private ModeloBaseDatos bd;
    private ViMain vista;
    private MoView modeloVista;
    private CoPrincipal coPrincipal; // Reference in case we need to call back

    public ControladorCRUD(CoPrincipal coPrincipal, ModeloBaseDatos bd, ViMain vista, MoView modeloVista) {
        this.coPrincipal = coPrincipal;
        this.bd = bd;
        this.vista = vista;
        this.modeloVista = modeloVista;
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
        coPrincipal.setEditando(false);
        String tabla = modeloVista.getTablaActual();
        ArrayList<String> columnas = bd.getNombresColumnas(tabla);

        if (!columnas.isEmpty() && !tabla.equals("especies_recintos")) {
            columnas.remove(0);
        }

        vista.getVentanaFormulario().crearFormulario(columnas);

        configurarCombos(tabla);

        OyenteFormulario oyF = new OyenteFormulario(coPrincipal, vista);
        vista.getVentanaFormulario().getBotones().get(0).addActionListener(oyF);
        vista.getVentanaFormulario().getBotones().get(1).addActionListener(oyF);

        vista.getVentanaFormulario().hacerVisible();
    }

    public void mostrarFormularioActualizar() {
        coPrincipal.setEditando(true);
        int fila = vista.getPanelTabla().getTabla().getSelectedRow();
        if (fila == -1)
            return;

        String tabla = modeloVista.getTablaActual();
        ArrayList<String> columnas = bd.getNombresColumnas(tabla);

        if (!columnas.isEmpty() && !tabla.equals("especies_recintos")) {
            columnas.remove(0);
        }

        vista.getVentanaFormulario().crearFormulario(columnas);

        configurarCombos(tabla);

        int totalColumnas = vista.getPanelTabla().getTabla().getColumnCount();
        ArrayList<String> valoresList = new ArrayList<>();
        int inicio = 1;

        if (tabla.equals("especies_recintos")) {
            inicio = 0;
        }

        for (int i = inicio; i < totalColumnas; i++) {
            Object val = vista.getPanelTabla().getTabla().getValueAt(fila, i);
            valoresList.add(val != null ? val.toString() : "");
        }

        String[] valores = valoresList.toArray(new String[0]);

        vista.getVentanaFormulario().rellenarDatos(valores);

        OyenteFormulario oyF = new OyenteFormulario(coPrincipal, vista);
        vista.getVentanaFormulario().getBotones().get(0).addActionListener(oyF);
        vista.getVentanaFormulario().getBotones().get(1).addActionListener(oyF);

        vista.getVentanaFormulario().hacerVisible();
    }

    private void configurarCombos(String tabla) {
        if (tabla.equals("animales")) {
            vista.getVentanaFormulario().agregarComboEspecies("especie_id", bd.getEspecies());
            vista.getVentanaFormulario().agregarComboCuidadores("cuidador_id", bd.getCuidadores());
            vista.getVentanaFormulario().agregarComboTipos("tipo", Arrays.asList("Male", "Female"));
        } else if (tabla.equals("especies_recintos")) {
            vista.getVentanaFormulario().agregarComboEspecies("especie_id", bd.getEspecies());
            vista.getVentanaFormulario().agregarComboRecintos("recinto_id", bd.getRecintos());
        } else if (tabla.equals("elementos")) {
            vista.getVentanaFormulario().agregarComboRecintos("recinto_id", bd.getRecintos());
        } else if (tabla.equals("traslados")) {
            vista.getVentanaFormulario().agregarComboAnimales("animal_id", bd.getAnimales());
            vista.getVentanaFormulario().agregarComboRecintos("recinto_origen_id", bd.getRecintos());
            vista.getVentanaFormulario().agregarComboRecintos("recinto_destino_id", bd.getRecintos());
        }
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
        if (!tabla.equals("especies_recintos")) {
            columnas.remove(0);
        }

        String[] valores = vista.getVentanaFormulario().obtenerValores();

        String consulta = "UPDATE " + tabla + " SET ";
        for (int i = 0; i < columnas.size(); i++) {
            consulta += columnas.get(i) + " = ?";
            if (i < columnas.size() - 1)
                consulta += ", ";
        }

        if (tabla.equals("especies_recintos")) {
            String idCol2 = vista.getPanelTabla().getTabla().getColumnName(1);
            consulta += " WHERE " + idCol + " = ? AND " + idCol2 + " = ?";
        } else {
            consulta += " WHERE " + idCol + " = ?";
        }

        ArrayList<String> parametros = new ArrayList<>(Arrays.asList(valores));
        parametros.add(id.toString());

        if (tabla.equals("especies_recintos")) {
            Object id2 = vista.getPanelTabla().getTabla().getValueAt(fila, 1);
            parametros.add(id2.toString());
        }

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

            if (tabla.equals("especies_recintos")) {
                String idCol2 = vista.getPanelTabla().getTabla().getColumnName(1);
                consulta += " AND " + idCol2 + " = ?";
            }

            ArrayList<String> parametros = new ArrayList<>();
            parametros.add(id.toString());

            if (tabla.equals("especies_recintos")) {
                Object id2 = vista.getPanelTabla().getTabla().getValueAt(fila, 1);
                parametros.add(id2.toString());
            }

            if (bd.ejecutarActualizacion(consulta, parametros) > 0) {
                vista.mostrarMensajeExito("Eliminado correctamente");
                rellenarTabla(tabla);
            } else {
                vista.mostrarMensajeError("Error al eliminar");
            }
        }
    }
}
