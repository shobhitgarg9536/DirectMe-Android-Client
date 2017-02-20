package in.silive.directme;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by yesha on 23-01-2017.
 */

public class Purchase_check
{
    public interface ResponsePurchase //to pass the values from asynctask to processfinish
    {

        void postFinish(String output);
    }

    public class FetchRequest extends AsyncTask<String, Void, String> {

        public Purchase_check.ResponsePurchase delegate = null;

        public FetchRequest(Purchase_check.ResponsePurchase delegate)
        {
            this.delegate=delegate;
        }

        protected void onPreExecute()
        {
        }

        public String doInBackground(String... arg0) {
            String dockid=arg0[0];

            String authtoken = "";
            JSONObject jsonObject = null;
            String result="";
            try
            {

                URL url = new URL("http://direct-me.herokuapp.com/core/update-ship/");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                // conn.setChunkedStreamingMode(0);

                //String post="nssdjb";
                conn.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.addRequestProperty("Authorization", "Token 54fff69acdaf6842d422b5fd5c15e10707383cd3");

                HashMap<String,Integer> hashMap=new HashMap<String, Integer>();
                hashMap.put("dock_id",9);
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("dock_id", 9);
                Log.d("jsonpaparam",jsonParam.toString());
                OutputStream outputStream = conn.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String post_data = URLEncoder.encode("ship_id","UTF-8")+"="+ URLEncoder.encode(dockid,"UTF-8");
                Log.d("hashmap",post_data);
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                //  outputStream.close();
                conn.connect();


                //OutputStreamWriter out = new   OutputStreamWriter(conn.getOutputStream());
                //out.write(jsonParam.toString());
                //out.close();

                /*OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();*/

                int responseCode = conn.getResponseCode();
                Log.d("response code", Integer.toString(responseCode));
                if (responseCode == 200)
                {
                    Log.d("response doinput",""+conn.getDoInput());
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line = "";

                    while ((line = in.readLine()) != null)
                    {

                        sb.append(line);
                        break;
                    }

                    in.close();
                    result=sb.toString();
                    Log.d("result",result);


                }
                if(responseCode==400)
                {
                    InputStream errorstream = conn.getErrorStream();

                    String response = "";

                    String line;

                    BufferedReader br = new BufferedReader(new InputStreamReader(errorstream));

                    while ((line = br.readLine()) != null)
                    {
                        response += line;
                    }
                    result = response;
                    Log.d("Bad Request", "Response: " + response);
                }
                else
                {
                    //Toast.makeText(getApplicationContext(),responseCode,
                            //Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                Log.d("Exception",e.getMessage());

            }

            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            delegate.postFinish(result);;

        }
    }

}


