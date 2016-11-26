package resto.android.hogent.be.hogentresto;



import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import resto.android.hogent.be.hogentresto.MenuTabs.Dinsdag;
import resto.android.hogent.be.hogentresto.MenuTabs.Maandag;
import resto.android.hogent.be.hogentresto.helpers.Traffic;
import resto.android.hogent.be.hogentresto.models.Menu;
import resto.android.hogent.be.hogentresto.models.Restaurant;

public class RestaurantActivity extends AppCompatActivity {


    private List<Menu> dataset;
    private Restaurant r;

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
    //@BindView(R.id.toolbar)
    //Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabLayout;

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
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        //getMenus();
    }

    /*private void getMenus() {
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
                    menus.addView(child);
                }
                else {
                    View child;

                    for (Menu m : dataset) {
                        child = getLayoutInflater().inflate(R.layout.menus_list_item, null);

                        title = (TextView) child.findViewById(R.id.title);
                        description = (TextView) child.findViewById(R.id.description);
                        price = (TextView) child.findViewById(R.id.price);

                        title.setText(m.getTitle());
                        description.setText(m.getDescription());
                        price.setText(String.format(Locale.getDefault(), "€ %.2f", m.getPrice()));

                        menus.addView(child);
                    }
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
    }*/

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

    /*public void refresh(View view) {
        progressBar.setVisibility(View.VISIBLE);
        //getMenus();
    }*/
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Maandag(), "Ma");
        adapter.addFrag(new Dinsdag(), "Di");
        adapter.addFrag(new Dinsdag(), "Woe");
        adapter.addFrag(new Dinsdag(), "Do");
        adapter.addFrag(new Dinsdag(), "Vr");
        viewPager.setAdapter(adapter);

    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {

            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

