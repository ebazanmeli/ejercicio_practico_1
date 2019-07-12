package dao;

import dao.interfaces.PersistenciaDao;
import domain.Item;

import java.util.HashMap;
import java.util.List;

public class HashMapDao implements PersistenciaDao {
    private HashMap<String, List<Item>> itemHashMap;

    public HashMapDao() {
        this.itemHashMap = new HashMap<>();
    }

    @Override
    public void savePersistencia(String key, List<Item> itemList) {
        itemHashMap.put(key, itemList);
    }

    @Override
    public boolean existPersistencia(String key) {
        if (itemHashMap.get(key) == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<Item> getPersistencia(String key) {
        return itemHashMap.get(key);
    }

    @Override
    public boolean addItem(String key, Item item) {
        List<Item> itemList = itemHashMap.get(key);
        int size = itemList.size();
        itemList.add(item);

        if(size == itemList.size()) {
            return false;
        } else {
            itemHashMap.put(key, itemList);
            return true;
        }
    }

    @Override
    public boolean deleteItem(String key, String idItem) {
        List<Item> itemList = itemHashMap.get(key);
        int size = itemList.size();
        for(Item item : itemList) {
            if(item.getId().equalsIgnoreCase(idItem)){
                itemList.remove(item);
                break;
            }
        }

        if(size == itemList.size()) {
            return false;
        } else {
            itemHashMap.put(key, itemList);
            return true;
        }
    }

    @Override
    public Item editItem(String key, Item item) {
        if(deleteItem(key, item.getId()) && addItem(key, item)) {
            return item;
        } else {
            return null;
        }
    }
}
