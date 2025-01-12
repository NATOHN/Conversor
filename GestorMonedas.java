import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;

public class GestorMonedas {
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/e60f8c0288cbb5d4d8571a4d/pair/";
    private static final String API_MONEDAS_URL = "https://v6.exchangerate-api.com/v6/e60f8c0288cbb5d4d8571a4d/codes";

    // Obtener lista de monedas disponibles desde la API
    public Map<String, String> obtenerMonedasDisponibles() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(API_MONEDAS_URL)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            Map<String, String> monedasDisponibles = new HashMap<>();
            for (JsonElement moneda : jsonObject.getAsJsonArray("supported_codes")) {
                String codigo = moneda.getAsJsonArray().get(0).getAsString();
                String nombre = moneda.getAsJsonArray().get(1).getAsString();
                monedasDisponibles.put(codigo, nombre);
            }
            return monedasDisponibles;

        } catch (Exception e) {
            System.out.println("Error al obtener las monedas disponibles: " + e.getMessage());
            return null;
        }
    }

    // Obtener tasa de cambio entre monedas
    public double obtenerTasaCambio(String monedaOrigen, String monedaDestino) {
        try {
            String url = BASE_URL + monedaOrigen + "/" + monedaDestino;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            if (!jsonObject.has("conversion_rate")) {
                throw new Exception("El objeto 'conversion_rate' no está disponible.");
            }
            return jsonObject.get("conversion_rate").getAsDouble();

        } catch (Exception e) {
            System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
            return -1;
        }
    }
}
