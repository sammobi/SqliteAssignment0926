package com.simpalm.sqliteassignment0926;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

// create fragment user from contact class and extend it to fragment class
public class FragmentUserFromContact extends Fragment {
    // declare list view
    private ListView mListView;
    // declare async task of type Uri, Void and List<user>
    private AsyncTask<Uri, Void, List<User>> asyncTask;
    // create sttic final variable and asign static value to read the contact
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private ProgressDialog mProgressDialog;
    // create array list of type user
    private List<User> userList = new ArrayList<>();
    // create empty constructor

    public FragmentUserFromContact() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.contact_listview, container, false);
        // create object of listivre and find the view for the list view
        mListView = (ListView) view.findViewById(R.id.contact_listview);
        mProgressDialog = new ProgressDialog(getActivity());
        // run the async
        // check to allow contact access based on build version and request for permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // execute the asyn task
            mAsyncTask.execute(ContactsContract.Contacts.CONTENT_URI);
        }
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // create the user oboject and get the position of the user
                User user = userList.get(position);
                // get the name of the user
                String name = user.getName();
                // get the number of the user
                String number = user.getNumber();
                // take user to the adduserfragment screen after tap on hte item litview
                AddUserFragment ldf = new AddUserFragment();
                // save the value in the bundle name and number
                Bundle args = new Bundle();
                args.putString(Util.KEY_NAME, name);
                args.putString(Util.KEY_NUMBER, number);
                ldf.setArguments(args);
                //Inflate the fragment
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, ldf).commit();
            }
        });
        // return the fragment view
        return view;
    }

    private AsyncTask mAsyncTask = new AsyncTask<Uri, Void, List<User>>() {

        @Override
        protected void onPreExecute() {
            mProgressDialog.setMessage("Loading please wait.....");
            mProgressDialog.show();
            super.onPreExecute();
        }

        // do in background method of type user and pass uri as parameter
        @Override
        protected List<User> doInBackground(Uri... params) {
            // get the user contacts from the phone create the content resolver object and get context of activity
            ContentResolver cr = getActivity().getContentResolver();
            // create cursor object and query from the beginning
            Cursor cur = cr.query(params[0],
                    null, null, null, null);
            // create condition if count of cursor is greater then 0
            if (cur.getCount() > 0) {
                // move to next cursor position
                while (cur.moveToNext()) {
                    // get the id and store it in the string of the contacts
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    // ger the name of the contact person using the cursor
                    String name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    // if contact number has a phone number then run this query
                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            String phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                            // create the user object and store the name and phone number retreived from above and set other fields to null
                            User user = new User(null, null, name, phoneNo, null, null);
                            // add user to the list for each phone number
                            userList.add(user);
                        }
                        // close the cursor
                        pCur.close();
                    }
                }
            }

            // return the userlist
            return userList;
        }

        // pass the userlist from the previous method
        @Override
        protected void onPostExecute(final List<User> userList) {

            super.onPostExecute(userList);
            mProgressDialog.dismiss();
            // set on item click listener
            // set the adapter to the userlist
            mListView.setAdapter(new CustomContactAdapter(getActivity(), userList));
        }
    };

    @Override
    // permission request result
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted then run the async task
                asyncTask.execute(ContactsContract.Contacts.CONTENT_URI);
            } else {

                // show the toast
                Toast.makeText(getActivity(), "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


