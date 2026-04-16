package caso3.model;

public final class Evento {
    private final String id;
    private final int sensorId;
    private final int secuenciaSensor;
    private final int tipoServidor;
    private final boolean fin;
    private final String origenFin;

    private Evento(
            String id,
            int sensorId,
            int secuenciaSensor,
            int tipoServidor,
            boolean fin,
            String origenFin) {
        this.id = id;
        this.sensorId = sensorId;
        this.secuenciaSensor = secuenciaSensor;
        this.tipoServidor = tipoServidor;
        this.fin = fin;
        this.origenFin = origenFin;
    }

    public static Evento normal(int sensorId, int secuenciaSensor, int tipoServidor) {
        return new Evento(
                "S" + sensorId + "-E" + secuenciaSensor,
                sensorId,
                secuenciaSensor,
                tipoServidor,
                false,
                null);
    }

    public static Evento fin(String origenFin) {
        return new Evento(
                "FIN-" + origenFin,
                0,
                0,
                0,
                true,
                origenFin);
    }

    public String id() {
        return id;
    }

    public int sensorId() {
        return sensorId;
    }

    public int secuenciaSensor() {
        return secuenciaSensor;
    }

    public int tipoServidor() {
        return tipoServidor;
    }

    public boolean esFin() {
        return fin;
    }

    public String origenFin() {
        return origenFin;
    }

    @Override
    public String toString() {
        if (fin) {
            return id;
        }

        return "Evento{"
                + "id='" + id + '\''
                + ", sensorId=" + sensorId
                + ", secuenciaSensor=" + secuenciaSensor
                + ", tipoServidor=" + tipoServidor
                + '}';
    }
}
