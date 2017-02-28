package in.silive.directme.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.utils.API_URL_LIST;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Shobhit-pc on 2/23/2017.
 */

public class LoginBackgroundWorker  extends AsyncTask<String , String , String> {

    public AsyncResponse delecate  = null;
    public static final String MyPREFERENCES = "Authorization_Token" ;
    ProgressDialog progressDialog;
    private String url;
    private String token;
    private String post_data;


    public LoginBackgroundWorker(AsyncResponse stringInterface){
        this.delecate = stringInterface;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    public void setArgs(String url, String token, String post_data){
        this.url = url;
        this.token = token;
        this.post_data = post_data;
    }

    @Override
    protected String doInBackground(String... params) {
        String token = "",result = "";
        String scopes = "oauth2:profile email";
        try {
            token = GoogleAuthUtil.getToken(getApplicationContext(), params[0] , scopes );
        } catch (IOException e) {
            e.printStackTrace();

        } catch (GoogleAuthException e) {
            e.printStackTrace();
        }
        try {
            URL url = new URL(API_URL_LIST.BASE_URL+API_URL_LIST.USER_GOOGLE_OAUTH_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("access_token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                String line = "";
                String tokenn="";
                while ((line = bufferedReader.readLine()) != null) {
                    try {
                        JSONObject jsonObject=new JSONObject(line);
                        tokenn=jsonObject.getString("token");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    result=tokenn;
                }
                bufferedReader.close();
                inputStream.close();
                SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("access_token", token);
                editor.putString("Authorization_Token", result);
                editor.commit();
            }
            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

//return result from the server
        return token;
    }

    @Override
    protected void onPostExecute(String s) {

        delecate.processFinish(s);
        super.onPostExecute(s);

    }
}