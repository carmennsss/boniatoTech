package vista;

import javax.swing.*;

import org.apache.commons.net.ftp.FTPFile;

import servidor.FileManager;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.border.EmptyBorder;

public class VistaGestorArchivos extends JFrame {

    private DefaultListModel<FTPFile> listaModel;
    private JList<FTPFile> listaArchivos;
    private FileManager ftp = new FileManager("127.0.0.1", 21, "juan", "");
    private String rutaActual = "/";

    public VistaGestorArchivos() {
        this.setTitle("File Manager");
        this.setLayout(new BorderLayout());
        Font fuenteUser = new Font("Arial", Font.BOLD, 25);
        JLabel user = new JLabel("Welcome, "+ftp.getUserName());
        Font fuenteTitulo = new Font("Arial", Font.BOLD, 20);
        JLabel titulo = new JLabel("Server Files: ");
        user.setFont(fuenteUser);
        titulo.setFont(fuenteTitulo);

        JButton botonSubida = new JButton("Upload File");
        JButton botonDescarga = new JButton("Download File");
        JButton botonEliminar = new JButton("Delete File");
        JButton botonCrearCarpeta = new JButton("Create Folder");
        JButton botonBorrarCarpeta = new JButton("Delete Folder");
        JButton botonVolver = new JButton("Back");

        JPanel derecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel izquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));

        derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));
        izquierda.setLayout(new BoxLayout(izquierda, BoxLayout.Y_AXIS));

        derecha.setBorder(new EmptyBorder(125, 0, 0, 20));
        izquierda.setBorder(new EmptyBorder(10, 10, 10, 10));

        accionBotonSubida(botonSubida);
        accionBotonDescarga(botonDescarga);
        accionBotonEliminar(botonEliminar);
        accionBotonCrearCarpeta(botonCrearCarpeta);
        accionBotonBorrarCarpeta(botonBorrarCarpeta);
        accionBotonVolver(botonVolver);

        derecha.add(botonSubida);
        derecha.add(Box.createRigidArea(new Dimension(0, 10)));
        derecha.add(botonDescarga);
        derecha.add(Box.createRigidArea(new Dimension(0, 10)));
        derecha.add(botonEliminar);
        derecha.add(Box.createRigidArea(new Dimension(0, 10)));
        derecha.add(botonCrearCarpeta);
        derecha.add(Box.createRigidArea(new Dimension(0, 10)));
        derecha.add(botonBorrarCarpeta);

        izquierda.add(user);
        izquierda.add(Box.createRigidArea(new Dimension(0, 20)));
        izquierda.add(titulo);
        izquierda.add(Box.createRigidArea(new Dimension(0, 10)));
        izquierda.add(botonVolver);
        izquierda.add(Box.createRigidArea(new Dimension(0, 10)));

        listaModel = new DefaultListModel<>();
        listaArchivos = new JList<>(listaModel);
        listaArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        detectarDobleClick(listaArchivos);

        JScrollPane scrollPane = new JScrollPane(listaArchivos);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        izquierda.add(scrollPane);

        this.add(derecha, BorderLayout.EAST);
        this.add(izquierda, BorderLayout.WEST);

        this.setSize(700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        actualizarListaFTP();

    }

    public void accionBotonSubida(JButton boton) {

        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                File file;
                fc.setDialogTitle("Select the file to upload");
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int respuesta = fc.showDialog(fc, "OK");
                if (respuesta == JFileChooser.APPROVE_OPTION) {
                    file = fc.getSelectedFile();
                    String archivo = file.getAbsolutePath();
                    String nombreArchivo = file.getName();
                    ftp.subirArchivo(archivo, nombreArchivo, rutaActual);
                    actualizarListaFTP();
                }
            }
        });

    }

    public void accionBotonDescarga(JButton boton) {
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JFileChooser fc = new JFileChooser();
                File carpeta;
                FTPFile select = listaArchivos.getSelectedValue();
                fc.setDialogTitle("Select where to download the file");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int respuesta = fc.showDialog(fc, "OK");
                if (respuesta == JFileChooser.APPROVE_OPTION) {
                    carpeta = fc.getSelectedFile();
                    if (select != null) {
                        ftp.descargarArchivo(select, carpeta.getAbsolutePath(), rutaActual);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a file", "Error",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    public void accionBotonEliminar(JButton boton) {

        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                FTPFile select = listaArchivos.getSelectedValue();
                if (select != null) {
                    ftp.borrarArchivo(select, rutaActual);
                    actualizarListaFTP();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a file", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    public void accionBotonCrearCarpeta(JButton boton) {
        boton.addActionListener(new ActionListener() {
            String nombreCarpeta = "";

            public void actionPerformed(ActionEvent e) {
                nombreCarpeta = JOptionPane.showInputDialog(null, "Enter the directory name", "");
                if (nombreCarpeta != null) {
                    ftp.crearCarpeta(nombreCarpeta, rutaActual);
                    actualizarListaFTP();
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a name for the folder", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    public void accionBotonBorrarCarpeta(JButton boton) {

        boton.addActionListener(new ActionListener() {
            FTPFile select;
            public void actionPerformed(ActionEvent e) {

                select = listaArchivos.getSelectedValue();
                if (select == null) {
                    JOptionPane.showMessageDialog(null, "Please select a folder.", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                if (!select.isDirectory()) {
                    JOptionPane.showMessageDialog(null, "You must select a folder, not a file.", "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                ftp.borrarCarpeta(select.getName(), rutaActual);
                actualizarListaFTP();
            }
        });
    }

    public void actualizarListaFTP(String ruta) {
        FTPFile[] archivos;
        if (this.ftp.conectar()) {
            this.listaModel.clear();
            archivos = ftp.listarArchivos(ruta);
            for (FTPFile archivo : archivos) {
                this.listaModel.addElement(archivo);
            }
            this.rutaActual = ruta;
            this.ftp.desconectar();
        } else {
            JOptionPane.showMessageDialog(this, "Could not connect to FTP");
        }
    }

    public void actualizarListaFTP() {
        actualizarListaFTP(this.rutaActual);
    }

    public void detectarDobleClick(JList<FTPFile> listaArchivos) {

        listaArchivos.addMouseListener(new MouseAdapter() {
            FTPFile seleccionado;
            String nuevaRuta;

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    seleccionado = listaArchivos.getSelectedValue();
                    if (seleccionado != null && seleccionado.isDirectory()) {
                        nuevaRuta = rutaActual + "/" + seleccionado.getName();
                        actualizarListaFTP(nuevaRuta);
                    }
                }
            }
        });

    }

    private void accionBotonVolver(JButton boton) {
        boton.addActionListener(e -> {
            String rutaPadre;
            if (!rutaActual.equals("/")) {
                rutaPadre = rutaActual.substring(0, rutaActual.lastIndexOf('/'));
                if (rutaPadre.isEmpty())
                    rutaPadre = "/";
                actualizarListaFTP(rutaPadre);
            }
        });

    }

}
