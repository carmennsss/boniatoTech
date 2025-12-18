package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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

        ensamblarVista(false);

    }

    // M�todo para inicializar todos los componentes una sola vez
    private void inicializarComponentes() {
        textoPara = new JTextField(40); // M�s ancho por defecto
        textoAsunto = new JTextField(40);
        textoCuerpo = new JTextArea(15, 50);
        textoCuerpo.setLineWrap(true);
        textoCuerpo.setWrapStyleWord(true);
        botonEnviar = new JButton("Send");
        botonEliminar = new JButton("Delete");
        botonNoLeido = new JButton("Mark unread");
        botonExportar = new JButton("Export");
        botonAdjuntar = new JButton("Attach");
    }

    private void ensamblarVista(boolean esEnvio) {
        // Main Container
        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Estilos.FONDO_PRINCIPAL);

        // --- SIDEBAR (Decorative) ---
        JPanel sidebar = new JPanel() {
            private java.awt.Image bgImage;

            {
                try {
                    java.net.URL url = getClass().getResource("/fondo_verde.png");
                    if (url != null) {
                        bgImage = javax.imageio.ImageIO.read(url);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    int imgW = bgImage.getWidth(this);
                    int imgH = bgImage.getHeight(this);

                    // Avoid division by zero
                    if (imgW > 0 && imgH > 0) {
                        int panelW = getWidth();
                        int panelH = getHeight();

                        // Scale to cover
                        double scale = Math.max((double) panelW / imgW, (double) panelH / imgH);

                        int newW = (int) (imgW * scale);
                        int newH = (int) (imgH * scale);

                        // Center the image
                        int x = (panelW - newW) / 2;
                        int y = (panelH - newH) / 2;

                        g.drawImage(bgImage, x, y, newW, newH, this);
                    }
                } else {
                    g.setColor(Estilos.DARK_SPRUCE);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        sidebar.setPreferredSize(new Dimension(250, 0));
        sidebar.setBackground(Estilos.DARK_SPRUCE); // Fallback

        panelPrincipal.add(sidebar, BorderLayout.WEST);

        // --- MAIN CONTENT AREA ---
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Estilos.FONDO_PRINCIPAL);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header Fields Panel
        JPanel panelDatosSuperiores = new JPanel(new GridLayout(0, 1, 5, 10));
        panelDatosSuperiores.setBackground(Estilos.FONDO_PRINCIPAL);

        if (esEnvio) {
            panelDatosSuperiores.add(crearCampoLabel("For:", textoPara));
        } else {
            panelDatosSuperiores.add(crearCampoLabel("From:", textoPara));
        }
        panelDatosSuperiores.add(crearCampoLabel("Subject:", textoAsunto));

        contentPanel.add(panelDatosSuperiores, BorderLayout.NORTH);

        // Body Panel
        JPanel panelCuerpo = new JPanel(new BorderLayout(5, 5));
        panelCuerpo.setBackground(Estilos.FONDO_PRINCIPAL);

        JLabel lblMessage = new JLabel("Message:");
        lblMessage.setFont(Estilos.FONT_TEXTO.deriveFont(Font.BOLD));
        lblMessage.setForeground(Estilos.TEXTO_PRINCIPAL);
        panelCuerpo.add(lblMessage, BorderLayout.NORTH);

        JScrollPane scrollCuerpo = new JScrollPane(textoCuerpo);
        scrollCuerpo.setBorder(BorderFactory.createLineBorder(Estilos.DARK_SPRUCE));
        panelCuerpo.add(scrollCuerpo, BorderLayout.CENTER);

        contentPanel.add(panelCuerpo, BorderLayout.CENTER);

        // Action Buttons Panel
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setBackground(Estilos.FONDO_PRINCIPAL);

        if (esEnvio) {
            estilarBoton(botonAdjuntar);
            estilarBoton(botonEnviar);
            panelBoton.add(botonAdjuntar);
            panelBoton.add(botonEnviar);
        } else {
            estilarBoton(botonNoLeido);
            estilarBoton(botonExportar);
            estilarBoton(botonEliminar);
            panelBoton.add(botonNoLeido);
            panelBoton.add(botonExportar);
            panelBoton.add(botonEliminar);
        }

        contentPanel.add(panelBoton, BorderLayout.SOUTH);

        panelPrincipal.add(contentPanel, BorderLayout.CENTER);
        this.add(panelPrincipal);
    }

    private JPanel crearCampoLabel(String labelText, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBackground(Estilos.FONDO_PRINCIPAL);

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(Estilos.FONT_TEXTO.deriveFont(Font.BOLD));
        lbl.setForeground(Estilos.TEXTO_PRINCIPAL);
        lbl.setPreferredSize(new Dimension(80, 25));

        if (field instanceof JTextField) {
            ((JTextField) field).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Estilos.DARK_SPRUCE),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            field.setFont(Estilos.FONT_TEXTO);
            field.setBackground(Estilos.COLOR_INPUT_BG);
            field.setForeground(Estilos.COLOR_INPUT_TEXT);
        }

        p.add(lbl, BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        return p;
    }

    private void estilarBoton(JButton btn) {
        btn.setFont(Estilos.FONT_BOTON);
        btn.setBackground(Estilos.DARK_SPRUCE);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        if (btn.equals(botonNoLeido)) {
            btn.setPreferredSize(new Dimension(130, 35));
        } else {
            btn.setPreferredSize(new Dimension(100, 35));
        }
    }

    public void propiedadesGenerales() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(700, 400);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(700, 400));
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

    public JTextField getTextoPara() {
        return textoPara;
    }

    public JTextField getTextoAsunto() {
        return textoAsunto;
    }

    public JTextArea getTextoCuerpo() {
        return textoCuerpo;
    }

    public JButton getBotonEnviar() {
        return botonEnviar;
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