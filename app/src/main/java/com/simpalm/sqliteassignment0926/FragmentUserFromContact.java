package com.simpalm.sqliteassignment0926;


import android.Manifest;
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
public class FragmentUserFromContact extends Fragment {

    private ListView mListView;

    private AsyncTask<Uri, Void, List<User>> asyncTask;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    private List<User> userList = new ArrayList<>();


    public FragmentUserFromContact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.contact_listview, container, false);


        asyncTask = new AsyncTask<Uri, Void, List<User>>() {

            @Override
            protected void onPreExecute() {


                super.onPreExecute();
            }


            @Override
            protected List<User> doInBackground(Uri... params) {


                ContentResolver cr = getActivity().getContentResolver();
                Cursor cur = cr.query(params[0],
                        null, null, null, null);

                if (cur.getCount() > 0) {
                    while (cur.moveToNext()) {
                        String id = cur.getString(
                                cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME));

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

                                User user = new User(null, null, name, phoneNo, null, null);

                                userList.add(user);


                            }
                            pCur.close();
                        }
                    }
                }

                return userList;

            }


            @Override
            protected void onPostExecute(final List<User> userList) {


                super.onPostExecute(userList);

                mListView = (ListView) view.findViewById(R.id.contact_listview);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        User user = userList.get(position);
                        String name = user.getName();
                        String number = user.getNumber();

                        AddUserFragment ldf = new AddUserFragment();
                        Bundle args = new Bundle();
                        args.putString("name", name);
                        args.putString("number", number);
                        ldf.setArguments(args);

//Inflate the fragment
                        getFragmentManager().beginTransaction().add(R.id.fragment_container, ldf).commit();


                    }
                });
                mListView.setAdapter(new CustomContactAdapter(getActivity(), userList));


            }


        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            asyncTask.execute(ContactsContract.Contacts.CONTENT_URI);
        }

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                asyncTask.execute(ContactsContract.Contacts.CONTENT_URI);
            } else {
                Toast.makeText(getActivity(), "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }


    // The contacts from the contacts content provider is stored in this cursor


}


