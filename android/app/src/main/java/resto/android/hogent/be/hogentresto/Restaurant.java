package resto.android.hogent.be.hogentresto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexa on 2/11/2016.
 */

public class Restaurant {
    @SerializedName("_id")
    private String id;
    private String name;
    private String address;
    private Coordinate coordinates;
    private String openingHours;
    private transient List<Menu> menus;

    public Restaurant() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public Coordinate getCoordinates(){
        return coordinates;
    }

    public void setCoordinates(Coordinate coordinates){
        this.coordinates= coordinates;
    }

    public String getOpeningHours(){
        return openingHours;
    }

    public void setOpeningHours(String openingHours){
        this.openingHours=openingHours;
    }

    public List<Menu> getMenus(){
        return menus;
    }

    public void setMenus(List<Menu> menus){
        this.menus= menus;
    }
}