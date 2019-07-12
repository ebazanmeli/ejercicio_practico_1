import com.google.gson.Gson;
import domain.Item;
import services.ItemServiceImpl;
import services.interfaces.ItemService;

import static spark.Spark.get;
import static spark.Spark.post;

public class BuscadorRest {
    public static void main(String[] args) {

        ItemService itemService = new ItemServiceImpl();

        get("/items/search", (req, res) -> {
            res.type("application/json");
            String query = req.queryParams("query");
            String tag = req.queryParams("tag");
            String priceLow = req.queryParams("priceLow");
            String priceHight = req.queryParams("priceHight");
            String orderBy = req.queryParams("orderBy");
            return new Gson().toJsonTree(itemService.getItemsSearch(query, tag, priceLow, priceHight, orderBy));
        });

        get("/items-title", (req, res) -> {
           res.type("application/json");
            String query = req.queryParams("query");
           return new Gson().toJsonTree(itemService.getAllItemsTitle(query));
        });

        post("/item", (req, res) -> {
            res.type("application/json");
            String query = req.queryParams("query");
            Item item = new Gson().fromJson(req.body(), Item.class);
            itemService.saveItem(query, item);
            return new Gson().toJson("SUCCESS");
        });

    }
}
