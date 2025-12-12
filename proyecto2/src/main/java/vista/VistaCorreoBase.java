package vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;

import modelo.Correo;

public class VistaCorreoBase extends JFrame {

    private JPanel panelPrincipal;
    private JTextField textoPara;
    private JTextField textoAsunto;
    private JTextArea textoCuerpo;
    private JButton botonEnviar;

    // CONSTRUCTOR ENVIAR/REDACTAR
    public VistaCorreoBase(String remitente) {
        this.setTitle("Redactar Nuevo Correo");
        
        inicializarComponentes();
        propiedadesGenerales();

        // Configuración Específica para ENVIAR
        textoPara.setText(""); // Se deja vacío para escribir el destinatario
        
        // El botón "Enviar" solo es necesario en la vista de REDACCIÓN
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(botonEnviar);

        // Se monta la vista (la lógica del diseño está en ensamblarVista())
        ensamblarVista(true, remitente, null); 
        
        this.setVisible(true);
    }

    // CONSTRUCTOR CONSULTAR
    public VistaCorreoBase(Correo correo) {
        this.setTitle("Consultar Correo");

        inicializarComponentes();
        propiedadesGenerales();

        // Configuración Específica para CONSULTAR
        textoPara.setText(correo.getRemitente());
        textoAsunto.setText(correo.getAsunto());
        textoCuerpo.setText(correo.getCuerpo());
        
        // Bloquear edición
        textoPara.setEditable(false);
        textoAsunto.setEditable(false);
        textoCuerpo.setEditable(false);
        
        ensamblarVista(false, null, correo.getRemitente()); 

        this.setVisible(true);
    }
    
    // Método para inicializar todos los componentes una sola vez
    private void inicializarComponentes() {
        textoPara = new JTextField(40); // Más ancho por defecto
        textoAsunto = new JTextField(40);
        textoCuerpo = new JTextArea(15, 50);
        textoCuerpo.setLineWrap(true);
        textoCuerpo.setWrapStyleWord(true);
        botonEnviar = new JButton("Enviar");
        botonEnviar.setPreferredSize(new Dimension(100, 30)); // Más grande
    }

    private void ensamblarVista(boolean esEnvio, String remitente, String remitenteCorreo) {
        panelPrincipal = new JPanel(new BorderLayout(5, 5)); // Espaciado de 5px
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen

        JPanel panelDatosSuperiores = new JPanel(new GridLayout(esEnvio ? 2 : 1, 2, 5, 5));
        
        // Si es envío, mostramos "Para:" y el campo, si no, De: y el campo
        if (esEnvio) {
            panelDatosSuperiores.add(new JLabel("Para:"));
            panelDatosSuperiores.add(textoPara);
        } else {
             panelDatosSuperiores.add(new JLabel("De:"));
             panelDatosSuperiores.add(textoPara); 
        }

        // Asunto va en ambos casos
        panelDatosSuperiores.add(new JLabel("Asunto:"));
        panelDatosSuperiores.add(textoAsunto);

        panelPrincipal.add(panelDatosSuperiores, BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new BorderLayout());
        panelCuerpo.add(new JLabel("Cuerpo del Mensaje:"), BorderLayout.NORTH);
        
        JScrollPane scrollCuerpo = new JScrollPane(textoCuerpo);
        panelCuerpo.add(scrollCuerpo, BorderLayout.CENTER);
        
        panelPrincipal.add(panelCuerpo, BorderLayout.CENTER);

        if (esEnvio) {
            JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelBoton.add(botonEnviar);
            panelPrincipal.add(panelBoton, BorderLayout.SOUTH);
        }
        
        this.add(panelPrincipal);
    }


    public void propiedadesGenerales() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(500, 400));
    }
    
    public JTextField getTextoPara() { return textoPara; }
    public JTextField getTextoAsunto() { return textoAsunto; }
    public JTextArea getTextoCuerpo() { return textoCuerpo; }
    public JButton getBotonEnviar() { return botonEnviar; }
}