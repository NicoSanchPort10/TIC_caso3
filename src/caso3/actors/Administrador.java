package caso3.actors;

import caso3.model.Evento;
import caso3.sync.Buzon;
import java.util.Random;

public final class Administrador extends ActorCaso3 {
    private final int numeroClasificadores;
    private final Buzon<Evento> buzonAlertas;
    private final Buzon<Evento> buzonClasificacion;
    private final Random random = new Random();

    public Administrador(
            int numeroClasificadores,
            Buzon<Evento> buzonAlertas,
            Buzon<Evento> buzonClasificacion) {
        super("Administrador");
        this.numeroClasificadores = numeroClasificadores;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
    }

    @Override
    public void run() {
        /*
         * TODO:
         * 1. Retirar eventos del buzon de alertas hasta recibir un evento de fin.
         * 2. Para cada alerta, generar un numero entre 0 y 20.
         * 3. Si es multiplo de 4, reenviar al buzon de clasificacion.
         * 4. Si no es multiplo de 4, descartar.
         * 5. Antes de terminar, enviar numeroClasificadores eventos de fin al
         *    buzon de clasificacion.
         */
        pendiente("leer " + buzonAlertas.nombre() + " y avisar fin a "
                + numeroClasificadores + " clasificadores por " + buzonClasificacion.nombre());
    }

    @SuppressWarnings("unused")
    private boolean alertaInofensiva() {
        return random.nextInt(21) % 4 == 0;
    }
}
