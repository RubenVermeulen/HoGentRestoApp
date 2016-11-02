package resto.android.hogent.be.hogentresto;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
        try{
            URL url = new URL("http://ec2-54-93-95-178.eu-central-1.compute.amazonaws.com:3000/restaurants");
            myUrl = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(myUrl.getInputStream());
                reader = new BufferedReader(new InputStreamReader(in));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                }

                return buffer.toString();
            } finally {
                myUrl.disconnect();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (myUrl != null) {
                myUrl.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        txtJson.setText(result);
    }
}
}
