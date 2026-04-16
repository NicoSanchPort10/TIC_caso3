package caso3.actors;

import caso3.model.Evento;
import caso3.sync.Buzon;
import java.util.Random;

public final class Broker extends Actor {
    private final int totalEventosEsperados;
    private final Buzon<Evento> buzonEntrada;
    private final Buzon<Evento> buzonAlertas;
    private final Buzon<Evento> buzonClasificacion;
    private final Random random = new Random();

    public Broker(
            int totalEventosEsperados,
            Buzon<Evento> buzonEntrada,
            Buzon<Evento> buzonAlertas,
            Buzon<Evento> buzonClasificacion) {
        super("Broker");
        this.totalEventosEsperados = totalEventosEsperados;
        this.buzonEntrada = buzonEntrada;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
    }

    @Override
    public void run() {
        /*
         * TODO:
         * 1. Retirar totalEventosEsperados eventos de buzonEntrada.
         * 2. Para cada evento, generar un numero entre 0 y 200.
         * 3. Si es multiplo de 8, depositar en buzonAlertas.
         * 4. Si no es multiplo de 8, depositar en buzonClasificacion.
         * 5. Al terminar, enviar Evento.fin("broker") al buzon de alertas.
         */
        pendiente("procesar " + totalEventosEsperados + " eventos desde " + buzonEntrada.nombre()
                + " hacia " + buzonAlertas.nombre() + " o " + buzonClasificacion.nombre());
    }

    @SuppressWarnings("unused")
    private boolean esAnomalo() {
        return random.nextInt(201) % 8 == 0;
    }
}
