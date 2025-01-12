import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;

public class MonedasDisponibles {
    // Mapa para almacenar las monedas disponibles
    private static final Map<String, Moneda> monedas = new HashMap<>();
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/YOUR_API_KEY/codes";

    static {
        cargarMonedasDesdeAPI();
    }

    private static void cargarMonedasDesdeAPI() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_URL)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if (jsonObject.has("supported_codes")) {
                for (JsonElement element : jsonObject.getAsJsonArray("supported_codes")) {
                    String codigo = element.getAsJsonArray().get(0).getAsString();
                    String nombre = element.getAsJsonArray().get(1).getAsString();
                    monedas.put(codigo, new Moneda(codigo, nombre, 1.0));
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las monedas desde la API: " + e.getMessage());
        }
    }

    public static Moneda obtenerMoneda(String codigo) {
        return monedas.get(codigo);
    }

    public static Map<String, Moneda> obtenerTodasLasMonedas() {
        return monedas;
    }
}
