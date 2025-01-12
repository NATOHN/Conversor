public class ConversorMonedas {

    public static double convertir(Moneda origen, Moneda destino, double cantidad) {
        double tasaOrigen = origen.getTasaCambio();
        double tasaDestino = destino.getTasaCambio();
        return (cantidad / tasaOrigen) * tasaDestino;
    }
}
