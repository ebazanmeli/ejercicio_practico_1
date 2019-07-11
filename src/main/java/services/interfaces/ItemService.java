package services.interfaces;

import domain.Item;

import java.util.Collection;

public interface ItemService {
    Collection<Item> getItemsSearch(String query, String tag, String priceLow, String priceHight);
    Collection<String> getAllItemsTitle(String query);
}
