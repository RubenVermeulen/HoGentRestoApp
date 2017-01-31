package resto.android.hogent.be.hogentresto.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.RestaurantActivity;
import resto.android.hogent.be.hogentresto.adapters.MenuPagerAdapter;
import resto.android.hogent.be.hogentresto.helpers.Updateable;
import resto.android.hogent.be.hogentresto.models.Menu;

public class MenuFragment extends Fragment implements Updateable {

    private TextView tvTitle;
    private LinearLayout llMenuList;

    private int page;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        // Get views
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        llMenuList = (LinearLayout) rootView.findViewById(R.id.menuList);

        // Get arguments
        Bundle args = getArguments();

        // Set attributes
        page = args.getInt(MenuPagerAdapter.PAGE);

        // Set views
        tvTitle.setText(args.getString(MenuPagerAdapter.TITLE_LONG));

        RestaurantActivity parentActivity = (RestaurantActivity) getActivity();
        List<Menu> menus = parentActivity.getMenusFromApi(page + 2);

        addMenus(menus);

        return rootView;
    }

    private void addMenus(List<Menu> menus) {
        llMenuList.removeAllViews();

        if (menus != null && ! menus.isEmpty()) {
            View child;
            TextView title;
            TextView description;
            TextView price;

            for (Menu m : menus) {
                child = getActivity().getLayoutInflater().inflate(R.layout.menus_list_item, null);

                title = (TextView) child.findViewById(R.id.title);
                description = (TextView) child.findViewById(R.id.description);
                price = (TextView) child.findViewById(R.id.price);
                title.setText(m.getTitle());
                description.setText(m.getProduct().getDescription());
                price.setText(String.format(Locale.getDefault(), "€ %.2f", m.getPrice()));

                final String allergens;

                if (m.getProduct().getAllergens().size() == 0) {
                    allergens = "Geen allergenen";
                } else {
                    allergens = m.getProduct().getAllergens().toString().replaceAll("[\\[\\](){}]", "");
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

                llMenuList.addView(child);
            }
        }
        else {
            View child = getActivity().getLayoutInflater().inflate(R.layout.menus_no_results, null);
            llMenuList.addView(child);
        }
    }

    @Override
    public void update() {
        RestaurantActivity parentActivity = (RestaurantActivity) getActivity();
        List<Menu> menus = parentActivity.getMenusFromApi(page + 2);

        addMenus(menus);
    }
}
