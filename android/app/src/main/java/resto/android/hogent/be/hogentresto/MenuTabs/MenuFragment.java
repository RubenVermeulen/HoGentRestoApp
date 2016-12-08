package resto.android.hogent.be.hogentresto.MenuTabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import resto.android.hogent.be.hogentresto.MenuContext.MenuContext;
import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.RestaurantActivity;
import resto.android.hogent.be.hogentresto.models.Menu;


/**
 * Created by jonas on 28/11/2016.
 */

// In this case, the fragment displays simple text based on the page
public class MenuFragment extends Fragment {


    View view;
    TextView tvTitle;
    TextView datum;
    View child;

    TextView title;
    TextView description;
    TextView price;


    public static final String ARG_PAGE = "ARG_PAGE";
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    Map<String, List<Menu>> menus= new HashMap<String, List<Menu>>();

    List<Menu> menuMaandag, menuDinsdag, menuWoensdag, menuDonderdag, menuVrijdag;

    private int mPage;

    public static MenuFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);


    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.menu_fragment, container, false);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        datum = (TextView) view.findViewById(R.id.menuDate);

        menus = RestaurantActivity.getMenusFromApi();
        LinearLayout fragmentlist = (LinearLayout) view.findViewById(R.id.fragment_list_id);
        if(!menus.isEmpty()) {
            menuMaandag = menus.get("maandag");
            menuDinsdag = menus.get("dinsdag");
            menuWoensdag = menus.get("woensdag");
            menuDonderdag = menus.get("donderdag");
            menuVrijdag = menus.get("vrijdag");
        }
        switch (mPage){

            case 1: tvTitle.setText("Maandag");
            try{

                datum.setText(df.format(menuMaandag.get(0).getAvailableAt()));
                for (Menu m : menuMaandag) {
                    child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_list_item, null);

                    title = (TextView) child.findViewById(R.id.title);
                    description = (TextView) child.findViewById(R.id.description);
                    price = (TextView) child.findViewById(R.id.price);

                    title.setText(m.getTitle());
                    description.setText(m.getProduct().getDescription());
                    price.setText(String.format(Locale.getDefault(), "€ %.2f", m.getPrice()));

                    fragmentlist.addView(child);
                }

                }
                catch(Exception e){
                    View child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_no_results, null);
                    fragmentlist.addView(child);
                }

                break;
            case 2: tvTitle.setText("Dinsdag");
                try{
                datum.setText(df.format(menuDinsdag.get(0).getAvailableAt()));
                for (Menu m : menuDinsdag) {
                    child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_list_item, null);

                    title = (TextView) child.findViewById(R.id.title);
                    description = (TextView) child.findViewById(R.id.description);
                    price = (TextView) child.findViewById(R.id.price);

                    title.setText(m.getTitle());
                    description.setText(m.getProduct().getDescription());
                    price.setText(String.format(Locale.getDefault(), "€ %.2f", m.getPrice()));

                    fragmentlist.addView(child);
                }
                }
                catch(Exception e){
                    View child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_no_results, null);
                    fragmentlist.addView(child);
                }
                break;
            case 3: tvTitle.setText("Woensdag");
                try{
                datum.setText(df.format(menuWoensdag.get(0).getAvailableAt()));
                for (Menu m : menuWoensdag) {
                    child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_list_item, null);

                    title = (TextView) child.findViewById(R.id.title);
                    description = (TextView) child.findViewById(R.id.description);
                    price = (TextView) child.findViewById(R.id.price);

                    title.setText(m.getTitle());
                    description.setText(m.getProduct().getDescription());
                    price.setText(String.format(Locale.getDefault(), "€ %.2f", m.getPrice()));
                    fragmentlist.addView(child);

                }
                }
                catch(Exception e){
                    View child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_no_results, null);
                    fragmentlist.addView(child);
                }
                break;
            case 4: tvTitle.setText("Donderdag");
                try{
                datum.setText(df.format(menuDonderdag.get(0).getAvailableAt()));
                for (Menu m : menuDonderdag) {
                    child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_list_item, null);
                    title = (TextView) child.findViewById(R.id.title);
                    description = (TextView) child.findViewById(R.id.description);
                    price = (TextView) child.findViewById(R.id.price);

                    title.setText(m.getTitle());
                    description.setText(m.getProduct().getDescription());
                    price.setText(String.format(Locale.getDefault(), "€ %.2f", m.getPrice()));

                    fragmentlist.addView(child);
                }
                }
                catch(Exception e){
                    View child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_no_results, null);
                    fragmentlist.addView(child);
                }
                break;
            case 5: tvTitle.setText("Vrijdag");
                try{
                datum.setText(df.format(menuVrijdag.get(0).getAvailableAt()));
                for (Menu m : menuVrijdag) {
                    child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_list_item, null);

                    title = (TextView) child.findViewById(R.id.title);
                    description = (TextView) child.findViewById(R.id.description);
                    price = (TextView) child.findViewById(R.id.price);

                    title.setText(m.getTitle());
                    description.setText(m.getProduct().getDescription());
                    price.setText(String.format(Locale.getDefault(), "€ %.2f", m.getPrice()));

                    fragmentlist.addView(child);
                }
                }
                catch(Exception e){
                    View child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_no_results, null);
                    fragmentlist.addView(child);
                }
                break;

        }


        return view;
    }


    public void setMenus(Map<String, List<Menu>> menus) {
        this.menus = menus;
    }



}