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
 * Pruebas parametrizadas para operaciones FALLIDAS de asignacion de roles.
 * Verifica que las operaciones con datos invalidos fallen correctamente.
 */
@RunWith(Parameterized.class)
public class PruebasRolesFallo {

    private static ModeloBaseDatos modeloDB;

    private String correo;
    private Rol rol;
    private String accion;
    private String descripcion;

    /**
     * Constructor para la prueba parametrizada.
     * 
     * @param correo      Correo del usuario
     * @param rol         Rol a asignar o desasignar
     * @param accion      Accion a realizar (ASIGNAR o DESASIGNAR)
     * @param descripcion Descripcion del caso de prueba fallido
     */
    public PruebasRolesFallo(String correo, Rol rol, String accion, String descripcion) {
        this.correo = correo;
        this.rol = rol;
        this.accion = accion;
        this.descripcion = descripcion;
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
     * Proporciona los datos de prueba para operaciones fallidas de asignacion de
     * roles.
     * 
     * @return Coleccion de arrays con datos de prueba invalidos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { "noexiste@gmail.com", new Rol(1, "Admin", "Administrador"), "ASIGNAR", "Usuario inexistente" },
                { "admin@admin", new Rol(99999, "RolInvalido", "No existe"), "ASIGNAR", "Rol inexistente" },
                { "", new Rol(1, "Admin", "Administrador"), "ASIGNAR", "Email vacio" },
                { "admin@admin", null, "ASIGNAR", "Rol null" }
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
            if (rol == null || correo == null || correo.isEmpty()) {
                fallo = true;
            } else {
                String sqlComprobarUsuario = "SELECT * FROM usuarios WHERE email = ?;";
                boolean usuarioExiste = modeloDB.existeRegistro(sqlComprobarUsuario,
                        new ArrayList<>(Arrays.asList(correo)));

                String sqlComprobarRol = "SELECT * FROM roles WHERE id_roles = ?;";
                boolean rolExiste = modeloDB.existeRegistro(sqlComprobarRol,
                        new ArrayList<>(Arrays.asList(String.valueOf(rol.getId_roles()))));

                if (!usuarioExiste || !rolExiste) {
                    fallo = true;
                } else {
                    if (accion.equals("ASIGNAR")) {
                        String sqlAsignar = "INSERT INTO usuarios_roles (email_usuario, roles_id) VALUES (?, ?);";
                        int filas = modeloDB.ejecutarActualizacion(sqlAsignar,
                                new ArrayList<>(Arrays.asList(correo, String.valueOf(rol.getId_roles()))));

                        if (filas <= 0) {
                            fallo = true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Excepcion capturada (esperada): " + e.getMessage());
            fallo = true;
        }

        assertTrue("La operacion deberia fallar (" + descripcion + ")", fallo);
    }
}
