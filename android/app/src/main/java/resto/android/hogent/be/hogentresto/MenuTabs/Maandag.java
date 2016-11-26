package resto.android.hogent.be.hogentresto.MenuTabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import resto.android.hogent.be.hogentresto.R;

/**
 * Created by jonas on 24/11/2016.
 */

public class Maandag extends Fragment {

    public Maandag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_menu_maandag, container, false);
        return v;
    }

}
