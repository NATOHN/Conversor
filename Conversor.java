import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;

public class Conversor {
    private static ArrayList<Historial> historialConversiones = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GestorMonedas gestorMonedas = new GestorMonedas();
        Map<String, String> monedasDisponibles = gestorMonedas.obtenerMonedasDisponibles();

        if (monedasDisponibles == null) {
            System.out.println("No se pudieron cargar las monedas disponibles. Verifique la conexión.");
            return;
        }

        boolean continuar = true;
        while (continuar) {
            imprimirMenuPrincipal(monedasDisponibles);

            System.out.print("Elige una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    convertirMonedas(scanner, gestorMonedas, monedasDisponibles);
                    break;
                case 2:
                    mostrarHistorial();
                    break;
                case 3:
                    continuar = false;
                    break;
                default:
                    System.out.println("⚠ Opción inválida.");
            }
        }
        System.out.println("✨ Gracias por usar el Conversor de Monedas !Global Exchange¡. ¡Hasta pronto!");
    }

    private static void imprimirMenuPrincipal(Map<String, String> monedasDisponibles) {
        System.out.println("\n==============================");
        System.out.println("     CONVERSOR DE MONEDAS");
        System.out.println("==============================");
        System.out.println("Monedas disponibles:");
        monedasDisponibles.forEach((codigo, nombre) -> System.out.println("- " + codigo + ": " + nombre));
        System.out.println("------------------------------");
        System.out.println("1. Convertir monedas");
        System.out.println("2. Mostrar historial");
        System.out.println("3. Salir");
        System.out.println("==============================");
    }

    private static void convertirMonedas(Scanner scanner, GestorMonedas gestorMonedas, Map<String, String> monedasDisponibles) {
        System.out.print("Elige la moneda de origen (código): ");
        String monedaOrigen = scanner.nextLine().toUpperCase();
        if (!monedasDisponibles.containsKey(monedaOrigen)) {
            System.out.println("⚠ Moneda de origen no válida.");
            return;
        }

        System.out.print("Elige la moneda de destino (código): ");
        String monedaDestino = scanner.nextLine().toUpperCase();
        if (!monedasDisponibles.containsKey(monedaDestino)) {
            System.out.println("⚠ Moneda de destino no válida.");
            return;
        }

        System.out.print("Ingresa la cantidad a convertir: ");
        double cantidad = scanner.nextDouble();
        scanner.nextLine();

        double tasaCambio = gestorMonedas.obtenerTasaCambio(monedaOrigen, monedaDestino);
        if (tasaCambio == -1) {
            System.out.println("⚠ No se pudo realizar la conversión.");
        } else {
            double resultado = cantidad * tasaCambio;
            System.out.printf("\n==============================\n");
            System.out.printf("%.2f %s equivale a %.2f %s\n", cantidad, monedaOrigen, resultado, monedaDestino);
            System.out.printf("==============================\n");

            // Guardar en el historial
            historialConversiones.add(new Historial(monedaOrigen, monedaDestino, cantidad, resultado));
        }
    }

    private static void mostrarHistorial() {
        System.out.println("\n======= HISTORIAL DE CONVERSIONES =======");
        if (historialConversiones.isEmpty()) {
            System.out.println("No hay conversiones registradas.");
        } else {
            for (Historial registro : historialConversiones) {
                System.out.println(registro);
            }
        }
    }
}
