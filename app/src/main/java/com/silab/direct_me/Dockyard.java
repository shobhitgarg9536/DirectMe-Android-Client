package com.silab.direct_me;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Dockyard extends AppCompatActivity
{
    JSONArray jArray;
    ViewPager mViewPager;
    int count=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        new SendRequest().execute();


    }

    void startfragments()
    {
        mViewPager.setAdapter(new Dockyard.BoatPagerAdapter(
                getSupportFragmentManager()));
    }
    public class SendRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute()
        {
        }

        public String doInBackground(String... arg0) {
            String authtoken = "";
            JSONObject jsonObject = null;
            String result = "";
            try {

                URL url = new URL("http://direct-me.herokuapp.com/user/ships/");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);

                conn.setRequestMethod("GET");
                conn.addRequestProperty("Authorization", "Token 54fff69acdaf6842d422b5fd5c15e10707383cd3");
                conn.connect();

                /*OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();*/

                int responseCode = conn.getResponseCode();

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
                    Toast.makeText(getApplicationContext(), responseCode,
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e)
            {
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            try
            {
                jArray = new JSONArray(result);
                count=jArray.length();

            }
            catch (JSONException e)
            {

            }
            startfragments();

        }
    }

    public class BoatPagerAdapter extends FragmentPagerAdapter
    {

        public BoatPagerAdapter(FragmentManager fm)
        {

            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            JSONObject json_send=null;
            if(jArray!=null) {
                try {
                    if (position == 0)
                    {
                        json_send = jArray.getJSONObject(0);
                        return Boats_equipped.newInstance(json_send);
                    }
                    if(position==1)
                    {
                        json_send = jArray.getJSONObject(1);
                        return Boats_equipped.newInstance(json_send);
                    }
                    if(position==2) {
                        json_send = jArray.getJSONObject(2);
                        return Boats_equipped.newInstance(json_send);
                    }
                    if(position==3) {
                        json_send = jArray.getJSONObject(3);
                        return Boats_equipped.newInstance(json_send);
                    }
                    else
                        json_send = jArray.getJSONObject(4);
                    return Boats_equipped.newInstance(json_send);

                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;

                }
            }
            else
                return null;
        }


        @Override
        public int getCount()
        {
            return count;

        }
    }
}
