import com.google.gson.Gson;
import services.ItemServiceImpl;
import services.interfaces.ItemService;

import static spark.Spark.get;

public class BuscadorRest {
    public static void main(String[] args) {

        ItemService itemService = new ItemServiceImpl();

        get("/items/search", (req, res) -> {
            res.type("application/json");
            String query = req.queryParams("query");
            String tag = req.queryParams("tag");
            String priceLow = req.queryParams("priceLow");
            String priceHight = req.queryParams("priceHight");
            return new Gson().toJsonTree(itemService.getItemsSearch(query, tag, priceLow, priceHight));
        });

        get("/items-title", (req, res) -> {
           res.type("application/json");
            String query = req.queryParams("query");
           return new Gson().toJsonTree(itemService.getAllItemsTitle(query));
        });

    }
}
