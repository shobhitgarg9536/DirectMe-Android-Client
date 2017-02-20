package in.silive.directme;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by yesha on 30-01-2017.
 */

public class fetch_dock_id {

    public interface Response //to pass the values from asynctask to processfinish
    {

        void processfinish(String output);
    }

    public class FetchRequest extends AsyncTask<String, Void, String> {

        public Response delegate = null;

        public FetchRequest(Response delegate)
        {
            this.delegate=delegate;
        }

        protected void onPreExecute()
        {
        }

        public String doInBackground(String... arg0) {
            String authtoken = "";
            JSONObject jsonObject = null;
            String result = "";
            try {

                URL url = new URL("http://direct-me.herokuapp.com/core/docks/");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);

                conn.setRequestMethod("GET");
                conn.addRequestProperty("Authorization", "Token 54fff69acdaf6842d422b5fd5c15e10707383cd3");
                conn.connect();



                int responseCode = conn.getResponseCode();
                Log.d("fetch response",""+responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    result = sb.toString();
                }
                else
                {
                }
            } catch (Exception e)
            {
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
         delegate.processfinish(result);

        }
    }

}
