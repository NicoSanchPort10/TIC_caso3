package caso3.actors;

abstract class Actor extends Thread {
    Actor(String nombre) {
        super(nombre);
    }

    protected final void pendiente(String detalle) {
        System.out.println(getName() + " TODO: " + detalle);
    }
}
