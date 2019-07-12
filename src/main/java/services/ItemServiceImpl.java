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

    private HashMap<String, Collection<Item>> itemHashMap;

    public ItemServiceImpl() {
        itemHashMap = new HashMap<>();
    }

    public ItemServiceImpl(HashMap<String, Collection<Item>> itemHashMap) {
        this.itemHashMap = itemHashMap;
    }

    @Override
    public Collection<Item> getItemsSearch(String query, String tag, String priceLow, String priceHight, String orderBy) {
        Collection<Item> itemCollection;
        if (itemHashMap.get(query) == null) {
            itemCollection = getAllItems(query);
        } else {
            itemCollection = itemHashMap.get(query);
        }

        if (tag != null) {
            itemCollection = getItemsByTag(query, tag);
        }

        if (priceLow != null && priceHight != null) {
            itemCollection = getItemsByPrice(query, Integer.parseInt(priceLow), Integer.parseInt(priceHight));
        }
        
        if(orderBy != null) {
            itemCollection = getItemsOrderBy(query, orderBy);
        }

        return itemCollection;
    }

    private Collection<Item> getItemsOrderBy(String query, String orderBy) {
        Collection<Item> itemCollection = new ArrayList<>();
        Collection<Item> items = itemHashMap.get(query);

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

            itemCollection = obtenerCollection(items);

            saveSearch(query, itemCollection);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return itemCollection;
    }

    private Collection<Item> obtenerCollection(Item[] items) {
        Collection<Item> itemCollection = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            itemCollection.add(items[i]);
        }
        return itemCollection;
    }

    private void saveSearch(String query, Collection<Item> items) {
        itemHashMap.put(query, items);
    }

    @Override
    public Collection<String> getAllItemsTitle(String query) {
        Collection<String> titleCollections = new ArrayList<>();
        Collection<Item> items = itemHashMap.get(query);
        for (Item item : items) {
            titleCollections.add(item.getTitle());
        }

        return titleCollections;
    }

    private Collection<Item> getItemsByPrice(String query, Integer priceLow, Integer priceHight) {
        Collection<Item> items = itemHashMap.get(query);
        Collection<Item> itemsFiltrados = new ArrayList<>();

        for (Item item : items) {
            if (item.getPrice() > priceLow && item.getPrice() < priceHight) {
                itemsFiltrados.add(item);
            }
        }

        return itemsFiltrados;
    }

    private Collection<Item> getItemsByTag(String query, String tag) {
        Collection<Item> items = itemHashMap.get(query);
        Collection<Item> itemsFiltrados = new ArrayList<>();

        for (Item item : items) {
            if (item.getTags() != null) {
                String[] tags = item.getTags();
                for (int j = 0; j < tags.length; j++) {
                    if (tags[j].compareTo(tag) == 0) {
                        itemsFiltrados.add(item);
                    }
                }
            }
        }

        return itemsFiltrados;
    }

    @Override
    public void saveItem(String query, Item item) {
        Collection<Item> items = itemHashMap.get(query);
        items.add(item);
        itemHashMap.put(query, items);
    }
}
