package caso3.actors;

import caso3.model.Evento;
import caso3.sync.Buzon;
import java.util.Random;

public final class Sensor extends Actor {
    private final int sensorId;
    private final int eventosAProducir;
    private final int numeroServidores;
    private final Buzon<Evento> buzonEntrada;
    private final Random random = new Random();

    public Sensor(int sensorId, int eventosAProducir, int numeroServidores, Buzon<Evento> buzonEntrada) {
        super("Sensor-" + sensorId);
        this.sensorId = sensorId;
        this.eventosAProducir = eventosAProducir;
        this.numeroServidores = numeroServidores;
        this.buzonEntrada = buzonEntrada;
    }

    @Override
    public void run() {
        try {
            for (int secuencia = 1; secuencia <= eventosAProducir; secuencia++) {
                int tipoServidor = random.nextInt(numeroServidores) + 1;
                buzonEntrada.depositar(Evento.normal(sensorId, secuencia, tipoServidor));
            }
            System.out.println(getName() + " termino. Eventos generados: " + eventosAProducir);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

