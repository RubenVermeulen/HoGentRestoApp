package resto.android.hogent.be.hogentresto.MenuContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import resto.android.hogent.be.hogentresto.models.Coordinate;
import resto.android.hogent.be.hogentresto.models.OccupancyUnit;
import resto.android.hogent.be.hogentresto.models.Restaurant;

/**
 * Created by jonas on 12/12/2016.
 */

public class RestaurantContext {
    Coordinate c = new Coordinate();

    MenuContext context = new MenuContext();
    List<Integer> cordinates = new ArrayList<Integer>(Arrays.asList(25, 25));
    Date nov28 = new GregorianCalendar(2016, Calendar.NOVEMBER, 28).getTime();
    OccupancyUnit unit = new OccupancyUnit(nov28, 0.8);
    List<OccupancyUnit> drukte = new ArrayList<OccupancyUnit>(Arrays.asList(unit));

    Restaurant bijloke = new Restaurant(c, "Bijloke 25", "J. Kluysjensstraat 2",
            "Elke werkdag van 8 tot 16 uur", context.getMenus().get(1), "http://www.rubenvermeulen.be/files/hogent-bijloke.jpg",
            drukte, 0.8);
    Restaurant Schooneersen_b = new Restaurant(c, "Schoonmeersen B", "Voskenslaan 255, 9000 gent",
            "Elke werkdag van 8 tot 15 uur", context.getMenus().get(2), "http://www.rubenvermeulen.be/files/hogent-bijloke.jpg",
            drukte, 0.2);

    public  List<Restaurant> restaurants = new ArrayList<Restaurant>(Arrays.asList(bijloke));

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
}
