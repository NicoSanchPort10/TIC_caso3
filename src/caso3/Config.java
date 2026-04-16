package caso3;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class Config {
    private final int numeroSensores;
    private final int eventosBase;
    private final int numeroClasificadores;
    private final int numeroServidores;
    private final int tamanoBuzonClasificacion;
    private final int tamanoBuzonConsolidacion;

    private Config(
            int numeroSensores,
            int eventosBase,
            int numeroClasificadores,
            int numeroServidores,
            int tamanoBuzonClasificacion,
            int tamanoBuzonConsolidacion) {
        this.numeroSensores = numeroSensores;
        this.eventosBase = eventosBase;
        this.numeroClasificadores = numeroClasificadores;
        this.numeroServidores = numeroServidores;
        this.tamanoBuzonClasificacion = tamanoBuzonClasificacion;
        this.tamanoBuzonConsolidacion = tamanoBuzonConsolidacion;
    }

    public static Config fromFile(Path path) throws IOException {
        Properties props = new Properties();
        try (InputStream input = Files.newInputStream(path)) {
            props.load(input);
        }

        return new Config(
                requiredPositive(props, "numeroSensores"),
                requiredPositive(props, "eventosBase"),
                requiredPositive(props, "numeroClasificadores"),
                requiredPositive(props, "numeroServidores"),
                requiredPositive(props, "tamanoBuzonClasificacion"),
                requiredPositive(props, "tamanoBuzonConsolidacion"));
    }

    private static int requiredPositive(Properties props, String key) {
        String value = props.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Falta el parametro: " + key);
        }

        int number;
        try {
            number = Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El parametro " + key + " debe ser entero", e);
        }

        if (number <= 0) {
            throw new IllegalArgumentException("El parametro " + key + " debe ser positivo");
        }

        return number;
    }

    public int totalEventosEsperados() {
        int total = 0;
        for (int sensorId = 1; sensorId <= numeroSensores; sensorId++) {
            total += eventosBase * sensorId;
        }
        return total;
    }

    public int numeroSensores() {
        return numeroSensores;
    }

    public int eventosBase() {
        return eventosBase;
    }

    public int numeroClasificadores() {
        return numeroClasificadores;
    }

    public int numeroServidores() {
        return numeroServidores;
    }

    public int tamanoBuzonClasificacion() {
        return tamanoBuzonClasificacion;
    }

    public int tamanoBuzonConsolidacion() {
        return tamanoBuzonConsolidacion;
    }

    @Override
    public String toString() {
        return "Config{"
                + "numeroSensores=" + numeroSensores
                + ", eventosBase=" + eventosBase
                + ", numeroClasificadores=" + numeroClasificadores
                + ", numeroServidores=" + numeroServidores
                + ", tamanoBuzonClasificacion=" + tamanoBuzonClasificacion
                + ", tamanoBuzonConsolidacion=" + tamanoBuzonConsolidacion
                + ", totalEventosEsperados=" + totalEventosEsperados()
                + '}';
    }
}
