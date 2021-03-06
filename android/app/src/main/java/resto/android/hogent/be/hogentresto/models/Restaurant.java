package resto.android.hogent.be.hogentresto.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Restaurant implements Serializable {
    @SerializedName("_id")
    private String id;
    private String name;
    private String address;
    private Coordinate coordinates;
    private String openingHours;
    private transient List<Menu> menus;
    private String urlImage;
    private double occupancy;
    private List<OccupancyUnit> forecast;

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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public double getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(double occupancy) {
        this.occupancy = occupancy;
    }

    public List<OccupancyUnit> getForecast() {
        return forecast;
    }

    public void setForecast(List<OccupancyUnit> forecast) {
        this.forecast = forecast;
    }
}