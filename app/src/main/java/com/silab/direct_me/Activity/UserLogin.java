package com.silab.direct_me.Activity;

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

import com.silab.direct_me.AsyncTask.UserAccountManagerBackgroundWorker;
import com.silab.direct_me.Interface.AsyncResponse;
import com.silab.direct_me.R;
import com.silab.direct_me.Utils.API_URL_LIST;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shobhit-pc on 12/21/2016.
 */

public class UserLogin extends AppCompatActivity{
    EditText name,password;
    String sname,spassword;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "UserName";
    public static final String Authorization_Token = "Authorization_Token";
    TextInputLayout textInputName,textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
        name = (EditText) findViewById(R.id.etloginname);
        password = (EditText) findViewById(R.id.etloginpassword);
        textInputName = (TextInputLayout)  findViewById(R.id.tilloginname);
        textInputPassword = (TextInputLayout)  findViewById(R.id.tilloginpassword);


        name.addTextChangedListener(new UserLogin.MyTextWatcher(name));
        password.addTextChangedListener(new UserLogin.MyTextWatcher(password));

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("UserName","");
        name.setText(username);


    }
    public void CreateUserAccount(View view) {
        AssignTheValues();

        if ((validateName()) && validatePassword() ) {
/*
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("username" , sname);
            params.put("password" , spassword);
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
                        editor.putString("Authorization_Token" , authorization_Token);
                        editor.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            },this);
            userAccountManagerBackgroundWorker.execute("USER_ACCOUNT_LOGIN", API_URL_LIST.USER_LOGIN_URL, sname,spassword);

            Intent i = new Intent(this , Dashboard.class);
            startActivity(i);
        }
    }

    private void AssignTheValues() {
        sname = name.getText().toString();
        spassword = password.getText().toString();
    }
    private boolean validateName() {
        if (name.getText().toString().trim().isEmpty()) {
            textInputName.setError("Enter your full name");
            //requsting focus on editText
            requestFocus(name);
            return false;
        } else {
            textInputName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePassword() {
        if (password.getText().toString().trim().isEmpty()) {
            textInputPassword.setError("Enter your Passowrd");
            //requsting focus on editText
            requestFocus(password);
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
