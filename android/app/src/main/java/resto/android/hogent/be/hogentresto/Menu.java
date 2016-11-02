package resto.android.hogent.be.hogentresto;

import java.util.Date;

/**
 * Created by alexa on 2/11/2016.
 */

public class Menu {
    String title;
    String description;
    Number price;
    Date availableAt;


    public Menu(String title, String description, Number price, Date availableAt) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.availableAt = availableAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Number getPrice() {
        return price;
    }

    public void setPrice(Number price) {
        this.price = price;
    }
}

