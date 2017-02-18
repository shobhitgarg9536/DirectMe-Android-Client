package com.silab.direct_me.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.silab.direct_me.Interface.AsyncResponse;

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

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Lenovo on 19-Jan-17.
 */

public class ApiCalling extends AsyncTask<String, String, String> {

    public AsyncResponse delegate = null;//Call back interface
    ProgressDialog progressDialog;
    Context ctx;


    public ApiCalling(AsyncResponse asyncResponse, Context context) {
        delegate = asyncResponse;//Assigning call back interfacethrough constructor
        this.ctx = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }

    @Override

    public String doInBackground(String... args) {
        String authtoken = "";
        JSONObject jsonObject = null;
        String result = "";

        if(args[2]=="get")
            try {

                URL url = new URL(args[0]);


                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);

                connection.setRequestMethod("GET");
                connection.addRequestProperty("Authorization", "Token "+args[1]);
                connection.connect();



                int responseCode = connection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK)
                {

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
                    Toast.makeText(getApplicationContext(), responseCode,
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e)
            {
            }


        else if(args[2]=="post")
        {
            try {

                URL url = new URL(args[0]);


                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(15000 /* milliseconds */);
                connection.setConnectTimeout(15000 /* milliseconds */);

                connection.setRequestMethod("POST");
                connection.addRequestProperty("Authorization", "Token 54fff69acdaf6842d422b5fd5c15e10707383cd3");
                connection.connect();

                OutputStream outputStream = connection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("island_id", "UTF-8") + "=" + URLEncoder.encode(args[2], "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK)
                {

                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
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
                    Toast.makeText(getApplicationContext(), responseCode,
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e)
            {
            }

        }
        return result;

    }
    @Override
    protected void onPostExecute(String result) {

        delegate.processFinish(result);
        super.onPostExecute(result);
    }

}