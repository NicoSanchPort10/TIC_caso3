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
        /*
         * TODO:
         * 1. Retirar eventos del buzon de clasificacion.
         * 2. Si recibe un evento de fin, registrar terminacion en coordinador.
         * 3. Si es el ultimo clasificador en terminar, enviar fin a servidores.
         * 4. Si es evento normal, calcular indice tipoServidor - 1.
         * 5. Depositar el evento en el buzon de consolidacion correcto.
         */
        pendiente("clasificar eventos de " + buzonClasificacion.nombre()
                + " entre " + buzonesConsolidacion.size() + " servidores");
    }

    @SuppressWarnings("unused")
    private int indiceServidor(Evento evento) {
        return evento.tipoServidor() - 1;
    }

    @SuppressWarnings("unused")
    private int clasificadorId() {
        return clasificadorId;
    }

    @SuppressWarnings("unused")
    private CoordinadorClasificadores coordinador() {
        return coordinador;
    }
}
