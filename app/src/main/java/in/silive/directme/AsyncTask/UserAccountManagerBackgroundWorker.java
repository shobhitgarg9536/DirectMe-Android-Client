package in.silive.directme.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import in.silive.directme.Interface.AsyncResponse;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Shobhit-pc on 2/18/2017.
 */

public class UserAccountManagerBackgroundWorker extends AsyncTask<String, String, String> {

    private AsyncResponse delegate = null;//Call back interface
    ProgressDialog progressDialog;
    private Context ctx;


    public UserAccountManagerBackgroundWorker(AsyncResponse asyncResponse, Context context) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
        this.ctx = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... args) {
        String result = "";
        String sUrl = args[1];
        String post_data = "";
        if (args[0].equals("CREATE_ACCOUNT")) {
            String sName = args[2];
            String sEmail = args[3];
            String sPassword = args[4];

            try {
                post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(sName, "UTF-8") + "&" +
                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(sEmail, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(sPassword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else if (args[1].equals("")) {
            String sName = args[2];
            String sPassword = args[3];
            try {
                post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(sName, "UTF-8") + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(sPassword, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {

            URL url = new URL(sUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);

            connection.setRequestMethod("GET");
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer sb = new StringBuffer("");
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
        }

        return result;
    }


    @Override
    protected void onPostExecute(String s) {
        delegate.processFinish(s);
        super.onPostExecute(s);
    }


}
