package services.interfaces;

import domain.Item;

import java.util.List;

public interface IServiceConection {
    List<Item> getApiResult(String query);
}
