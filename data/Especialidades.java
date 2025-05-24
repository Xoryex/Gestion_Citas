package data;

import java.util.HashMap;

public class Especialidades {
    public static HashMap<String, String> Especialidades = new HashMap<>();

    public Especialidades() {
        
        Especialidades.put("1", "Medicina General");
        Especialidades.put("2", "Pediatría");
        Especialidades.put("3", "Ginecología y Obstetricia");
        Especialidades.put("4", "Medicina Interna");
        Especialidades.put("5", "Traumatología y Ortopedia");
        Especialidades.put("6", "Cirugía General");
        Especialidades.put("7", "Dermatología");
        Especialidades.put("8", "Odontología General y Especialidades");
        Especialidades.put("9", "Cardiología");
        Especialidades.put("10", "Oftalmología");
        Especialidades.put("11", "Otorrinolaringología");
        Especialidades.put("12", "Neurología");
        Especialidades.put("13", "Urología");
        Especialidades.put("14", "Psicología o Psiquiatría");
        Especialidades.put("15", "Gastroenterología");
        Especialidades.put("16", "Endocrinología");
    }

    public void imprimirEspecialidades() {
        for (String key : Especialidades.keySet()) {
            System.out.println(Especialidades.get(key));
        }
    }
}
