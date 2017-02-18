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


public class CreateAccount extends AppCompatActivity {
    EditText name,email,password;
    String sname,semail,spassword;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "UserName";
    TextInputLayout textInputName,textInputPassword,textInputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);
        name = (EditText) findViewById(R.id.etname);
        email = (EditText) findViewById(R.id.etemail);
        password = (EditText) findViewById(R.id.etpassword);
        textInputName = (TextInputLayout)  findViewById(R.id.tilname);
        textInputPassword = (TextInputLayout)  findViewById(R.id.tilpassword);
        textInputEmail = (TextInputLayout)  findViewById(R.id.tilemail);

        name.addTextChangedListener(new MyTextWatcher(name));
        password.addTextChangedListener(new MyTextWatcher(password));
        email.addTextChangedListener(new MyTextWatcher(email));

    }
    public void CreateUserAccount(View view) {
        AssignTheValues();

        if ((validateName()) && validatePassword() && validateEmail()) {
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("UserName", sname);
            editor.commit();
 /*           AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("username" , sname);
            params.put("email" , semail);
            params.put("password" , spassword);
            client.post("http://direct-me.herokuapp.com/user/register/" ,params, new AsyncHttpResponseHandler()
            {
            @Override
            public void onSuccess(String response){

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

                }
            },this);
            userAccountManagerBackgroundWorker.execute("CREATE_ACCOUNT", API_URL_LIST.USER_REGISTER_URL, sname,semail,spassword);

            Intent i = new Intent(this , UserLogin.class);
            startActivity(i);

        }
    }

    private void AssignTheValues() {
        sname = name.getText().toString();
        semail = email.getText().toString();
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
        if (password.getText().toString().trim().isEmpty() || password.length() < 8) {
            if(password.getText().toString().trim().isEmpty())
                textInputPassword.setError("Enter your Password");
            if(password.length() < 8 )
                textInputPassword.setError("Enter your password of minimum 8 alphabets or digits");
            //requsting focus on editText
            requestFocus(password);
            return false;
        }
        else {
            textInputPassword.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateEmail() {
        if (email.getText().toString().trim().isEmpty() || !semail.contains("@") || !semail.contains(".com") ) {
            if(semail.trim().isEmpty())
                textInputEmail.setError("Enter your Email Id");
            if(!semail.contains("@") || !semail.contains(".com"))
                textInputEmail.setError("Enter your correct Email Id");
            //requsting focus on editText
            requestFocus(email);
            return false;
        }
        else {
            textInputEmail.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            //showing keyboard
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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
                case R.id.etemail:
                    textInputEmail.setErrorEnabled(false);
                    break;
            }
        }
        public void afterTextChanged(Editable editable) {

        }
    }

}

