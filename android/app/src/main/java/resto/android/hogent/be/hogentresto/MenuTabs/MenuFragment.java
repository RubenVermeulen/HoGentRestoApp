package resto.android.hogent.be.hogentresto.MenuTabs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.RestaurantActivity;
import resto.android.hogent.be.hogentresto.models.Menu;

// In this case, the fragment displays simple text based on the page
public class MenuFragment extends Fragment {

    List<Menu> menusMonday,
            menusTuesday,
            menusWednesday,
            menuThursday,
            menusFriday;

    View view;
    TextView tvTitle;
    TextView datum;
    View child;

    TextView title;
    TextView description;
    TextView price;


    public static final String ARG_PAGE = "ARG_PAGE";
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    Map<Integer, List<Menu>> menus= new HashMap<>();


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

        LinearLayout fragmentList = (LinearLayout) view.findViewById(R.id.fragment_list_id);
        fragmentList.removeAllViews();

        if( ! menus.isEmpty()) {
            // Sunday == 1
            menusMonday = menus.get(2);
            menusTuesday = menus.get(3);
            menusWednesday = menus.get(4);
            menuThursday = menus.get(5);
            menusFriday = menus.get(6);
        }
        switch (mPage){
            case 1: tvTitle.setText(R.string.monday);
                addMenusToList(fragmentList, menusMonday, savedInstanceState);
                break;
            case 2: tvTitle.setText(R.string.tuesday);
                addMenusToList(fragmentList, menusTuesday, savedInstanceState);
                break;
            case 3: tvTitle.setText(R.string.wednesday);
                addMenusToList(fragmentList, menusWednesday, savedInstanceState);
                break;
            case 4: tvTitle.setText(R.string.thursday);
                addMenusToList(fragmentList, menuThursday, savedInstanceState);
                break;
            case 5: tvTitle.setText(R.string.friday);
                addMenusToList(fragmentList, menusFriday, savedInstanceState);
                break;

        }

        return view;
    }

    private void addMenusToList(LinearLayout ll, List<Menu> menus, Bundle savedInstanceState) {
        try{
            datum.setText(df.format(menus.get(0).getAvailableAt()));

            for (Menu m : menus) {
                child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_list_item, null);

                title = (TextView) child.findViewById(R.id.title);
                description = (TextView) child.findViewById(R.id.description);
                price = (TextView) child.findViewById(R.id.price);
                title.setText(m.getTitle());
                description.setText(m.getProduct().getDescription());
                price.setText(String.format(Locale.getDefault(), "€ %.2f", m.getPrice()));

                final String allergens;

                if (m.getProduct().getAllergens().size() == 0) {
                    allergens = "Geen allergenen";
                }
                else {
                    allergens = m.getProduct().getAllergens().toString().replaceAll("[\\[\\](){}]","");
                }

                child.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                        builder.setMessage(allergens)
                                .setTitle("Allergenen");

                        builder.setPositiveButton("Sluiten", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                });

                ll.addView(child);
            }
        }
        catch(Exception e){
            View child = getLayoutInflater(savedInstanceState).inflate(R.layout.menus_no_results, null);
            ll.addView(child);
        }
    }
}