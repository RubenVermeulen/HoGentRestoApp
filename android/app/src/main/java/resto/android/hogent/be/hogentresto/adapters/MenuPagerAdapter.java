package resto.android.hogent.be.hogentresto.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import resto.android.hogent.be.hogentresto.fragments.MenuFragment;

public class MenuPagerAdapter extends FragmentPagerAdapter {

    private List<MenuFragment> fragments;
    private final String[] titlesShort = new String[] { "MA", "DI", "WO", "DO", "VR" };
    private final String[] titlesLong = new String[] { "Maandag", "Dinsdag", "Woensdag", "Donderdag", "Vrijdag" };

    public static final String PAGE = "page";
    public static final String TITLE_LONG = "titleLong";

    public MenuPagerAdapter(FragmentManager fm) {
        super(fm);

        fragments = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            MenuFragment fragment = new MenuFragment();
            Bundle args = new Bundle();

            args.putInt(PAGE, i);
            args.putString(TITLE_LONG, titlesLong[i]);
            fragment.setArguments(args);

            fragments.add(fragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titlesShort[position];
    }

    @Override
    public int getItemPosition(Object object) {
        MenuFragment fragment = (MenuFragment) object;

        if (fragment != null) {
            fragment.update();
        }

        return super.getItemPosition(object);
    }
}
