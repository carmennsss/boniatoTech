package vista;

import javax.swing.*;

import org.apache.commons.net.ftp.FTPFile;

import controlador.CoPrincipal;
import modelo.ModeloClienteFTP;
import servidor.FileManager;

import java.io.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.border.EmptyBorder;
import java.net.URL;

public class VistaGestorArchivos extends JFrame {

    private VistaMenuPrincipal menu;
    private DefaultListModel<FTPFile> listaModel;
    private JList<FTPFile> listaArchivos;
    private ModeloClienteFTP client;
    private FileManager ftp;
    private String rutaActual = "/";

    public VistaGestorArchivos(ModeloClienteFTP client, VistaMenuPrincipal menu) {
        this.client = client;
        this.menu = menu;
        ftp = new FileManager("13.62.51.110", 21, client.getUser(), client.getPass());
        this.setTitle("File Manager");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLocationRelativeTo(null);

        Color colorFondo = new Color(248, 245, 242);
        Color colorTexto = new Color(74, 88, 89);
        Color colorBotonAccion = new Color(110, 137, 115);
        Color colorBotonNav = new Color(200, 190, 170);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(colorFondo);
        setContentPane(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        JPanel sidePanel = new JPanel() {
            private Image imagen;
            {
                URL url = getClass().getResource("/lateral_files.jpg");
                if (url != null) {
                    imagen = new ImageIcon(url).getImage();
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagen != null) {
                    double scale = Math.max((double) getWidth() / imagen.getWidth(this),
                            (double) getHeight() / imagen.getHeight(this));
                    int w = (int) (imagen.getWidth(this) * scale);
                    int h = (int) (imagen.getHeight(this) * scale);
                    g.drawImage(imagen, 0, 0, w, h, this);
                }
            }
        };

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 1.0;
        mainPanel.add(sidePanel, gbc);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(colorFondo);
        contentPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        gbc.weighty = 1.0;
        mainPanel.add(contentPanel, gbc);

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(colorFondo);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("File Repository");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(colorTexto);

        JLabel subtitle = new JLabel("Manage your server files efficiently");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(150, 150, 150));

        header.add(title);
        header.add(Box.createVerticalStrut(5));
        header.add(subtitle);
        header.add(Box.createVerticalStrut(20));

        contentPanel.add(header, BorderLayout.NORTH);

        listaModel = new DefaultListModel<>();
        listaArchivos = new JList<>(listaModel);
        listaArchivos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaArchivos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        listaArchivos.setFixedCellHeight(30);
        listaArchivos.setBackground(Color.WHITE);
        listaArchivos.setBorder(new EmptyBorder(5, 5, 5, 5));
        detectarDobleClick(listaArchivos);

        JScrollPane scrollPane = new JScrollPane(listaArchivos);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel centerContainer = new JPanel(new BorderLayout(20, 0));
        centerContainer.setBackground(colorFondo);
        centerContainer.add(scrollPane, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel();
        botonesPanel.setLayout(new BoxLayout(botonesPanel, BoxLayout.Y_AXIS));
        botonesPanel.setBackground(colorFondo);

        JButton botonSubida = new JButton("Upload");
        JButton botonDescarga = new JButton("Download");
        JButton botonEliminar = new JButton("Delete");
        JButton botonCrearCarpeta = new JButton("New Folder");
        JButton botonBorrarCarpeta = new JButton("Delete Folder");

        estilarBoton(botonSubida, colorBotonAccion, Color.WHITE);
        estilarBoton(botonDescarga, colorBotonAccion, Color.WHITE);
        estilarBoton(botonEliminar, new Color(200, 100, 100), Color.WHITE);
        estilarBoton(botonCrearCarpeta, colorBotonAccion, Color.WHITE);
        estilarBoton(botonBorrarCarpeta, new Color(200, 100, 100), Color.WHITE);

        botonesPanel.add(new JLabel("Actions"));
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(botonSubida);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(botonDescarga);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(botonEliminar);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(botonCrearCarpeta);
        botonesPanel.add(Box.createVerticalStrut(10));
        botonesPanel.add(botonBorrarCarpeta);
        botonesPanel.add(Box.createVerticalGlue());

        centerContainer.add(botonesPanel, BorderLayout.EAST);
        contentPanel.add(centerContainer, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.setBackground(colorFondo);

        JButton botonVolver = new JButton("Back");
        JButton botonVolverMenuPrincipal = new JButton("Main Menu");

        estilarBoton(botonVolver, colorBotonNav, Color.BLACK);
        estilarBoton(botonVolverMenuPrincipal, colorBotonNav, Color.BLACK);

        accionBotonSubida(botonSubida);
        accionBotonDescarga(botonDescarga);
        accionBotonEliminar(botonEliminar);
        accionBotonCrearCarpeta(botonCrearCarpeta);
        accionBotonBorrarCarpeta(botonBorrarCarpeta);
        accionBotonVolver(botonVolver);
        accionBotonVolverMenuPrincipal(botonVolverMenuPrincipal);

        footer.add(botonVolverMenuPrincipal);
        footer.add(botonVolver);

        contentPanel.add(footer, BorderLayout.SOUTH);
    }

    private void estilarBoton(JButton btn, Color bg, Color fg) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(120, 35));
        btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    }

    public void inicializarFileManager() {
        ftp = new FileManager("13.62.51.110", 21, client.getUser(), client.getPass());
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

    public void accionBotonVolver(JButton boton) {
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String rutaPadre;
                if (!rutaActual.equals("/")) {
                    rutaPadre = rutaActual.substring(0, rutaActual.lastIndexOf('/'));
                    if (rutaPadre.isEmpty())
                        rutaPadre = "/";
                    actualizarListaFTP(rutaPadre);
                }
            }
        });

    }

    public void accionBotonVolverMenuPrincipal(JButton boton) {
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                menu.hacerVisible();
            }
        });

    }

    public void hacerVisible() {
        setVisible(true);
    }

}
