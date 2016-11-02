package resto.android.hogent.be.hogentresto;

import java.io.Serializable;

/**
 * Created by alexa on 2/11/2016.
 */

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private String address;
    private double[] coordinates;
    private String openingHours;
    private Menu[] menus;

    public Restaurant() {
    }

    public Restaurant(String id, String name, String address, double[] coordinates, String openingHours, Menu[] menus) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.coordinates = coordinates;
        this.openingHours = openingHours;
        this.menus = menus;
    }

    public String getId() {
        return id;
    }

    public Restaurant setId(String id) {
        this.id = id;

        return this;
    }

    public String getName() {
        return name;
    }

    public Restaurant setName(String name) {
        this.name = name;

        return this;
    }

    public String getAddress() {
        return address;
    }

    public Restaurant setAddress(String address){
        this.address=address;

        return this;
    }

    public double[] getCoordinates(){
        return coordinates;
    }

    public Restaurant setCoordinates(double[] coordinates){
        this.coordinates= coordinates;

        return this;
    }

    public String getOpeningHours(){
        return openingHours;
    }

    public Restaurant setOpeningHours(String openingHours){
        this.openingHours=openingHours;

        return this;
    }

    public Menu[] getMenus(){
        return menus;
    }

    public Restaurant setMenus(Menu[] menus){
        this.menus= menus;

        return this;
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