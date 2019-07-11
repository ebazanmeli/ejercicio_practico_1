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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class ItemServiceImpl implements ItemService {

    private HashMap<String, Item[]> itemHashMap;

    public ItemServiceImpl() {
        itemHashMap = new HashMap<>();
    }

    public ItemServiceImpl(HashMap<String, Item[]> itemHashMap) {
        this.itemHashMap = itemHashMap;
    }

    @Override
    public Collection<Item> getItemsSearch(String query, String tag, String priceLow, String priceHight) {
        Collection<Item> itemCollection;
        if(itemHashMap.get(query) == null) {
            itemCollection = getAllItems(query);
        } else {
            itemCollection = obtenerArrayList(itemHashMap.get(query));
        }

        if(tag != null) {
            itemCollection = getItemsByTag(query, tag);
        }

        if(priceLow != null && priceHight != null) {
            itemCollection = getItemsByPrice(query, Integer.parseInt(priceLow), Integer.parseInt(priceHight));
        }

        return itemCollection;
    }

    private Collection<Item> getAllItems(String query) {
        Collection<Item> itemCollection = new ArrayList<>();
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

            itemCollection = obtenerArrayList(items);

            saveSearch(query, items);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemCollection;
    }

    private Collection<Item> obtenerArrayList(Item[] items) {
        Collection<Item> itemCollection = new ArrayList<>();
        for(int i = 0; i < items.length; i++) {
            itemCollection.add(items[i]);
        }
        return itemCollection;
    }

    private void saveSearch(String query, Item[] items) {
        itemHashMap.put(query, items);
    }

    @Override
    public Collection<String> getAllItemsTitle(String query) {
        Collection<String> titleCollections = new ArrayList<>();
        Item[] item = itemHashMap.get(query);
        for (int i = 0; i < item.length; i++) {
            titleCollections.add(item[i].getTitle());
        }

        return titleCollections;
    }

    private Collection<Item> getItemsByPrice(String query, Integer priceLow, Integer priceHight) {
        Item[] item = itemHashMap.get(query);
        Collection<Item> itemsFiltrados = new ArrayList<>();
        for (int i = 0; i < item.length; i++) {
            if(item[i].getPrice() > priceLow && item[i].getPrice() < priceHight) {
                itemsFiltrados.add(item[i]);
            }
        }

        return itemsFiltrados;
    }

    private Collection<Item> getItemsByTag(String query, String tag) {
        Item[] item = itemHashMap.get(query);
        Collection<Item> itemsFiltrados = new ArrayList<>();
        for (int i = 0; i < item.length; i++) {
            if (item[i].getTags() != null) {
                String[] tags = item[i].getTags();
                for (int j = 0; j < tags.length; j++) {
                    if (tags[j].compareTo(tag) == 0) {
                        itemsFiltrados.add(item[i]);
                    }
                }
            }
        }

        return itemsFiltrados;
    }
}
