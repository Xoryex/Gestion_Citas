package src.entidades;

import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern; // Para validación de correo más robusta

public class Doctores {
    private static TreeMap<String, Doctor> listaDoctores = new TreeMap<>();

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public Doctores() {
        if (!listaDoctores.containsKey("12345678A")) {
             listaDoctores.put("12345678A", new Doctor("Juan", "Pérez", "Cardiología", "12345678A", "987654321", "juan.perez@example.com"));
        }
    }

    public void agregarDoctor(Scanner scanner) {
        String nombre, apellido, codEspecialidad, dni, telefono, correo;

        System.out.println("\n--- Agregar Nuevo Doctor ---");

        do {
            System.out.print("Ingrese el DNI del doctor: ");
            dni = scanner.nextLine().trim().toUpperCase();
            if (dni.isEmpty()) {
                System.out.println("El DNI no puede estar vacío. Intente de nuevo.");
            } else if (listaDoctores.containsKey(dni)) {
                System.out.println("Error: Ya existe un doctor registrado con el DNI " + dni + ". Intente con otro.");
                dni = "";
            }
        } while (dni.isEmpty());

        do {
            System.out.print("Ingrese el nombre del doctor: ");
            nombre = scanner.nextLine().trim();
             if (nombre.isEmpty()) System.out.println("El nombre no puede estar vacío.");
        } while (nombre.isEmpty());

        do {
            System.out.print("Ingrese el apellido del doctor: ");
            apellido = scanner.nextLine().trim();
            if (apellido.isEmpty()) System.out.println("El apellido no puede estar vacío.");
        } while (apellido.isEmpty());

        do {
            System.out.print("Ingrese el código o nombre de la especialidad del doctor: ");
            codEspecialidad = scanner.nextLine().trim();
            if (codEspecialidad.isEmpty()) System.out.println("La especialidad no puede estar vacía.");
        } while (codEspecialidad.isEmpty());

        do {
            System.out.print("Ingrese el número de teléfono del doctor: ");
            telefono = scanner.nextLine().trim();
            telefono = telefono.replaceAll("[^0-9]", ""); // Conservar solo números
            if (telefono.length() != 9) {
                System.out.println("El número de teléfono debe contener 9 dígitos numéricos. Ej: 987654321");
            }
        } while (telefono.length() != 9);

        do {
            System.out.print("Ingrese el correo electrónico del doctor: ");
            correo = scanner.nextLine().trim();
            if (correo.isEmpty()) {
                 System.out.println("El correo no puede estar vacío.");
            } else if (!EMAIL_PATTERN.matcher(correo).matches()) {
                System.out.println("Formato de correo electrónico inválido. Ej: usuario@dominio.com");
            }
        } while (correo.isEmpty() || !EMAIL_PATTERN.matcher(correo).matches());

        Doctor nuevoDoctor = new Doctor(nombre, apellido, codEspecialidad, dni, telefono, correo);
        listaDoctores.put(dni, nuevoDoctor);

        System.out.println("Doctor '" + nombre + " " + apellido + "' agregado correctamente con DNI " + dni + ".\n");
    }

    public void borrarDoctor(Scanner scanner) {
        String dni;
        System.out.print("Ingrese el DNI del doctor a eliminar: ");
        dni = scanner.nextLine().trim().toUpperCase();

        if (listaDoctores.containsKey(dni)) {
            Doctor docEliminado = listaDoctores.remove(dni);
            System.out.println("Doctor '" + docEliminado.getnombre() + " " + docEliminado.getapellido() + "' eliminado correctamente.");
        } else {
            System.out.println("No existe un doctor con el DNI " + dni + ".");
        }
    }

    public void editarnombre(Scanner scanner) {
        System.out.print("Ingrese el DNI del doctor a editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            String nuevoNombre;
            do {
                System.out.print("Ingrese el nuevo nombre: ");
                nuevoNombre = scanner.nextLine().trim();
                if (nuevoNombre.isEmpty()) System.out.println("El nombre no puede estar vacío.");
            } while (nuevoNombre.isEmpty());
            listaDoctores.get(dni).setnombre(nuevoNombre);
            System.out.println("Nombre actualizado correctamente.");
        } else {
            System.out.println("No existe un doctor con ese DNI.");
        }
    }

