package in.silive.directme.AsyncTask;


import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import in.silive.directme.Interface.AsyncResponse;
import in.silive.directme.Utils.API_URL_LIST;
import in.silive.directme.Utils.FCMConfig;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FirebaseTokenBackgroundWorker extends AsyncTask<String, String, String> {

    private AsyncResponse delecate = null;

    public FirebaseTokenBackgroundWorker(AsyncResponse stringInterface) {
        this.delecate = stringInterface;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        try {

            String firebaseUid = params[0];
            URL url = new URL(API_URL_LIST.FIREBASE_TOKEN_UPDATE);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("access_token", "UTF-8") + "=" + URLEncoder.encode(firebaseUid, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(FCMConfig.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("FirebaseIdSendToServer", "1");//1 means firebase id is registered
        editor.commit();
        delecate.processFinish(s);
        super.onPostExecute(s);

    }
}
