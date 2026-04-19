package caso3;

import caso3.actors.Administrador;
import caso3.actors.Broker;
import caso3.actors.Clasificador;
import caso3.actors.Sensor;
import caso3.actors.ServidorDespliegue;
import caso3.model.Evento;
import caso3.sync.Buzon;
import caso3.sync.BuzonIlimitado;
import caso3.sync.BuzonLimitado;
import caso3.sync.BuzonSemiActivo;
import caso3.sync.CoordinadorClasificadores;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class Main {
    private Main() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Uso: java -cp out caso3.Main <archivo-config>");
            return;
        }

        Config config = Config.fromFile(Path.of(args[0]));
        System.out.println("Configuracion cargada: " + config);

        
        Buzon<Evento> buzonEntrada = new BuzonIlimitado<>("entrada");

        
        Buzon<Evento> buzonAlertas = new BuzonSemiActivo<>("alertas");

        Buzon<Evento> buzonClasificacion = new BuzonLimitado<>(
                "clasificacion",
                config.tamanoBuzonClasificacion());

        List<Buzon<Evento>> buzonesConsolidacion = new ArrayList<>();
        for (int servidorId = 1; servidorId <= config.numeroServidores(); servidorId++) {
            buzonesConsolidacion.add(new BuzonLimitado<>(
                    "consolidacion-" + servidorId,
                    config.tamanoBuzonConsolidacion()));
        }

        CoordinadorClasificadores coordinador = new CoordinadorClasificadores(
                config.numeroClasificadores(),
                buzonesConsolidacion);

        List<Thread> hilos = new ArrayList<>();

        for (int sensorId = 1; sensorId <= config.numeroSensores(); sensorId++) {
            int eventosAProducir = config.eventosBase() * sensorId;
            hilos.add(new Sensor(
                    sensorId,
                    eventosAProducir,
                    config.numeroServidores(),
                    buzonEntrada));
        }

        hilos.add(new Broker(
                config.totalEventosEsperados(),
                buzonEntrada,
                buzonAlertas,
                buzonClasificacion));

        hilos.add(new Administrador(
                config.numeroClasificadores(),
                buzonAlertas,
                buzonClasificacion));

        for (int clasificadorId = 1; clasificadorId <= config.numeroClasificadores(); clasificadorId++) {
            hilos.add(new Clasificador(
                    clasificadorId,
                    buzonClasificacion,
                    buzonesConsolidacion,
                    coordinador));
        }

        for (int servidorId = 1; servidorId <= config.numeroServidores(); servidorId++) {
            hilos.add(new ServidorDespliegue(
                    servidorId,
                    buzonesConsolidacion.get(servidorId - 1)));
        }

        for (Thread hilo : hilos) {
            hilo.start();
        }

        for (Thread hilo : hilos) {
            hilo.join();
        }

        System.out.println("Simulacion completada.");
        imprimirEstadoBuzones(buzonEntrada, buzonAlertas, buzonClasificacion, buzonesConsolidacion);
    }

    private static void imprimirEstadoBuzones(
            Buzon<Evento> buzonEntrada,
            Buzon<Evento> buzonAlertas,
            Buzon<Evento> buzonClasificacion,
            List<Buzon<Evento>> buzonesConsolidacion) {
        System.out.println("Estado final de buzones (deben ser todos 0):");
        System.out.println("- " + buzonEntrada.nombre() + ": " + buzonEntrada.tamano());
        System.out.println("- " + buzonAlertas.nombre() + ": " + buzonAlertas.tamano());
        System.out.println("- " + buzonClasificacion.nombre() + ": " + buzonClasificacion.tamano());

        for (Buzon<Evento> buzon : buzonesConsolidacion) {
            System.out.println("- " + buzon.nombre() + ": " + buzon.tamano());
        }
    }
}