    public void editarapellido(Scanner scanner) {
        System.out.print("Ingrese el DNI del doctor a editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            String nuevoApellido;
            do {
                System.out.print("Ingrese el nuevo apellido: ");
                nuevoApellido = scanner.nextLine().trim();
                if (nuevoApellido.isEmpty()) System.out.println("El apellido no puede estar vacío.");
            } while (nuevoApellido.isEmpty());
            listaDoctores.get(dni).setapellido(nuevoApellido);
            System.out.println("Apellido actualizado correctamente.");
        } else {
            System.out.println("No existe un doctor con ese DNI.");
        }
    }

    public void editartlf(Scanner scanner) {
        System.out.print("Ingrese el DNI del doctor a editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            String nuevoTelefono;
            do {
                System.out.print("Ingrese el nuevo número de teléfono (9 dígitos): ");
                nuevoTelefono = scanner.nextLine().trim();
                nuevoTelefono = nuevoTelefono.replaceAll("[^0-9]", "");
                if (nuevoTelefono.length() != 9) {
                     System.out.println("El teléfono debe tener 9 dígitos numéricos.");
                }
            } while (nuevoTelefono.length() != 9);
            listaDoctores.get(dni).settlf(nuevoTelefono);
            System.out.println("Número de teléfono actualizado correctamente.");
        } else {
            System.out.println("No existe un doctor con ese DNI.");
        }
    }

    public void editarCorreo(Scanner scanner) {
        System.out.print("Ingrese el DNI del doctor a editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            String nuevoCorreo;
            do {
                System.out.print("Ingrese el nuevo correo electrónico: ");
                nuevoCorreo = scanner.nextLine().trim();
                 if (nuevoCorreo.isEmpty()) {
                    System.out.println("El correo no puede estar vacío.");
                } else if (!EMAIL_PATTERN.matcher(nuevoCorreo).matches()) {
                    System.out.println("Formato de correo electrónico inválido.");
                }
            } while (nuevoCorreo.isEmpty() || !EMAIL_PATTERN.matcher(nuevoCorreo).matches());
            listaDoctores.get(dni).setcorreo(nuevoCorreo);
            System.out.println("Correo electrónico actualizado correctamente.");
        } else {
            System.out.println("No existe un doctor con ese DNI.");
        }
    }

    public void editarEspecialidad(Scanner scanner) {
        System.out.print("Ingrese el DNI del doctor a editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            String nuevaEspecialidad;
            do {
                System.out.print("Ingrese la nueva especialidad: ");
                nuevaEspecialidad = scanner.nextLine().trim();
                if (nuevaEspecialidad.isEmpty()) System.out.println("La especialidad no puede estar vacía.");
            } while (nuevaEspecialidad.isEmpty());
            listaDoctores.get(dni).setcodEspecialidad(nuevaEspecialidad);
            System.out.println("Especialidad actualizada correctamente.");
        } else {
            System.out.println("No existe un doctor con ese DNI.");
        }
    }

    public void editarDNI(Scanner scanner) {
        String dniActual, nuevoDNI;
        System.out.print("Ingrese el DNI actual del doctor a editar: ");
        dniActual = scanner.nextLine().trim().toUpperCase();

        if (listaDoctores.containsKey(dniActual)) {
            Doctor doctorAEditar = listaDoctores.get(dniActual);
            do {
                System.out.print("Ingrese el nuevo DNI: ");
                nuevoDNI = scanner.nextLine().trim().toUpperCase();
                if (nuevoDNI.isEmpty()) {
                    System.out.println("El nuevo DNI no puede estar vacío.");
                } else if (nuevoDNI.equals(dniActual)) {
                    System.out.println("El nuevo DNI es igual al actual. No se realizarán cambios.");
                    return;
                } else if (listaDoctores.containsKey(nuevoDNI)) {
                    System.out.println("Error: Ya existe otro doctor registrado con el DNI " + nuevoDNI + ". Intente con otro.");
                    nuevoDNI = "";
                }
            } while (nuevoDNI.isEmpty());

            doctorAEditar.setdni(nuevoDNI);
            listaDoctores.remove(dniActual);
            listaDoctores.put(nuevoDNI, doctorAEditar);

            System.out.println("DNI actualizado correctamente de " + dniActual + " a " + nuevoDNI + ".");
        } else {
            System.out.println("No existe un doctor con el DNI " + dniActual + ".");
        }
    }

    public void listarDoctores() {
        if (listaDoctores.isEmpty()) {
            System.out.println("No hay doctores registrados.");
        } else {
            System.out.println("\n--- Lista de Doctores Registrados ---");
            for (String dni : listaDoctores.keySet()) {
                Doctor doctor = listaDoctores.get(dni);
                System.out.println(doctor.toString());
            }
        }
    }
}