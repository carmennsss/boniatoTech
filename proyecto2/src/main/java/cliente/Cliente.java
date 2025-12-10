package cliente;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import javax.swing.event.*;

public class Cliente extends JFrame implements Runnable {

    static Socket socket;

    ObjectInputStream inObjeto;
    ObjectOutputStream outObjeto;

    EstructuraFicheros nodo = null;
    EstructuraFicheros Raiz;

    static JList<EstructuraFicheros> listaDirec = new JList<>();
    static String direcSelec = "";
    static String ficheroSelec = "";
    static String ficheroCompleto = "";

    // Componentes Swing
    JLabel cab = new JLabel("Conectando...");
    JLabel cab3 = new JLabel();
    JTextField campo = new JTextField();
    JTextField campo2 = new JTextField();
    JButton botonSalir = new JButton("Salir");
    JButton botonDescargar = new JButton("Descargar");
    JButton botonCargar = new JButton("Cargar");

    public static void main(String[] args) throws IOException {
        int puerto = 44441;
        Socket s = new Socket("localhost", puerto);
        Cliente hiloC = new Cliente(s);
        hiloC.setBounds(100, 100, 540, 500);
        hiloC.setVisible(true);
        new Thread(hiloC).start();
    }

    public Cliente(Socket s) throws IOException {
        super("SERVIDOR DE FICHEROS BÁSICO");
        socket = s;

        outObjeto = new ObjectOutputStream(socket.getOutputStream());
        inObjeto = new ObjectInputStream(socket.getInputStream());

        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel norte = new JPanel(new GridLayout(2, 1));
        norte.add(cab);
        norte.add(cab3);

        JPanel sur = new JPanel(new GridLayout(2, 1));
        sur.add(campo);
        sur.add(campo2);

        JPanel botones = new JPanel();
        botones.add(botonDescargar);
        botones.add(botonCargar);
        botones.add(botonSalir);

        add(norte, BorderLayout.NORTH);
        add(new JScrollPane(listaDirec), BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);
        add(botones, BorderLayout.PAGE_END);

        listaDirec.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                nodo = listaDirec.getSelectedValue();
                if (nodo == null) return;

                if (nodo.isDir()) {
                    campo.setText("Directorio seleccionado");
                } else {
                    ficheroSelec = nodo.getName();
                    ficheroCompleto = nodo.getPath();
                    campo.setText("Fichero: " + ficheroSelec);
                }
            }
        });

        botonSalir.addActionListener(e -> {
            try {
                socket.close();
            } catch (IOException ignored) {}
            System.exit(0);
        });

        botonDescargar.addActionListener(e -> {
            if (ficheroCompleto.isEmpty()) return;

            try {
                outObjeto.writeObject(new PideFichero(ficheroCompleto));
                Object obj = inObjeto.readObject();

                if (obj instanceof ObtieneFichero of) {
                    try (FileOutputStream fos = new FileOutputStream(ficheroSelec)) {
                        fos.write(of.getContenidoFichero());
                    }
                    JOptionPane.showMessageDialog(this, "FICHERO DESCARGADO");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        botonCargar.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = fc.getSelectedFile();
                try {
                    byte[] datos = new byte[(int) f.length()];
                    try (FileInputStream fis = new FileInputStream(f)) {
                        fis.read(datos);
                    }
                    outObjeto.writeObject(new EnviaFichero(datos, f.getName(), direcSelec));
                    JOptionPane.showMessageDialog(this, "FICHERO CARGADO");

                    nodo = (EstructuraFicheros) inObjeto.readObject();
                    direcSelec = nodo.getPath();
                    llenarLista(nodo.getLista());
                    campo2.setText("Número de ficheros: " + nodo.getNumFich());

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void run() {
        try {
            Raiz = (EstructuraFicheros) inObjeto.readObject();
            direcSelec = Raiz.getPath();
            llenarLista(Raiz.getLista());

            cab.setText("CONECTADO");
            cab3.setText("RAÍZ: " + direcSelec);
            campo2.setText("Número de ficheros: " + Raiz.getNumFich());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void llenarLista(EstructuraFicheros[] files) {
        DefaultListModel<EstructuraFicheros> modelo = new DefaultListModel<>();
        if (files != null) {
            for (EstructuraFicheros f : files) modelo.addElement(f);
        }
        listaDirec.setFont(new Font("Courier", Font.PLAIN, 12));
        listaDirec.setForeground(Color.BLUE);
        listaDirec.setModel(modelo);
    }
}
