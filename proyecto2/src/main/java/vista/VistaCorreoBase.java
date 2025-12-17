package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import modelo.Correo;

public class VistaCorreoBase extends JFrame {

    private JPanel panelPrincipal;
    private JTextField textoPara;
    private JTextField textoAsunto;
    private JTextArea textoCuerpo;
    private JButton botonEnviar;
    private JButton botonEliminar;
    private JButton botonExportar;
    private JButton botonNoLeido;
    private JButton botonAdjuntar;
    private String remitente;
    private List<File> adjuntos = new ArrayList<>();

    public VistaCorreoBase(String remitente) {
        this.setTitle("Compose new Mail");
        this.remitente = remitente;

        inicializarComponentes();
        propiedadesGenerales();

        textoPara.setText("");

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(botonEnviar);

        ensamblarVista(true, remitente, null);

        this.setVisible(true);
        // Ya no creamos el panel aquí, dejamos que ensamblarVista lo haga
        ensamblarVista(true);

    }

    // CONSTRUCTOR CONSULTAR
    public VistaCorreoBase(Correo correo) {
        this.setTitle("Check Mail");

        inicializarComponentes();
        propiedadesGenerales();

        textoPara.setText(correo.getRemitente());
        textoAsunto.setText(correo.getAsunto());
        textoCuerpo.setText(correo.getCuerpo());

        textoPara.setEditable(false);
        textoAsunto.setEditable(false);
        textoCuerpo.setEditable(false);

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(botonEliminar);

        ensamblarVista(false, null, correo.getRemitente());

        ensamblarVista(false);

    }

    // Mtodo para inicializar todos los componentes una sola vez
    private void inicializarComponentes() {
        textoPara = new JTextField(40); // Ms ancho por defecto
        textoAsunto = new JTextField(40);
        textoCuerpo = new JTextArea(15, 50);
        textoCuerpo.setLineWrap(true);
        textoCuerpo.setWrapStyleWord(true);
        botonEnviar = new JButton("Send");
        botonEliminar = new JButton("Delete");
        botonNoLeido = new JButton("Mark as unread");
        botonExportar = new JButton("Export");
        botonAdjuntar = new JButton("Attach");
    }

