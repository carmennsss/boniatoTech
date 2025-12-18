package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import modelo.MoTextos;

public class ViCrearRol extends JFrame {
    private ArrayList<JLabel> textos;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private ArrayList<JButton> botones;
    private ViTabla panelTabla;

    public ViCrearRol() {
        this.setTitle(MoTextos.roles_title_create);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Panel with background image
        JPanel panelFondo = new JPanel() {
            private java.awt.Image imagen;
            {
                // java.net.URL url = getClass().getResource("/fondo_verde.png");
                java.net.URL url = getClass().getResource("/fondo_zoo_1.png");
                if (url != null) {
                    imagen = new javax.swing.ImageIcon(url).getImage();
                }
            }

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    int width = getWidth();
                    int height = getHeight();
                    g.drawImage(imagen, 0, 0, width, height, this);
                } else {
                    g.setColor(Estilos.FONDO_PRINCIPAL);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        panelFondo.setLayout(new BorderLayout(20, 20));
        this.setContentPane(panelFondo);

        panelTabla = new ViTabla();
        panelTabla.setOpaque(false); // Make transparent if supported

        this.textos = new ArrayList<>();
        this.botones = new ArrayList<>();

        JLabel titulo = new JLabel(MoTextos.roles_title_available, SwingConstants.CENTER);
        titulo.setFont(Estilos.FONT_TITULO);
        titulo.setForeground(Estilos.COLOR_TITULO_APP);
        this.textos.add(titulo);

        this.botones.add(new JButton(MoTextos.roles_btn_create));
        this.botones.add(new JButton(MoTextos.btn_back_whitelist)); // Reusing "Volver"

        JPanel panelCentro = new JPanel(new BorderLayout(20, 20));
        panelCentro.setOpaque(false);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 40, 0, 40));

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
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panelTitulo.add(titulo);

        panelCentro.add(panelTitulo, BorderLayout.NORTH);
        panelCentro.add(panelTabla, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setOpaque(false);

        estilarBoton(this.botones.get(0));
        estilarBoton(this.botones.get(1));
        // Special color for back button
        this.botones.get(1).setBackground(new java.awt.Color(200, 100, 100));

        panelBotones.add(this.botones.get(0));
        panelBotones.add(this.botones.get(1));

        panelFondo.add(panelCentro, BorderLayout.CENTER);
        panelFondo.add(panelBotones, BorderLayout.SOUTH);
    }

    private void estilarBoton(JButton btn) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(Estilos.COLOR_BOTON_MENU);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(150, 40));
    }

    public int mostrarAgregarRol() {
        final JDialog dialog = new javax.swing.JDialog(
                this,
                MoTextos.roles_dialog_title,
                true);

        dialog.setUndecorated(true);
        dialog.setLayout(new java.awt.BorderLayout());

        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBackground(Estilos.BEIGE_CANVAS); // Use defined color
        panel.setBorder(javax.swing.BorderFactory.createLineBorder(Estilos.DARK_SPRUCE, 2));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        panel.setBorder(new javax.swing.border.EmptyBorder(20, 20, 20, 20));

        javax.swing.JLabel lblTitulo = new javax.swing.JLabel(MoTextos.roles_dialog_title);
        lblTitulo.setFont(Estilos.FONT_TITULO);
        lblTitulo.setForeground(Estilos.COLOR_TITULO_APP);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblTitulo);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        JLabel lblNombre = new JLabel(MoTextos.roles_lbl_name);
        lblNombre.setFont(Estilos.FONT_BOTON);
        lblNombre.setForeground(Estilos.COLOR_LABEL);

        txtNombre = new JTextField();
        estilarInput(txtNombre);

        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        JLabel lblDescripcion = new JLabel(MoTextos.roles_lbl_desc);
        lblDescripcion.setFont(Estilos.FONT_BOTON);
        lblDescripcion.setForeground(Estilos.COLOR_LABEL);

        txtDescripcion = new JTextField();
        estilarInput(txtDescripcion);

        panel.add(lblDescripcion);
        panel.add(txtDescripcion);
        panel.add(javax.swing.Box.createVerticalStrut(20));

        final int[] result = { -1 };

        JButton btnNew = crearBotonDialogo(MoTextos.btn_dialog_add, Estilos.COLOR_BOTON_MENU);
        JButton btnCancel = crearBotonDialogo(MoTextos.btn_dialog_cancel, new java.awt.Color(200, 100, 100));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result[0] = 0;
                dialog.dispose();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                result[0] = 1;
                dialog.dispose();
            }
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

    public void mostrar() {
        setVisible(true);
    }

    public void hacerVisible() {
        setVisible(true);
    }

    public ArrayList<JButton> getBotones() {
        return botones;
    }

    public JTextField getTextFieldNombre() {
        return txtNombre;
    }

    public JTextField getTextFieldDescripcion() {
        return txtDescripcion;
    }

    public JButton getBoton(int i) {
        return botones.get(i);
    }

    public ViTabla getPanelTabla() {
        return panelTabla;
    }

    public void actualizarTextos() {
        this.setTitle(MoTextos.roles_title_create);
        if (!textos.isEmpty()) {
            textos.get(0).setText(MoTextos.roles_title_available);
        }
        if (botones.size() >= 2) {
            botones.get(0).setText(MoTextos.roles_btn_create);
            botones.get(1).setText(MoTextos.btn_back_whitelist);
        }
        repaint();
    }
}
