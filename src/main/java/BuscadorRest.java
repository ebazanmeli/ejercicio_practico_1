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
            return new Gson().toJsonTree(itemService.getItemsSearch(query));
        });

        get("/items-title", (req, res) -> {
           res.type("application/json");
           return new Gson().toJsonTree(itemService.getAllItemsTitle());
        });

    }
}
