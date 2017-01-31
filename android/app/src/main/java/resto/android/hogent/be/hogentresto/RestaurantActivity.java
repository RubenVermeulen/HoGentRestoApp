package resto.android.hogent.be.hogentresto;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.astuetz.PagerSlidingTabStrip;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import resto.android.hogent.be.hogentresto.adapters.MenuPagerAdapter;
import resto.android.hogent.be.hogentresto.config.Config;
import resto.android.hogent.be.hogentresto.helpers.DummyData;
import resto.android.hogent.be.hogentresto.helpers.Traffic;
import resto.android.hogent.be.hogentresto.models.Menu;
import resto.android.hogent.be.hogentresto.models.OccupancyUnit;
import resto.android.hogent.be.hogentresto.models.Restaurant;
import resto.android.hogent.be.hogentresto.services.HoGentRestoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestaurantActivity extends AppCompatActivity {

    private List<Menu> menus;
    private MenuPagerAdapter adapter;
    private List<Menu> dataset;
    private Restaurant r;
    public Map<Integer, List<Menu>> menusFromApi;
    private Uri gmmIntentUri = null;

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
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.refresh)
    TextView refresh;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabsStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        r = (Restaurant) getIntent().getSerializableExtra("restaurant");

        setTitle(r.getName());

        ButterKnife.bind(this);
        setRestaurant(r);

        menusFromApi = new HashMap<>();

        getMenus();

        adapter = new MenuPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        pager.setCurrentItem(currentBusinessDay());
        pager.setOffscreenPageLimit(4);
        pager.destroyDrawingCache();

        tabsStrip.setViewPager(pager);

        initializeGraph();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionLocation:
                if (gmmIntentUri != null) {
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    startActivity(mapIntent);

                    return true;
                }

                return super.onOptionsItemSelected(item);

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void getMenus() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HoGentRestoService service = retrofit.create(HoGentRestoService.class);

        Call<List<Menu>> call = service.menus(r.getId());

        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                progressBar.setVisibility(View.GONE);
                refresh.setVisibility(View.GONE);

                menusFromApi.clear();
                dataset = response.body();

                for (Menu m : dataset) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(m.getAvailableAt());

                    int key = c.get(Calendar.DAY_OF_WEEK);

                    if (menusFromApi.containsKey(key)) {
                        menus = menusFromApi.get(key);
                    }
                    else {
                        menus = new ArrayList<>();
                    }

                    menus.add(m);
                    menusFromApi.put(key, menus);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);

                Toast toast = Toast.makeText(RestaurantActivity.this, R.string.not_connected, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void setRestaurant(Restaurant r) {
        Picasso.with(this).load(r.getUrlImage()).into(thumbnail);
        name.setText(r.getName());
        openingHours.setText(r.getOpeningHours());

        Traffic.setTraffic(r.getOccupancy(), trafficGrade, trafficIndicator);

        gmmIntentUri = Uri.parse(String.format(Locale.getDefault(), "geo:%s,%s?q=%s,%s(Hogent resto:+%s)",
                r.getCoordinates().getLat(),
                r.getCoordinates().getLon(),
                r.getCoordinates().getLat(),
                r.getCoordinates().getLon(),
                name
        ));
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

    public void refresh(View view) {
        progressBar.setVisibility(View.VISIBLE);
        getMenus();
    }

    public List<Menu> getMenusFromApi(int position) {
        return menusFromApi.get(position);
    }

    /**
     * Monday: 0
     * Tuesday: 1
     * ...
     *
     * @return
     */
    private int currentBusinessDay() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // saturday: 0; sunday: 1; ...

        if (dayOfWeek == 0 || dayOfWeek == 1) {
            dayOfWeek = 1;
        }

        return dayOfWeek - 2;
    }

    private void initializeGraph() {
        GraphView graph = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> forecastSeries = new LineGraphSeries<>(DummyData.getForecastData());
        graph.addSeries(forecastSeries);

        LineGraphSeries<DataPoint> currentPosition = new LineGraphSeries<>(DummyData.getCurrentPosition());

        Paint paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorTrafficFull));
        paint.setStrokeWidth(8);

        currentPosition.setCustomPaint(paint);

        graph.addSeries(currentPosition);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {
                SimpleDateFormat format = new SimpleDateFormat("kk:mm", Locale.getDefault());

                if(isValueX) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis((long) value);

                    return format.format(cal.getTime());
                }

                return super.formatLabel(value,isValueX);
            }
        });

        //
        graph.getGridLabelRenderer().setNumHorizontalLabels(5);

        graph.getViewport().setMinX(DummyData.getForecastData()[0].getX());
        graph.getViewport().setMaxX(DummyData.getForecastData()[DummyData.getForecastData().length - 1].getX());

        graph.getGridLabelRenderer().setNumVerticalLabels(5);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(1);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getGridLabelRenderer().setHumanRounding(false);

        graph.getViewport().setScrollable(false);
        graph.refreshDrawableState();
    }
}
