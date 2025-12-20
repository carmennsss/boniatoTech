package controladorCRUD;

import controladorPrincipal.CoPrincipal;

import modelo.MoView;
import modelo.MoTextos;
import vista.VistaCRUD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.TableModelListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Oyente para manejar eventos en la tabla CRUD general y la tabla de roles.
 * Maneja doble clic para menú de opciones y cambios en celdas (checkboxes de
 * permisos).
 */
public class OyenteTablaCRUD extends MouseAdapter implements TableModelListener {
    /** Controlador principal de la aplicación. */
    private CoPrincipal controlador;

    /** Vista CRUD principal. */
    private VistaCRUD vistaCRUD;

    /** Modelo de vista para gestión de datos. */
    private MoView modeloVista;

    /** Lista de nombres de permisos para la tabla de roles. */
    private java.util.ArrayList<String> listaNombresPermisos;

    /**
     * Constructor del oyente de tabla CRUD.
     *
     * @param controlador Controlador principal.
     * @param vistaCRUD   Vista CRUD.
     * @param modeloVista Modelo de vista.
     */
    public OyenteTablaCRUD(CoPrincipal controlador, VistaCRUD vistaCRUD, MoView modeloVista) {
        this.controlador = controlador;
        this.vistaCRUD = vistaCRUD;
        this.modeloVista = modeloVista;
    }

    /**
     * Maneja el doble clic para mostrar las opciones (Nuevo, Actualizar, Borrar).
     *
     * @param e El evento de ratón.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            int fila = vistaCRUD.getPanelTabla().getTabla().getSelectedRow();
            if (fila != -1) {
                modeloVista.setFilaSeleccionada(fila);
                mostrarOpciones();
            }
        }
    }

    /**
     * Detecta cambios en la tabla, específicamente para la edición de permisos de
     * roles.
     *
     * @param eventoModelo El evento de cambio en el modelo de la tabla.
     */
    @Override
    public void tableChanged(javax.swing.event.TableModelEvent eventoModelo) {
        if (eventoModelo.getType() == javax.swing.event.TableModelEvent.UPDATE) {
            int filaModificada = eventoModelo.getFirstRow();
            int columnaModificada = eventoModelo.getColumn();

            if (filaModificada >= 0 && columnaModificada >= 3 && listaNombresPermisos != null) {
                javax.swing.table.TableModel modeloTabla = (javax.swing.table.TableModel) eventoModelo.getSource();

                Object idObj = modeloTabla.getValueAt(filaModificada, 0);
                int idRol = -1;
                if (idObj instanceof Integer) {
                    idRol = (Integer) idObj;
                }

                if (idRol == 3) {
                    return;
                }

                String nombreRol = (String) modeloTabla.getValueAt(filaModificada, 1);
                String nombrePermiso = listaNombresPermisos.get(columnaModificada - 3);
                Boolean nuevoValor = (Boolean) modeloTabla.getValueAt(filaModificada, columnaModificada);

                controlador.getControladorRoles().actualizarPermiso(nombreRol, nombrePermiso, nuevoValor);
            }
        }
    }

    /**
     * Crea un botón para el diálogo de opciones.
     *
     * @param texto Texto del botón.
     * @param color Color de fondo del botón.
     * @return Botón configurado.
     */
    private JButton crearBotonDialogo(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Muestra un diálogo personalizado para seleccionar una acción sobre una fila.
     * Ofrece opciones para Crear Nuevo, Actualizar, Borrar o Cancelar.
     *
     * @return 0 para Nuevo, 1 para Actualizar, 2 para Borrar, 3 para Cancelar.
     */
    private int mostrarDialogoOpciones() {
        final JDialog dialog = new JDialog(
                (JFrame) SwingUtilities.getWindowAncestor(vistaCRUD),
                MoTextos.dialog_select_action,
                true);

        dialog.setUndecorated(true);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setBackground(new Color(248, 245, 242));
        panel.setBorder(BorderFactory.createLineBorder(new Color(74, 88, 89), 2));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel(MoTextos.dialog_select_action);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(74, 88, 89));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(Box.createVerticalStrut(20));

        final int[] result = { -1 };

        Color colorBtn = new Color(110, 137, 115);
        Color colorCancel = new Color(200, 100, 100);

        JButton btnNew = crearBotonDialogo(MoTextos.btn_create_new, colorBtn);
        JButton btnUpdate = crearBotonDialogo(MoTextos.btn_sys_update, colorBtn);
        JButton btnDelete = crearBotonDialogo(MoTextos.btn_sys_delete, colorCancel);
        JButton btnCancel = crearBotonDialogo(MoTextos.btn_sys_cancel, Color.GRAY);

        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result[0] = 0;
                dialog.dispose();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result[0] = 1;
                dialog.dispose();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result[0] = 2;
                dialog.dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result[0] = 3;
                dialog.dispose();
            }
        });

        panel.add(btnNew);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnUpdate);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnDelete);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnCancel);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(vistaCRUD);
        dialog.setVisible(true);

        return result[0];
    }

    /**
     * Muestra el diálogo de opciones y ejecuta la acción seleccionada.
     */
    private void mostrarOpciones() {
        int eleccion = mostrarDialogoOpciones();

        if (eleccion == 0) {
            controlador.getControladorCRUD().mostrarFormularioNuevo();
        } else if (eleccion == 1) {
            controlador.getControladorCRUD().mostrarFormularioActualizar();
        } else if (eleccion == 2) {
            controlador.getControladorCRUD().eliminarRegistro();
        }
    }

    // --- GETTERS Y SETTERS ---

    /**
     * Establece la lista de nombres de permisos para la tabla de roles.
     * * @param listaNombresPermisos Lista de nombres de permisos.
     */
    public void setListaNombresPermisos(java.util.ArrayList<String> listaNombresPermisos) {
        this.listaNombresPermisos = listaNombresPermisos;
    }

    /**
     * Obtiene el controlador principal.
     * @return El objeto CoPrincipal.
     */
    public CoPrincipal getControlador() {
        return controlador;
    }

    /**
     * Obtiene la vista CRUD.
     * @return El objeto VistaCRUD.
     */
    public VistaCRUD getVistaCRUD() {
        return vistaCRUD;
    }

    /**
     * Obtiene el modelo de la vista.
     * @return El objeto MoView.
     */
    public MoView getModeloVista() {
        return modeloVista;
    }
}