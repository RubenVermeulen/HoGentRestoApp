package resto.android.hogent.be.hogentresto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtJson;
    ListView listView;

    private String url = "http://ec2-54-93-95-178.eu-central-1.compute.amazonaws.com:3000/restaurants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_homescreen);

        listView = (ListView) findViewById(R.id.list);

        RestaurantsTask asyncTask = new RestaurantsTask(MainActivity.this);
        asyncTask.execute(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public TextView getTxtJson() {
        return txtJson;
    }

    public ListView getListView() {
        return listView;
    }
}
