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
        int eventosProcesados = 0;

        try {
            Evento evento = buzonConsolidacion.retirar();
            while (!evento.esFin()) {
                Thread.sleep(100 + random.nextInt(901));
                eventosProcesados++;
                evento = buzonConsolidacion.retirar();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println("Servidor " + servidorId
                    + " proceso " + eventosProcesados + " eventos.");
        }
    }
}
