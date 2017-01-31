package resto.android.hogent.be.hogentresto.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.fragments.MenuFragment;

public class MenuPagerAdapter extends FragmentPagerAdapter {

    private List<MenuFragment> fragments;
    private String[] titlesShort;
    private String[] titlesLong;

    public static final String PAGE = "page";
    public static final String TITLE_LONG = "titleLong";

    public MenuPagerAdapter(Context context, FragmentManager fm) {
        super(fm);

        fragments = new ArrayList<>();

        setTitlesShort(context);
        setTitlesLong(context);

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

    public void setTitlesShort(Context context) {
        titlesShort = new String[] {
                context.getResources().getString(R.string.monday_short),
                context.getResources().getString(R.string.tuesday_short),
                context.getResources().getString(R.string.wednesday_short),
                context.getResources().getString(R.string.thursday_short),
                context.getResources().getString(R.string.friday_short),
        };


    }

    public void setTitlesLong(Context context) {
        titlesLong = new String[] {
                context.getResources().getString(R.string.monday),
                context.getResources().getString(R.string.tuesday),
                context.getResources().getString(R.string.wednesday),
                context.getResources().getString(R.string.thursday),
                context.getResources().getString(R.string.friday),
        };
    }
}
