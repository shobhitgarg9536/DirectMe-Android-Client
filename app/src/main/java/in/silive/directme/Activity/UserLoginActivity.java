package in.silive.directme.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import in.silive.directme.AsyncTask.UserAccountManagerBackgroundWorker;
import in.silive.directme.Interface.AsyncResponse;
import in.silive.directme.R;
import in.silive.directme.Utils.API_URL_LIST;

/**
 * Created by Shobhit-pc on 12/21/2016.
 */

public class UserLoginActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "UserName";
    public static final String Authorization_Token = "Authorization_Token";
    EditText etName, etPassword;
    String name, password;
    SharedPreferences sharedpreferences;
    TextInputLayout textInputName, textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
        etName = (EditText) findViewById(R.id.etloginname);
        etPassword = (EditText) findViewById(R.id.etloginpassword);
        textInputName = (TextInputLayout) findViewById(R.id.tilloginname);
        textInputPassword = (TextInputLayout) findViewById(R.id.tilloginpassword);


        etName.addTextChangedListener(new UserLoginActivity.MyTextWatcher(etName));
        etPassword.addTextChangedListener(new UserLoginActivity.MyTextWatcher(etPassword));

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("UserName", "");
        etName.setText(username);


    }

    public void CreateUserAccount(View view) {
        AssignTheValues();

        if ((validateName()) && validatePassword()) {
/*
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("username" , etName);
            params.put("etPassword" , etPassword);
            client.post("http://direct-me.herokuapp.com/user/login/" ,params, new AsyncHttpResponseHandler()
                    {
                        @Override
                        public void onSuccess(String response){
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String authorization_Token = jsonObject.getString("token");
                                sharedpreferences = getSharedPreferences(Authorization_Token, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString("Authorization_Token" , authorization_Token);
                                editor.commit();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                        // When error occured

                        @Override
                        public void onFailure(int statusCode, Throwable error, String content) {
                            // TODO Auto-generated method stub
                            // Hide ProgressBar
                            if (statusCode == 404) {

                                Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                            } else if (statusCode == 500) {
                                Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet]",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            );
            */

            UserAccountManagerBackgroundWorker userAccountManagerBackgroundWorker = new UserAccountManagerBackgroundWorker(new AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    try {
                        JSONObject jsonObject = new JSONObject(output);
                        String authorization_Token = jsonObject.getString("token");
                        sharedpreferences = getSharedPreferences(Authorization_Token, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("Authorization_Token", authorization_Token);
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, this);
            userAccountManagerBackgroundWorker.execute("USER_ACCOUNT_LOGIN", API_URL_LIST.USER_LOGIN_URL, name, password);

            Intent i = new Intent(this, DashboardActivity.class);
            startActivity(i);
        }
    }

    private void AssignTheValues() {
        name = etName.getText().toString();
        password = etPassword.getText().toString();
    }

    private boolean validateName() {
        if (etName.getText().toString().trim().isEmpty()) {
            textInputName.setError("Enter your full etName");
            //requsting focus on editText
            requestFocus(etName);
            return false;
        } else {
            textInputName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (etPassword.getText().toString().trim().isEmpty()) {
            textInputPassword.setError("Enter your Passowrd");
            //requsting focus on editText
            requestFocus(etPassword);
            return false;
        } else {
            textInputName.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            //showing keyboard
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (view.getId()) {
                case R.id.etname:
                    textInputName.setErrorEnabled(false);
                    break;
                case R.id.etpassword:
                    textInputPassword.setErrorEnabled(false);
                    break;
            }
        }

        public void afterTextChanged(Editable editable) {

        }
    }


}
