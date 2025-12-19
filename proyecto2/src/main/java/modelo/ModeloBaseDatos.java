package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

/**
 * Clase encargada de la gestión de la base de datos. Maneja la conexión,
 * consultas y actualizaciones en la base de datos MySQL.
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

    /**
     * Obtiene todas las especies registradas en la base de datos.
     *
     * @return Lista de objetos Especie con todos los registros de la tabla
     *         especies.
     */
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

    /**
     * Obtiene todos los cuidadores registrados en la base de datos.
     *
     * @return Lista de objetos Cuidador con todos los registros de la tabla
     *         cuidadores.
     */
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

    /**
     * Obtiene todos los recintos registrados en la base de datos.
     *
     * @return Lista de objetos Recinto con todos los registros de la tabla
     *         recintos.
     */
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

    /**
     * Obtiene todos los animales registrados en la base de datos.
     *
     * @return Lista de objetos Animal con todos los registros de la tabla
     *         animales.
     */
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

    /**
     * Obtiene los nombres de todas las columnas de una tabla específica.
     *
     * @param tabla Nombre de la tabla de la cual obtener las columnas.
     * @return Lista con los nombres de todas las columnas de la tabla.
     */
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

    /**
     * Verifica si un campo es una clave foránea (Foreign Key).
     *
     * @param nombreCampo Nombre del campo a verificar.
     * @return true si el campo es una clave foránea, false en caso contrario.
     */
    public boolean esCampoFK(String nombreCampo) {
        return nombreCampo.endsWith("_id") && !nombreCampo.equals("animal_id")
                && !nombreCampo.equals("especie_id") && !nombreCampo.equals("recinto_id")
                && !nombreCampo.equals("cuidador_id");
    }

    /**
     * Valida las credenciales de un usuario.
     *
     * @param correo   Correo electrónico del usuario.
     * @param password Contraseña del usuario.
     * @return true si las credenciales son válidas, false en caso contrario.
     */
    public boolean validarUsuario(String correo, String password) {
        boolean valido = false;
        try {
            PreparedStatement pstmt = getConexion().prepareStatement(
                    "SELECT * FROM usuarios WHERE email = ? AND contrasena = ?");
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

    /**
     * Verifica si existe al menos un registro que cumpla con la consulta SQL
     * proporcionada.
     *
     * @param sql        Consulta SQL preparada (con ?).
     * @param parametros Lista de parámetros para la consulta.
     * @return true si existe al menos un registro, false en caso contrario.
     */
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

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param correo      Correo electrónico del usuario.
     * @param nombre      Nombre del usuario.
     * @param password    Contraseña del usuario.
     * @param claveCorreo Clave de correo del usuario.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
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

    /**
     * Inserta un nuevo archivo o carpeta en la base de datos.
     *
     * @param nombre        Nombre del archivo o carpeta.
     * @param directorio    Ruta completa del directorio.
     * @param extension     Extensión del archivo (vacío para carpetas).
     * @param tipo          Tipo de elemento ("File" o "Folder").
     * @param id_padre      ID del directorio padre (null si está en raíz).
     * @param email_usuario Email del usuario propietario.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
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

    /**
     * Obtiene el ID del directorio padre basándose en la ruta actual.
     *
     * @param rutaActual Ruta del directorio actual.
     * @return ID del archivo padre, o null si está en la raíz.
     */
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

    /**
     * Obtiene el email de un usuario basándose en su nombre de usuario.
     *
     * @param nombreUsuario Nombre del usuario.
     * @return Email del usuario, o null si no se encuentra.
     */
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

    /**
     * Elimina un usuario de la base de datos.
     *
     * @param email Email del usuario a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     */
    public boolean eliminarUsuario(String email) {
        String sql = "DELETE FROM usuarios WHERE email = ?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un archivo de la base de datos.
     *
     * @param nombreArchivo Nombre del archivo a eliminar.
     * @param ruta          Ruta donde se encuentra el archivo.
     */
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

    /**
     * Renombra un archivo en la base de datos (solo actualiza el nombre).
     *
     * @param nombreActual Nombre actual del archivo.
     * @param nuevoNombre  Nuevo nombre para el archivo.
     * @param ruta         Ruta donde se encuentra el archivo.
     * @return true si el renombrado fue exitoso, false en caso contrario.
     */
    public boolean renombrarArchivoSQL(String nombreActual, String nuevoNombre, String ruta) {
        String sql = "UPDATE archivos SET nombre_archivo = ? WHERE nombre_archivo = ? AND directorio = ?";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setString(2, nombreActual);
            ps.setString(3, ruta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Renombra un archivo en la base de datos actualizando nombre y extensión.
     *
     * @param nombreActual Nombre actual del archivo.
     * @param nuevoNombre  Nuevo nombre para el archivo.
     * @param ruta         Ruta donde se encuentra el archivo.
     * @param extension    Nueva extensión del archivo.
     * @return true si el renombrado fue exitoso, false en caso contrario.
     */
    public boolean renombrarArchivo(String nombreActual, String nuevoNombre, String ruta, String extension) {
        String sql = "UPDATE archivos SET nombre_archivo = ?, extension = ? WHERE nombre_archivo = ? AND directorio = ?;";
        try (PreparedStatement ps = getConexion().prepareStatement(sql)) {
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

    /**
     * Renombra una carpeta y actualiza recursivamente todas las rutas de archivos
     * y subcarpetas contenidos.
     * 
     * @param nombreActual El nombre actual de la carpeta.
     * @param nuevoNombre  El nuevo nombre para la carpeta.
     * @param rutaPadre    La ruta del directorio padre donde se encuentra la
     *                     carpeta.
     * @return true si el renombrado fue exitoso, false en caso contrario.
     */
    public boolean renombrarCarpeta(String nombreActual, String nuevoNombre, String rutaPadre) {
        try {
            String rutaAntigua = rutaPadre.equals("/") ? "/" + nombreActual : rutaPadre + "/" + nombreActual;
            String rutaNueva = rutaPadre.equals("/") ? "/" + nuevoNombre : rutaPadre + "/" + nuevoNombre;

            getConexion().setAutoCommit(false);

            String sqlCarpeta = "UPDATE archivos SET nombre_archivo = ?, directorio = ? WHERE nombre_archivo = ? AND directorio = ?";
            try (PreparedStatement psCarpeta = getConexion().prepareStatement(sqlCarpeta)) {
                psCarpeta.setString(1, nuevoNombre);
                psCarpeta.setString(2, rutaNueva);
                psCarpeta.setString(3, nombreActual);
                psCarpeta.setString(4, rutaAntigua);
                psCarpeta.executeUpdate();
            }

            String sqlHijos = "UPDATE archivos SET directorio = REPLACE(directorio, ?, ?) WHERE directorio LIKE ?";
            try (PreparedStatement psHijos = getConexion().prepareStatement(sqlHijos)) {
                psHijos.setString(1, rutaAntigua + "/");
                psHijos.setString(2, rutaNueva + "/");
                psHijos.setString(3, rutaAntigua + "/%");
                psHijos.executeUpdate();
            }

            getConexion().commit();
            getConexion().setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                getConexion().rollback();
                getConexion().setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }

}
