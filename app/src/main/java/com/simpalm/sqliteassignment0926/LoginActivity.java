package com.simpalm.sqliteassignment0926;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//
public class LoginActivity extends Activity {

    private EditText mUsernameEt, mPasswordEt;
    private Button mLoginBtn, mSignUpBtn;
    private UserDataSource userDataSource;
    private AsyncTask<String, Void, String> asyncTask;
    private ProgressDialog mProgressdialog;
    public SharedPreferences mSharedPreferences;
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mUsernameEt = (EditText) findViewById(R.id.username_login_et);
        mPasswordEt = (EditText) findViewById(R.id.password_login_et);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mSignUpBtn = (Button) findViewById(R.id.signup_btn);
        userDataSource = new UserDataSource(this);
        mUsernameEt.requestFocus();
        mProgressdialog = new ProgressDialog(this);
        asyncTask = new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                mProgressdialog.setMessage("Loading please wait.....");
                mProgressdialog.show();

                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String[] params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                userDataSource.open();
                String password = userDataSource.checkUserPassword(params[0]);


                return password;
            }

            @Override
            protected void onPostExecute(String password) {
                super.onPostExecute(password);
                mProgressdialog.dismiss();
                userDataSource.closeDatabase();
                String username = mUsernameEt.getText().toString();
                String pwd = mPasswordEt.getText().toString();
                if (password.equals(pwd)) {

                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("username", username);
                    editor.commit();


                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        };


        mSignUpBtn.setOnClickListener(new View.OnClickListener()

                                      {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                                              startActivity(intent);
                                              finish();
                                          }
                                      }

        );

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validLoginFields() == true) {

                    asyncTask.execute(mUsernameEt.getText().toString());

                }

            }
        });
    }


    public boolean validLoginFields() {

        userDataSource.open();
        if (mUsernameEt.getText().toString().length() == 0) {
            Toast.makeText(this, "Username cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPasswordEt.getText().toString().length() == 0) {
            Toast.makeText(this, "Password cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (userDataSource.checkUserNotExist(mUsernameEt.getText().toString())) {
            Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!userDataSource.checkUserPassword(mUsernameEt.getText().toString()).
                equals(mPasswordEt.getText().toString())) {

            Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show();
            return false;


        }


        return true;

    }
}




