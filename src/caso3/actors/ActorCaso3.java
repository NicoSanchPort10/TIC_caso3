package caso3.actors;

abstract class ActorCaso3 extends Thread {
    ActorCaso3(String nombre) {
        super(nombre);
    }

    protected final void pendiente(String detalle) {
        System.out.println(getName() + " TODO: " + detalle);
    }
}
