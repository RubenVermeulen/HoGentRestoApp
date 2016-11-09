package resto.android.hogent.be.hogentresto;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;

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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by alexa on 2/11/2016.
 */

public class RestaurantTask extends AsyncTask<String, Void, JSONObject> {
    private RestaurantActivity context;

    public RestaurantTask(RestaurantActivity context) {
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
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

            JSONObject jsonObject = new JSONObject(jsonString);

            return jsonObject;


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
    protected void onPostExecute(JSONObject s) {
        super.onPostExecute(s);

        Restaurant r = new Restaurant();

        try {
            JSONObject jsonCoordinates = s.getJSONObject("coordinates");
            double coordinates[] = {jsonCoordinates.getDouble("lat"), jsonCoordinates.getDouble("lat")};

            r.setId(s.getString("_id"))
                    .setName(s.getString("name"))
                    .setAddress(s.getString("address"))
                    .setCoordinates(coordinates)
                    .setOpeningHours(s.getString("openingHours"));

            JSONArray jsonMenus = s.getJSONArray("menus");
            List<Menu> menus = r.getMenus();

            for (int i = 0; i < jsonMenus.length(); i++) {
                JSONObject menuObject = jsonMenus.getJSONObject(i);

                Menu m = new Menu();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                m.setId(menuObject.getString("_id"))
                        .setTitle(menuObject.getString("title"))
                        .setDescription(menuObject.getString("description"))
                        .setPrice(menuObject.getDouble("price"))
                        .setAvailableAt(simpleDateFormat.parse(menuObject.getString("availableAt")));

                menus.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MenusAdapter adapter = new MenusAdapter(context, r.getMenus());

        context.getListView().setAdapter(adapter);

//        context.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Restaurant r = (Restaurant) parent.getItemAtPosition(position);
//
//                Intent intent = new Intent(context, RestaurantActivity.class);
//                intent.putExtra("restaurant", r);
//
//                context.startActivity(intent);
//            }
//        });
    }
}
