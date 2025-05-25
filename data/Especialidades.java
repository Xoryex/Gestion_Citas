package data;

import java.util.HashMap;

public class Especialidades {
    public static HashMap<String, String> Especialidades = new HashMap<>();

    public Especialidades() {
        
        Especialidades.put("1", "Cardiología");
        Especialidades.put("2", "Cirugía General");
        Especialidades.put("3", "Dermatología");
        Especialidades.put("4", "Endocrinología");
        Especialidades.put("5", "Gastroenterología");
        Especialidades.put("6", "Ginecología y Obstetricia");
        Especialidades.put("7", "Medicina General");
        Especialidades.put("8", "Medicina Interna");
        Especialidades.put("9", "Neurología");
        Especialidades.put("10", "Odontología General y Especialidades");
        Especialidades.put("11", "Oftalmología");
        Especialidades.put("12", "Otorrinolaringología");
        Especialidades.put("13", "Pediatría");
        Especialidades.put("14", "Psicología o Psiquiatría");
        Especialidades.put("15", "Traumatología y Ortopedia");
        Especialidades.put("16", "Urología");
    }

    public void imprimirEspecialidades() {
        for (String key : Especialidades.keySet()) {
            System.out.println(Especialidades.get(key));
        }
    }
}
