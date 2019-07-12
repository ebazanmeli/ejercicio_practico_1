package services;

import dao.HashMapDao;
import dao.interfaces.PersistenciaDao;
import domain.Item;
import services.interfaces.IServiceConection;
import services.interfaces.ItemService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ItemServiceImplementacion implements ItemService {

    private List<Item> itemListTemp = new ArrayList<>();
    private IServiceConection serviceConection;
    private PersistenciaDao hashMapDao;

    public ItemServiceImplementacion() {
        serviceConection = new ServiceConection();
        hashMapDao = new HashMapDao();
    }

    @Override
    public List<Item> getItemsSearch(String query, String tag, String priceLow, String priceHight, String orderBy) {
        if (!hashMapDao.existPersistencia(query)) {
            itemListTemp = serviceConection.getApiResult(query);
            hashMapDao.savePersistencia(query, itemListTemp);
        } else {
            itemListTemp = hashMapDao.getPersistencia(query);
        }

        if (tag != null) {
            getItemsByTag(tag);
        }

        if (priceLow != null && priceHight != null) {
            getItemsByPrice(Integer.parseInt(priceLow), Integer.parseInt(priceHight));
        }
        
        if(orderBy != null) {
            getItemsOrderBy(orderBy);
        }

        return itemListTemp;
    }

    @Override
    public List<String> getAllItemsTitle(String query) {
        List<String> titleList = new ArrayList<>();
        List<Item> items = hashMapDao.getPersistencia(query);
        for (Item item : items) {
            titleList.add(item.getTitle());
        }

        return titleList;
    }

    private void getItemsOrderBy(String orderBy) {
        String order = orderBy.split(" ")[0].trim();
        String way = orderBy.split(" ")[1].trim();
        List<Item> itemList = new ArrayList<>();
        List<Item> items = itemListTemp;
        if(way.equalsIgnoreCase("ASC")) {
            itemList.addAll(orderAscendente(order, items));
        } else if(way.equalsIgnoreCase("DESC")) {
            itemList.addAll(orderDescendente(order, items));
        }

        itemListTemp = itemList;
    }

    private List<Item> orderAscendente(String order, List<Item> items) {
        if(order.equalsIgnoreCase("PRICE")) {
            items.sort(Comparator.comparingInt(Item::getPrice));
        } else if(order.equalsIgnoreCase("LISTING_TYPE")) {
            items.sort(Comparator.comparing(Item::getListing_type_id));
        }
        return items;
    }

    private List<Item> orderDescendente(String order, List<Item> items) {
        if(order.equalsIgnoreCase("PRICE")) {
            items.sort(Comparator.comparingInt(Item::getPrice).reversed());
        } else if(order.equalsIgnoreCase("LISTING_TYPE")) {
            items.sort(Comparator.comparing(Item::getListing_type_id).reversed());
        }
        return items;
    }

    private void getItemsByPrice(Integer priceLow, Integer priceHight) {
        List<Item> items = itemListTemp;
        List<Item> itemsFiltrados = new ArrayList<>();

        for (Item item : items) {
            if (item.getPrice() > priceLow && item.getPrice() < priceHight) {
                itemsFiltrados.add(item);
            }
        }

        itemListTemp = itemsFiltrados;
    }

    private void getItemsByTag(String tag) {
        List<Item> items = itemListTemp;
        List<Item> itemsFiltrados = new ArrayList<>();

        for (Item item : items) {
            if (item.getTags() != null) {
                String[] tags = item.getTags();
                for (int j = 0; j < tags.length; j++) {
                    if (tags[j].compareTo(tag) == 0) {
                        itemsFiltrados.add(item);
                        break;
                    }
                }
            }
        }

        itemListTemp = itemsFiltrados;
    }

    @Override
    public boolean saveItem(String query, Item item) {
        return hashMapDao.addItem(query, item);
    }

    @Override
    public Item editItem(String query, Item itemEditado) {
        return hashMapDao.editItem(query, itemEditado);
    }

    @Override
    public boolean deleteItem(String query, String idItemDeleted) {
        return hashMapDao.deleteItem(query, idItemDeleted);
    }
}
