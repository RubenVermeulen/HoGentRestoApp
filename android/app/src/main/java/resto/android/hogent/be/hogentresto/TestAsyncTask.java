package resto.android.hogent.be.hogentresto;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

/**
 * Created by jonas on 2/11/2016.
 */

public class TestAsyncTask extends AsyncTask<Void, Void, String> {

    TextView txtJson;
    private Context myContext;
    private HttpURLConnection myUrl;
    private BufferedReader reader;

    public TestAsyncTask(Context context, HttpURLConnection url){
        myContext = context;
        myUrl = url;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        //..... ids ophalen
    }

    @Override
    protected String doInBackground(Void... params) {
        try{
            myUrl.connect();
            InputStream input = myUrl.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            return buffer.toString();


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


