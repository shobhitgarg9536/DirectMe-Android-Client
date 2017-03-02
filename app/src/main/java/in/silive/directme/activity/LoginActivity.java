package in.silive.directme.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.application.DirectMe;
import in.silive.directme.network.LoginBackgroundWorker;
import in.silive.directme.listeners.AsyncResponse;
import in.silive.directme.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {


    //Signin button
    private SignInButton signInButton;
    //Signing Options
    private GoogleSignInOptions gso;
//    public static final String MyPREFERENCES = "Authorization_Token" ;
    //google api client
    private GoogleApiClient mGoogleApiClient;
    public static final String usergid = "gidKey";
    public static final String userfbid = "Authorization_Token";
    //Signin constant to check the activity result
    public int RC_SIGN_IN = 100;
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        info = (TextView) findViewById(R.id.info);

        SharedPreferences sharedpreferences = DirectMe.getInstance().sharedPrefs;
        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //Setting onclick listener to signing button
        signInButton.setOnClickListener(this);
        loginButton = (LoginButton) findViewById(R.id.login_button);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                info.setText(
                        "UserModel ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
                Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(i);

            }

            @Override
            public void onCancel() {
                info.setText("LoginActivity attempt canceled.");

            }

            @Override
            public void onError(FacebookException e) {

                info.setText("LoginActivity attempt failed.");
            }
        });

    }


    //This function will option signing intent
    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }

    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String email = acct.getEmail();


            LoginBackgroundWorker loginBackgroundWorker = new LoginBackgroundWorker(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    JSONObject jsonObject= null;
                    try {
                        jsonObject = new JSONObject(output);
                        token=jsonObject.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                  
                    SharedPreferences sharedpreferences = DirectMe.getInstance().sharedPrefs;
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("Authorization_Token", token);
                    editor.commit();

                    System.out.println(output);
                    info.setText(output);
                    startDashboardActivity();
                }
            });
            loginBackgroundWorker.execute(email);


        } else {
            //If login fails
            Toast.makeText(this, "LoginActivity Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void startDashboardActivity() {
        Intent i = new Intent(LoginActivity.this , DashboardActivity.class);
        finish();
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            //Calling signin
            signIn();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}