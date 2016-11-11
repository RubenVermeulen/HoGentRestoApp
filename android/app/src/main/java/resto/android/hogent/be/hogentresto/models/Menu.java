package resto.android.hogent.be.hogentresto.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by alexa on 2/11/2016.
 */

public class Menu implements Serializable {
    String id;
    String title;
    String description;
    double price;
    Date availableAt;

    public Menu() {
    }

    public Menu(String id, String title, String description, double price, Date availableAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.availableAt = availableAt;
    }

    public String getId() {
        return id;
    }

    public Menu setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Menu setTitle(String title) {

        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Menu setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Menu setPrice(double price) {
        this.price = price;
        return this;
    }

    public Date getAvailableAt() {
        return availableAt;
    }

    public Menu setAvailableAt(Date availableAt) {
        this.availableAt = availableAt;
        return this;
    }
}



