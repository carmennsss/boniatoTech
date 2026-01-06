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

import controladorCRUD.ControladorCRUD;
import modelo.Animal;
import modelo.Cuidador;
import modelo.Especie;
import modelo.ModeloBaseDatos;
import modelo.Recinto;

/**
 * Pruebas parametrizadas para operaciones EXITOSAS del zoologico (CRUD).
 * Verifica que las operaciones ANIADIR y ACTUALIZAR funcionen correctamente con
 * datos validos.
 */
@RunWith(Parameterized.class)
public class PruebasZooExito {

    private static ModeloBaseDatos modeloDB;
    private static ControladorCRUD controladorCRUD;

    private String tabla;
    private Object objeto;
    private String accion;

    /**
     * Constructor para la prueba parametrizada.
     * 
     * @param tabla  Nombre de la tabla a probar
     * @param objeto Objeto con los datos de prueba
     * @param accion Accion a realizar (ANIADIR o ACTUALIZAR)
     */
    public PruebasZooExito(String tabla, Object objeto, String accion) {
        this.tabla = tabla;
        this.objeto = objeto;
        this.accion = accion;
    }

    /**
     * Inicializa la conexion a la base de datos y el controlador CRUD antes de
     * ejecutar las pruebas.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        modeloDB = new ModeloBaseDatos();
        controladorCRUD = new ControladorCRUD(null, modeloDB, null, null, null);
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
     * Proporciona los datos de prueba para operaciones exitosas del zoologico.
     * 
     * @return Coleccion de arrays con datos de prueba validos
     */
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // ANIMALES
                { "animales", new Animal(9001, "Leon de Prueba", "Male", 1, 1), "ANIADIR" },
                { "animales", new Animal(1, "Leon Modificado", "Female", 1, 1), "ACTUALIZAR" },

                // CUIDADORES
                { "cuidadores", new Cuidador(9001, "Juan Perez", "Calle Falsa 123"), "ANIADIR" },
                { "cuidadores", new Cuidador(1, "Juan P. Modificado", "Av. Siempre Viva 742"), "ACTUALIZAR" },

                // RECINTOS
                { "recintos", new Recinto(9001, "Jaula Norte", "Zona A", 5, 5), "ANIADIR" },
                { "recintos", new Recinto(1, "Jaula Norte Mod", "Zona B", 10, 10), "ACTUALIZAR" },

                // ESPECIES
                { "especies", new Especie(9001, "Panthera leo"), "ANIADIR" },
                { "especies", new Especie(1, "Panthera leo persica"), "ACTUALIZAR" },
        });
    }

    /**
     * Verifica que las operaciones ANIADIR y ACTUALIZAR funcionen correctamente con
     * datos validos.
     */
    @Test
    public void testOperacionExitosa() {
        System.out.println("Ejecutando " + accion + " en " + tabla);

        int filas = 0;

        switch (accion) {
            case "ANIADIR":
                ArrayList<String> columnas = modeloDB.getNombresColumnas(tabla);
                columnas.remove(0);
                String[] valores = obtenerValoresObjeto(objeto);

                assertTrue("Los valores deben ser validos", controladorCRUD.comprobarValores(valores));

                filas = controladorCRUD.crearRegistroInserccion(tabla, columnas, valores);
                break;

            case "ACTUALIZAR":
                ArrayList<String> columnasUpd = modeloDB.getNombresColumnas(tabla);
                columnasUpd.remove(0);
                String idCol = modeloDB.getNombresColumnas(tabla).get(0);
                Object id = obtenerIdObjeto(objeto);
                String[] valoresUpd = obtenerValoresObjeto(objeto);

                assertTrue("Los valores deben ser validos", controladorCRUD.comprobarValores(valoresUpd));

                filas = controladorCRUD.crearRegistroActualizacion(tabla, id, idCol, columnasUpd, valoresUpd);
                break;
        }

        assertTrue("Operacion exitosa: " + accion + " en " + tabla, filas > 0);
    }

    /**
     * Extrae los valores de un objeto para usarlos en operaciones de base de datos.
     * 
     * @param obj Objeto del cual extraer los valores
     * @return Array de strings con los valores del objeto
     */
    private String[] obtenerValoresObjeto(Object obj) {
        ArrayList<String> valores = new ArrayList<>();

        if (obj instanceof Animal) {
            Animal a = (Animal) obj;
            valores.add(a.getNombre_animales());
            valores.add(a.getTipo());
            valores.add(String.valueOf(a.getEspecie_id()));
            valores.add(String.valueOf(a.getCuidador_id()));
        } else if (obj instanceof Cuidador) {
            Cuidador c = (Cuidador) obj;
            valores.add(c.getNombre_cuidadores());
            valores.add(c.getDireccion_cuidadores());
        } else if (obj instanceof Recinto) {
            Recinto r = (Recinto) obj;
            valores.add(r.getNombre_recintos());
            valores.add(r.getDireccion_recintos());
            valores.add(String.valueOf(r.getCantidad_origen()));
            valores.add(String.valueOf(r.getCantidad_destino()));
        } else if (obj instanceof Especie) {
            Especie e = (Especie) obj;
            valores.add(e.getNombre_especies());
        }

        return valores.toArray(new String[0]);
    }

    /**
     * Obtiene el ID de un objeto.
     * 
     * @param obj Objeto del cual obtener el ID
     * @return ID del objeto o null si no se reconoce el tipo
     */
    private Object obtenerIdObjeto(Object obj) {
        if (obj instanceof Animal) {
            Animal animal = (Animal) obj;
            return animal.getAnimal_id();
        } else if (obj instanceof Cuidador) {
            Cuidador cuidador = (Cuidador) obj;
            return cuidador.getCuidador_id();
        } else if (obj instanceof Recinto) {
            Recinto recinto = (Recinto) obj;
            return recinto.getRecinto_id();
        } else if (obj instanceof Especie) {
            Especie especie = (Especie) obj;
            return especie.getEspecie_id();
        }
        return null;
    }
}
