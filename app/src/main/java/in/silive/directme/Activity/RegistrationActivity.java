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

import in.silive.directme.AsyncTask.UserAccountManagerBackgroundWorker;
import in.silive.directme.Interface.AsyncResponse;
import in.silive.directme.R;
import in.silive.directme.Utils.API_URL_LIST;


public class RegistrationActivity extends AppCompatActivity {
    public static final String MyPREFERENCES = "UserName";
    EditText etName, etEmail, etPassword;
    String name, email, password;
    SharedPreferences sharedpreferences;
    TextInputLayout textInputName, textInputPassword, textInputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createaccount);
        etName = (EditText) findViewById(R.id.etname);
        etEmail = (EditText) findViewById(R.id.etemail);
        etPassword = (EditText) findViewById(R.id.etpassword);
        textInputName = (TextInputLayout) findViewById(R.id.tilname);
        textInputPassword = (TextInputLayout) findViewById(R.id.tilpassword);
        textInputEmail = (TextInputLayout) findViewById(R.id.tilemail);

        etName.addTextChangedListener(new MyTextWatcher(etName));
        etPassword.addTextChangedListener(new MyTextWatcher(etPassword));
        etEmail.addTextChangedListener(new MyTextWatcher(etEmail));

    }

    public void CreateUserAccount(View view) {
        AssignTheValues();

        if ((validateName()) && validatePassword() && validateEmail()) {
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();

            editor.putString("UserName", name);
            editor.apply();
 /*           AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("username" , etName);
            params.put("etEmail" , email);
            params.put("etPassword" , etPassword);
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
            }, this);
            userAccountManagerBackgroundWorker.execute("CREATE_ACCOUNT", API_URL_LIST.USER_REGISTER_URL, name, email, password);

            Intent i = new Intent(this, UserLoginActivity.class);
            startActivity(i);

        }
    }

    private void AssignTheValues() {
        name = etName.getText().toString();
        email = etEmail.getText().toString();
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
        if (etPassword.getText().toString().trim().isEmpty() || etPassword.length() < 8) {
            if (etPassword.getText().toString().trim().isEmpty())
                textInputPassword.setError("Enter your Password");
            if (etPassword.length() < 8)
                textInputPassword.setError("Enter your etPassword of minimum 8 alphabets or digits");
            //requsting focus on editText
            requestFocus(etPassword);
            return false;
        } else {
            textInputPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        if (etEmail.getText().toString().trim().isEmpty() || !email.contains("@") || !email.contains(".com")) {
            if (email.trim().isEmpty())
                textInputEmail.setError("Enter your Email Id");
            if (!email.contains("@") || !email.contains(".com"))
                textInputEmail.setError("Enter your correct Email Id");
            //requsting focus on editText
            requestFocus(etEmail);
            return false;
        } else {
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

