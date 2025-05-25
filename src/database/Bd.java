package src.database;

import src.entidades.Consultorios;
import src.entidades.Doctores;
import src.entidades.Especialidades;
import src.entidades.Pacientes;
import src.entidades.Users;

public class Bd {
        public final Users usuarios=new Users();
        public final Doctores doctores=new Doctores();
        public final Especialidades especialidades =new Especialidades();
        public final Consultorios consultorios=new Consultorios();
        public final Pacientes pacientes=new Pacientes();
        
}
