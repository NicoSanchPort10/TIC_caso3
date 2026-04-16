package caso3.sync;

public interface Buzon<T> {
    void depositar(T elemento) throws InterruptedException;

    T retirar() throws InterruptedException;

    int tamano();

    String nombre();
}
