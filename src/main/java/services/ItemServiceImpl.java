package services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Item;
import services.interfaces.ItemService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {

    private HashMap<String, Item> itemHashMap;

    public ItemServiceImpl() {
        itemHashMap = new HashMap<>();
    }

    public ItemServiceImpl(HashMap<String, Item> itemHashMap) {
        this.itemHashMap = itemHashMap;
    }

    @Override
    public Item[] getItemsSearch(String query) {

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

            saveSearch(items);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    @Override
    public void saveSearch(Item[] items) {
        for (int i = 0; i < items.length; i++) {
            itemHashMap.put(items[i].getId(), items[i]);
        }
    }

    @Override
    public Item[] getAllItemsTitle() {
        return new Item[0];
    }

    @Override
    public Item[] getItemsOrderBy() {
        return new Item[0];
    }

    @Override
    public Item[] getItemsByPrice() {
        return new Item[0];
    }

    @Override
    public Item[] getItemsByTag() {
        return new Item[0];
    }
}
