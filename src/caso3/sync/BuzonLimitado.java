package caso3.sync;

import java.util.LinkedList;
import java.util.Queue;

public final class BuzonLimitado<T> implements Buzon<T> {
    private final String nombre;
    private final int capacidad;
    private final Queue<T> elementos = new LinkedList<>();

    public BuzonLimitado(String nombre, int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }

        this.nombre = nombre;
        this.capacidad = capacidad;
    }

    @Override
    public synchronized void depositar(T elemento) throws InterruptedException {
        while (elementos.size() == capacidad) {
            wait();
        }

        elementos.add(elemento);
        notifyAll();
    }

    @Override
    public synchronized T retirar() throws InterruptedException {
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
