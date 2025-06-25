package src.querys;

interface Query<T> {

    void Insetar(T datos_persona);
    void Eliminar(String codigo);
    void actualizar (T datos_modificados);
    String[][] seleccionar();

}
