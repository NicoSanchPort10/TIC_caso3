package caso3.sync;

import java.util.LinkedList;
import java.util.Queue;


public class BuzonSemiActivo<T> implements Buzon<T> {
    private final String nombre;
    private final Queue<T> elementos = new LinkedList<>();

    public BuzonSemiActivo(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public synchronized void depositar(T elemento) {
        elementos.add(elemento);
        notifyAll();
    }

    @Override
    public T retirar() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!elementos.isEmpty()) {
                    T elemento = elementos.remove();
                    notifyAll();
                    return elemento;
                }
                wait(1);
            }

            Thread.yield();
        }
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
