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

import controladorLogs.GestionLogs;
import modelo.ModeloBaseDatos;
import modelo.ModeloClienteFTP;
import modelo.Log;
import modelo.MoTextos;
import servidor.FileManager;
import vista.VistaGestorArchivos;
import vista.VistaMenuPrincipal;

/**
 * Oyente que gestiona las operaciones de archivos en la vista del Gestor de
 * Archivos.
 * Maneja subidas, descargas, creación y eliminación de archivos y carpetas.
 */
public class OyenteArchivos implements ActionListener {

    private VistaGestorArchivos vista;
    private ModeloClienteFTP client;
    private ModeloBaseDatos db;
    private VistaMenuPrincipal menu;
    private FileManager ftp;
    private String rutaActual = "/";

    /**
     * Constructor del oyente de archivos.
     *
     * @param vista  Vista del gestor de archivos.
     * @param client Cliente FTP.
     * @param db     Modelo de base de datos.
     * @param menu   Vista del menú principal.
     */
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

    /**
     * Inicializa el gestor de archivos con las credenciales actuales del usuario.
     */
    public void inicializarFileManager() {
        this.ftp = new FileManager("13.62.51.110", 21, client.getUser(), client.getPass());
        actualizarListaFTP();
    }

    /**
     * Maneja los eventos de los botones en la vista del gestor de archivos.
     *
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == vista.getBotonSubida()) {
            accionBotonSubida();
        } else if (source == vista.getBotonDescarga()) {
            accionBotonDescarga();
        } else if (source == vista.getBotonEliminar()) {
            accionBotonEliminar();
        } else if (source == vista.getBotonCrearCarpeta()) {
            accionBotonCrearCarpeta();
        } else if (source == vista.getBotonBorrarCarpeta()) {
            accionBotonBorrarCarpeta();
        } else if (source == vista.getBotonRenombrar()) {
            accionBotonRenombrar();
        } else if (source == vista.getBotonVolver()) {
            accionBotonVolver();
        } else if (source == vista.getBotonVolverMenuPrincipal()) {
            accionBotonVolverMenuPrincipal();
        }
    }

    /**
     * Verifica si el usuario actual tiene permiso para realizar una acción
     * específica sobre un archivo o carpeta.
     *
     * @param ruta   La ruta del archivo o carpeta.
     * @param accion La acción a realizar (e.g., "Subir archivos").
     * @return true si tiene permiso, false en caso contrario.
     */
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

