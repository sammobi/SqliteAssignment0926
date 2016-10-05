package com.simpalm.sqliteassignment0926;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserFragment extends Fragment implements View.OnClickListener {

    private EditText mNameEt, mPhoneEt, mAddressEt;
    private TextView mDobTv;
    private Button mAddContactBtn;
    private UserDataSource userDataSource;
    private AsyncTask<String, Void, String> asyncTask;
    private ProgressDialog mProgressdialog;
    public SharedPreferences mSharedPreferences;
    public static final String PREFS_NAME = "AOP_PREFS";
    public static final String PREFS_KEY = "AOP_PREFS_String";


    public AddUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        mNameEt = (EditText) view.findViewById(R.id.name_adduser_et);
        mPhoneEt = (EditText) view.findViewById(R.id.number_adduser_et);
        mAddressEt = (EditText) view.findViewById(R.id.address_adduser_et);
        mDobTv = (TextView) view.findViewById(R.id.dob_adduser_tv);
        mAddContactBtn = (Button) view.findViewById(R.id.addser_btn);
        mDobTv.setOnClickListener(this);
        mAddContactBtn.setOnClickListener(this);
        mProgressdialog = new ProgressDialog(getActivity());
        asyncTask = new AsyncTask<String, Void, String>() {

            @Override
            protected void onPreExecute() {
                mProgressdialog.setMessage("Adding contact please wait....");
                mProgressdialog.show();


                super.onPreExecute();
            }


            @Override
            protected String doInBackground(String[] params) {

                if (validateFields() == true) {


                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    String username = settings.getString("username", null);

                    return username;
                }


            }


            @Override
            protected void onPostExecute(String username) {
                super.onPostExecute(username);

                String name = mNameEt.getText().toString();
                String phone = mPhoneEt.getText().toString();
                String address = mAddressEt.getText().toString();
                String dob = mDobTv.getText().toString();

                userDataSource.open();
                userDataSource.addContact(username, name, phone, dob, address);
                userDataSource.closeDatabase();

                Toast.makeText(getActivity(), "Contact added to database", Toast.LENGTH_SHORT).show();


                mProgressdialog.dismiss();
                userDataSource.closeDatabase();


            }
        }

        ;
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId() /*to get clicked view id**/) {
            case R.id.dob_adduser_tv:
                DatePickerDialog.OnDateSetListener dpd = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        int s = monthOfYear + 1;
                        String a = s + "/" + dayOfMonth + "/" + year;
                        mDobTv.setText("" + a);
                    }
                };

                Time date = new Time();
                DatePickerDialog d = new DatePickerDialog(getActivity(), dpd, date.year, date.month, date.monthDay);
                d.show();


                break;

            case R.id.addser_btn:


                break;
        }

    }

    public boolean validateFields() {
        if (mNameEt.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please enter the name of the contact", Toast.LENGTH_SHORT).show();
            return false;

        } else if (mPhoneEt.getText().toString().length() < 10) {
            Toast.makeText(getActivity(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return false;


        } else if (mPhoneEt.getText().toString().length() > 10) {
            Toast.makeText(getActivity(), "Please enter valid phone number", Toast.LENGTH_SHORT).show();
            return false;

        } else if (mDobTv.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please select date of birth", Toast.LENGTH_SHORT).show();
            return false;

        } else if (mAddressEt.getText().toString().length() == 0) {
            Toast.makeText(getActivity(), "Please enter the address", Toast.LENGTH_SHORT).show();
            return false;


        }
        return true;
    }
}

