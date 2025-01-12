import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GestorMonedas {
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/e60f8c0288cbb5d4d8571a4d/pair/";

    public double obtenerTasaCambio(String monedaOrigen, String monedaDestino) {
        try {
            String url = BASE_URL + monedaOrigen + "/" + monedaDestino;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Imprimir el JSON para depuración
            System.out.println("Respuesta de la API: " + response.body());

            JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();

            // Validar si 'conversion_rate' está en la respuesta
            if (!jsonObject.has("conversion_rate")) {
                throw new Exception("El objeto 'conversion_rate' no está disponible en la respuesta JSON.");
            }

            return jsonObject.get("conversion_rate").getAsDouble();

        } catch (Exception e) {
            System.out.println("Error al obtener la tasa de cambio: " + e.getMessage());
            return -1; // Retornar -1 para indicar error
        }
    }
}
