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
        int anomalos = 0;
        int normales = 0;

        try {
            for (int i = 0; i < totalEventosEsperados; i++) {
                Evento evento = buzonEntrada.retirar();
                int numero = random.nextInt(201);

                if (numero % 8 == 0) {
                    buzonAlertas.depositar(evento);
                    anomalos++;
                } else {
                    buzonClasificacion.depositar(evento);
                    normales++;
                }
            }

            System.out.println("Broker termino. Anomalos: " + anomalos + ", Normales: " + normales);
            buzonAlertas.depositar(Evento.fin("broker"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

