package modelo;

import org.apache.commons.net.ftp.FTPClient;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

public class ModeloClienteFTP {
    private FTPClient cliente;
    private String servidor = "13.62.51.110";
    private int puerto = 21;

    // --- CONFIGURACIÓN DE RED ---
    // NOTA: En Java, para escribir "\\" tienes que poner "\\\\"
    private static final String RUTA_REMOTA = "\\\\13.62.51.110\\FileZillaFTP";
    private static final String RUTA_XML = RUTA_REMOTA + "\\FileZilla Server.xml";

    // DATOS DE WINDOWS DE LA MÁQUINA VIRTUAL (Para poder entrar en la carpeta)
    private static final String USUARIO_WINDOWS_VM = "Administrator";
    private static final String PASS_WINDOWS_VM = "-riMth%@$GAW2NmZVsjKG@px.gxfflrx";

    public ModeloClienteFTP() {
        cliente = new FTPClient();
    }

    // ... (Tus métodos de conectar y desconectar FTP siguen igual) ...

    public void establecerConexion() throws IOException {
        if (!cliente.isConnected()) {
            cliente.enterLocalPassiveMode();
            cliente.connect(servidor, puerto);
        }
    }

    public void desconectar() {
        try {
            if (cliente.isConnected()) {
                cliente.logout();
                cliente.disconnect();
            }
        } catch (Exception e) {
        }
    }

    // --- MÉTODO MÁGICO PARA CONECTAR SIN UNIDAD Z ---
    private void conectarCarpetaCompartida() {
        try {
            // Este comando hace un "login" silencioso en la carpeta de red sin crear unidad
            // Z
            String comando = "net use \"" + RUTA_REMOTA + "\" /user:" + USUARIO_WINDOWS_VM + " " + PASS_WINDOWS_VM;
            Process p = Runtime.getRuntime().exec(comando);
            p.waitFor(); // Esperar a que se conecte
            System.out.println("Conexión a carpeta compartida establecida.");
        } catch (Exception e) {
            System.err.println("No se pudo conectar a la carpeta de red: " + e.getMessage());
        }
    }

    public void añadirUsuario(String nombre, String password) {

        // 1. PRIMERO NOS AUTENTICAMOS EN LA CARPETA
        conectarCarpetaCompartida();

        try {
            // Ahora Java ya tiene permiso para ver ese archivo lejano
            File xmlFile = new File(RUTA_XML);

            if (!xmlFile.exists()) {
                System.err.println("ERROR: No encuentro el archivo en: " + RUTA_XML);
                System.err.println("Asegúrate de que la carpeta 'FileZillaFTP' está compartida en la VM.");
                return;
            }

            // ... (A PARTIR DE AQUÍ TODO ES IGUAL QUE ANTES) ...

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList usersList = doc.getElementsByTagName("Users");
            Node usersNode = usersList.item(0);

            Element newUser = doc.createElement("User");
            newUser.setAttribute("Name", nombre);

            Element passOption = doc.createElement("Option");
            passOption.setAttribute("Name", "Pass");
            passOption.setTextContent(md5(password));
            newUser.appendChild(passOption);

            // Opciones obligatorias
            agregarOpcion(doc, newUser, "Group", "");
            agregarOpcion(doc, newUser, "Bypass server userlimit", "0");
            agregarOpcion(doc, newUser, "User Limit", "0");
            agregarOpcion(doc, newUser, "IP Limit", "0");
            agregarOpcion(doc, newUser, "Enabled", "1");
            agregarOpcion(doc, newUser, "Comments", "Creado sin unidad Z");
            agregarOpcion(doc, newUser, "ForceSsl", "0");

            // RUTA HOME (Cuidado, esta ruta es la ruta INTERNA de la VM)
            String carpetaHome = "C:\\xampp\\htdocs\\" + nombre;

            Element permissions = doc.createElement("Permissions");
            Element permission = doc.createElement("Permission");
            permission.setAttribute("Dir", carpetaHome);

            // Permisos full
            agregarOpcion(doc, permission, "FileRead", "1");
            agregarOpcion(doc, permission, "FileWrite", "1");
            agregarOpcion(doc, permission, "FileDelete", "1");
            agregarOpcion(doc, permission, "DirCreate", "1");
            agregarOpcion(doc, permission, "DirDelete", "1");
            agregarOpcion(doc, permission, "DirList", "1");
            agregarOpcion(doc, permission, "DirSubdirs", "1");
            agregarOpcion(doc, permission, "IsHome", "1");
            agregarOpcion(doc, permission, "AutoCreate", "1");

            permissions.appendChild(permission);
            newUser.appendChild(permissions);
            usersNode.appendChild(newUser);

            // Guardar cambios
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("Usuario añadido correctamente al XML remoto.");

            // NOTA: No podemos recargar el servidor automáticamente porque estamos en red.
            // Tendrás que recargarlo manualmente en la VM o esperar a que FileZilla lo
            // detecte.

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void agregarOpcion(Document doc, Element parent, String name, String value) {
        Element opt = doc.createElement("Option");
        opt.setAttribute("Name", name);
        opt.setTextContent(value);
        parent.appendChild(opt);
    }

    private String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public FTPClient getCliente() {
        return cliente;
    }
}