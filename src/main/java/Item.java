import java.util.Arrays;

public class Item {

    private String id;
    private String site_id;
    private String title;
    private int price;
    private Currency currency;
    private String listing_type_id;
    private String stop_time;
    private String thumbnail;
    private String[] tags;

    public Item() {
    }

    public Item(String id, String site_id, String title, int price, Currency currency, String listing_type_id, String stop_time, String thumbnail, String[] tags) {
        this.id = id;
        this.site_id = site_id;
        this.title = title;
        this.price = price;
        this.currency = currency;
        this.listing_type_id = listing_type_id;
        this.stop_time = stop_time;
        this.thumbnail = thumbnail;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getListing_type_id() {
        return listing_type_id;
    }

    public void setListing_type_id(String listing_type_id) {
        this.listing_type_id = listing_type_id;
    }

    public String getStop_time() {
        return stop_time;
    }

    public void setStop_time(String stop_time) {
        this.stop_time = stop_time;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", site_id='" + site_id + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                ", listing_type_id='" + listing_type_id + '\'' +
                ", stop_time='" + stop_time + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
