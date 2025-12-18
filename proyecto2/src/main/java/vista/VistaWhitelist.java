package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VistaWhitelist extends JFrame {
    private ArrayList<JButton> botones;
    private ViTabla tabla;
    private JTextField txtEmail;
    private JTextField txtNombre;
    private Image imagenFondo;
    private JButton btnAnadir; // Exposed for Controller/Main
    private JButton btnDesasignar;
    private JButton btnVolver;

    public VistaWhitelist() {
        this.botones = new ArrayList<>();
        this.tabla = new ViTabla();

        this.setTitle("Gestión de Whitelist");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        URL url = getClass().getResource("/fondo_abstracto_2.png");
        if (url != null) {
            imagenFondo = new ImageIcon(url).getImage();
        }

        JPanel panelFondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    int width = getWidth();
                    int height = getHeight();
                    g.drawImage(imagenFondo, 0, 0, width, height, this);
                } else {
                    g.setColor(Estilos.FONDO_PRINCIPAL);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(new BorderLayout(20, 20));
        panelFondo.setBorder(new EmptyBorder(20, 20, 20, 20));
        this.setContentPane(panelFondo);

        JLabel titulo = new JLabel("Gestión de Whitelist");
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
        panelFondo.add(panelTitulo, BorderLayout.NORTH);

        // Container for table with semi-transparent background
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

        this.tabla.setOpaque(false);
        panelTablaContenedor.add(this.tabla, BorderLayout.CENTER);
        panelFondo.add(panelTablaContenedor, BorderLayout.CENTER);

        // Bottom panel for controls
        JPanel panelSur = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelSur.setOpaque(false);

        // Fields
        // Fields - Removed from main view, used in Dialog
        // txtEmail & txtNombre will be initialized in mostrarAgregarUsuario

        // Buttons
        btnAnadir = new JButton("Añadir");
        btnDesasignar = new JButton("Desasignar");
        btnVolver = new JButton("Volver");

        estilarBoton(btnAnadir, Estilos.COLOR_BOTON_MENU);
        estilarBoton(btnDesasignar, Estilos.BLUE_SLATE);
        estilarBoton(btnVolver, new Color(200, 100, 100));

        this.botones.add(btnAnadir);
        this.botones.add(btnDesasignar);
        this.botones.add(btnVolver);

        panelSur.add(btnAnadir);
        panelSur.add(btnDesasignar);
        panelSur.add(btnVolver);

        panelFondo.add(panelSur, BorderLayout.SOUTH);
    }

    private void estilarBoton(JButton btn, Color bgColor) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 40));
    }

    private void estilarInput(javax.swing.JComponent input) {
        input.setFont(Estilos.FONT_TEXTO);
        input.setBackground(Estilos.COLOR_INPUT_BG);
        input.setForeground(Estilos.COLOR_INPUT_TEXT);
        input.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(Estilos.BLUE_SLATE, 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        input.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    }

    private javax.swing.JButton crearBotonDialogo(String texto, Color color) {
        javax.swing.JButton btn = new javax.swing.JButton(texto);
        btn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 40));
        return btn;
    }

    public int mostrarAgregarUsuario() {
        final javax.swing.JDialog dialog = new javax.swing.JDialog(
                this,
                "Agregar Usuario",
                true);

        dialog.setUndecorated(true);
        dialog.setLayout(new java.awt.BorderLayout());

        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBackground(Estilos.BEIGE_CANVAS);
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(Estilos.DARK_SPRUCE, 2));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("Agregar a Whitelist");
        lblTitulo.setFont(Estilos.FONT_TITULO);
        lblTitulo.setForeground(Estilos.COLOR_TITULO_APP);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(Estilos.FONT_BOTON);
        lblEmail.setForeground(Estilos.COLOR_LABEL);

        txtEmail = new JTextField();
        estilarInput(txtEmail);

        panel.add(lblEmail);
        panel.add(txtEmail);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(Estilos.FONT_BOTON);
        lblNombre.setForeground(Estilos.COLOR_LABEL);

        txtNombre = new JTextField();
        estilarInput(txtNombre);

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        final int[] result = { -1 };

        JButton btnNew = crearBotonDialogo("Agregar", Estilos.COLOR_BOTON_MENU);
        JButton btnCancel = crearBotonDialogo("Cancelar", new java.awt.Color(200, 100, 100));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        btnNew.addActionListener(e -> {
            result[0] = 0;
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> {
            result[0] = 1;
            dialog.dispose();
        });

        buttonPanel.add(btnNew);
        buttonPanel.add(btnCancel);
        panel.add(buttonPanel);

        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        return result[0];
    }

    public ViTabla getTabla() {
        return this.tabla;
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public void setTabla(ViTabla tabla) {
        this.tabla = tabla;
    }

    public JTextField getTxtEmail() {
        return txtEmail;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JButton getBtnAnadir() {
        return btnAnadir;
    }

    public JButton getBtnDesasignar() {
        return btnDesasignar;
    }

    public JButton getBtnVolver() {
        return btnVolver;
    }

    public void hacerVisible() {
        this.setVisible(true);
    }

}
