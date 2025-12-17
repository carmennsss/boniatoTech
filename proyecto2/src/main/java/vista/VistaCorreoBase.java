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
    private JButton botonExportar;
    private JButton botonNoLeido;
    private JButton botonAdjuntar;
    private String remitente;

    public VistaCorreoBase(String remitente) {
        this.setTitle("Redactar Nuevo Correo");
        this.remitente = remitente;
        
        inicializarComponentes();
        propiedadesGenerales();

        // Ya no creamos el panel aquí, dejamos que ensamblarVista lo haga
        ensamblarVista(true); 
        
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
        botonLeido = new JButton("Mark as read");
        botonNoLeido = new JButton("Mark as unread");
        botonExportar = new JButton("Export");
        botonAdjuntar = new JButton("Adjuntar");
    }

    private void ensamblarVista(boolean esEnvio) {
        panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cambiamos a 0 filas para que crezca según necesite
        JPanel panelDatosSuperiores = new JPanel(new GridLayout(0, 2, 5, 5));
        
        if (esEnvio) {
            panelDatosSuperiores.add(new JLabel("Para:"));
        } else {
            panelDatosSuperiores.add(new JLabel("De:"));
        }
        panelDatosSuperiores.add(textoPara);

        panelDatosSuperiores.add(new JLabel("Asunto:"));
        panelDatosSuperiores.add(textoAsunto);

        panelPrincipal.add(panelDatosSuperiores, BorderLayout.NORTH);

        JPanel panelCuerpo = new JPanel(new BorderLayout());
        panelCuerpo.add(new JLabel("Cuerpo del Mensaje:"), BorderLayout.NORTH);
        
        JScrollPane scrollCuerpo = new JScrollPane(textoCuerpo);
        panelCuerpo.add(scrollCuerpo, BorderLayout.CENTER);
        
        panelPrincipal.add(panelCuerpo, BorderLayout.CENTER);

        // LÓGICA DE BOTONES CENTRALIZADA
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        if (esEnvio) {
            panelBoton.add(botonAdjuntar);
            panelBoton.add(botonEnviar);
        } else {
            // Añadimos todos los botones que querías ver en consulta
            panelBoton.add(botonLeido);
            panelBoton.add(botonNoLeido);
            panelBoton.add(botonExportar);
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
	
	
    
}