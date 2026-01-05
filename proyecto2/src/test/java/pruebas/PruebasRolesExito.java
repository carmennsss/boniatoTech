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

import controladorRoles.ControladorRoles;
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
    private static ControladorRoles controladorRoles;

    private String correo;
    private int idRol;
    private String accion;

    /**
     * Constructor para la prueba parametrizada.
     * 
     * @param correo Correo del usuario
     * @param idRol  ID del Rol a asignar o desasignar
     * @param accion Accion a realizar (ASIGNAR o DESASIGNAR)
     */
    public PruebasRolesExito(String correo, int idRol, String accion) {
        this.correo = correo;
        this.idRol = idRol;
        this.accion = accion;
    }

    /**
     * Inicializa la conexion a la base de datos antes de ejecutar las pruebas.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();
        controladorRoles = new ControladorRoles(null, modeloDB, null, null, null, null, null, null);
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
                { "admin@admin", 1, "ASIGNAR" },
                { "pablo.pruebas.mail@gmail.com", 2, "ASIGNAR" },
                { "admin@admin", 1, "DESASIGNAR" },
                { "pablo.pruebas.mail@gmail.com", 2, "DESASIGNAR" }
        });
    }

    /**
     * Verifica que las operaciones de asignar y desasignar roles funcionen
     * correctamente con datos validos.
     */
    @Test
    public void testOperacionRolExitosa() {
        System.out.println("Ejecutando " + accion + " rol " + idRol + " para " + correo);

        try {
            Rol rol = new Rol(idRol, "Rol Test", "Descripcion Test");
            ArrayList<String> correos = new ArrayList<>(Arrays.asList(correo));
            boolean asigna = accion.equals("ASIGNAR");

            controladorRoles.asignarRol(asigna, correos, rol);

            String sqlComprobar = "SELECT * FROM usuarios_roles WHERE email_usuario = ? AND roles_id = ?;";
            boolean existe = modeloDB.existeRegistro(sqlComprobar,
                    new ArrayList<>(Arrays.asList(correo, String.valueOf(idRol))));

            if (accion.equals("ASIGNAR")) {
                assertTrue("El rol deberia estar asignado al usuario", existe);
            } else {
                assertTrue("El rol deberia estar desasignado del usuario", !existe);
            }

        } catch (Exception e) {
            assertTrue("No deberia lanzar excepcion: " + e.getMessage(), false);
        }
    }
}
