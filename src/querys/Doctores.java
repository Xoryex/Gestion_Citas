package src.querys;

import java.util.Scanner;
import java.util.TreeMap;

import src.querys.objetos.Doctor;



public class Doctores {
    private static TreeMap<String, Doctor> listaDoctores = new TreeMap<>();

    public Doctores() {
        if (!listaDoctores.containsKey("12345678A")) {
            listaDoctores.put("12345678A", new Doctor("Juan", "Pérez", "CAR", "12345678A", "987654321", "juan.perez@example.com"));
        }
    }

    public void agregarDoctor(Scanner scanner, Especialidades especialidades) {
        String nombre, apellido, dni, telefono, correo;

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

        System.out.println("Especialidades disponibles:");
        especialidades.imprimirEspecialidades();

        String codEspecialidad;
        do {
            System.out.print("Ingrese el código de la especialidad del doctor: ");
            codEspecialidad = scanner.nextLine().trim();
            if (!Especialidades.especialidades.containsKey(codEspecialidad)) {
                System.out.println("Código de especialidad no válido. Intente de nuevo.");
            }
        } while (!Especialidades.especialidades.containsKey(codEspecialidad));

        do {
            System.out.print("Ingrese el número de teléfono del doctor: ");
            telefono = scanner.nextLine().trim();
            telefono = telefono.replaceAll("[^0-9]", "");
            if (telefono.length() != 9) {
                System.out.println("El número de teléfono debe contener 9 dígitos numéricos. Ej: 987654321");
            }
        } while (telefono.length() != 9);

        boolean correoValido;
        do {
            System.out.print("Ingrese el correo electrónico del doctor: ");
            correo = scanner.nextLine().trim();
            if (correo.isEmpty()) {
                System.out.println("El correo no puede estar vacío.");
                correoValido = false;
            } else if (!correo.contains("@") || !correo.contains(".") || correo.indexOf('@') > correo.lastIndexOf('.')) {
                System.out.println("Formato de correo electrónico inválido. Debe contener '@' y '.' (ej: usuario@dominio.com) y el '@' debe ir antes del último '.'.");
                correoValido = false;
            } else {
                correoValido = true;
            }
        } while (!correoValido);

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
        System.out.print("Ingrese el DNI del doctor cuyo nombre desea editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            Doctor doctorAEditar = listaDoctores.get(dni);
            String nuevoNombre;
            do {
                System.out.print("El nombre actual es: " + doctorAEditar.getnombre() + ". Ingrese el nuevo nombre: ");
                nuevoNombre = scanner.nextLine().trim();
                if (nuevoNombre.isEmpty()) System.out.println("El nombre no puede estar vacío.");
            } while (nuevoNombre.isEmpty());
            doctorAEditar.setnombre(nuevoNombre);
            System.out.println("Nombre actualizado correctamente para el doctor con DNI " + dni + ".");
        } else {
            System.out.println("No existe un doctor con el DNI " + dni + ".");
        }
    }

    public void editarapellido(Scanner scanner) {
        System.out.print("Ingrese el DNI del doctor cuyo apellido desea editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            Doctor doctorAEditar = listaDoctores.get(dni);
            String nuevoApellido;
            do {
                System.out.print("El apellido actual es: " + doctorAEditar.getapellido() + ". Ingrese el nuevo apellido: ");
                nuevoApellido = scanner.nextLine().trim();
                if (nuevoApellido.isEmpty()) System.out.println("El apellido no puede estar vacío.");
            } while (nuevoApellido.isEmpty());
            doctorAEditar.setapellido(nuevoApellido);
            System.out.println("Apellido actualizado correctamente para el doctor con DNI " + dni + ".");
        } else {
            System.out.println("No existe un doctor con el DNI " + dni + ".");
        }
    }

    public void editartlf(Scanner scanner) {
        System.out.print("Ingrese el DNI del doctor cuyo teléfono desea editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            Doctor doctorAEditar = listaDoctores.get(dni);
            String nuevoTelefono;
            do {
                System.out.print("El teléfono actual es: " + doctorAEditar.gettlf() + ". Ingrese el nuevo número de teléfono: ");
                nuevoTelefono = scanner.nextLine().trim();
                nuevoTelefono = nuevoTelefono.replaceAll("[^0-9]", "");
                if (nuevoTelefono.length() != 9) {
                    System.out.println("El teléfono debe tener 9 dígitos numéricos.");
                }
            } while (nuevoTelefono.length() != 9);
            doctorAEditar.settlf(nuevoTelefono);
            System.out.println("Número de teléfono actualizado correctamente para el doctor con DNI " + dni + ".");
        } else {
            System.out.println("No existe un doctor con el DNI " + dni + ".");
        }
    }

    public void editarCorreo(Scanner scanner) {
        System.out.print("Ingrese el DNI del doctor cuyo correo desea editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            Doctor doctorAEditar = listaDoctores.get(dni);
            String nuevoCorreo;
            boolean correoValido;
            do {
                System.out.print("El correo actual es: " + doctorAEditar.getcorreo() + ". Ingrese el nuevo correo electrónico: ");
                nuevoCorreo = scanner.nextLine().trim();
                if (nuevoCorreo.isEmpty()) {
                    System.out.println("El correo no puede estar vacío.");
                    correoValido = false;
                } else if (!nuevoCorreo.contains("@") || !nuevoCorreo.contains(".") || nuevoCorreo.indexOf('@') > nuevoCorreo.lastIndexOf('.')) {
                    System.out.println("Formato de correo electrónico inválido. Debe contener '@' y '.' (ej: usuario@dominio.com) y el '@' debe ir antes del último '.'.");
                    correoValido = false;
                } else {
                    correoValido = true;
                }
            } while (!correoValido);
            doctorAEditar.setcorreo(nuevoCorreo);
            System.out.println("Correo electrónico actualizado correctamente para el doctor con DNI " + dni + ".");
        } else {
            System.out.println("No existe un doctor con el DNI " + dni + ".");
        }
    }

    public void editarEspecialidad(Scanner scanner) {
        System.out.print("Ingrese el DNI del doctor cuya especialidad desea editar: ");
        String dni = scanner.nextLine().trim().toUpperCase();
        if (listaDoctores.containsKey(dni)) {
            Doctor doctorAEditar = listaDoctores.get(dni);
            String nuevaEspecialidad;
            do {
                System.out.print("La especialidad actual es: " + doctorAEditar.getcodEspecialidad() + ". Ingrese la nueva especialidad: ");
                nuevaEspecialidad = scanner.nextLine().trim();
                if (nuevaEspecialidad.isEmpty()) System.out.println("La especialidad no puede estar vacía.");
            } while (nuevaEspecialidad.isEmpty());
            doctorAEditar.setcodEspecialidad(nuevaEspecialidad);
            System.out.println("Especialidad actualizada correctamente para el doctor con DNI " + dni + ".");
        } else {
            System.out.println("No existe un doctor con el DNI " + dni + ".");
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
                    nuevoDNI = ""; // Reset nuevoDNI to loop again
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
            System.out.println();
        }
    }

    public TreeMap<String, Doctor> getListaDoctores() {
        return listaDoctores;
    }
}