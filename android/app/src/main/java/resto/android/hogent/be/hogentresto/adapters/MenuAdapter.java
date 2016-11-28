package resto.android.hogent.be.hogentresto.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import resto.android.hogent.be.hogentresto.MenuTabs.MenuFragment;

/**
 * Created by jonas on 28/11/2016.
 */

public class MenuAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Ma", "Di", "Wo", "Do", "Vr" };
    MenuFragment fragment;

    public MenuAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        fragment = MenuFragment.newInstance(position + 1);
        return fragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    public MenuFragment getFragment() {
        return fragment;
    }
}
