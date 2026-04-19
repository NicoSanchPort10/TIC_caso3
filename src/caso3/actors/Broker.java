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
        Random generador = random;
        Buzon<Evento> entrada = buzonEntrada;
        Buzon<Evento> alertas = buzonAlertas;
        Buzon<Evento> clasificacion = buzonClasificacion;

        try {
            for (int i = 0; i < totalEventosEsperados; i++) {
                Evento evento = entrada.retirar();
                if (generador.nextInt(201) % 8 == 0) {
                    alertas.depositar(evento);
                } else {
                    clasificacion.depositar(evento);
                }
            }

            alertas.depositar(Evento.fin("broker"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
