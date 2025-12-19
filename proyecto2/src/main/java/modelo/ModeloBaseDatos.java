package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

/**
 * Clase encargada de la gestión de la base de datos.
 * Maneja la conexión, consultas y actualizaciones en la base de datos MySQL.
 */
public class ModeloBaseDatos {
    private final static String url = "jdbc:mysql://13.62.51.110:3306/serwo?useSSL=false&serverTimezone=UTC";
    private final static String usuario = "appuser";
    private final static String password = "mariaenmiami";
    public static Connection conexion;

    /**
     * Cierra la conexión actual con la base de datos si está abierta.
     */
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la instancia única de la conexión a la base de datos (Singleton).
     * Si no existe o está cerrada, crea una nueva.
     *
     * @return Objeto Connection activo.
     */
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(url, usuario, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    /**
     * Ejecuta una consulta SQL de selección (SELECT).
     *
     * @param consulta La sentencia SQL a ejecutar.
     * @return ResultSet con los resultados de la consulta, o null si ocurre un
     *         error.
     */
    public ResultSet getConsulta(String consulta) {
        try {
            return getConexion().createStatement().executeQuery(consulta);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Ejecuta una actualización en la base de datos (INSERT, UPDATE, DELETE) con
     * parámetros preparados.
     *
     * @param consulta   La sentencia SQL preparada (con ?).
     * @param parametros Lista de parámetros (Strings) para sustituir en la
     *                   consulta.
     * @return El número de filas afectadas, o -1 si ocurre un error.
     */
    public int ejecutarActualizacion(String consulta, ArrayList<String> parametros) {
        try {
            PreparedStatement pstmt = getConexion().prepareStatement(consulta);
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setString(i + 1, parametros.get(i));
            }
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Obtiene todos los registros de una tabla específica.
     *
     * @param tabla Nombre de la tabla.
     * @return ResultSet con todos los registros.
     */
    public ResultSet getTabla(String tabla) {
        return getConsulta("SELECT * FROM " + tabla);
    }

    public ArrayList<Especie> getEspecies() {
        ArrayList<Especie> lista = new ArrayList<>();
        try {
            ResultSet rs = getConsulta("SELECT * FROM especies");
            while (rs.next()) {
                lista.add(new Especie(rs.getInt("especie_id"), rs.getString("nombre_especies")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Cuidador> getCuidadores() {
        ArrayList<Cuidador> lista = new ArrayList<>();
        try {
            ResultSet rs = getConsulta("SELECT * FROM cuidadores");
            while (rs.next()) {
                lista.add(new Cuidador(rs.getInt("cuidador_id"), rs.getString("nombre_cuidadores"),
                        rs.getString("direccion_cuidadores")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Recinto> getRecintos() {
        ArrayList<Recinto> lista = new ArrayList<>();
        try {
            ResultSet rs = getConsulta("SELECT * FROM recintos");
            if (rs != null) {
                while (rs.next()) {
                    lista.add(new Recinto(rs.getInt("recinto_id"), rs.getString("nombre_recintos"),
                            rs.getString("direccion_recintos"), rs.getInt("cantidad_origen"),
                            rs.getInt("cantidad_destino")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<Animal> getAnimales() {
        ArrayList<Animal> lista = new ArrayList<>();
        try {
            ResultSet rs = getConsulta("SELECT * FROM animales");
            if (rs != null) {
                while (rs.next()) {
                    lista.add(new Animal(rs.getInt("animal_id"), rs.getString("nombre_animales"),
                            rs.getString("tipo"), rs.getInt("especie_id"), rs.getInt("cuidador_id")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public ArrayList<String> getNombresColumnas(String tabla) {
        ArrayList<String> columnas = new ArrayList<>();
        try {
            ResultSet rs = getTabla(tabla);
            if (rs != null) {
                ResultSetMetaData metaData = (ResultSetMetaData) rs.getMetaData();
                int cantidadColumnas = metaData.getColumnCount();
                for (int i = 1; i <= cantidadColumnas; i++) {
                    columnas.add(metaData.getColumnName(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnas;
    }

    public boolean esCampoFK(String nombreCampo) {
        return nombreCampo.endsWith("_id") && !nombreCampo.equals("animal_id")
                && !nombreCampo.equals("especie_id") && !nombreCampo.equals("recinto_id")
                && !nombreCampo.equals("cuidador_id");
    }

    public boolean validarUsuario(String correo, String password) {
        boolean valido = false;
        try {
            PreparedStatement pstmt = getConexion().prepareStatement(
                    "SELECT * FROM usuarios WHERE correo = ? AND password = ?");
            pstmt.setString(1, correo);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                valido = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return valido;
    }

    public boolean existeRegistro(String sql, ArrayList<String> parametros) {
        try {
            PreparedStatement pstmt = getConexion().prepareStatement(sql);
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setString(i + 1, parametros.get(i));
            }
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registrarUsuario(String correo, String nombre, String password, String claveCorreo) {
        String sql = "INSERT INTO usuarios (email, nombre_usuario, contrasena, clave_correo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = getConexion().prepareStatement(sql);
            pstmt.setString(1, correo);
            pstmt.setString(2, nombre);
            pstmt.setString(3, password);
            pstmt.setString(4, claveCorreo);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertarArchivo(String nombre, String directorio, String extension, String tipo, Integer id_padre,
            String email_usuario) {
        String sql = "INSERT INTO archivos (nombre_archivo, directorio, extension, tipo, id_padre, email_usuario) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = getConexion().prepareStatement(sql);
            pstmt.setString(1, nombre);
            pstmt.setString(2, directorio);
            pstmt.setString(3, extension);
            pstmt.setString(4, tipo);
            if (id_padre == null) {
                pstmt.setNull(5, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(5, id_padre);
            }
            pstmt.setString(6, email_usuario);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Integer obtenerIdPadre(String rutaActual) {
        if (rutaActual.equals("/")) {
            return null;
        }
        Integer idPadre = null;
        String consulta = "SELECT id_archivo FROM archivos WHERE directorio = ?";
        try {
            PreparedStatement pstmt = getConexion().prepareStatement(consulta);
            pstmt.setString(1, rutaActual);
            ResultSet rs = pstmt.executeQuery();
            if (rs != null && rs.next()) {
                idPadre = rs.getInt("id_archivo");
                if (rs.wasNull()) {
                    idPadre = null;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idPadre;
    }

    public String obtenerEmailPorUsuario(String nombreUsuario) {
        String email = null;
        String sql = "SELECT email FROM usuarios WHERE nombre_usuario = ?";
        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                email = rs.getString("email");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    public boolean eliminarUsuario(String email) {
        String sql = "DELETE FROM usuarios WHERE email = ?s";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void eliminarArchivo(String nombreArchivo, String ruta) {
        String sql = "DELETE FROM archivos WHERE nombre_archivo = ? AND directorio = ?";
        try (PreparedStatement pstmt = getConexion().prepareStatement(sql)) {
            pstmt.setString(1, nombreArchivo);
            pstmt.setString(2, ruta);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean renombrarArchivoSQL(String nombreActual, String nuevoNombre, String ruta) {
        String sql = "UPDATE archivos SET nombre_archivo = ? WHERE nombre_archivo = ? AND directorio = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setString(2, nombreActual);
            ps.setString(3, ruta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean renombrarArchivo(String nombreActual, String nuevoNombre, String ruta, String extension) {
        String sql = "UPDATE archivos SET nombre_archivo = ?, extension = ? WHERE nombre_archivo = ? AND directorio = ?;";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setString(2, extension);
            ps.setString(3, nombreActual);
            ps.setString(4, ruta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean renombrarCarpeta(String nombreActual, String nuevoNombre, String ruta) {
        String sql = "UPDATE archivos SET nombre_archivo = ? WHERE nombre_archivo = ?;";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setString(2, nombreActual);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
