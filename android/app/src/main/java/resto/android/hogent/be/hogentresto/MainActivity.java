package resto.android.hogent.be.hogentresto;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    TextView txtJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_homescreen);
        txtJson = (TextView) findViewById(R.id.activity_json);
        TestAsyncTask asyncTask = new TestAsyncTask(MainActivity.this);
        asyncTask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

public class TestAsyncTask extends AsyncTask<Void, Void, String> {

    JSONObject jsonObject= new JSONObject();
    String jsonString = "";
    private Context myContext;
    private HttpURLConnection myUrl;
    private BufferedReader reader;

    public TestAsyncTask(Context context){
        myContext = context;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpsURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL("https://ec2-54-93-95-178.eu-central-1.compute.amazonaws.com:3000/restaurants");

            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            jsonString = buffer.toString();
            jsonObject = new JSONObject(jsonString);

            // makes sure the swipe to refresh icon is show (prevents flickering)
            Thread.sleep(1000);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException | JSONException | InterruptedException e) {

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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (jsonString.isEmpty()){txtJson.setText("tis leeg");}
        else{
        txtJson.setText(jsonString);}
    }
}
}
