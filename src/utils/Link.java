package src.utils;
  
import src.querys.Consultorios;
import src.querys.Doctores;
import src.querys.Especialidades;
import src.querys.Horarios;
import src.querys.QueryPacientes;
import src.querys.QueryUser;

public class Link {
        public final QueryUser usuarios=new QueryUser();
        public final Doctores doctores=new Doctores();
        public final Especialidades especialidades =new Especialidades();
        public final Consultorios consultorios=new Consultorios();
        public final Horarios horarios=new Horarios();
        public final QueryPacientes pacientes=new QueryPacientes();
        
}