    private void ensamblarVista(boolean esEnvio, String remitente, String remitenteCorreo) {
        panelPrincipal = new JPanel(new BorderLayout());

        // Panel Lateral con Imagen
        JPanel sidePanel = new JPanel() {
            private java.awt.Image imagen;
            {
                java.net.URL url = getClass().getResource("/lateral_menu.jpg");
                if (url != null) {
                    imagen = new javax.swing.ImageIcon(url).getImage();
                }
            }

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    double scale = Math.max((double) getWidth() / imagen.getWidth(this),
                            (double) getHeight() / imagen.getHeight(this));
                    int w = (int) (imagen.getWidth(this) * scale);
                    int h = (int) (imagen.getHeight(this) * scale);
                    g.drawImage(imagen, 0, 0, w, h, this);
                } else {
                    g.setColor(Estilos.COLOR_BOTON_MENU);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        sidePanel.setPreferredSize(new Dimension(150, 0));
        panelPrincipal.add(sidePanel, BorderLayout.WEST);

        // Panel Central del Formulario
        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.setBackground(Estilos.BEIGE_CANVAS);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelDatosSuperiores = new JPanel(new java.awt.GridBagLayout());
        panelDatosSuperiores.setOpaque(false);
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(5, 5, 5, 5);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

        if (esEnvio) {
            agregarCampo(panelDatosSuperiores, "Para:", textoPara, gbc, 0);
        } else {
            agregarCampo(panelDatosSuperiores, "De:", textoPara, gbc, 0);
        }
        agregarCampo(panelDatosSuperiores, "Asunto:", textoAsunto, gbc, 1);

        formPanel.add(panelDatosSuperiores, BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new BorderLayout());
        panelCuerpo.setOpaque(false);
        JLabel lblCuerpo = new JLabel("Cuerpo del Mensaje:");
        lblCuerpo.setFont(Estilos.FONT_BOTON);
        lblCuerpo.setForeground(Estilos.COLOR_LABEL);
        panelCuerpo.add(lblCuerpo, BorderLayout.NORTH);
    }

    private void ensamblarVista(boolean esEnvio) {
        panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelDatosSuperiores = new JPanel(new GridLayout(0, 2, 5, 5));

        if (esEnvio) {
            panelDatosSuperiores.add(new JLabel("For:"));
        } else {
            panelDatosSuperiores.add(new JLabel("From:"));
        }
        panelDatosSuperiores.add(textoPara);

        panelDatosSuperiores.add(new JLabel("Subject:"));
        panelDatosSuperiores.add(textoAsunto);

        panelPrincipal.add(panelDatosSuperiores, BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new BorderLayout());
        panelCuerpo.add(new JLabel("Message:"), BorderLayout.NORTH);

        JScrollPane scrollCuerpo = new JScrollPane(textoCuerpo);
        scrollCuerpo.setBorder(javax.swing.BorderFactory.createLineBorder(Estilos.COLOR_TABLA_HEADER, 1));
        panelCuerpo.add(scrollCuerpo, BorderLayout.CENTER);

        formPanel.add(panelCuerpo, BorderLayout.CENTER);

        // LÓGICA DE BOTONES CENTRALIZADA
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);

        estilarBoton(botonEnviar);
        estilarBoton(botonEliminar);
        estilarBoton(botonLeido);
        // Special colors
        botonEliminar.setBackground(new java.awt.Color(200, 100, 100));

        if (esEnvio) {
            panelBoton.add(botonAdjuntar);
            panelBoton.add(botonEnviar);
        } else {
            panelBoton.add(botonLeido);
            panelBoton.add(botonEliminar);
        }
        formPanel.add(panelBoton, BorderLayout.SOUTH);

        panelPrincipal.add(formPanel, BorderLayout.CENTER);

        this.setContentPane(panelPrincipal);

        // Añadimos todos los botones que querías ver en consulta
        panelBoton.add(botonNoLeido);
        panelBoton.add(botonExportar);
        panelBoton.add(botonEliminar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        this.add(panelPrincipal);
    }

    private void agregarCampo(JPanel panel, String texto, javax.swing.JComponent campo, java.awt.GridBagConstraints gbc,
            int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.0;
        JLabel lbl = new JLabel(texto);
        lbl.setFont(Estilos.FONT_BOTON);
        lbl.setForeground(Estilos.COLOR_LABEL);
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        estilarInput(campo);
        panel.add(campo, gbc);
    }

    private void estilarInput(javax.swing.JComponent input) {
        input.setFont(Estilos.FONT_TEXTO);
        input.setBackground(Estilos.COLOR_INPUT_BG);
        input.setForeground(Estilos.COLOR_INPUT_TEXT);
        input.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(Estilos.BLUE_SLATE, 1),
                javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    private void estilarBoton(JButton btn) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(Estilos.COLOR_BOTON_MENU);
        btn.setForeground(java.awt.Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(120, 35));
    }

    public void propiedadesGenerales() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(500, 400));
    }

    public File exportarCorreo(String nombreSugerido) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export EML");
        // Filtro para archivos .eml
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Files (*.eml)", "eml"));
        fileChooser.setSelectedFile(new File(nombreSugerido + ".eml"));

        int seleccion = fileChooser.showSaveDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            // Forzar extensión .eml si el usuario no la puso
            if (!f.getName().toLowerCase().endsWith(".eml")) {
                f = new File(f.getAbsolutePath() + ".eml");
            }
            return f;
        }
        return null;
    }

    public void mostrarMensaje(String mensaje, boolean esError) {
        JOptionPane.showMessageDialog(this, mensaje,
                esError ? "Error" : "success",
                esError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }

    public File mostrarSelectorAdjuntos() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);
        int seleccion = fileChooser.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public void agregarAdjunto(File archivo) {
        this.adjuntos.add(archivo);
    }

    public List<File> getAdjuntos() {
        return adjuntos;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public JButton getBotonEliminar() {
        return botonEliminar;
    }

    public void setBotonEliminar(JButton botonEliminar) {
        this.botonEliminar = botonEliminar;
    }

    public JButton getBotonLeido() {
        return botonLeido;
    }

    public void setBotonLeido(JButton botonLeido) {
        this.botonLeido = botonLeido;
    }

    public JButton getBotonExportar() {
        return botonExportar;
    }

    public void setBotonExportar(JButton botonExportar) {
        this.botonExportar = botonExportar;
    }

    public JButton getBotonNoLeido() {
        return botonNoLeido;
    }

    public void setBotonNoLeido(JButton botonNoLeido) {
        this.botonNoLeido = botonNoLeido;
    }

    public JButton getBotonAdjuntar() {
        return botonAdjuntar;
    }

    public void setBotonAdjuntar(JButton botonAdjuntar) {
        this.botonAdjuntar = botonAdjuntar;
    }
}