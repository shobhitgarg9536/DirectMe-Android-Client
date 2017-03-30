package in.silive.directme.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.utils.API_URL_LIST;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.LoggerUtils;
import in.silive.directme.utils.ToasterUtils;


public class FetchData extends AsyncTask<String, String, String> {

    private String token = null;
    private FetchDataListener delegate = null;
    private String post_data = null;
    private String urlString = "";
    private Context mContext;
    ProgressDialog progressDialog;

    public FetchData(FetchDataListener fetchDataListener, Context context) {
        delegate = fetchDataListener;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        LoggerUtils.logger(
                "urlString :" + urlString +
                        "\ntoken :" + token +
                        "\nPost Data :" + post_data);
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();

    }

    public void setArgs(String url, String token, String post_data) {
        this.urlString = url;
        this.token = token;
        this.post_data = post_data;
    }

    @Override
    public String doInBackground(String... args) {
        String result = "";
        try {
            URL url = new URL(API_URL_LIST.BASE_URL + urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
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
            LoggerUtils.logger("response code : " + Integer.toString(responseCode));
            SharedPreferences sharedpreferences = DirectMe.getInstance().sharedPrefs;
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(Constants.RESPONSE_CODE, Integer.toString(responseCode));
            editor.commit();
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
                ToasterUtils.toaster("response code : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(String result) {
        LoggerUtils.logger("postExecute() : \n" + result);
        progressDialog.cancel();
        delegate.processFinish(result);
        super.onPostExecute(result);
    }
}