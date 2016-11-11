package resto.android.hogent.be.hogentresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import resto.android.hogent.be.hogentresto.models.Restaurant;

public class RestaurantActivity extends AppCompatActivity {

    private ListView listView;

    private String url = "http://ec2-54-93-95-178.eu-central-1.compute.amazonaws.com:3000/restaurants/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        Restaurant r = (Restaurant) getIntent().getSerializableExtra("restaurant");

        setTitle(r.getName());

        listView = (ListView) findViewById(R.id.list);

        url += r.getId();

//        RestaurantTask asyncTask = new RestaurantTask(this);
//        asyncTask.execute(url);
    }

    public ListView getListView() {
        return listView;
    }
}
