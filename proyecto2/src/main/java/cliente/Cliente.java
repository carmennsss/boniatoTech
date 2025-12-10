import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class clienteFicheros extends JFrame implements Runnable {
    static Socket socket;
    EstructuraFicheros nodo = null; //objeto EstructuraFicheros actual
    ObjectInputStream inObjeto; //stream de entrada
    ObjectOutputStream outObjeto; //stream de salida
    EstructuraFicheros Raiz; //objeto EstructuraFicheros Raiz
    //lista para los datos del directorio
    static JList listaDirec = new JList();
    //para saber directorio y fichero seleccionado
    static String direcSelec = ""; //nombre del directorio actual
    static String ficheroSelec = ""; //fichero seleccionado
    static String ficheroCompleto = ""; //directorio + fichero

    // Assuming cab, cab3, campo, campo2, botonSalir, botonDescargar, botonCargar are defined somewhere in the class
    // For completeness, they should be declared as instance variables, but not shown in snippets.

    public static void main(String[] args) throws IOException {
        int puerto = 44441;
        Socket s = new Socket("localhost", puerto);
        clienteFicheros hiloC = new clienteFicheros(s);
        hiloC.setBounds(0, 0, 540, 500);
        hiloC.setVisible(true);
        new Thread(hiloC).start();
    }

    public clienteFicheros(Socket s) throws IOException {
        super("SERVIDOR DE FICHEROS BÁSICO");
        socket = s;
        try {
            outObjeto = new ObjectOutputStream(socket.getOutputStream());
            inObjeto = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
        //definición de los campos de la pantalla
        //ACCIONES DE LOS BOTONES
        listaDirec.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent lse) {
                if (lse.getValueIsAdjusting()) {
                    ficheroSelec = "";
                    ficheroCompleto = "";
                    //se obtiene el elemento seleccionado de la lista
                    nodo = (EstructuraFicheros) listaDirec.getSelectedValue();
                    if (nodo.isDir()) { //ES UN DIRECTORIO
                        campo.setText("FUNCIÓN NO IMPLEMENTADA.....");
                    } else { //SE TRATA DE UN FICHERO
                        ficheroSelec = nodo.getName();
                        ficheroCompleto = nodo.getPath();
                        campo.setText("FICHERO seleccionado: " + ficheroSelec);
                    }
                }
            }
        });

        botonSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    socket.close(); //cerrar socket
                    System.exit(0);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        botonDescargar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ficheroCompleto.equals("")) return; //no se ha seleccionado
                //PIDE ESTE FICHERO ficheroCompleto
                PideFichero pide = new PideFichero(ficheroCompleto);
                try {
                    //pido fichero al servidor
                    outObjeto.writeObject(pide);
                    //se crea fichero con el nombre del seleccionado
                    //en el directorio actual
                    FileOutputStream fos = new FileOutputStream(ficheroSelec);
                    //recibo el fichero del servidor
                    Object obtengo = inObjeto.readObject();
                    if (obtengo instanceof ObtieneFichero) {
                        ObtieneFichero fic = (ObtieneFichero) obtengo;
                        fos.write(fic.getContenidoFichero()); //escribo bytes
                        fos.close();
                        JOptionPane.showMessageDialog(null, "FICHERO DESCARGADO");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
        });

        botonCargar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser f = new JFileChooser();
                f.setFileSelectionMode(JFileChooser.FILES_ONLY);
                f.setDialogTitle("Selecciona el Fichero a SUBIR AL SERVIDOR DE FICHEROS");
                int returnVal = f.showDialog(f, "Cargar");
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = f.getSelectedFile();
                    String archivo = file.getAbsolutePath();
                    String nombreArchivo = file.getName();
                    BufferedInputStream in;
                    //leer fichero y almacenarlo en array de bytes
                    try {
                        in = new BufferedInputStream(new FileInputStream(archivo));
                        long bytes = file.length();
                        byte[] buff = new byte[(int) bytes];
                        int i, j = 0;
                        //leer fichero y almacenarlo en array de bytes
                        while ((i = in.read()) != -1) {
                            buff[j] = (byte) i; //carga datos en el array
                            j++;
                        }
                        in.close(); // cerrar stream de entrada
                        //Crear objeto EnviaFichero con los bytes del fichero,
                        //el nombre y el directorio donde se cargará
                        EnviaFichero ff = new EnviaFichero(buff, nombreArchivo, direcSelec);
                        //se envia al servidor
                        outObjeto.writeObject(ff);
                        JOptionPane.showMessageDialog(null, "FICHERO CARGADO");
                        //obtengo de nuevo la lista de ficheros
                        nodo = (EstructuraFicheros) inObjeto.readObject();
                        EstructuraFicheros[] lista = nodo.getLista();
                        direcSelec = nodo.getPath();
                        llenarLista(lista, nodo.getNumFich());
                        campo2.setText("Número de ficheros en el directorio: " + lista.length);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    } catch (ClassNotFoundException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        });
    } //constructor

    public void run() {
        try {
            cab.setText("Conectando con el servidor ........");
            // OBTENER DIRECTORIO RAIZ
            Raiz = (EstructuraFicheros) inObjeto.readObject();
            EstructuraFicheros[] nodos = Raiz.getLista(); //lista de ficheros
            direcSelec = Raiz.getPath(); //directorio actual
            llenarLista(nodos, Raiz.getNumerich());
            cab3.setText("RAIZ: " + direcSelec);
            cab.setText("CONECTADO AL SERVIDOR DE FICHEROS");
            campo2.setText("Número de ficheros en el directorio: " + Raiz.getNumerich());
        } catch (IOException e1) {
            e1.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
            System.exit(1);
        }
    } //fin run

    private static void llenarLista(EstructuraFicheros[] files, int numero) {
        if (numero == 0) return;
        DefaultListModel modeloLista = new DefaultListModel();
        listaDirec.setForeground(Color.blue);
        Font fuente = new Font("Courier", Font.PLAIN, 12);
        listaDirec.setFont(fuente);
        listaDirec.removeAll();
        for (int i = 0; i < files.length; i++) {
            modeloLista.addElement(files[i]);
        }
        try {
            listaDirec.setModel(modeloLista);
        } catch (NullPointerException n) {}
    } // Fin llenarLista
}