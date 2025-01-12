import java.util.ArrayList;
import java.util.List;

public class ConversorMonedas {
    private static final List<Historial> historialConversiones = new ArrayList<>();

    public static double convertir(Moneda origen, Moneda destino, double cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero.");
        }

        double tasaOrigen = origen.getTasaCambio();
        double tasaDestino = destino.getTasaCambio();

        if (tasaOrigen <= 0 || tasaDestino <= 0) {
            throw new IllegalArgumentException("Las tasas de cambio deben ser mayores a cero.");
        }

        double resultado = (cantidad / tasaOrigen) * tasaDestino;

        // Agregar la conversión al historial con marca de tiempo
        historialConversiones.add(new Historial(origen.getCodigo(), destino.getCodigo(), cantidad, resultado));
        return resultado;
    }

    public static void mostrarHistorial() {
        if (historialConversiones.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            System.out.println("\n======= HISTORIAL DE CONVERSIONES =======");
            for (Historial registro : historialConversiones) {
                System.out.println(registro);
            }
            System.out.println("=========================================\n");
        }
    }
}
