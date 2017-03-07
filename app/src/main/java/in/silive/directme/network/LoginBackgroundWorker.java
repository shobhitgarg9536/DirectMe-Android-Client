package in.silive.directme.network;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;

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

import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.utils.API_URL_LIST;


/**
 * Created by Shobhit-pc on 2/23/2017.
 */

public class LoginBackgroundWorker  extends AsyncTask<String , String , String> {

    private FetchDataListener delecate  = null;

    public LoginBackgroundWorker(FetchDataListener stringInterface){
        this.delecate = stringInterface;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }
    public void setArgs(String url, String token, String post_data){
    }

    @Override
    protected String doInBackground(String... params) {
        String token = "",result = "";
        String scopes = "oauth2:profile email";
        try {
            token = GoogleAuthUtil.getToken(DirectMe.getInstance(), params[0] , scopes );
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
                while ((line = bufferedReader.readLine()) != null) {

                        result += line;
                }
                bufferedReader.close();
                inputStream.close();
            }
            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

//return result from the server
        return result;
    }

    @Override
    protected void onPostExecute(String s) {

        delecate.processFinish(s);
        super.onPostExecute(s);

    }
}