package caso3.actors;

import caso3.model.Evento;
import caso3.sync.Buzon;
import java.util.Random;

public final class Administrador extends Actor {
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
        Buzon<Evento> alertas = buzonAlertas;
        Buzon<Evento> clasificacion = buzonClasificacion;
        int clasificadores = numeroClasificadores;
        try {
            Evento evento = alertas.retirar();
            while (!evento.esFin()) {
                int numero = random.nextInt(21);

                if (numero % 4 == 0){
                    clasificacion.depositar(evento);
                }

                evento = alertas.retirar();
            }
            

            for (int i = 0; i < clasificadores; i ++) {
                clasificacion.depositar(Evento.fin("administrador"));
            }
            
            

        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }




       
    }

    
}
