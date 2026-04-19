package caso3.actors;

import caso3.model.Evento;
import caso3.sync.Buzon;
import caso3.sync.CoordinadorClasificadores;
import java.util.List;

public final class Clasificador extends Actor {
    private final int clasificadorId;
    private final Buzon<Evento> buzonClasificacion;
    private final List<Buzon<Evento>> buzonesConsolidacion;
    private final CoordinadorClasificadores coordinador;

    public Clasificador(
            int clasificadorId,
            Buzon<Evento> buzonClasificacion,
            List<Buzon<Evento>> buzonesConsolidacion,
            CoordinadorClasificadores coordinador) {
        super("Clasificador-" + clasificadorId);
        this.clasificadorId = clasificadorId;
        this.buzonClasificacion = buzonClasificacion;
        this.buzonesConsolidacion = List.copyOf(buzonesConsolidacion);
        this.coordinador = coordinador;
    }

    @Override
    public void run() {
        int procesados = 0;

        try {
            Evento evento = buzonClasificacion.retirar();

            while (!evento.esFin()) {
                
                int destino = evento.tipoServidor() - 1;
                buzonesConsolidacion.get(destino).depositar(evento);
                procesados++;
                evento = buzonClasificacion.retirar();
            }

            System.out.println(getName() + " termino. Eventos clasificados: " + procesados);

            boolean esUltimo = coordinador.registrarTerminacion();
            if (esUltimo) {
                System.out.println(getName() + " es el ultimo. Enviando fin a servidores.");
                coordinador.enviarFinesAServidores("clasificador-" + clasificadorId);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}