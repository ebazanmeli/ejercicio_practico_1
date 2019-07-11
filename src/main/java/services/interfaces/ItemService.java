package services.interfaces;

import domain.Item;

public interface ItemService {
    Item[] getItemsSearch(String query);
    void saveSearch(Item[] items);
    Item[] getAllItemsTitle();
    Item[] getItemsOrderBy();
    Item[] getItemsByPrice();
    Item[] getItemsByTag();
}
