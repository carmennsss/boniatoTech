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
    private JButton botonEliminar;
    private JButton botonLeido;
    private String remitente;

    // CONSTRUCTOR ENVIAR/REDACTAR
    public VistaCorreoBase(String remitente) {
        this.setTitle("Redactar Nuevo Correo");
        this.remitente = remitente;
        
        inicializarComponentes();
        propiedadesGenerales();

        textoPara.setText(""); 
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.add(botonEnviar);

        ensamblarVista(true, remitente, null); 
        
        this.setVisible(true);
    }

    // CONSTRUCTOR CONSULTAR
    public VistaCorreoBase(Correo correo) {
        this.setTitle("Consultar Correo");

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

        this.setVisible(true);
    }
    
    // M�todo para inicializar todos los componentes una sola vez
    private void inicializarComponentes() {
        textoPara = new JTextField(40); // M�s ancho por defecto
        textoAsunto = new JTextField(40);
        textoCuerpo = new JTextArea(15, 50);
        textoCuerpo.setLineWrap(true);
        textoCuerpo.setWrapStyleWord(true);
        botonEnviar = new JButton("Enviar");
        botonEliminar = new JButton("Delete");
        botonLeido = new JButton("Marcar Leido");
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

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        if (esEnvio) {
            panelBoton.add(botonEnviar);
        } else {
        	panelBoton.add(botonLeido);
            panelBoton.add(botonEliminar);
        }
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        
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
	
	
    
}