package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Item;
import services.interfaces.IServiceConection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceConection implements IServiceConection {

    public ServiceConection() {
    }

    @Override
    public List<Item> getApiResult(String query) {
        List<Item> itemList = new ArrayList<>();
        Item[] items = null;
        URL url = null;

        try {
            url = new URL("https://api.mercadolibre.com/sites/MLA/search?q=" + query);
            URLConnection urlConnection = null;
            urlConnection = url.openConnection();

            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

            } else {
                System.out.println("URL inválida");
            }

            BufferedReader in = null;
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String urlString = in.lines().collect(Collectors.joining());
            in.close();

            JsonObject jsonObject = new JsonParser().parse(urlString).getAsJsonObject();

            Gson gson = new Gson();
            items = gson.fromJson(jsonObject.getAsJsonArray("results"), Item[].class);

            itemList = obtenerList(items);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemList;
    }

    private List<Item> obtenerList(Item[] items) {
        List<Item> itemList = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            itemList.add(items[i]);
        }
        return itemList;
    }
}

