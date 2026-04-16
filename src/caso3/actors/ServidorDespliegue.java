package caso3.actors;

import caso3.model.Evento;
import caso3.sync.Buzon;
import java.util.Random;

public final class ServidorDespliegue extends Actor {
    private final int servidorId;
    private final Buzon<Evento> buzonConsolidacion;
    private final Random random = new Random();

    public ServidorDespliegue(int servidorId, Buzon<Evento> buzonConsolidacion) {
        super("Servidor-" + servidorId);
        this.servidorId = servidorId;
        this.buzonConsolidacion = buzonConsolidacion;
    }

    @Override
    public void run() {
        /*
         * TODO:
         * 1. Retirar eventos del buzon de consolidacion.
         * 2. Si recibe evento de fin, terminar.
         * 3. Para cada evento normal, dormir entre 100 ms y 1000 ms.
         * 4. Registrar metricas: cuantos eventos proceso este servidor.
         */
        pendiente("procesar eventos desde " + buzonConsolidacion.nombre());
    }

    @SuppressWarnings("unused")
    private long tiempoProcesamientoMs() {
        return random.nextInt(901) + 100L;
    }

    @SuppressWarnings("unused")
    private int servidorId() {
        return servidorId;
    }
}
