package resto.android.hogent.be.hogentresto;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import resto.android.hogent.be.hogentresto.adapters.RestaurantsAdapter;
import resto.android.hogent.be.hogentresto.config.Config;
import resto.android.hogent.be.hogentresto.models.Restaurant;
import resto.android.hogent.be.hogentresto.services.HoGentRestoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private List<Restaurant> dataset;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.refresh)
    TextView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_homescreen);

        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // set layout manager
        setLayoutManager();

        recyclerView.setLayoutManager(layoutManager);

        // API
        getRestaurants();
    }

    private void getRestaurants() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HoGentRestoService service = retrofit.create(HoGentRestoService.class);

        Call<List<Restaurant>> call = service.restaurants();

        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                progressBar.setVisibility(View.GONE);
                refresh.setVisibility(View.GONE);

                dataset = response.body();

                Collections.sort(dataset, new Comparator<Restaurant>() {
                    @Override
                    public int compare(Restaurant o1, Restaurant o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });

                adapter = new RestaurantsAdapter(MainActivity.this, dataset);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                refresh.setVisibility(View.VISIBLE);

                Toast toast = Toast.makeText(MainActivity.this, R.string.not_connected, Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private void setLayoutManager() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 1);
        }
        else{
            layoutManager = new GridLayoutManager(this, 2);
        }
    }

    public void refresh(View view) {
        progressBar.setVisibility(View.VISIBLE);
        getRestaurants();
    }
}
