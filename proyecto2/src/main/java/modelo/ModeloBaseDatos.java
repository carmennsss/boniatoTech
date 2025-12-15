package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

public class ModeloBaseDatos {
    private final String url = "jdbc:mysql://13.62.51.110:3306/serwo?useSSL=false&serverTimezone=UTC";
    private final String usuario = "appuser";
    private final String password = "mariaenmiami";
    private Connection conexion;

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(url, usuario, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

    public ResultSet getConsulta(String consulta) {
        try {
            return getConexion().createStatement().executeQuery(consulta);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

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
}
