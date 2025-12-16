package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class VistaRegistroUsuarios extends JFrame{
	
	VistaAdmin vistaAdmin;
	
	public VistaRegistroUsuarios(VistaAdmin vistaAdmin) {
		this.vistaAdmin=vistaAdmin;
		
		this.setTitle("User Register");
		this.setLayout(new BorderLayout());
		
		JLabel nombre = new JLabel("Name: ");
		JTextField textNombre = new JTextField(20);
		
		JLabel correo = new JLabel("Address: ");
		JTextField textCorreo = new JTextField(20);
		
		JLabel claveCorreo = new JLabel("Address Key: ");
		JTextField textClaveCorreo = new JTextField(20);
		
		JLabel contrasena = new JLabel("Password: ");
		JPasswordField  textContrasena = new JPasswordField (20);
		
		JLabel confContrasena = new JLabel("Confirm password: ");
		JPasswordField  textConfContrasena = new JPasswordField (20);
		
		JButton anadir = new JButton("Register user");
		JButton volver = new JButton("Admin Menu");
		
		JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel layout = new JPanel(new GridLayout(5, 2, 10, 10));
		layout.setBorder(new EmptyBorder(20, 20, 20, 20));

		
		layout.add(nombre);
		layout.add(textNombre);
		
		layout.add(correo);
		layout.add(textCorreo);
		
		layout.add(claveCorreo);
		layout.add(textClaveCorreo);
		
		layout.add(contrasena);
		layout.add(textContrasena);
		
		layout.add(confContrasena);
		layout.add(textConfContrasena);
		
		accionBotonVolver(volver);
		
		contenedor.add(layout);
		contenedor.add(anadir);
		contenedor.add(volver);

		
		this.add(contenedor, BorderLayout.CENTER);
		this.setSize(500, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void hacerVisible() {
		this.setVisible(true);
	}
	
	public void accionBotonVolver(JButton boton) {
		boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	VistaRegistroUsuarios.this.setVisible(false);
            	vistaAdmin.hacerVisible();
            }
        });
	}
	
}
