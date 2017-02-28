package in.silive.directme.network;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.LoggerUtils;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Lenovo on 19-Jan-17.
 */

public class FetchData extends AsyncTask<String, String, String> {

    String token = null;
    private AsyncResponse delegate = null;//Call back interface
    private String post_data = null;
    private String url = "";

    public FetchData(AsyncResponse asyncResponse) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
    }

    @Override
    protected void onPreExecute() {
        LoggerUtils.logger("pre execute : " +
                "\nurl :" + url +
                "\ntoken :" + token +
                "\nPost Data :" + post_data);
        super.onPreExecute();

    }

    public void setArgs(String url, String token, String post_data) {
        this.url = url;
        this.token = token;
        this.post_data = post_data;
    }

    @Override
    public String doInBackground(String... args) {
        String result = "";
        try {
            URL url = new URL(API_URL_LIST.BASE_URL + this.url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
//                connection.setRequestMethod("GET");
            connection.addRequestProperty("Authorization", "Token " + token);

            if (!this.post_data.equals("")) {
                connection.setRequestMethod("POST");
            }
            connection.connect();

            if (!this.post_data.equals("")) {
                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
            }

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder("");
                String line = "";

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
        LoggerUtils.logger("post execute : " + result);
        delegate.processFinish(result);
        super.onPostExecute(result);
    }
}