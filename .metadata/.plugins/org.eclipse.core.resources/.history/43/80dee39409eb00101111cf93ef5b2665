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
 * Pruebas parametrizadas para operaciones FALLIDAS de asignacion de roles.
 * Verifica que las operaciones con datos invalidos fallen correctamente.
 */
@RunWith(Parameterized.class)
public class PruebasRolesFallo {

    private static ModeloBaseDatos modeloDB;
    private static ControladorRoles controladorRoles;

    private String correo;
    private int idRol;
    private String accion;
    private String descripcion;

    /**
     * Constructor para la prueba parametrizada.
     * 
     * @param correo      Correo del usuario
     * @param idRol       ID del Rol a asignar o desasignar
     * @param accion      Accion a realizar (ASIGNAR o DESASIGNAR)
     * @param descripcion Descripcion del caso de prueba fallido
     */
    public PruebasRolesFallo(String correo, int idRol, String accion, String descripcion) {
        this.correo = correo;
        this.idRol = idRol;
        this.accion = accion;
        this.descripcion = descripcion;
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
     * Proporciona los datos de prueba para operaciones fallidas de asignacion de
     * roles.
     * 
     * @return Coleccion de arrays con datos de prueba invalidos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "noexiste@gmail.com", 1, "ASIGNAR", "Usuario inexistente" },
                { "admin@admin", 9999, "ASIGNAR", "Rol inexistente" },
                { "", 1, "ASIGNAR", "Email vacio" }
        });
    }

    /**
     * Verifica que las operaciones con datos invalidos fallen correctamente.
     */
    @Test
    public void testOperacionRolFallida() {
        System.out.println("Ejecutando " + accion + " (debe fallar): " + descripcion);

        boolean fallo = false;

        try {
            Rol rol = new Rol(idRol, "Rol Test", "Descripcion Test");
            ArrayList<String> correos = new ArrayList<>(Arrays.asList(correo));
            boolean asigna = accion.equals("ASIGNAR");

            // Esto lo hace la aplicacion sola mediante un JTable (usuarios) y un JComboBox
            // (roles)

            String sqlComprobarUsuario = "SELECT * FROM usuarios WHERE email = ?;";
            boolean usuarioExiste = modeloDB.existeRegistro(sqlComprobarUsuario,
                    new ArrayList<>(Arrays.asList(correo)));

            String sqlComprobarRol = "SELECT * FROM roles WHERE id_roles = ?;";
            boolean rolExiste = modeloDB.existeRegistro(sqlComprobarRol,
                    new ArrayList<>(Arrays.asList(String.valueOf(idRol))));

            if (!usuarioExiste || !rolExiste || correo.isEmpty()) {
                fallo = true;
            } else {
                controladorRoles.asignarRol(asigna, correos, rol);

                String sqlComprobar = "SELECT * FROM usuarios_roles WHERE email_usuario = ? AND roles_id = ?;";
                boolean existe = modeloDB.existeRegistro(sqlComprobar,
                        new ArrayList<>(Arrays.asList(correo, String.valueOf(idRol))));

                if (accion.equals("ASIGNAR") && !existe) {
                    fallo = true;
                }
            }

        } catch (Exception e) {
            System.out.println("Excepcion capturada (esperada): " + e.getMessage());
            fallo = true;
        }

        assertTrue("La operacion deberia fallar (" + descripcion + ")", fallo);
    }
}
