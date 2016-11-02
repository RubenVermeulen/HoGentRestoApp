package resto.android.hogent.be.hogentresto;

/**
 * Created by alexa on 2/11/2016.
 */

public class Restaurant {
    private String address;
    private double[] coordinates;
    private String openingHours;
    private Menu[] menus;

    public Restaurant(String address, double[] coordinates, String openingHours, Menu[] menus) {
        this.address = address;
        this.coordinates = coordinates;
        this.openingHours = openingHours;
        this.menus = menus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public double[] getCoordinates(){
        return coordinates;
    }

    public void setCoordinates(double[] coordinates){
        this.coordinates= coordinates;
    }

    public String getOpeningHours(){
        return openingHours;
    }

    public void setOpeningHours(String openingHours){
        this.openingHours=openingHours;
    }

    public Menu[] getMenus(){
        return menus;
    }

    public void setMenus(Menu[] menus){
        this.menus= menus;
    }







}

/*
name: String,
        address: String,
        coordinates: {
        lat: {type: Number, default: 0},
        long: {type: Number, default: 0}
        },
        openingHours: String,
        menus: [{type: mongoose.Schema.Types.ObjectId, ref: 'Menu'}]

        */