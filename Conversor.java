import java.util.Scanner;

public class Conversor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("Conversor de Monedas");
            System.out.println("Monedas disponibles:");
            System.out.println("ARS - Peso Argentino");
            System.out.println("BOB - Boliviano");
            System.out.println("BRL - Real Brasileño");
            System.out.println("CLP - Peso Chileno");
            System.out.println("COP - Peso Colombiano");
            System.out.println("USD - Dólar Estadounidense");
            System.out.println("1. Convertir monedas");
            System.out.println("2. Salir");
            System.out.print("Elige una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    convertirMonedas(scanner);
                    break;
                case 2:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }

        System.out.println("Gracias por usar el conversor.");
    }

    private static void convertirMonedas(Scanner scanner) {
        System.out.print("Elige la moneda de origen (código): ");
        String monedaOrigen = scanner.nextLine().toUpperCase();

        System.out.print("Elige la moneda de destino (código): ");
        String monedaDestino = scanner.nextLine().toUpperCase();

        System.out.print("Ingresa la cantidad a convertir: ");
        double cantidad = scanner.nextDouble();
        scanner.nextLine(); // Consumir el salto de línea

        // Crear instancia de GestorMonedas y obtener la tasa
        GestorMonedas gestorMonedas = new GestorMonedas();
        double tasaCambio = gestorMonedas.obtenerTasaCambio(monedaOrigen, monedaDestino);

        if (tasaCambio == -1) {
            System.out.println("No se pudo realizar la conversión. Verifica las monedas o la conexión a la API.");
            return;
        }

        // Realizar la conversión
        double resultado = cantidad * tasaCambio;
        System.out.printf("Resultado: %.2f %s\n", resultado, monedaDestino);
    }
}
