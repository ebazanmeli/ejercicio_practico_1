package dao;

import domain.Item;

import java.util.List;

public interface IHashMapDao {
    void saveHashMap(String key, List<Item> itemList);
    boolean existHashMap(String key);
    List<Item> getHashMap(String key);
    void addItem(String key, Item item);
    void deleteItem(String key, String idItem);
}
