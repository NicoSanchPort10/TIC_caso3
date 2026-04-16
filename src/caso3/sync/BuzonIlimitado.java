package caso3.sync;

import java.util.LinkedList;
import java.util.Queue;

public final class BuzonIlimitado<T> implements Buzon<T> {
    private final String nombre;
    private final Queue<T> elementos = new LinkedList<>();

    public BuzonIlimitado(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public synchronized void depositar(T elemento) {
        elementos.add(elemento);
        notifyAll();
    }

    @Override
    public synchronized T retirar() throws InterruptedException {
        // Espera pasiva: el hilo libera el monitor hasta que alguien deposite.
        while (elementos.isEmpty()) {
            wait();
        }

        T elemento = elementos.remove();
        notifyAll();
        return elemento;
    }

    @Override
    public synchronized int tamano() {
        return elementos.size();
    }

    @Override
    public String nombre() {
        return nombre;
    }
}
