package resto.android.hogent.be.hogentresto.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
import java.util.Map;

import resto.android.hogent.be.hogentresto.MenuTabs.MenuFragment;
import resto.android.hogent.be.hogentresto.models.Menu;

/**
 * Created by jonas on 28/11/2016.
 */

public class MenuAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 5;
    private String tabTitles[] = new String[] { "Ma", "Di", "Wo", "Do", "Vr" };
    MenuFragment fragmentMa = MenuFragment.newInstance(1);
    MenuFragment fragmentDi = MenuFragment.newInstance(2);
    MenuFragment fragmentWoe = MenuFragment.newInstance(3);
    MenuFragment fragmentDo = MenuFragment.newInstance(4);
    MenuFragment fragmentVr = MenuFragment.newInstance(5);


    public MenuAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        /*fragment = MenuFragment.newInstance(position + 1);
        return fragment;*/
        switch (position){
            case 0: return fragmentMa;
            case 1: return fragmentDi;
            case 2: return fragmentWoe;
            case 3: return fragmentDo;
            case 4: return fragmentVr;
            default:return null;
        }


    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


}
