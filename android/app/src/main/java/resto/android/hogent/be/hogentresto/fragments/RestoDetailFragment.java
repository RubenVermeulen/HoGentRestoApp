package resto.android.hogent.be.hogentresto.fragments;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;


import com.astuetz.PagerSlidingTabStrip;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import resto.android.hogent.be.hogentresto.MenuContext.MenuContext;
import resto.android.hogent.be.hogentresto.R;
import resto.android.hogent.be.hogentresto.adapters.MenuAdapter;
import resto.android.hogent.be.hogentresto.helpers.Traffic;
import resto.android.hogent.be.hogentresto.models.Item;
import resto.android.hogent.be.hogentresto.models.OccupancyUnit;
import resto.android.hogent.be.hogentresto.models.Restaurant;

public class RestoDetailFragment extends Fragment{
	private Restaurant resto;
	private MenuAdapter adapter;
	private List<OccupancyUnit> occupancyData;
	private List<OccupancyUnit> forecastData;

	@BindView(R.id.cardView)
	CardView cardView;
	@BindView(R.id.thumbnail)
	ImageView thumbnail;
	@BindView(R.id.name)
	TextView name;
	@BindView(R.id.openingHours)
	TextView openingHours;
	@BindView(R.id.trafficGrade)
	TextView trafficGrade;
	@BindView(R.id.trafficIndicator)
	ImageView trafficIndicator;
	@BindView(R.id.stats)
	TableLayout stats;
	@BindView(R.id.expand)
	ImageView expand;
	@BindView(R.id.viewpager)
	ViewPager viewPager;
	//@BindView(R.id.progressBar)
	//ProgressBar progressBar;
	//@BindView(R.id.refresh)
	//TextView refresh;
	@BindView(R.id.tabs)
	PagerSlidingTabStrip tabsStrip;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		resto = (Restaurant) getArguments().getSerializable("item");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_restaurant_detail,
				container, false);
		ButterKnife.bind(this, view);
		/*ImageView thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
		ImageView trafficIndicator = (ImageView) view.findViewById(R.id.trafficIndicator);
		TextView trafficGrade = (TextView) view.findViewById(R.id.trafficGrade);
		TextView name = (TextView) view.findViewById(R.id.name);
		TextView openingHours = (TextView) view.findViewById(R.id.openingHours);
		LinearLayout menus = (LinearLayout) view.findViewById(R.id.menus);
		tvTitle.setText("test");
		tvBody.setText("test");*/

		setRestaurant(resto);
		adapter = new MenuAdapter(getActivity().getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		tabsStrip.setViewPager(viewPager);

		GraphView graph = (GraphView) view.findViewById(R.id.graph);

		LineGraphSeries<DataPoint> currentSeries = new LineGraphSeries<>(getCurrentData());
		LineGraphSeries<DataPoint> forecastSeries = new LineGraphSeries<>(getForecastData());
//        graph.addSeries(currentSeries);
		graph.addSeries(forecastSeries);


		graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
			@Override
			public String formatLabel(double value, boolean isValueX) {
				if(isValueX) {
					Calendar cal = Calendar.getInstance();
					cal.setTimeInMillis((long) value);
					return cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE);
				} else {
					return super.formatLabel(value,isValueX);
				}

			}
		});
		graph.getGridLabelRenderer().setNumHorizontalLabels(5);
		graph.getViewport().setMinX(occupancyData.get(0).getTime().getTime());
		graph.getViewport().setMaxX(occupancyData.get(0).getTime().getTime()+1200000);
		graph.getGridLabelRenderer().setNumVerticalLabels(5);
		graph.getViewport().setMinY(0);
		graph.getViewport().setMaxY(1);

		graph.getViewport().setXAxisBoundsManual(true);
		graph.getViewport().setYAxisBoundsManual(true);
		graph.getGridLabelRenderer().setHumanRounding(false);

		graph.getViewport().setScrollable(true);
		graph.refreshDrawableState();
		return view;
	}
    
    // RestoDetailFragment.newInstance(item)
    public static RestoDetailFragment newInstance(Restaurant item) {
    	RestoDetailFragment fragmentDemo = new RestoDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
	private void setRestaurant(Restaurant r) {
		Picasso.with(getContext()).load(r.getUrlImage()).into(thumbnail);
		name.setText(r.getName());
		openingHours.setText(r.getOpeningHours());

		Traffic.setTraffic(r.getOccupation(), trafficGrade, trafficIndicator);
	}

	public void expand(View view) {
		if (stats.getVisibility() == View.GONE) {
			stats.setVisibility(View.VISIBLE);
			expand.setImageResource(R.drawable.ic_expand_less_black_32dp);

		}
		else {
			stats.setVisibility(View.GONE);
			expand.setImageResource(R.drawable.ic_expand_more_black_32dp);
		}
	}

	/*public void refresh(View view) {
		//progressBar.setVisibility(View.VISIBLE);
		//getMenus();
	}*/

	private DataPoint[] getCurrentData(){
		occupancyData = new ArrayList<>();
		occupancyData.add(new OccupancyUnit(new Date(1480933800000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480933830000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480933860000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480933890000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480933920000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480933950000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480933980000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934010000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934040000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934070000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934100000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934130000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934160000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934190000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934220000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934250000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934280000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934310000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934340000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934370000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934400000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934430000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934460000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934490000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934520000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934550000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934580000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934610000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934640000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934670000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934700000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934730000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934760000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934790000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934820000L), 0));
		occupancyData.add(new OccupancyUnit(new Date(1480934850000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480934880000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480934910000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480934940000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480934970000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480935000000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480935030000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480935060000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480935090000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480935120000L), 0.02));
		occupancyData.add(new OccupancyUnit(new Date(1480935150000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480935180000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480935210000L), 0.02));
		occupancyData.add(new OccupancyUnit(new Date(1480935240000L), 0.01));
		occupancyData.add(new OccupancyUnit(new Date(1480935270000L), 0.02));
		occupancyData.add(new OccupancyUnit(new Date(1480935300000L), 0.02));
		occupancyData.add(new OccupancyUnit(new Date(1480935330000L), 0.02));
		occupancyData.add(new OccupancyUnit(new Date(1480935360000L), 0.03));
		occupancyData.add(new OccupancyUnit(new Date(1480935390000L), 0.03));
		occupancyData.add(new OccupancyUnit(new Date(1480935420000L), 0.03));
		occupancyData.add(new OccupancyUnit(new Date(1480935450000L), 0.03));
		occupancyData.add(new OccupancyUnit(new Date(1480935480000L), 0.03));
		occupancyData.add(new OccupancyUnit(new Date(1480935510000L), 0.05));
		occupancyData.add(new OccupancyUnit(new Date(1480935540000L), 0.03));
		occupancyData.add(new OccupancyUnit(new Date(1480935570000L), 0.04));
		occupancyData.add(new OccupancyUnit(new Date(1480935600000L), 0.06));
		occupancyData.add(new OccupancyUnit(new Date(1480935630000L), 0.06));
		occupancyData.add(new OccupancyUnit(new Date(1480935660000L), 0.06));
		occupancyData.add(new OccupancyUnit(new Date(1480935690000L), 0.06));
		occupancyData.add(new OccupancyUnit(new Date(1480935720000L), 0.07));
		occupancyData.add(new OccupancyUnit(new Date(1480935750000L), 0.07));
		occupancyData.add(new OccupancyUnit(new Date(1480935780000L), 0.07));
		occupancyData.add(new OccupancyUnit(new Date(1480935810000L), 0.09));
		occupancyData.add(new OccupancyUnit(new Date(1480935840000L), 0.09));
		occupancyData.add(new OccupancyUnit(new Date(1480935870000L), 0.09));
		occupancyData.add(new OccupancyUnit(new Date(1480935900000L), 0.1));
		occupancyData.add(new OccupancyUnit(new Date(1480935930000L), 0.09));
		occupancyData.add(new OccupancyUnit(new Date(1480935960000L), 0.11));
		occupancyData.add(new OccupancyUnit(new Date(1480935990000L), 0.13));
		occupancyData.add(new OccupancyUnit(new Date(1480936020000L), 0.13));
		occupancyData.add(new OccupancyUnit(new Date(1480936050000L), 0.14));
		occupancyData.add(new OccupancyUnit(new Date(1480936080000L), 0.15));
		occupancyData.add(new OccupancyUnit(new Date(1480936110000L), 0.15));
		occupancyData.add(new OccupancyUnit(new Date(1480936140000L), 0.15));
		occupancyData.add(new OccupancyUnit(new Date(1480936170000L), 0.17));
		occupancyData.add(new OccupancyUnit(new Date(1480936200000L), 0.17));
		occupancyData.add(new OccupancyUnit(new Date(1480936230000L), 0.18));
		occupancyData.add(new OccupancyUnit(new Date(1480936260000L), 0.2));
		occupancyData.add(new OccupancyUnit(new Date(1480936290000L), 0.23));
		occupancyData.add(new OccupancyUnit(new Date(1480936320000L), 0.24));
		occupancyData.add(new OccupancyUnit(new Date(1480936350000L), 0.23));
		occupancyData.add(new OccupancyUnit(new Date(1480936380000L), 0.24));
		occupancyData.add(new OccupancyUnit(new Date(1480936410000L), 0.25));
		occupancyData.add(new OccupancyUnit(new Date(1480936440000L), 0.27));
		occupancyData.add(new OccupancyUnit(new Date(1480936470000L), 0.29));
		occupancyData.add(new OccupancyUnit(new Date(1480936500000L), 0.29));
		occupancyData.add(new OccupancyUnit(new Date(1480936530000L), 0.31));
		occupancyData.add(new OccupancyUnit(new Date(1480936560000L), 0.32));
		occupancyData.add(new OccupancyUnit(new Date(1480936590000L), 0.36));
		occupancyData.add(new OccupancyUnit(new Date(1480936620000L), 0.36));
		occupancyData.add(new OccupancyUnit(new Date(1480936650000L), 0.37));
		occupancyData.add(new OccupancyUnit(new Date(1480936680000L), 0.4));
		occupancyData.add(new OccupancyUnit(new Date(1480936710000L), 0.43));
		occupancyData.add(new OccupancyUnit(new Date(1480936740000L), 0.41));
		occupancyData.add(new OccupancyUnit(new Date(1480936770000L), 0.45));
		occupancyData.add(new OccupancyUnit(new Date(1480936800000L), 0.45));
		occupancyData.add(new OccupancyUnit(new Date(1480936830000L), 0.47));



		DataPoint[] data = new DataPoint[occupancyData.size()];
		for(int i=0;i<occupancyData.size();i++){
			data[i]=new DataPoint(occupancyData.get(i).getTime(), occupancyData.get(i).getOccupancy());
		}

		return data;

	}

	private DataPoint[] getForecastData(){
		forecastData = new ArrayList<>();
		forecastData.add(new OccupancyUnit(new Date(1479119400000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119430000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119460000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119490000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119520000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119550000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119580000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119610000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119640000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119670000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119700000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119730000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119760000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119790000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119820000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119850000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119880000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119910000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119940000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479119970000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120000000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120030000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120060000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120090000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120120000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120150000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120180000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120210000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120240000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120270000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120300000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120330000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120360000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120390000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120420000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120450000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120480000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120510000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120540000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120570000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120600000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479120630000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120660000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120690000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120720000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120750000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120780000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120810000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120840000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120870000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120900000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479120930000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479120960000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479120990000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479121020000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479121050000L),0.03));
		forecastData.add(new OccupancyUnit(new Date(1479121080000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479121110000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479121140000L),0.03));
		forecastData.add(new OccupancyUnit(new Date(1479121170000L),0.03));
		forecastData.add(new OccupancyUnit(new Date(1479121200000L),0.04));
		forecastData.add(new OccupancyUnit(new Date(1479121230000L),0.03));
		forecastData.add(new OccupancyUnit(new Date(1479121260000L),0.04));
		forecastData.add(new OccupancyUnit(new Date(1479121290000L),0.04));
		forecastData.add(new OccupancyUnit(new Date(1479121320000L),0.04));
		forecastData.add(new OccupancyUnit(new Date(1479121350000L),0.05));
		forecastData.add(new OccupancyUnit(new Date(1479121380000L),0.06));
		forecastData.add(new OccupancyUnit(new Date(1479121410000L),0.05));
		forecastData.add(new OccupancyUnit(new Date(1479121440000L),0.06));
		forecastData.add(new OccupancyUnit(new Date(1479121470000L),0.07));
		forecastData.add(new OccupancyUnit(new Date(1479121500000L),0.06));
		forecastData.add(new OccupancyUnit(new Date(1479121530000L),0.07));
		forecastData.add(new OccupancyUnit(new Date(1479121560000L),0.08));
		forecastData.add(new OccupancyUnit(new Date(1479121590000L),0.08));
		forecastData.add(new OccupancyUnit(new Date(1479121620000L),0.1));
		forecastData.add(new OccupancyUnit(new Date(1479121650000L),0.1));
		forecastData.add(new OccupancyUnit(new Date(1479121680000L),0.11));
		forecastData.add(new OccupancyUnit(new Date(1479121710000L),0.11));
		forecastData.add(new OccupancyUnit(new Date(1479121740000L),0.11));
		forecastData.add(new OccupancyUnit(new Date(1479121770000L),0.13));
		forecastData.add(new OccupancyUnit(new Date(1479121800000L),0.15));
		forecastData.add(new OccupancyUnit(new Date(1479121830000L),0.15));
		forecastData.add(new OccupancyUnit(new Date(1479121860000L),0.15));
		forecastData.add(new OccupancyUnit(new Date(1479121890000L),0.16));
		forecastData.add(new OccupancyUnit(new Date(1479121920000L),0.19));
		forecastData.add(new OccupancyUnit(new Date(1479121950000L),0.18));
		forecastData.add(new OccupancyUnit(new Date(1479121980000L),0.19));
		forecastData.add(new OccupancyUnit(new Date(1479122010000L),0.18));
		forecastData.add(new OccupancyUnit(new Date(1479122040000L),0.21));
		forecastData.add(new OccupancyUnit(new Date(1479122070000L),0.23));
		forecastData.add(new OccupancyUnit(new Date(1479122100000L),0.24));
		forecastData.add(new OccupancyUnit(new Date(1479122130000L),0.24));
		forecastData.add(new OccupancyUnit(new Date(1479122160000L),0.27));
		forecastData.add(new OccupancyUnit(new Date(1479122190000L),0.27));
		forecastData.add(new OccupancyUnit(new Date(1479122220000L),0.3));
		forecastData.add(new OccupancyUnit(new Date(1479122250000L),0.29));
		forecastData.add(new OccupancyUnit(new Date(1479122280000L),0.29));
		forecastData.add(new OccupancyUnit(new Date(1479122310000L),0.31));
		forecastData.add(new OccupancyUnit(new Date(1479122340000L),0.34));
		forecastData.add(new OccupancyUnit(new Date(1479122370000L),0.34));
		forecastData.add(new OccupancyUnit(new Date(1479122400000L),0.33));
		forecastData.add(new OccupancyUnit(new Date(1479122430000L),0.36));
		forecastData.add(new OccupancyUnit(new Date(1479122460000L),0.38));
		forecastData.add(new OccupancyUnit(new Date(1479122490000L),0.4));
		forecastData.add(new OccupancyUnit(new Date(1479122520000L),0.41));
		forecastData.add(new OccupancyUnit(new Date(1479122550000L),0.43));
		forecastData.add(new OccupancyUnit(new Date(1479122580000L),0.48));
		forecastData.add(new OccupancyUnit(new Date(1479122610000L),0.48));
		forecastData.add(new OccupancyUnit(new Date(1479122640000L),0.47));
		forecastData.add(new OccupancyUnit(new Date(1479122670000L),0.49));
		forecastData.add(new OccupancyUnit(new Date(1479122700000L),0.5));
		forecastData.add(new OccupancyUnit(new Date(1479122730000L),0.51));
		forecastData.add(new OccupancyUnit(new Date(1479122760000L),0.53));
		forecastData.add(new OccupancyUnit(new Date(1479122790000L),0.56));
		forecastData.add(new OccupancyUnit(new Date(1479122820000L),0.57));
		forecastData.add(new OccupancyUnit(new Date(1479122850000L),0.58));
		forecastData.add(new OccupancyUnit(new Date(1479122880000L),0.61));
		forecastData.add(new OccupancyUnit(new Date(1479122910000L),0.61));
		forecastData.add(new OccupancyUnit(new Date(1479122940000L),0.6));
		forecastData.add(new OccupancyUnit(new Date(1479122970000L),0.61));
		forecastData.add(new OccupancyUnit(new Date(1479123000000L),0.63));
		forecastData.add(new OccupancyUnit(new Date(1479123030000L),0.66));
		forecastData.add(new OccupancyUnit(new Date(1479123060000L),0.66));
		forecastData.add(new OccupancyUnit(new Date(1479123090000L),0.67));
		forecastData.add(new OccupancyUnit(new Date(1479123120000L),0.68));
		forecastData.add(new OccupancyUnit(new Date(1479123150000L),0.66));
		forecastData.add(new OccupancyUnit(new Date(1479123180000L),0.67));
		forecastData.add(new OccupancyUnit(new Date(1479123210000L),0.73));
		forecastData.add(new OccupancyUnit(new Date(1479123240000L),0.7));
		forecastData.add(new OccupancyUnit(new Date(1479123270000L),0.71));
		forecastData.add(new OccupancyUnit(new Date(1479123300000L),0.76));
		forecastData.add(new OccupancyUnit(new Date(1479123330000L),0.7));
		forecastData.add(new OccupancyUnit(new Date(1479123360000L),0.75));
		forecastData.add(new OccupancyUnit(new Date(1479123390000L),0.71));
		forecastData.add(new OccupancyUnit(new Date(1479123420000L),0.73));
		forecastData.add(new OccupancyUnit(new Date(1479123450000L),0.76));
		forecastData.add(new OccupancyUnit(new Date(1479123480000L),0.69));
		forecastData.add(new OccupancyUnit(new Date(1479123510000L),0.73));
		forecastData.add(new OccupancyUnit(new Date(1479123540000L),0.72));
		forecastData.add(new OccupancyUnit(new Date(1479123570000L),0.71));
		forecastData.add(new OccupancyUnit(new Date(1479123600000L),0.72));
		forecastData.add(new OccupancyUnit(new Date(1479123630000L),0.74));
		forecastData.add(new OccupancyUnit(new Date(1479123660000L),0.76));
		forecastData.add(new OccupancyUnit(new Date(1479123690000L),0.74));
		forecastData.add(new OccupancyUnit(new Date(1479123720000L),0.72));
		forecastData.add(new OccupancyUnit(new Date(1479123750000L),0.73));
		forecastData.add(new OccupancyUnit(new Date(1479123780000L),0.69));
		forecastData.add(new OccupancyUnit(new Date(1479123810000L),0.73));
		forecastData.add(new OccupancyUnit(new Date(1479123840000L),0.69));
		forecastData.add(new OccupancyUnit(new Date(1479123870000L),0.68));
		forecastData.add(new OccupancyUnit(new Date(1479123900000L),0.67));
		forecastData.add(new OccupancyUnit(new Date(1479123930000L),0.69));
		forecastData.add(new OccupancyUnit(new Date(1479123960000L),0.67));
		forecastData.add(new OccupancyUnit(new Date(1479123990000L),0.66));
		forecastData.add(new OccupancyUnit(new Date(1479124020000L),0.62));
		forecastData.add(new OccupancyUnit(new Date(1479124050000L),0.61));
		forecastData.add(new OccupancyUnit(new Date(1479124080000L),0.6));
		forecastData.add(new OccupancyUnit(new Date(1479124110000L),0.62));
		forecastData.add(new OccupancyUnit(new Date(1479124140000L),0.58));
		forecastData.add(new OccupancyUnit(new Date(1479124170000L),0.59));
		forecastData.add(new OccupancyUnit(new Date(1479124200000L),0.58));
		forecastData.add(new OccupancyUnit(new Date(1479124230000L),0.56));
		forecastData.add(new OccupancyUnit(new Date(1479124260000L),0.53));
		forecastData.add(new OccupancyUnit(new Date(1479124290000L),0.49));
		forecastData.add(new OccupancyUnit(new Date(1479124320000L),0.5));
		forecastData.add(new OccupancyUnit(new Date(1479124350000L),0.49));
		forecastData.add(new OccupancyUnit(new Date(1479124380000L),0.48));
		forecastData.add(new OccupancyUnit(new Date(1479124410000L),0.46));
		forecastData.add(new OccupancyUnit(new Date(1479124440000L),0.45));
		forecastData.add(new OccupancyUnit(new Date(1479124470000L),0.4));
		forecastData.add(new OccupancyUnit(new Date(1479124500000L),0.42));
		forecastData.add(new OccupancyUnit(new Date(1479124530000L),0.42));
		forecastData.add(new OccupancyUnit(new Date(1479124560000L),0.39));
		forecastData.add(new OccupancyUnit(new Date(1479124590000L),0.4));
		forecastData.add(new OccupancyUnit(new Date(1479124620000L),0.37));
		forecastData.add(new OccupancyUnit(new Date(1479124650000L),0.34));
		forecastData.add(new OccupancyUnit(new Date(1479124680000L),0.33));
		forecastData.add(new OccupancyUnit(new Date(1479124710000L),0.33));
		forecastData.add(new OccupancyUnit(new Date(1479124740000L),0.31));
		forecastData.add(new OccupancyUnit(new Date(1479124770000L),0.31));
		forecastData.add(new OccupancyUnit(new Date(1479124800000L),0.3));
		forecastData.add(new OccupancyUnit(new Date(1479124830000L),0.26));
		forecastData.add(new OccupancyUnit(new Date(1479124860000L),0.28));
		forecastData.add(new OccupancyUnit(new Date(1479124890000L),0.24));
		forecastData.add(new OccupancyUnit(new Date(1479124920000L),0.2));
		forecastData.add(new OccupancyUnit(new Date(1479124950000L),0.22));
		forecastData.add(new OccupancyUnit(new Date(1479124980000L),0.18));
		forecastData.add(new OccupancyUnit(new Date(1479125010000L),0.21));
		forecastData.add(new OccupancyUnit(new Date(1479125040000L),0.19));
		forecastData.add(new OccupancyUnit(new Date(1479125070000L),0.17));
		forecastData.add(new OccupancyUnit(new Date(1479125100000L),0.16));
		forecastData.add(new OccupancyUnit(new Date(1479125130000L),0.15));
		forecastData.add(new OccupancyUnit(new Date(1479125160000L),0.15));
		forecastData.add(new OccupancyUnit(new Date(1479125190000L),0.13));
		forecastData.add(new OccupancyUnit(new Date(1479125220000L),0.13));
		forecastData.add(new OccupancyUnit(new Date(1479125250000L),0.13));
		forecastData.add(new OccupancyUnit(new Date(1479125280000L),0.12));
		forecastData.add(new OccupancyUnit(new Date(1479125310000L),0.12));
		forecastData.add(new OccupancyUnit(new Date(1479125340000L),0.1));
		forecastData.add(new OccupancyUnit(new Date(1479125370000L),0.1));
		forecastData.add(new OccupancyUnit(new Date(1479125400000L),0.08));
		forecastData.add(new OccupancyUnit(new Date(1479125430000L),0.1));
		forecastData.add(new OccupancyUnit(new Date(1479125460000L),0.09));
		forecastData.add(new OccupancyUnit(new Date(1479125490000L),0.06));
		forecastData.add(new OccupancyUnit(new Date(1479125520000L),0.08));
		forecastData.add(new OccupancyUnit(new Date(1479125550000L),0.07));
		forecastData.add(new OccupancyUnit(new Date(1479125580000L),0.05));
		forecastData.add(new OccupancyUnit(new Date(1479125610000L),0.05));
		forecastData.add(new OccupancyUnit(new Date(1479125640000L),0.06));
		forecastData.add(new OccupancyUnit(new Date(1479125670000L),0.06));
		forecastData.add(new OccupancyUnit(new Date(1479125700000L),0.05));
		forecastData.add(new OccupancyUnit(new Date(1479125730000L),0.03));
		forecastData.add(new OccupancyUnit(new Date(1479125760000L),0.05));
		forecastData.add(new OccupancyUnit(new Date(1479125790000L),0.04));
		forecastData.add(new OccupancyUnit(new Date(1479125820000L),0.03));
		forecastData.add(new OccupancyUnit(new Date(1479125850000L),0.04));
		forecastData.add(new OccupancyUnit(new Date(1479125880000L),0.03));
		forecastData.add(new OccupancyUnit(new Date(1479125910000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479125940000L),0.03));
		forecastData.add(new OccupancyUnit(new Date(1479125970000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479126000000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479126030000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479126060000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126090000L),0.02));
		forecastData.add(new OccupancyUnit(new Date(1479126120000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126150000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126180000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126210000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126240000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126270000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126300000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126330000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126360000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126390000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126420000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126450000L),0.01));
		forecastData.add(new OccupancyUnit(new Date(1479126480000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126510000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126540000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126570000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126600000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126630000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126660000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126690000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126720000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126750000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126780000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126810000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126840000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126870000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126900000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126930000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126960000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479126990000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127020000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127050000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127080000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127110000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127140000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127170000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127200000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127230000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127260000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127290000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127320000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127350000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127380000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127410000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127440000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127470000L),0));
		forecastData.add(new OccupancyUnit(new Date(1479127500000L),0));





		DataPoint[] data = new DataPoint[forecastData.size()];
		for(int i=0;i<forecastData.size();i++){
			data[i]=new DataPoint(forecastData.get(i).getTime(), forecastData.get(i).getOccupancy());
		}

		return data;

	}
}
