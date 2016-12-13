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
    private double occupation;
    private List<OccupancyUnit> forecast;

    public Restaurant() {

    }

    public Restaurant(Coordinate coordinates, String name, String address, String openingHours, List<Menu> menus, String urlImage, List<OccupancyUnit> forecast, double occupation) {
        this.coordinates = coordinates;
        this.name = name;
        this.address = address;
        this.openingHours = openingHours;
        this.menus = menus;
        this.urlImage = urlImage;
        this.forecast = forecast;
        this.occupation = occupation;
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

    public double getOccupation() {
        return occupation;
    }

    public void setOccupation(double occupation) {
        this.occupation = occupation;
    }

    public List<OccupancyUnit> getForecast() {
        return forecast;
    }

    public void setForecast(List<OccupancyUnit> forecast) {
        this.forecast = forecast;
    }
}