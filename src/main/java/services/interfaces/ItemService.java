package services.interfaces;

import domain.Item;

import java.util.Collection;

public interface ItemService {
    Item[] getItemsSearch(String query);
    void saveSearch(Item[] items);
    Collection<String> getAllItemsTitle();
    Item[] getItemsOrderBy();
    Item[] getItemsByPrice();
    Collection<Item> getItemsByTag(String tag);
}
