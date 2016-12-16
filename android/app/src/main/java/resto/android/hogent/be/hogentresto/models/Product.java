package resto.android.hogent.be.hogentresto.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jonas on 28/11/2016.
 */

public class Product implements Serializable
{
        @SerializedName("_id")
        String id;
        String description;
        List<String> allergens;



        public Product(String description, List<String> allergens) {
            this.description= description;
            this.allergens=allergens;

        }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(ArrayList<String> allergens) {
        this.allergens = allergens;
    }
}



