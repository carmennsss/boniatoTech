package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelo.Log;

public class VistaLogs extends JFrame {

    private JPanel panelPrincipal;
    private JTable tabla;
    private DefaultTableModel tablaModelo;
    private JButton btnExport;
    private JButton btnVolver;

    public VistaLogs() {
        propiedadesVentana();
        inicializarComponentes();
    }

    private void propiedadesVentana() {
        this.setTitle("System Logs");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(700, 500);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(630, 400));
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());
        
        String[] nombresColumnas = { "Action", "User", "Date", "Result" };
        tablaModelo = new DefaultTableModel(nombresColumnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        tabla = new JTable(tablaModelo);
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(tabla);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVolver = new JButton("Back");
        btnExport = new JButton("Export CSV");
        
        btnVolver.setPreferredSize(new Dimension(100, 30));
        btnExport.setPreferredSize(new Dimension(120, 30));

        panelBotones.add(btnVolver);
        panelBotones.add(btnExport);

        // Añadimos el panel de botones a la parte inferior (SOUTH)
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        this.add(panelPrincipal);
    }

    public void cargarLogs(ArrayList<Log> logs) {
        tablaModelo.setRowCount(0);
        for (Log log : logs) {
            Object[] fila = new Object[4];
            fila[0] = log.getAction();
            fila[1] = log.getCorreo();
            fila[2] = log.getDate();
            fila[3] = log.getResult();
            tablaModelo.addRow(fila);
        }
    }

    public File seleccionarArchivoGuardar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save logs");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            if (!fileToSave.getAbsolutePath().toLowerCase().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }
            return fileToSave;
        }
        return null;
    }

    // Getters para el controlador
    public JButton getBtnExport() { return btnExport; }
    public JButton getBtnVolver() { return btnVolver; }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}