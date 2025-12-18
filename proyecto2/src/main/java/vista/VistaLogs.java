package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import modelo.Log;
import modelo.MoTextos;

public class VistaLogs extends JFrame {

    private ViTabla tabla;
    private JButton btnExport;
    private JButton btnVolver;
    private ArrayList<JButton> botonesConsultas;
    private Image imagenFondo;
    private JLabel titulo;
    private VistaAdmin vistaAdmin;

    public VistaLogs(VistaAdmin vistaAdmin) {
        this.vistaAdmin = vistaAdmin;
        this.botonesConsultas = new ArrayList<>();
        propiedades();
    }

    private void propiedades() {
        configurarVentana();
        configurarFondo();
        configurarTitulo();
        configurarTabla();
        configurarBotones();
        inicializarModeloTabla();
    }

    private void configurarVentana() {
        this.setTitle(MoTextos.logs_title);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(900, 600);
        this.setLocationRelativeTo(null);
    }

    private void configurarFondo() {
        URL url = getClass().getResource("/fondo_abstracto_1.png");
        if (url != null) {
            imagenFondo = new ImageIcon(url).getImage();
        }

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Estilos.FONDO_PRINCIPAL);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(new BorderLayout(20, 20));
        panelFondo.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panelFondo);
    }

    private void configurarTitulo() {

        titulo = new JLabel(MoTextos.logs_title);
        titulo.setFont(Estilos.FONT_TITULO);
        titulo.setForeground(Estilos.DARK_SPRUCE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(new EmptyBorder(10, 20, 10, 20));
        panelTitulo.add(titulo);
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
    }

    private void configurarTabla() {
        tabla = new ViTabla();

        JPanel panelTablaContenedor = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 180));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };
        panelTablaContenedor.setOpaque(false);
        panelTablaContenedor.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelTablaContenedor.add(tabla, BorderLayout.CENTER);

        getContentPane().add(panelTablaContenedor, BorderLayout.CENTER);
    }

    private void configurarBotones() {

        JPanel panelBotonesConsultas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonesConsultas.setOpaque(false);
        botonesConsultas.add(new JButton(MoTextos.logs_col_action));
        botonesConsultas.add(new JButton(MoTextos.logs_col_user));
        botonesConsultas.add(new JButton(MoTextos.logs_col_date));
        botonesConsultas.add(new JButton(MoTextos.logs_col_result));

        for (JButton boton : botonesConsultas) {
            aniadirEstiloBoton(boton, Estilos.COLOR_BOTON_MENU);
            panelBotonesConsultas.add(boton);
        }
        getContentPane().add(panelBotonesConsultas, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.setOpaque(false);

        btnVolver = new JButton(MoTextos.del_btn_back);
        btnExport = new JButton(MoTextos.logs_btn_export);

        aniadirEstiloBoton(btnVolver, new Color(200, 100, 100));
        aniadirEstiloBoton(btnExport, Estilos.COLOR_BOTON_MENU);

        panelBotones.add(btnVolver);
        panelBotones.add(btnExport);
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }

    public void setTituloTexto(String t) {
        titulo.setText(t);
    }

    private void inicializarModeloTabla() {
        String[] nombresColumnas = { MoTextos.logs_col_action, MoTextos.logs_col_user, MoTextos.logs_col_date,
                MoTextos.logs_col_result };
        DefaultTableModel tablaModelo = new DefaultTableModel(nombresColumnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla.setModelo(tablaModelo);
    }

    private void aniadirEstiloBoton(JButton btn, Color bgColor) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 40));
    }

    public void cargarLogs(ArrayList<Log> logs) {
        DefaultTableModel model = (DefaultTableModel) tabla.getTabla().getModel();
        model.setRowCount(0);
        for (Log log : logs) {
            Object[] fila = new Object[4];
            fila[0] = log.getAction();
            fila[1] = log.getCorreo();
            fila[2] = log.getDate();
            fila[3] = log.getResult();
            model.addRow(fila);
        }
    }

    public File seleccionarArchivoGuardar() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(MoTextos.logs_dialog_save_title);
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

    public void actualizarTextos() {
        this.setTitle(MoTextos.logs_title);
        titulo.setText(MoTextos.logs_title);
        btnVolver.setText(MoTextos.del_btn_back);
        btnExport.setText(MoTextos.logs_btn_export);

        DefaultTableModel model = (DefaultTableModel) tabla.getTabla().getModel();
        String[] header = { MoTextos.logs_col_action, MoTextos.logs_col_user, MoTextos.logs_col_date,
                MoTextos.logs_col_result };
        model.setColumnIdentifiers(header);
    }

    public JButton getBtnExport() {
        return btnExport;
    }

    public ArrayList<JButton> getBotonesConsultas() {
        return botonesConsultas;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public VistaAdmin getVistaAdmin() {
        return vistaAdmin;
    }

    public void hacerVisible() {
        setVisible(true);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
