package resto.android.hogent.be.hogentresto.MenuContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import resto.android.hogent.be.hogentresto.models.Menu;

/**
 * Created by jonas on 24/11/2016.
 */

public class MenuContext {
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

    Date nov28 = dateFormat.parse("28 november 2016");
    Date nov29 = dateFormat.parse("29 november 2016");
    Date nov30 = dateFormat.parse("30 november 2016");
    Date dec1 = dateFormat.parse("1 december 2016");
    Date dec2 = dateFormat.parse("2 december 2016");

    Menu menu1 = new Menu(nov28, 1.80, "verse frietjes", "Frietjes");
    Menu menu2 = new Menu(nov28, 0.60, "Tomatensoep met balletjes", "Soep");
    Menu menu3 = new Menu(nov28, 3.80, "Kip op zijn provenciaals", "Dagschotel");

    Menu menu4 = new Menu(nov29, 1.80, "verse frietjes", "Frietjes");
    Menu menu5 = new Menu(nov29, 0.60, "Tomatensoep met balletjes", "Soep");
    Menu menu6 = new Menu(nov29, 3.80, "Kip op zijn provenciaals", "Dagschotel");

    Menu menu7 = new Menu(nov30, 1.80, "verse frietjes", "Frietjes");
    Menu menu8 = new Menu(nov30, 0.60, "Tomatensoep met balletjes", "Soep");
    Menu menu9 = new Menu(nov30, 3.80, "Kip op zijn provenciaals", "Dagschotel");

    Menu menu10 = new Menu(dec1, 1.80, "verse frietjes", "Frietjes");
    Menu menu11 = new Menu(dec1, 0.60, "Tomatensoep met balletjes", "Soep");
    Menu menu12 = new Menu(dec1, 3.80, "Kip op zijn provenciaals", "Dagschotel");

    Menu menu13 = new Menu(dec2, 1.80, "verse frietjes", "Frietjes");
    Menu menu14 = new Menu(dec2, 0.60, "Tomatensoep met balletjes", "Soep");
    Menu menu15 = new Menu(dec2, 3.80, "Kip op zijn provenciaals", "Dagschotel");

    public Map<String, List<Menu>> menus = new HashMap<String, List<Menu>>() {

        {
            put("maandag", Arrays.asList(menu1, menu2, menu3));
            put("dinsdag", Arrays.asList(menu4, menu5, menu6));
            put("woensdag", Arrays.asList(menu7, menu8, menu9));
            put("donderdag", Arrays.asList(menu10, menu11, menu12));
            put("vrijdag", Arrays.asList(menu13, menu14, menu15));
        }

        ;
    };

    public MenuContext() throws ParseException {
    }
}
