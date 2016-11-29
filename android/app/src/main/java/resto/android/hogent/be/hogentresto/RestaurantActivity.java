package resto.android.hogent.be.hogentresto;



import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import resto.android.hogent.be.hogentresto.MenuTabs.MenuFragment;
import resto.android.hogent.be.hogentresto.adapters.MenuAdapter;
import resto.android.hogent.be.hogentresto.config.Config;
import resto.android.hogent.be.hogentresto.helpers.Traffic;
import resto.android.hogent.be.hogentresto.models.Menu;
import resto.android.hogent.be.hogentresto.models.Restaurant;
import resto.android.hogent.be.hogentresto.services.HoGentRestoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static resto.android.hogent.be.hogentresto.R.id.progressBar;

public class RestaurantActivity extends AppCompatActivity {

    private MenuFragment fragment;
    private MenuAdapter adapter;
    private List<Menu> dataset;
    private Restaurant r;
    Map<String, List<Menu>> menus = new HashMap<>();
    DateFormat df = new SimpleDateFormat("EEEE");
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
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.refresh)
    TextView refresh;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabsStrip;

    TextView title;
    TextView description;
    TextView price;

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
        adapter = new MenuAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabsStrip.setViewPager(viewPager);

        //getMenus();

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

                dataset = response.body();

                if (dataset.isEmpty()) {
                    View child = getLayoutInflater().inflate(R.layout.menus_no_results, null);
                    viewPager.addView(child);
                }
                else {

                    for (Menu m : dataset) {
                        menus.put(df.format(m.getAvailableAt()).toLowerCase(), Arrays.asList(m));
                    }
                    fragment = adapter.getFragment();
                    fragment.setMenus(menus);
                }
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

    public void refresh(View view) {
        progressBar.setVisibility(View.VISIBLE);
        getMenus();
    }






}

