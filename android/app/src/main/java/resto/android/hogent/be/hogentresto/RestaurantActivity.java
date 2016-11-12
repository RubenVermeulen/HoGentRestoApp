package resto.android.hogent.be.hogentresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class RestaurantActivity extends AppCompatActivity {

    private List<Menu> dataset;

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
    CardView stats;
    @BindView(R.id.expand)
    ImageView expand;
    @BindView(R.id.menus)
    LinearLayout menus;

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

        Restaurant r = (Restaurant) getIntent().getSerializableExtra("restaurant");

        setTitle(r.getName());

        ButterKnife.bind(this);

        setRestaurant(r);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HoGentRestoService service = retrofit.create(HoGentRestoService.class);

        Call<List<Menu>> call = service.menus(r.getId());

        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                dataset = response.body();

                for (Menu m : dataset) {
                    View child = getLayoutInflater().inflate(R.layout.menus_list_item, null);

                    title = (TextView) child.findViewById(R.id.title);
                    description = (TextView) child.findViewById(R.id.description);
                    price = (TextView) child.findViewById(R.id.price);

                    title.setText(m.getTitle());
                    description.setText(m.getDescription());
                    price.setText(String.format(Locale.getDefault(), "€ %.2f", m.getPrice()));

                    menus.addView(child);
                }
            }

            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                Log.d("CallBack", " Throwable is " +t);
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
}
