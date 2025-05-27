package src.entidades;
import java.util.Scanner;
import java.util.TreeMap;

import src.utils.Link.consultorio;

public class Consultorios {
    private final TreeMap<String, Consultorio> listaConsultorios = new TreeMap<>();

    public Consultorios(){
         listaConsultorios.put("1",new Consultorio ("Consultorio", "piso", "habitación"));
      }

    public String RegistrarConsultorio(Scanner tcl, String ConsultActual) {
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
        return ConsultActual;
    }

    listaConsultorios.remove(ConsultActual);
    
    listaConsultorios.put(codigo, new Consultorio(nombre, piso, numCuarto));
        //.put para agregar la llave y el valor
    System.out.println("Consultorio actualizado.");
    System.out.println("Código anterior: " + ConsultActual);
    System.out.println("Nuevo código: " + codigo);

    return codigo;

    }
    
    public String GenerarCodigo(String nombre, String piso, String numCuarto){
        String abreviacion = nombre.replaceAll("(?i)[aeiou]", "");
        return abreviacion + piso + numCuarto;
    }

    public String CmbNomCnstl(Scanner tcl, String NomActual){
        //ListaConsult();
        System.out.print("Ingresa el nuevo nombre del consultorio: ");
        String nuevoNombre=tcl.nextLine();
        System.out.print("Ingresa el codigo del consultorio");
        String CodConsult=tcl.nextLine();

        if()
    }

    


}
