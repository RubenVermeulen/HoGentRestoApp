package resto.android.hogent.be.hogentresto.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Menu implements Serializable {
    @SerializedName("_id")
    String id;
    String title;
    Product product;
    double price;
    Date availableAt;

    public Menu(String title, Product product, double price, Date availableAt) {
        this.availableAt = availableAt;
        this.price = price;
        this.product=product;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getAvailableAt() {
        return availableAt;
    }

    public void setAvailableAt(Date availableAt) {
        this.availableAt = availableAt;
    }

    public Product getProduct() {        return product;    }

    public void setProduct(Product product) {        this.product = product;    }
}



