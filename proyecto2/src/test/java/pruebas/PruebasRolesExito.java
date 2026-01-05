package pruebas;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import modelo.ModeloBaseDatos;
import modelo.Rol;

/**
 * Pruebas parametrizadas para operaciones EXITOSAS de asignacion de roles.
 * Verifica que las operaciones de asignar y desasignar roles funcionen
 * correctamente con datos validos.
 */
@RunWith(Parameterized.class)
public class PruebasRolesExito {

    private static ModeloBaseDatos modeloDB;

    private String correo;
    private Rol rol;
    private String accion;

    /**
     * Constructor para la prueba parametrizada.
     * 
     * @param correo Correo del usuario
     * @param rol    Rol a asignar o desasignar
     * @param accion Accion a realizar (ASIGNAR o DESASIGNAR)
     */
    public PruebasRolesExito(String correo, Rol rol, String accion) {
        this.correo = correo;
        this.rol = rol;
        this.accion = accion;
    }

    /**
     * Inicializa la conexion a la base de datos antes de ejecutar las pruebas.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();
    }

    /**
     * Cierra la conexion a la base de datos despues de ejecutar todas las pruebas.
     */
    @AfterClass
    public static void tearDownAfterClass() {
        if (modeloDB != null) {
            modeloDB.cerrarConexion();
        }
    }

    /**
     * Proporciona los datos de prueba para operaciones exitosas de asignacion de
     * roles.
     * 
     * @return Coleccion de arrays con datos de prueba validos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "admin@admin", new Rol(1, "Admin", "Administrador del sistema"), "ASIGNAR" },
                { "pablo.pruebas.mail@gmail.com", new Rol(2, "Usuario", "Usuario normal"), "ASIGNAR" },
                { "admin@admin", new Rol(1, "Admin", "Administrador del sistema"), "DESASIGNAR" },
                { "pablo.pruebas.mail@gmail.com", new Rol(2, "Usuario", "Usuario normal"), "DESASIGNAR" }
        });
    }

    /**
     * Verifica que las operaciones de asignar y desasignar roles funcionen
     * correctamente con datos validos.
     */
    @Test
    public void testOperacionRolExitosa() {
        System.out.println("Ejecutando " + accion + " rol " + rol.getNombre_roles() + " para " + correo);

        try {
            String sqlComprobarAntes = "SELECT * FROM usuarios_roles WHERE email_usuario = ? AND roles_id = ?;";
            boolean existeAntes = modeloDB.existeRegistro(sqlComprobarAntes,
                    new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))));

            if (accion.equals("ASIGNAR")) {
                if (!existeAntes) {
                    String sqlAsignar = "INSERT INTO usuarios_roles (email_usuario, roles_id) VALUES (?, ?);";
                    int filas = modeloDB.ejecutarActualizacion(sqlAsignar,
                            new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))));
                    assertTrue("La asignacion deberia ejecutarse correctamente", filas > 0);
                }

                boolean existeDespues = modeloDB.existeRegistro(sqlComprobarAntes,
                        new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))));
                assertTrue("El rol deberia estar asignado al usuario", existeDespues);

            } else if (accion.equals("DESASIGNAR")) {
                if (existeAntes) {
                    String sqlDesasignar = "DELETE FROM usuarios_roles WHERE email_usuario = ? AND roles_id = ?;";
                    int filas = modeloDB.ejecutarActualizacion(sqlDesasignar,
                            new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))));
                    assertTrue("La desasignacion deberia ejecutarse correctamente", filas > 0);
                }

                boolean existeDespues = modeloDB.existeRegistro(sqlComprobarAntes,
                        new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))));
                assertTrue("El rol deberia estar desasignado del usuario", !existeDespues);
            }
        } catch (Exception e) {
            assertTrue("No deberia lanzar excepcion: " + e.getMessage(), false);
        }
    }
}
