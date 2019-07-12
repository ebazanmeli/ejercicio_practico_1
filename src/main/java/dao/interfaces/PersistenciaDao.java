package dao.interfaces;

import domain.Item;

import java.util.List;

public interface PersistenciaDao {
    void savePersistencia(String key, List<Item> itemList);
    boolean existPersistencia(String key);
    List<Item> getPersistencia(String key);
    boolean addItem(String key, Item item);
    boolean deleteItem(String key, String idItem);
    Item editItem(String key, Item item);
}
