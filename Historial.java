import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Historial {
    private String monedaOrigen;
    private String monedaDestino;
    private double cantidad;
    private double resultado;
    private String timestamp;

    public Historial(String monedaOrigen, String monedaDestino, double cantidad, double resultado) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.cantidad = cantidad;
        this.resultado = resultado;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format("De %s a %s: %.2f -> %.2f (Fecha: %s)",
                monedaOrigen, monedaDestino, cantidad, resultado, timestamp);
    }
}
