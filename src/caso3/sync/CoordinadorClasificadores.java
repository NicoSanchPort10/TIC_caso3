package caso3.sync;

import caso3.model.Evento;
import java.util.List;

public final class CoordinadorClasificadores {
    private final int totalClasificadores;
    private final List<Buzon<Evento>> buzonesServidores;
    private int clasificadoresTerminados;

    public CoordinadorClasificadores(
            int totalClasificadores,
            List<Buzon<Evento>> buzonesServidores) {
        this.totalClasificadores = totalClasificadores;
        this.buzonesServidores = List.copyOf(buzonesServidores);
    }

    public synchronized boolean registrarTerminacion() {
        clasificadoresTerminados++;
        return clasificadoresTerminados == totalClasificadores;
    }

    public void enviarFinesAServidores(String origen) throws InterruptedException {
        for (Buzon<Evento> buzon : buzonesServidores) {
            buzon.depositar(Evento.fin(origen));
        }
    }
}
