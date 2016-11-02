package resto.android.hogent.be.hogentresto;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



public class RestaurantTask extends AsyncTask<String, Void, JSONArray> {

    private MainActivity context;
    private Map<String, Restaurant> restaurants;

    public RestaurantTask(MainActivity context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //..... ids ophalen
    }

    @Override
    protected JSONArray doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String jsonString = buffer.toString();

            JSONArray jsonArray = new JSONArray(jsonString);

            return jsonArray;


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(JSONArray s) {
        super.onPostExecute(s);

        /// List view
        List<Restaurant> values = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            try {
                JSONObject object = s.getJSONObject(i);

                Restaurant r = new Restaurant();

                JSONObject jsonCoordinates = object.getJSONObject("coordinates");
                double coordinates[] = {jsonCoordinates.getDouble("lat"), jsonCoordinates.getDouble("lat")};

                r.setId(object.getString("_id"))
                        .setName(object.getString("name"))
                        .setAddress(object.getString("address"))
                        .setCoordinates(coordinates)
                        .setOpeningHours(object.getString("openingHours"));

                values.add(r);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        RestaurantAdapter adapter = new RestaurantAdapter(context, values);

        context.getListView().setAdapter(adapter);

        context.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant r = (Restaurant) parent.getItemAtPosition(position);

                Intent intent = new Intent(context, RestaurantActivity.class);
                intent.putExtra("restaurant", r);

                context.startActivity(intent);
            }
        });
    }
}


