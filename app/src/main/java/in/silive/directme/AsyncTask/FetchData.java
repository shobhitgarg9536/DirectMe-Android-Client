package in.silive.directme.AsyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import in.silive.directme.Interface.AsyncResponse;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Lenovo on 19-Jan-17.
 */

public class FetchData extends AsyncTask<String, String, String> {

    private AsyncResponse delegate = null;//Call back interface


    public FetchData(AsyncResponse asyncResponse) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    public String doInBackground(String... args) {
        String result = "";
            try {
                URL url = new URL(args[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod(args[1]);
                connection.addRequestProperty("Authorization", "Token "+args[2]);
                connection.connect();

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(args[3]);
                bufferedWriter.flush();
                bufferedWriter.close();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line;
                    while ((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    result = sb.toString();
                } else {
                    Toast.makeText(getApplicationContext(), responseCode,
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        return result;

    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
        super.onPostExecute(result);
    }
}