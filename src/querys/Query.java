package querys;

interface Query<T> {

    void Insetar(T datos_persona);

    void Eliminar(int codigo);

    void actualizar (T datos_modificados);
    
    String[][] seleccionar();

}
