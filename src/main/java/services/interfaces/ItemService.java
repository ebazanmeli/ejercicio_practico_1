package services.interfaces;

import domain.Item;

import java.util.Collection;

public interface ItemService {
    Collection<Item> getItemsSearch(String query, String tag, String priceLow, String priceHight, String orderBy);
    Collection<String> getAllItemsTitle(String query);
    boolean saveItem(String query, Item item);
    Item editItem(String query, Item item);
    boolean deleteItem(String query, String idItemDeleted);
}
