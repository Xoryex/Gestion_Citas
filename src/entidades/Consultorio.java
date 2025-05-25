package src.entidades;

import java.util.*;

public class Consultorio {

    private String nombre;
    private String piso;
    private String numCuarto;
    private String codigo;

    public static final List<Consultorio> registros = new ArrayList<>();
    public static final Map<String, Consultorio> mapaRegistros = new HashMap<>();

    public void Registrar() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre: ");
        this.nombre = scanner.nextLine().trim();

        System.out.print("Piso: ");
        this.piso = scanner.nextLine().trim();

        System.out.print("Número de habitación: ");
        this.numCuarto = scanner.nextLine().trim();

        if (consultorioExiste(this.nombre, this.piso, this.numCuarto)) {
            System.out.println("Este consultorio ya existe");
            return;
        }

        this.codigo = generarCodigo(this.nombre, this.piso, this.numCuarto);
        registros.add(this);
        mapaRegistros.put(this.codigo, this);

        System.out.println("Consultorio registrado con código: " + this.codigo);
    }

    // Verifica si ya existe un consultorio con los mismos datos
    private boolean consultorioExiste(String nombre, String piso, String numCuarto) {
        return registros.stream().anyMatch(c ->
            c.getNombre().equalsIgnoreCase(nombre) &&
            c.getPiso().equalsIgnoreCase(piso) &&
            c.getNumCuarto().equalsIgnoreCase(numCuarto));
    }

    // Crea un código identificador basado en el nombre, piso y número de habitación
    public static String generarCodigo(String nombre, String piso, String numCuarto) {
        String abreviacion = nombre.replaceAll("(?i)[aeiou]", "").toUpperCase();
        return abreviacion + piso + numCuarto;
    }

    public void generarCodigo() {
        this.codigo = generarCodigo(this.nombre, this.piso, this.numCuarto);
    }

    public static void mostrarRegistros() {
        if (registros.isEmpty()) {
            System.out.println("No hay registros.");
            return;
        }

        System.out.println("\n--- Lista de Consultorios Registrados ---");
        for (Consultorio r : registros) {
            System.out.println("Código: " + r.getCodigo()
                    + " | Nombre: " + r.getNombre()
                    + " | Piso: " + r.getPiso()
                    + " | Habitación: " + r.getNumCuarto());
        }
    }

    // Permite editar los datos de un consultorio buscando por el inicio de su nombre
    public static void editarPorCodigo(Scanner scanner) {
        System.out.print("Ingrese el nombre del consultorio a buscar: ");
        String nombreBuscar = scanner.nextLine().trim().toLowerCase();

        List<Consultorio> encontrados = new ArrayList<>();
        for (Consultorio r : registros) {
            if (r.getNombre().toLowerCase().startsWith(nombreBuscar)) {
                encontrados.add(r);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron consultorios que coincidan con ese nombre.");
            return;
        }

        System.out.println("\n=== Resultados encontrados ===");
        encontrados.forEach(r ->
            System.out.println("Código: " + r.getCodigo()
                + " | Nombre: " + r.getNombre()
                + " | Piso: " + r.getPiso()
                + " | Habitación: " + r.getNumCuarto())
        );

        System.out.print("\nIngrese el código del consultorio que desea editar: ");
        String codigoBuscar = scanner.nextLine().trim();

        for (Consultorio r : encontrados) {
            if (r.getCodigo().equalsIgnoreCase(codigoBuscar)) {
                System.out.println("\nConsultorio encontrado. Ingrese los nuevos datos:");

                System.out.print("Nuevo nombre: ");
                String nuevoNombre = scanner.nextLine().trim();

                System.out.print("Nuevo piso: ");
                String nuevoPiso = scanner.nextLine().trim();

                System.out.print("Nueva habitación: ");
                String nuevaHabitacion = scanner.nextLine().trim();

                if (r.consultorioExiste(nuevoNombre, nuevoPiso, nuevaHabitacion)) {
                    System.out.println("Ya existe otro consultorio con esos datos.");
                    return;
                }

                String codigoAnterior = r.getCodigo();
                r.setNombre(nuevoNombre);
                r.setPiso(nuevoPiso);
                r.setNumCuarto(nuevaHabitacion);
                r.generarCodigo();

                mapaRegistros.remove(codigoAnterior);
                mapaRegistros.put(r.getCodigo(), r);

                System.out.println("Se actualizó el registro y se creó un nuevo código: " + r.getCodigo());
                return;
            }
        }

        System.out.println("No se encontró un consultorio con ese código.");
    }

    // Elimina un consultorio buscando por nombre y confirmando por código
    public static void eliminarPorCodigo(Scanner scanner) {
        System.out.print("Ingrese el nombre del consultorio a buscar para eliminar: ");
        String nombreBuscar = scanner.nextLine().trim().toLowerCase();

        List<Consultorio> encontrados = new ArrayList<>();
        for (Consultorio r : registros) {
            if (r.getNombre().toLowerCase().startsWith(nombreBuscar)) {
                encontrados.add(r);
            }
        }

        if (encontrados.isEmpty()) {
            System.out.println("No se encontraron consultorios que coincidan con ese nombre.");
            return;
        }

        System.out.println("\n=== Consultorios encontrados ===");
        encontrados.forEach(r ->
            System.out.println("Código: " + r.getCodigo()
                    + " | Nombre: " + r.getNombre()
                    + " | Piso: " + r.getPiso()
                    + " | Habitación: " + r.getNumCuarto())
        );

        System.out.print("\nIngrese el código exacto del consultorio que desea eliminar: ");
        String codigoEliminar = scanner.nextLine().trim();

        Iterator<Consultorio> iterator = registros.iterator();
        while (iterator.hasNext()) {
            Consultorio r = iterator.next();
            if (r.getCodigo().equalsIgnoreCase(codigoEliminar)) {
                iterator.remove();
                mapaRegistros.remove(r.getCodigo());
                System.out.println("Registro eliminado correctamente.");
                return;
            }
        }

        System.out.println("No se encontró un consultorio con ese código.");
    }

    public String getNombre() { return nombre; }
    public String getPiso() { return piso; }
    public String getNumCuarto() { return numCuarto; }
    public String getCodigo() { return codigo; }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setPiso(String piso) { this.piso = piso; }
    public void setNumCuarto(String numCuarto) { this.numCuarto = numCuarto; }
}
