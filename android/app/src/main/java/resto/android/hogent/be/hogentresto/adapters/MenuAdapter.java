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
    MenuFragment fragment;
    /*MenuFragment fr1 = MenuFragment.newInstance(0);
    MenuFragment fr2 = MenuFragment.newInstance(1);
    MenuFragment fr3 = MenuFragment.newInstance(2);
    MenuFragment fr4 = MenuFragment.newInstance(3);
    MenuFragment fr5 = MenuFragment.newInstance(4);
    MenuFragment fr6 = MenuFragment.newInstance(4);*/



    public MenuAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

       /* switch (position){
            case 0:return fr1;
            case 1:return fr2;
            case 2:return fr3;
            case 3:return fr4;
            case 4:return fr5;
            default:return fr1;
        }*/
        fragment = MenuFragment.newInstance(position + 1);
        return fragment;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }


}
