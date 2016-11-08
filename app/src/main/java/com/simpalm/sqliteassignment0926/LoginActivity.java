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
    private ProgressDialog mProgressdialog;
    public SharedPreferences mSharedPreferences;

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
        mSignUpBtn.setOnClickListener(new View.OnClickListener()

                                      {
                                          @Override
                                          public void onClick(View v) {
                                              Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                                              startActivity(intent);
                                          }
                                      }
        );
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if all validate fields are true before proceeding to the next task
                if (validLoginFields()) {
                    // if above fields are true then execute the async task
                    // execute the async task and get the username of the user
                    asyncTask.execute(mUsernameEt.getText().toString());
                }
            }
        });
    }

    private AsyncTask asyncTask = new AsyncTask<String, Void, String>() {

        @Override
        protected void onPreExecute() {
            mProgressdialog.setMessage("Loading please wait.....");
            mProgressdialog.show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String[] params) {
            // show the progress dialog for 2 seconds
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // open the userdataosource class
            userDataSource.open();
            // save the user password in the password variable by getting from userdatasrouce class
            // return the user password
            return userDataSource.checkUserPassword(params[0]);
        }

        @Override
        // get user password from above
        protected void onPostExecute(String password) {
            super.onPostExecute(password);
            mProgressdialog.dismiss();
            userDataSource.closeDatabase();
            String username = mUsernameEt.getText().toString();
            String pwd = mPasswordEt.getText().toString();
            // check if password is equal to the user entered password
            if (password.equals(pwd)) {
                // get the username of user and save it in the sharedpreference
                mSharedPreferences = getApplicationContext().getSharedPreferences("Logged User", MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(Util.KEY_USERNAME, username); // Storing string
                editor.commit(); // commit
                // take user to the to the main activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

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




