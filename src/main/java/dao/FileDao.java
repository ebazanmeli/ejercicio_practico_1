package dao;

import dao.interfaces.PersistenciaDao;
import domain.Item;

import java.io.FileWriter;
import java.util.List;

public class FileDao implements PersistenciaDao {

    FileWriter file = null;

    @Override
    public void savePersistencia(String key, List<Item> itemList) {
        try {
            file = new FileWriter(key + ".json");
            file.write(itemList.toString());
            file.flush();
            file.close();
        } catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
        }
    }

    @Override
    public boolean existPersistencia(String key) {
        return false;
    }

    @Override
    public List<Item> getPersistencia(String key) {
        return null;
    }

    @Override
    public boolean addItem(String key, Item item) {
        return false;
    }

    @Override
    public boolean deleteItem(String key, String idItem) {
        return false;
    }

    @Override
    public Item editItem(String key, Item item) {
        return null;
    }
}
