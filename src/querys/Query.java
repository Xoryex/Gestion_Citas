package src.querys;

interface Query<T> {

    void insetar(T datos_persona);
    void Eliminar(String indice);
    void actualizar (T datos_modificados);
    Object seleccionar();

}
