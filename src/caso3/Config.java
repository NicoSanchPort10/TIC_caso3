package caso3;

import java.io.IOException;
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

    private Config(int numeroSensores, int eventosBase, int numeroClasificadores,
                   int numeroServidores, int tamanoBuzonClasificacion, int tamanoBuzonConsolidacion) {
        this.numeroSensores = numeroSensores;
        this.eventosBase = eventosBase;
        this.numeroClasificadores = numeroClasificadores;
        this.numeroServidores = numeroServidores;
        this.tamanoBuzonClasificacion = tamanoBuzonClasificacion;
        this.tamanoBuzonConsolidacion = tamanoBuzonConsolidacion;
    }

    public static Config fromFile(Path path) throws IOException {
        Properties props = new Properties();
        props.load(Files.newBufferedReader(path));

        int ni   = Integer.parseInt(props.getProperty("numeroSensores"));
        int base = Integer.parseInt(props.getProperty("eventosBase"));
        int nc   = Integer.parseInt(props.getProperty("numeroClasificadores"));
        int ns   = Integer.parseInt(props.getProperty("numeroServidores"));
        int tam1 = Integer.parseInt(props.getProperty("tamanoBuzonClasificacion"));
        int tam2 = Integer.parseInt(props.getProperty("tamanoBuzonConsolidacion"));

        return new Config(ni, base, nc, ns, tam1, tam2);
    }

    public int numeroSensores()             { return numeroSensores; }
    public int eventosBase()                { return eventosBase; }
    public int numeroClasificadores()       { return numeroClasificadores; }
    public int numeroServidores()           { return numeroServidores; }
    public int tamanoBuzonClasificacion()   { return tamanoBuzonClasificacion; }
    public int tamanoBuzonConsolidacion()   { return tamanoBuzonConsolidacion; }

    public int totalEventosEsperados() {
        return eventosBase * numeroSensores * (numeroSensores + 1) / 2;
    }

    @Override
    public String toString() {
        return "Config{ni=" + numeroSensores + ", base=" + eventosBase
                + ", nc=" + numeroClasificadores + ", ns=" + numeroServidores
                + ", tam1=" + tamanoBuzonClasificacion + ", tam2=" + tamanoBuzonConsolidacion
                + ", totalEventos=" + totalEventosEsperados() + "}";
    }
}
