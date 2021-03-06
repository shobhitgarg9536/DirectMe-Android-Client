package in.silive.directme.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.R;
import in.silive.directme.application.DirectMe;
import in.silive.directme.listeners.FetchDataListener;
import in.silive.directme.network.LoginBackgroundWorker;
import in.silive.directme.utils.Constants;
import in.silive.directme.utils.ToasterUtils;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {


    public int RC_SIGN_IN = 100;
    private SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    //This function will option signing intent
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            int statusCode = result.getStatus().getStatusCode();
            System.out.print(statusCode);
            handleSignInResult(result);
        }
    }

    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            String email = acct.getEmail();


            LoginBackgroundWorker loginBackgroundWorker = new LoginBackgroundWorker(new FetchDataListener() {
                @Override
                public void processStart() {

                }

                @Override
                public void processFinish(String output) {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(output);
                        token = jsonObject.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences sharedpreferences = DirectMe.getInstance().sharedPrefs;
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Constants.AUTH_TOKEN, token);
                    editor.commit();

                    System.out.println(output);
                    startDashboardActivity();
                }
            });
            loginBackgroundWorker.execute(email);
        } else {
            //If login fails
            ToasterUtils.toaster("Login Failed");
        }
    }

    private void startDashboardActivity() {
        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            signIn();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        ToasterUtils.toaster(connectionResult.getErrorMessage());
    }

}