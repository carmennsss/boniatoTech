package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JList;

import org.apache.commons.net.ftp.FTPFile;

import modelo.ModeloBaseDatos;
import modelo.ModeloClienteFTP;
import servidor.FileManager;
import vista.VistaGestorArchivos;
import vista.VistaMenuPrincipal;

public class OyenteArchivos implements ActionListener {

    private VistaGestorArchivos vista;
    private ModeloClienteFTP client;
    private ModeloBaseDatos db;
    private VistaMenuPrincipal menu;
    private FileManager ftp;
    private String rutaActual = "/";

    public OyenteArchivos(VistaGestorArchivos vista, ModeloClienteFTP client, ModeloBaseDatos db,
            VistaMenuPrincipal menu) {
        this.vista = vista;
        this.client = client;
        this.db = db;
        this.menu = menu;
        this.ftp = new FileManager("13.62.51.110", 21, client.getUser(), client.getPass());

        inicializarFileManager();
        detectarDobleClick(vista.getListaArchivos());
    }

    public void inicializarFileManager() {
        this.ftp = new FileManager("13.62.51.110", 21, client.getUser(), client.getPass());
        actualizarListaFTP();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Upload":
                accionBotonSubida();
                break;
            case "Download":
                accionBotonDescarga();
                break;
            case "Delete":
                accionBotonEliminar();
                break;
            case "New Folder":
                accionBotonCrearCarpeta();
                break;
            case "Delete Folder":
                accionBotonBorrarCarpeta();
                break;
            case "Back":
                accionBotonVolver();
                break;
            case "Main Menu":
                accionBotonVolverMenuPrincipal();
                break;
        }
    }

    public boolean verificarPermiso(String ruta, String accion) {
        String emailUsuario = db.obtenerEmailPorUsuario(client.getUser());

        String sqlPropietario = "SELECT * FROM archivos WHERE email_usuario = ? AND directorio = ?";
        try {
            if (db.existeRegistro(sqlPropietario, new ArrayList<>(Arrays.asList(emailUsuario, ruta)))) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        String sqlPermisos = "SELECT * FROM usuarios_roles ur " +
                "JOIN roles_permisos rp ON ur.roles_id = rp.roles_id " +
                "JOIN permisos p ON rp.permisos_id = p.id_permisos " +
                "WHERE ur.email_usuario = ? AND p.nombre_permisos = ?";

        try {
            return db.existeRegistro(sqlPermisos, new ArrayList<>(Arrays.asList(emailUsuario, accion)));
        } catch (Exception e) {
            return false;
        }
    }

    public void accionBotonSubida() {
        if (!verificarPermiso(rutaActual, "Subir archivos")) {
            JOptionPane.showMessageDialog(null, "No tienes permiso para subir archivos.", "Permiso denegado",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fc = new JFileChooser();
        File file;
        String archivo;
        String nombreArchivo;
        String extension;
        Integer idPadre;
        String tipo;
        String emailUsuario;

        fc.setDialogTitle("Select the file to upload");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int respuesta = fc.showDialog(fc, "OK");
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            file = fc.getSelectedFile();
            archivo = file.getAbsolutePath();
            nombreArchivo = file.getName();
            extension = "";
            idPadre = db.obtenerIdPadre(rutaActual);
            tipo = "File";
            emailUsuario = db.obtenerEmailPorUsuario(client.getUser());
            if (!file.isDirectory() && nombreArchivo.contains(".")) {
                extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1);
            }
            db.insertarArchivo(nombreArchivo, rutaActual, extension, tipo, idPadre, emailUsuario);
            ftp.subirArchivo(archivo, nombreArchivo, rutaActual);
            actualizarListaFTP();
        }
    }

    public void accionBotonDescarga() {
        JFileChooser fc = new JFileChooser();
        File carpeta;
        FTPFile select = vista.getListaArchivos().getSelectedValue();

        if (select == null) {
            JOptionPane.showMessageDialog(null, "Please select a file", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String rutaArchivo = rutaActual.equals("/") ? "/" + select.getName() : rutaActual + "/" + select.getName();
        if (!verificarPermiso(rutaArchivo, "Descargar archivos")) {
            JOptionPane.showMessageDialog(null, "No tienes permiso para descargar este archivo.", "Permiso denegado",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        fc.setDialogTitle("Select where to download the file");
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int respuesta = fc.showDialog(fc, "OK");
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            carpeta = fc.getSelectedFile();
            ftp.descargarArchivo(select, carpeta.getAbsolutePath(), rutaActual);
        }
    }

    public void accionBotonEliminar() {
        FTPFile select = vista.getListaArchivos().getSelectedValue();
        if (select != null) {
            String rutaArchivo = rutaActual.equals("/") ? "/" + select.getName() : rutaActual + "/" + select.getName();
            if (!verificarPermiso(rutaArchivo, "Borrar archivos")) {
                JOptionPane.showMessageDialog(null, "No tienes permiso para borrar este archivo.", "Permiso denegado",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ftp.borrarArchivo(select, rutaActual);
            db.eliminarArchivo(select.getName(), rutaActual);
            actualizarListaFTP();
        } else {
            JOptionPane.showMessageDialog(null, "Please select a file", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void accionBotonCrearCarpeta() {
        if (!verificarPermiso(rutaActual, "Crear carpeta")) {
            JOptionPane.showMessageDialog(null, "No tienes permiso para crear carpetas.", "Permiso denegado",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreCarpeta;
        Integer idPadre;
        String emailUsuario;
        String directorioServidor;
        nombreCarpeta = JOptionPane.showInputDialog(null, "Enter the directory name", "");
        if (nombreCarpeta != null) {
            idPadre = db.obtenerIdPadre(rutaActual);
            emailUsuario = db.obtenerEmailPorUsuario(client.getUser());
            if (rutaActual.equals("/")) {
                directorioServidor = "/" + nombreCarpeta;
            } else {
                directorioServidor = rutaActual + "/" + nombreCarpeta;
            }
            db.insertarArchivo(nombreCarpeta, directorioServidor, "", "Folder", idPadre, emailUsuario);
            ftp.crearCarpeta(nombreCarpeta, rutaActual);
            actualizarListaFTP();
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a name for the folder", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void accionBotonBorrarCarpeta() {
        FTPFile select = vista.getListaArchivos().getSelectedValue();
        String rutaCarpeta;

        if (select == null) {
            JOptionPane.showMessageDialog(null, "Please select a folder.", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        rutaCarpeta = rutaActual.equals("/") ? "/" + select.getName() : rutaActual + "/" + select.getName();

        if (!verificarPermiso(rutaCarpeta, "Borrar carpeta")) {
            JOptionPane.showMessageDialog(null, "No tienes permiso para borrar esta carpeta.", "Permiso denegado",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!select.isDirectory()) {
            JOptionPane.showMessageDialog(null, "You must select a folder, not a file.", "Error",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        db.eliminarArchivo(select.getName(), rutaCarpeta);
        ftp.borrarCarpeta(select.getName(), rutaActual);

        actualizarListaFTP();
    }

    public void accionBotonVolver() {
        String rutaPadre;
        if (!rutaActual.equals("/")) {
            rutaPadre = rutaActual.substring(0, rutaActual.lastIndexOf('/'));
            if (rutaPadre.isEmpty())
                rutaPadre = "/";
            actualizarListaFTP(rutaPadre);
        }
    }

    public void accionBotonVolverMenuPrincipal() {
        vista.setVisible(false);
        menu.hacerVisible();
    }

    public void actualizarListaFTP(String ruta) {
        FTPFile[] archivos;
        if (this.ftp.conectar()) {
            vista.getListaModel().clear();
            archivos = ftp.listarArchivos(ruta);
            for (FTPFile archivo : archivos) {
                vista.getListaModel().addElement(archivo);
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
}
