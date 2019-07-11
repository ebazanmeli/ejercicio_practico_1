import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

import static spark.Spark.post;

public class BuscadorRest {
    public static void main(String[] args) {

        post("/search", (req, res) -> {
            String codigo = req.queryParams("codigo");
            String q = req.queryParams("q");

            URL url = new URL("https://api.mercadolibre.com/sites/MLA/listing_types");
            URLConnection urlConnection = url.openConnection();

            HttpURLConnection connection = null;
            if(urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");

            } else {
                System.out.println("URL inválida");
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String urlString = in.lines().collect(Collectors.joining());
            in.close();
        });

    }
}
