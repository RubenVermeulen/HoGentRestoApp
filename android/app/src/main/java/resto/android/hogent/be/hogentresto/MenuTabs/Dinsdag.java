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

public class Dinsdag extends Fragment {

    public Dinsdag() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_dinsdag, container, false);
    }

}
