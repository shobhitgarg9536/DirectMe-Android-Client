package com.silab.direct_me;

import android.app.ProgressDialog;
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

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Lenovo on 19-Jan-17.
 */

public class MyAsyncTask extends AsyncTask<String, String, String> {

    public AsyncResponse delegate = null;//Call back interface
    ProgressDialog progressDialog;
    Context ctx;


    public MyAsyncTask(AsyncResponse asyncResponse, Context context) {
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
        String type="copy";
        if(type==args[0])
        try {

            URL url = new URL("http://direct-me.herokuapp.com/core/ports/");


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);

            conn.setRequestMethod("GET");
            conn.addRequestProperty("Authorization", "Token "+args[1]);
            conn.connect();



            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK)
            {

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
                Toast.makeText(getApplicationContext(), responseCode,
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e)
        {
        }
        else if(args[0]=="fragments")
        {
            try {

                URL url = new URL("http://direct-me.herokuapp.com/core/ships/");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);

                conn.setRequestMethod("GET");
                conn.addRequestProperty("Authorization", "Token "+args[1]);
                conn.connect();



                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK)
                {

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
                    Toast.makeText(getApplicationContext(), responseCode,
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e)
            {
            }
        }
        else if(args[0]=="dashboard")
        {
            try {

                URL url = new URL("http://direct-me.herokuapp.com/user/");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);

                conn.setRequestMethod("GET");
                conn.addRequestProperty("Authorization", "Token 54fff69acdaf6842d422b5fd5c15e10707383cd3");
                conn.connect();



                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK)
                {

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
                    Toast.makeText(getApplicationContext(), responseCode,
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e)
            {
            }

        }
        else if(args[0]=="parkNowUserList")
        {
            try {

                URL url = new URL("http://direct-me.herokuapp.com/user/get-suggestions/");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);

                conn.setRequestMethod("POST");
                conn.addRequestProperty("Authorization", "Token 54fff69acdaf6842d422b5fd5c15e10707383cd3");
                conn.connect();

                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("island_id", "UTF-8") + "=" + URLEncoder.encode(args[2], "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();

                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK)
                {

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
