package resto.android.hogent.be.hogentresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        Restaurant r = (Restaurant) getIntent().getSerializableExtra("restaurant");

        setTitle(r.getName());
    }
}
