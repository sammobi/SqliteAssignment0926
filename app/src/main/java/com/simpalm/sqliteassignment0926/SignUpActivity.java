package com.simpalm.sqliteassignment0926;

import android.app.ActionBar;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class SignUpActivity extends Activity {

    private EditText mUsernameEt, mPasswordEt, mConfirmPassEt;
    private Button mSignUpBtn;
    private Toolbar mToolbar;
    UserDataSource userDataSource;
    ProgressDialog mProgressDialog;
    private SharedPreferences mSharedPreferences;
    private AsyncTask<String, Void, String> asyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // get sharedpreference

        mUsernameEt = (EditText) findViewById(R.id.username_signup_et);
        mPasswordEt = (EditText) findViewById(R.id.password_signup_et);
        mConfirmPassEt = (EditText) findViewById(R.id.confirmpass_signup_et);
        mSignUpBtn = (Button) findViewById(R.id.signupuser_btn);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Signup");
        mToolbar.setNavigationIcon(R.drawable.back);
        mProgressDialog = new ProgressDialog(SignUpActivity.this);
        asyncTask = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {
                mProgressDialog.setMessage("Loading please wait.....");
                mProgressDialog.show();

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


                return userDataSource.checkUserPassword(params[0]);
            }

            @Override
            protected void onPostExecute(String password) {
                super.onPostExecute(password);
                mProgressDialog.dismiss();
                userDataSource.closeDatabase();
                String username = mUsernameEt.getText().toString();

                if (password.equals(username)) {

                    mSharedPreferences = getApplicationContext().getSharedPreferences("Logged User", MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putString("user_name", username); // Storing string

                    editor.commit();


                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        };


        userDataSource = new UserDataSource(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = mUsernameEt.getText().toString();
                String password = mPasswordEt.getText().toString();
                if (validateFields()) {
                    userDataSource.open();


                    userDataSource.insertNewUser(username, password);

                    userDataSource.closeDatabase();

                    SharedPreferences mSharedPreferences = PreferenceManager
                            .getDefaultSharedPreferences(SignUpActivity.this);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();

                    editor.putString("user_name", username);

                    editor.commit();

                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }


    public boolean validateFields() {
        userDataSource.open();
        if (mUsernameEt.getText().toString().length() == 0) {
            Toast.makeText(this, "Username cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPasswordEt.getText().toString().length() == 0) {
            Toast.makeText(this, " Confirm Password cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mConfirmPassEt.getText().toString().length() == 0) {
            Toast.makeText(this, " Confirm Password cannot be blank", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mPasswordEt.getText().toString().equals(mConfirmPassEt.getText().toString())) {
            Toast.makeText(this, "Password and confirm password does not match", Toast.LENGTH_SHORT).show();
            return false;

        } else if (userDataSource.checkUserExist(mUsernameEt.getText().toString())) {
            Toast.makeText(this, "Email address already exist", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }
}


