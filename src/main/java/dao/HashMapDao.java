package dao;

import domain.Item;

import java.util.HashMap;
import java.util.List;

public class HashMapDao implements IHashMapDao {
    private HashMap<String, List<Item>> itemHashMap;

    public HashMapDao() {
        this.itemHashMap = new HashMap<>();
    }

    @Override
    public void saveHashMap(String key, List<Item> itemList) {
        itemHashMap.put(key, itemList);
    }

    @Override
    public boolean existHashMap(String key) {
        if (itemHashMap.get(key) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<Item> getHashMap(String key) {
        return itemHashMap.get(key);
    }

    @Override
    public void addItem(String key, Item item) {
        itemHashMap.get(key).add(item);
    }

    @Override
    public void deleteItem(String key, String idItem) {
        itemHashMap.get(key).remove(idItem);
    }
}
