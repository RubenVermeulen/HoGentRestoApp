package resto.android.hogent.be.hogentresto.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import resto.android.hogent.be.hogentresto.MainActivity;
import resto.android.hogent.be.hogentresto.MenuContext.RestaurantContext;
import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.adapters.RestaurantsAdapter;
import resto.android.hogent.be.hogentresto.adapters.RestoAdapter;
import resto.android.hogent.be.hogentresto.models.Coordinate;
import resto.android.hogent.be.hogentresto.models.Item;
import resto.android.hogent.be.hogentresto.models.Menu;
import resto.android.hogent.be.hogentresto.models.OccupancyUnit;
import resto.android.hogent.be.hogentresto.models.Restaurant;

public class RestoListFragment extends Fragment {
	private RestoAdapter adapterItems;
	private ListView lvItems;
	private String name;
	private String address;
	private Coordinate coordinates;
	private String openingHours;
	private transient List<Menu> menus;
	private String urlImage;
	private double occupation;
	private List<OccupancyUnit> forecast;

	List<Restaurant> items;
	View child;
	private OnItemSelectedListener listener;

	public interface OnItemSelectedListener {
		public void onItemSelected(Restaurant i);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if (activity instanceof OnItemSelectedListener) {
			listener = (OnItemSelectedListener) activity;
		} else {
			throw new ClassCastException(activity.toString()
					+ " must implement RestoListFragment.OnItemSelectedListener");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create arraylist from item fixtures
		RestaurantContext c = new RestaurantContext();
		items =c.getRestaurants();

		/*try {
			do{
			items = MainActivity.restos;
			}
			while(MainActivity.getRestos==false);
*/

			adapterItems = new RestoAdapter(getContext(), R.layout.restaurant_list_item, items);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate view
		View view = inflater.inflate(R.layout.fragment_restaurant_list, container,
				false);
		// Bind adapter to ListView
		lvItems = (ListView) view.findViewById(R.id.lvItems);



		lvItems.setAdapter(adapterItems);
		lvItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View item, int position,
									long rowId) {
				// Retrieve item based on position
				Restaurant r = adapterItems.getItem(position);
				// Fire selected event for item
				listener.onItemSelected(r);
			}
		});
		return view;
	}
	
	/**
	 * Turns on activate-on-click mode. When this mode is on, list items will be
	 * given the 'activated' state when touched.
	 */
	public void setActivateOnItemClick(boolean activateOnItemClick) {
		// When setting CHOICE_MODE_SINGLE, ListView will automatically
		// give items the 'activated' state when touched.
		lvItems.setChoiceMode(
				activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
						: ListView.CHOICE_MODE_NONE);
	}
}