        if (accion.equals("Renombrar")) {
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

    /**
     * Acción para subir un archivo al servidor FTP.
     * Abre un selector de archivos y gestiona la subida si hay permisos.
     */
    public void accionBotonSubida() {
        if (!verificarPermiso(rutaActual, "Subir archivos")) {
            JOptionPane.showMessageDialog(null, MoTextos.msg_permission_denied_upload,
                    MoTextos.msg_permission_denied_title,
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

        fc.setDialogTitle(MoTextos.title_select_upload);
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
            GestionLogs.writeLog(new Log("Upload, file uploaded " + nombreArchivo, "", true));
            actualizarListaFTP();
        }
    }

    /**
     * Acción para descargar un archivo o carpeta del servidor FTP.
     */
    public void accionBotonDescarga() {
        JFileChooser fc = new JFileChooser();
        File carpeta;
        FTPFile select = vista.getListaArchivos().getSelectedValue();

        if (select == null) {
            JOptionPane.showMessageDialog(null, MoTextos.msg_select_file, MoTextos.msg_error_title,
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String rutaArchivo = rutaActual.equals("/") ? "/" + select.getName() : rutaActual + "/" + select.getName();
        if (!verificarPermiso(rutaArchivo, "Descargar archivos")) {
            JOptionPane.showMessageDialog(null, MoTextos.msg_permission_denied_download,
                    MoTextos.msg_permission_denied_title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        fc.setDialogTitle(MoTextos.title_select_download);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int respuesta = fc.showDialog(fc, "OK");
        if (respuesta == JFileChooser.APPROVE_OPTION) {
            carpeta = fc.getSelectedFile();
            ftp.descargarArchivo(select, carpeta.getAbsolutePath(), rutaActual);
            GestionLogs.writeLog(new Log("Download, file downloaded " + select.getName(), "", true));
        }
    }

    /**
     * Acción para eliminar un archivo del servidor FTP.
     * Verifica permisos antes de proceder.
     */
    public void accionBotonEliminar() {
        FTPFile select = vista.getListaArchivos().getSelectedValue();
        if (select != null) {
            String rutaArchivo = rutaActual.equals("/") ? "/" + select.getName() : rutaActual + "/" + select.getName();
            if (!verificarPermiso(rutaArchivo, "Borrar archivos")) {
                JOptionPane.showMessageDialog(null, MoTextos.msg_permission_denied_delete,
                        MoTextos.msg_permission_denied_title,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            ftp.borrarArchivo(select, rutaActual);
            db.eliminarArchivo(select.getName(), rutaActual);
            actualizarListaFTP();
            GestionLogs.writeLog(new Log("Delete, file deleted " + select.getName(), "", true));
        } else {
            JOptionPane.showMessageDialog(null, MoTextos.msg_select_file, MoTextos.msg_error_title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Acción para crear una nueva carpeta en el servidor FTP.
     * Solicita el nombre de la carpeta y verifica permisos.
     */
    public void accionBotonCrearCarpeta() {
        if (!verificarPermiso(rutaActual, "Crear carpeta")) {
            JOptionPane.showMessageDialog(null, MoTextos.msg_permission_denied_create_folder,
                    MoTextos.msg_permission_denied_title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombreCarpeta;
        Integer idPadre;
        String emailUsuario;
        String directorioServidor;
        nombreCarpeta = JOptionPane.showInputDialog(null, MoTextos.msg_enter_folder_name, "");
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
            GestionLogs.writeLog(new Log("Create, folder created " + nombreCarpeta, "", true));
        } else {
            JOptionPane.showMessageDialog(null, MoTextos.msg_enter_folder_name, MoTextos.msg_error_title,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Acción para borrar una carpeta del servidor FTP.
     * Verifica que sea un directorio y que el usuario tenga permisos.
     */
    public void accionBotonBorrarCarpeta() {
        FTPFile select = vista.getListaArchivos().getSelectedValue();
        String rutaCarpeta;

        if (select == null) {
            JOptionPane.showMessageDialog(null, MoTextos.msg_select_folder, MoTextos.msg_error_title,
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        rutaCarpeta = rutaActual.equals("/") ? "/" + select.getName() : rutaActual + "/" + select.getName();

        if (!verificarPermiso(rutaCarpeta, "Borrar carpeta")) {
            JOptionPane.showMessageDialog(null, MoTextos.msg_permission_denied_delete_folder,
                    MoTextos.msg_permission_denied_title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!select.isDirectory()) {
            JOptionPane.showMessageDialog(null, MoTextos.msg_select_folder_not_file,
                    MoTextos.msg_error_title,
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        db.eliminarArchivo(select.getName(), rutaCarpeta);
        ftp.borrarCarpeta(select.getName(), rutaActual);
        GestionLogs.writeLog(new Log("Delete, folder deleted " + select.getName(), "", true));
        actualizarListaFTP();
    }

    /**
     * Acción para volver al directorio padre en la navegación FTP.
     */
    public void accionBotonVolver() {
        String rutaPadre;
        if (!rutaActual.equals("/")) {
            rutaPadre = rutaActual.substring(0, rutaActual.lastIndexOf('/'));
            if (rutaPadre.isEmpty())
                rutaPadre = "/";
            actualizarListaFTP(rutaPadre);
        }
    }

    /**
     * Acción para volver al menú principal desde el gestor de archivos.
     */
    public void accionBotonVolverMenuPrincipal() {
        vista.setVisible(false);
        menu.hacerVisible();
    }

    /**
     * Actualiza la lista de archivos mostrada en la vista obteniendo el contenido
     * del servidor FTP.
     *
     * @param ruta La ruta del directorio a listar.
     */
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

    /**
     * Actualiza la lista de archivos en la ruta actual.
     */
    public void actualizarListaFTP() {
        actualizarListaFTP(this.rutaActual);
    }

    /**
     * Detecta doble clic en la lista de archivos para navegar a carpetas.
     * 
     * @param listaArchivos La lista de archivos FTP.
     */
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

    private void accionBotonRenombrar() {
        if (!verificarPermiso(rutaActual, "Renombrar")) {
            JOptionPane.showMessageDialog(null, MoTextos.msg_permission_denied_rename,
                    MoTextos.msg_permission_denied_title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        FTPFile select = vista.getListaArchivos().getSelectedValue();
        if (select == null) {
            JOptionPane.showMessageDialog(null, MoTextos.msg_select_file, MoTextos.msg_error_title,
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String nuevoNombre = JOptionPane.showInputDialog(vista, MoTextos.msg_enter_new_name, select.getName());
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, MoTextos.msg_invalid_name, MoTextos.msg_info_title,
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (select.isDirectory()) {
            if (db.renombrarCarpeta(select.getName(), nuevoNombre.trim(), rutaActual)) {
                GestionLogs.writeLog(
                        new Log("Rename, folder renamed " + select.getName() + " to " + nuevoNombre.trim(), "", true));
                ftp.renombrar(select, nuevoNombre.trim(), rutaActual);
                actualizarListaFTP();
            } else {
                JOptionPane.showMessageDialog(vista, MoTextos.msg_rename_canceled, MoTextos.msg_info_title,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            String extension = "";
            if (nuevoNombre.contains(".")) {
                extension = nuevoNombre.substring(nuevoNombre.lastIndexOf(".") + 1);
            }
            if (db.renombrarArchivo(select.getName(), nuevoNombre.trim(), rutaActual, extension)) {
                GestionLogs.writeLog(
                        new Log("Rename, file renamed " + select.getName() + " to " + nuevoNombre.trim(), "", true));
                ftp.renombrar(select, nuevoNombre.trim(), rutaActual);
                actualizarListaFTP();
            } else {
                JOptionPane.showMessageDialog(vista, MoTextos.msg_rename_canceled, MoTextos.msg_info_title,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
