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
    private String user = "";
    private String pass = "";

    public String getUser() {
        return user;
    }

    // --- CONFIGURACIÓN DE RED ---
    // NOTA: En Java, para escribir "\\" tienes que poner "\\\\"
    private static final String RUTA_REMOTA = "\\\\13.62.51.110\\FileZillaFTP";
    private static final String RUTA_XML = RUTA_REMOTA + "\\FileZilla Server.xml";

    // DATOS DE WINDOWS DE LA MÁQUINA VIRTUAL (Para poder entrar en la carpeta)
    private static final String USUARIO_WINDOWS_VM = "Administrator";
    private static final String PASS_WINDOWS_VM = "-riMth%@$GAW2NmZVsjKG@px.gxfflrx";

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

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

    public void aniadirUsuario(String nombre, String password) {

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

            // IpFilter (Necesario para que se vea igual que el resto)
            Element ipFilter = doc.createElement("IpFilter");
            ipFilter.appendChild(doc.createElement("Disallowed"));
            ipFilter.appendChild(doc.createElement("Allowed"));
            newUser.appendChild(ipFilter);

            // RUTA HOME (Cuidado, esta ruta es la ruta INTERNA de la VM)
            String carpetaHome = "C:\\Users\\Administrator\\Documents\\serwo";

            Element permissions = doc.createElement("Permissions");
            Element permission = doc.createElement("Permission");
            permission.setAttribute("Dir", carpetaHome);

            // Permisos full (Incluyendo FileAppend que faltaba)
            agregarOpcion(doc, permission, "FileRead", "1");
            agregarOpcion(doc, permission, "FileWrite", "0");
            agregarOpcion(doc, permission, "FileDelete", "0");
            agregarOpcion(doc, permission, "FileAppend", "0");
            agregarOpcion(doc, permission, "DirCreate", "0");
            agregarOpcion(doc, permission, "DirDelete", "0");
            agregarOpcion(doc, permission, "DirList", "1");
            agregarOpcion(doc, permission, "DirSubdirs", "1");
            agregarOpcion(doc, permission, "IsHome", "1");
            agregarOpcion(doc, permission, "AutoCreate", "1");

            permissions.appendChild(permission);
            newUser.appendChild(permissions);

            // SpeedLimits
            Element speedLimits = doc.createElement("SpeedLimits");
            speedLimits.setAttribute("DlLimit", "10");
            speedLimits.setAttribute("DlType", "0");
            speedLimits.setAttribute("ServerDlLimitBypass", "0");
            speedLimits.setAttribute("ServerUlLimitBypass", "0");
            speedLimits.setAttribute("UlLimit", "10");
            speedLimits.setAttribute("UlType", "0");
            speedLimits.appendChild(doc.createElement("Download"));
            speedLimits.appendChild(doc.createElement("Upload"));
            newUser.appendChild(speedLimits);
            usersList.item(0).appendChild(newUser);

            guardarXML(doc,xmlFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void guardarXML(Document doc, File xmlFile) throws Exception {
        // 1. Limpieza de nodos vacíos (espacios en blanco antiguos) para que no se
        // dupliquen
        cleanEmptyTextNodes(doc);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Configuración para una indentación perfecta
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt%7Dindent-amount", "4"); // 4 espacios
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(xmlFile);
        transformer.transform(source, result);
    }

    // AÑADE ESTE MÉTODO AUXILIAR
    // Esto es magia negra para limpiar el XML antes de guardarlo y que no se rompa
    // el formato
    private void cleanEmptyTextNodes(Node parentNode) {
        NodeList childNodes = parentNode.getChildNodes();
        for (int n = childNodes.getLength() - 1; n >= 0; n--) {
            Node child = childNodes.item(n);
            short nodeType = child.getNodeType();
            if (nodeType == Node.ELEMENT_NODE) {
                cleanEmptyTextNodes(child);
            } else if (nodeType == Node.TEXT_NODE) {
                String trimmedNodeVal = child.getNodeValue().trim();
                if (trimmedNodeVal.length() == 0) {
                    parentNode.removeChild(child);
                } else {
                    child.setNodeValue(trimmedNodeVal);
                }
            } else if (nodeType == Node.COMMENT_NODE) {
                // Opcional: Si quieres mantener comentarios, no hagas nada
            }
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