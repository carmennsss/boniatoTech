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
import modelo.MoTextos;

/**
 * Vista base para la visualización y composición de correos electrónicos.
 * Se reutiliza tanto para enviar nuevos correos como para leer los existentes.
 */
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
    private JLabel lblPara;
    private JLabel lblAsunto;
    private JLabel lblMessage;

    /**
     * Constructor para el modo de redacción de nuevo correo.
     *
     * @param remitente Dirección de correo del usuarios que envía el mensaje.
     */
    public VistaCorreoBase(String remitente) {
        this.remitente = remitente;
        propiedades(true);
    }

    /**
     * Constructor para el modo de lectura de correo existente.
     *
     * @param correo Objeto Correo con los datos a visualizar.
     */
    public VistaCorreoBase(Correo correo) {
        propiedades(false);

        textoPara.setText(correo.getRemitente());
        textoAsunto.setText(correo.getAsunto());
        textoCuerpo.setText(correo.getCuerpo());

        textoPara.setEditable(false);
        textoAsunto.setEditable(false);
        textoCuerpo.setEditable(false);
    }

    private void propiedades(boolean esEnvio) {
        if (esEnvio) {
            this.setTitle(MoTextos.mail_title_compose);
        } else {
            this.setTitle(MoTextos.mail_title_check);
        }

        inicializarComponentes();
        propiedadesGenerales();
        ensamblarVista(esEnvio);
    }

    private void inicializarComponentes() {
        textoPara = new JTextField(40);
        textoAsunto = new JTextField(40);
        textoCuerpo = new JTextArea(15, 50);
        textoCuerpo.setLineWrap(true);
        textoCuerpo.setWrapStyleWord(true);
        botonEnviar = new JButton(MoTextos.btn_send);
        botonEliminar = new JButton(MoTextos.btn_delete);
        botonNoLeido = new JButton(MoTextos.btn_mark_unread);
        botonExportar = new JButton(MoTextos.btn_export);
        botonAdjuntar = new JButton(MoTextos.btn_attach);

        lblPara = new JLabel();
        lblAsunto = new JLabel(MoTextos.mail_lbl_subject);
        lblMessage = new JLabel(MoTextos.mail_lbl_message);
    }

    private void ensamblarVista(boolean esEnvio) {

        panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Estilos.FONDO_PRINCIPAL);

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

                    if (imgW > 0 && imgH > 0) {
                        int panelW = getWidth();
                        int panelH = getHeight();

                        double scale = Math.max((double) panelW / imgW, (double) panelH / imgH);

                        int newW = (int) (imgW * scale);
                        int newH = (int) (imgH * scale);

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
        sidebar.setBackground(Estilos.DARK_SPRUCE);

        panelPrincipal.add(sidebar, BorderLayout.WEST);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(Estilos.FONDO_PRINCIPAL);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelDatosSuperiores = new JPanel(new GridLayout(0, 1, 5, 10));
        panelDatosSuperiores.setBackground(Estilos.FONDO_PRINCIPAL);

        if (esEnvio) {
            lblPara.setText(MoTextos.mail_lbl_for);
            panelDatosSuperiores.add(crearCampoLabel(lblPara, textoPara));
        } else {
            lblPara.setText(MoTextos.mail_lbl_from);
            panelDatosSuperiores.add(crearCampoLabel(lblPara, textoPara));
        }
        panelDatosSuperiores.add(crearCampoLabel(lblAsunto, textoAsunto));

        contentPanel.add(panelDatosSuperiores, BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new BorderLayout(5, 5));
        panelCuerpo.setBackground(Estilos.FONDO_PRINCIPAL);

        lblMessage.setFont(Estilos.FONT_TEXTO.deriveFont(Font.BOLD));
        lblMessage.setForeground(Estilos.TEXTO_PRINCIPAL);
        panelCuerpo.add(lblMessage, BorderLayout.NORTH);

        JScrollPane scrollCuerpo = new JScrollPane(textoCuerpo);
        scrollCuerpo.setBorder(BorderFactory.createLineBorder(Estilos.DARK_SPRUCE));
        panelCuerpo.add(scrollCuerpo, BorderLayout.CENTER);

        contentPanel.add(panelCuerpo, BorderLayout.CENTER);

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

    private JPanel crearCampoLabel(JLabel lbl, JComponent field) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBackground(Estilos.FONDO_PRINCIPAL);

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

    /**
     * Abre un diálogo para guardar el correo actual como archivo .eml.
     *
     * @param nombreSugerido Nombre sugerido para el archivo.
     * @return El objeto File seleccionado por el usuario, o null si cancela.
     */
    public File exportarCorreo(String nombreSugerido) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export EML");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Files (*.eml)", "eml"));
        fileChooser.setSelectedFile(new File(nombreSugerido + ".eml"));

        int seleccion = fileChooser.showSaveDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            File f = fileChooser.getSelectedFile();
            if (!f.getName().toLowerCase().endsWith(".eml")) {
                f = new File(f.getAbsolutePath() + ".eml");
            }
            return f;
        }
        return null;
    }

    public void mostrarMensaje(String mensaje, boolean esError) {
        JOptionPane.showMessageDialog(this, mensaje,
                esError ? MoTextos.msg_error_title : MoTextos.msg_success_title,
                esError ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Abre un diálogo para seleccionar archivos adjuntos.
     * Permite selección múltiple.
     *
     * @return El archivo seleccionado (o el último de los seleccionados).
     */
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

    /**
     * Actualiza los textos de la interfaz según el idioma seleccionado.
     */
    public void actualizarTextos() {
        if (getTitle().equals(MoTextos.mail_title_compose) || getTitle().equals("Compose new Mail")
                || getTitle().equals("Redactar Nuevo Correo")) {
            this.setTitle(MoTextos.mail_title_compose);
            lblPara.setText(MoTextos.mail_lbl_for);
        } else {
            this.setTitle(MoTextos.mail_title_check);
            lblPara.setText(MoTextos.mail_lbl_from);
        }

        lblAsunto.setText(MoTextos.mail_lbl_subject);
        lblMessage.setText(MoTextos.mail_lbl_message);

        botonEnviar.setText(MoTextos.btn_send);
        botonEliminar.setText(MoTextos.btn_delete);
        botonNoLeido.setText(MoTextos.btn_mark_unread);
        botonExportar.setText(MoTextos.btn_export);
        botonAdjuntar.setText(MoTextos.btn_attach);

        repaint();
    }
}