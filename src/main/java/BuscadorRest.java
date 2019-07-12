import com.google.gson.Gson;
import domain.Item;
import services.ItemServiceImplementacion;
import services.interfaces.ItemService;

import static spark.Spark.*;

public class BuscadorRest {
    public static void main(String[] args) {

        ItemService itemService = new ItemServiceImplementacion();

        /**
         * Se obtiene el listado de Items según la busqueda realizada
         *
         * @param query es el filtro principal por el que se realizará la búsqueda. (obligatorio)
         * @param tag atributo del Item por el cual, sumado a query, se resolverá la búsqueda. (opcional)
         * @param priceLow precio mínimo para la busqueda por rango de precio. (opcional)
         * @param priceHight precio máximo para la busqueda por rango de precio. (opcional)
         * @param orderBy este atributo puede asumir ´price´ o ´listing_type´ acompañado de ASC o DESC según el caso,
         *                ejemplo: ´price ASC´ (opcional)
         *
         * @return un listado de los Items resultantes de la busqueda de acuerdo a los filtros ingresados
         *
         */
        get("/items/search", (req, res) -> {
            res.type("application/json");
            String query = req.queryParams("query");
            String tag = req.queryParams("tag");
            String priceLow = req.queryParams("priceLow");
            String priceHight = req.queryParams("priceHight");
            String orderBy = req.queryParams("orderBy");
            return new Gson().toJsonTree(itemService.getItemsSearch(query, tag, priceLow, priceHight, orderBy));
        });

        /**
         * Se obtiene un listado de los títulos de todos los items según búsqueda
         *
         * @param query es el filtro principal por el que se realizará la búsqueda. (obligatorio)
         *
         * @return el listado de Items por título según la ´query´ utilizada.
         */
        get("/items-title", (req, res) -> {
           res.type("application/json");
            String query = req.queryParams("query");
           return new Gson().toJsonTree(itemService.getAllItemsTitle(query));
        });

        /**
         * Agregar un Item a la colección
         *
         * @param query es la busqueda realizada y el nombre que se le dio al
         *              hashmap para identificar la colección de Items según la búsqueda realizada.
         *              Sin este parámetro no se va a poder insertar un item en la lista adecuada.
         *
         * @param body el Item completo que se agregará a la colección.
         *
         * @return la confirmación de la inserción <codigo>200</codigo>
         */
        post("/item", (req, res) -> {
            res.type("application/json");
            String query = req.queryParams("query");
            Item item = new Gson().fromJson(req.body(), Item.class);
            if(itemService.saveItem(query, item)) {
                return new Gson().toJson("SUCCESS");
            } else {
                return new Gson().toJson("FAILD");
            }

        });

        /**
         * Actualizar un Item de la colección
         *
         * @param query es la busqueda realizada y el nombre que se le dio al
         *              hashmap para identificar la colección de Items según la búsqueda realizada.
         *              Sin este parámetro no se va a poder actualizar un item en la lista adecuada.
         *
         * @param body el Item completo que se actualizará en la colección.
         *
         * @return el Item actualizado
         */
        put("/item", (req, res) -> {
            res.type("application/json");
            String query = req.queryParams("query");
            Item item = new Gson().fromJson(req.body(), Item.class);
            return new Gson().toJsonTree(itemService.editItem(query, item));
        });

        /**
         * Eliminar un item a la colección
         *
         * @param query es la busqueda realizada y el nombre que se le dio al
         *              hashmap para identificar la colección de Items según la búsqueda realizada.
         *              Sin este parámetro no se va a poder eliminar un item en la lista adecuada.
         * @param id  Item que se eliminará de la colección.
         *
         * @return la confirmación de la eliminación
         */
        delete("/item", (req, res) -> {
            res.type("application/json");
            String query = req.queryParams("query");
            String id = req.queryParams("id");
            if(itemService.deleteItem(query, id)) {
                return new Gson().toJson("SUCCESS");
            } else {
                return new Gson().toJson("FAILD");
            }
        });

    }
}
