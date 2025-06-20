package src.querys;
import java.util.Scanner;
import java.util.TreeMap;

import src.objetos.Consultorio;

public class Consultorios {
    private final TreeMap<String, Consultorio> listaConsultorios = new TreeMap<>();

    public Consultorios(){

      }
//void no retorna nada
    public void RegistrarConsultorio(Scanner tcl) {
        System.out.print("Nombre del consultorio: ");
        String nombre = tcl.nextLine().trim();
        System.out.print("Piso: ");
        String piso = tcl.nextLine().trim();
        System.out.print("Número de habitación: ");
        String numCuarto = tcl.nextLine().trim();
        
        String codigo = GenerarCodigo(nombre, piso, numCuarto);
        //para guardar el codigo generado

        if (listaConsultorios.containsKey(codigo)) {
        System.out.println("Ya existe un consultorio con esos datos.");
        return;
    }
    
    listaConsultorios.put(codigo, new Consultorio(nombre, piso, numCuarto));
        //.put para agregar la llave y el valor
    }
    
    public String GenerarCodigo(String nombre, String piso, String numCuarto){
        String abreviacion = nombre.replaceAll("(?i)[aeiou]", "");
        return abreviacion + piso + numCuarto;
    }

    public void CambioNombre(Scanner tcl){
        String CodConsult, nuevoNombre;
        ListaConsult();
        do{
        System.out.print("Ingresa el codigo del consultorio");
        CodConsult=tcl.nextLine();
        System.out.print("Ingresa el nuevo nombre del consultorio: ");
        nuevoNombre=tcl.nextLine();
        }while (CodConsult.equals("")||nuevoNombre.equals(""));
        //cuando se crea una variable dentro de do while no te permite usarlo afuera
//containsKey retorna un booleado, mira si la llave que estoy ingresando está dentro de la lista
        if(listaConsultorios.containsKey(CodConsult)){
            listaConsultorios.get(CodConsult).setNombre(nuevoNombre);
            System.out.print("Nombre del consultorio cambiado");
        } else {
            System.out.print("No se puede cambiar el nombre");
        }
    }

    public void CambioPiso(Scanner tcl){
        String CodConsult, nuevoPiso;
        ListaConsult();
        do{
        System.out.print("Ingresa el codigo del consultorio");
        CodConsult=tcl.nextLine();
        System.out.print("Ingresa el nuevo Piso del consultorio: ");
        nuevoPiso=tcl.nextLine();
        }while (CodConsult.equals("")||nuevoPiso.equals(""));

        if(listaConsultorios.containsKey(CodConsult)){
            listaConsultorios.get(CodConsult).setNombre(nuevoPiso);
            System.out.print("Piso del consultorio cambiado");
        } else {
            System.out.print("No se puede cambiar el Piso");
        }
    }

    public void CambioHabitacion(Scanner tcl){
        String CodConsult, nuevaHabitacion;
        ListaConsult();
        do{
        System.out.print("Ingresa el codigo del consultorio");
        CodConsult=tcl.nextLine();
        System.out.print("Ingresa la nueva habitación del consultorio: ");
        nuevaHabitacion=tcl.nextLine();
        }while (CodConsult.equals("")||nuevaHabitacion.equals(""));
        
        if(listaConsultorios.containsKey(CodConsult)){
            listaConsultorios.get(CodConsult).setNombre(nuevaHabitacion);
            System.out.print("Habitación del consultorio cambiado");
        } else {
            System.out.print("No se puede cambiar la habitación");
        }
    }

    public void Eliminar(Scanner tcl){
        String CodEliminar;
        System.out.println("Ingresa el código del consultorio que desea eliminar: ");
        CodEliminar = tcl.nextLine().trim().toUpperCase();

        if(listaConsultorios.containsKey(CodEliminar)){
            Consultorio ConsultEliminado = listaConsultorios.remove(CodEliminar);
            System.out.println("Consultorio" + ConsultEliminado.getNombre() + " " + ConsultEliminado.getPiso() + " " + ConsultEliminado.getNumCuarto() + "Eliminado correctamente." );
         }else{
            System.out.println("No existe el consultorio " + CodEliminar + ".");
         }

    }

    public void ListaConsult() {
        if (listaConsultorios.isEmpty()) {
            System.out.println("No hay consultorios registrados.");
        } else {
            System.out.println("\n---CONSULTORIOS REGISTRADOS---");
            for (String codigo : listaConsultorios.keySet()) {
            Consultorio consultorio = listaConsultorios.get(codigo);
            System.out.print("|Código: " + codigo);
            System.out.print("|Nombre: " + consultorio.getNombre());
            System.out.print("|Piso: " + consultorio.getPiso());
            System.out.print("|Habitación: " + consultorio.getNumCuarto());
            System.out.println();
            }
        }
    }

}

