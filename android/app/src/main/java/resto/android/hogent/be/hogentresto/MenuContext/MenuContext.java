package resto.android.hogent.be.hogentresto.MenuContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import resto.android.hogent.be.hogentresto.models.Menu;
import resto.android.hogent.be.hogentresto.models.Product;

/**
 * Created by jonas on 24/11/2016.
 */

public class MenuContext {
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

    Date nov28 = new GregorianCalendar(2016, Calendar.NOVEMBER, 28).getTime();
    Date nov29 = new GregorianCalendar(2016, Calendar.NOVEMBER, 29).getTime();
    Date nov30 = new GregorianCalendar(2016, Calendar.DECEMBER, 30).getTime();
    Date dec1 = new GregorianCalendar(2016, Calendar.DECEMBER, 1).getTime();
    Date dec2 = new GregorianCalendar(2016, Calendar.DECEMBER, 2).getTime();

    List<String> allergenen = new ArrayList<String>(Arrays.asList("zetmeel", "nog iets"));
    List<String> allergenen1 = new ArrayList<String>(Arrays.asList("water", "bouillon"));
    Product p = new Product("Verse frietjes", allergenen);

    Menu menu1 = new Menu("Frietjes", p, 0.80, nov28);
    Menu menu2 = new Menu( "Soep", p, 0.60, nov28);
    Menu menu3 = new Menu("Dagschotel" , p, 3.80, nov28);

    Menu menu4 = new Menu("Frietjes", p, 0.80, nov29);
    Menu menu5 = new Menu( "Soep", p, 0.60, nov29);
    Menu menu6 = new Menu("Dagschotel" , p, 3.80, nov29);

    Menu menu7 = new Menu("Frietjes", p, 0.80, nov30);
    Menu menu8 = new Menu( "Soep", p, 0.60, nov30);
    Menu menu9 = new Menu("Dagschotel" , p, 3.80, nov30);

    Menu menu10 = new Menu("Frietjes", p, 0.80, dec1);
    Menu menu11 = new Menu( "Soep", p, 0.60, dec1);
    Menu menu12 = new Menu("Dagschotel" , p, 3.80, dec1);

    Menu menu13 = new Menu("Frietjes", p, 0.80, dec2);
    Menu menu14 = new Menu( "Soep", p, 0.60, dec2);
    Menu menu15 = new Menu("Dagschotel" , p, 3.80, dec2);

    public Map<Integer, List<Menu>> menus = new HashMap<Integer, List<Menu>>() {

        {
            put(2, Arrays.asList(menu1, menu2, menu3));
            put(3, Arrays.asList(menu4, menu5, menu6));
            put(4, Arrays.asList(menu7, menu8, menu9));
            put(5, Arrays.asList(menu10, menu11, menu12));
            put(6, Arrays.asList(menu13, menu14, menu15));
        }

        ;
    };

    public Map<Integer, List<Menu>> getMenus() {
        return menus;
    }
}
