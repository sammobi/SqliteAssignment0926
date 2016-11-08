package com.simpalm.sqliteassignment0926;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllUserFragment extends Fragment {
    // create the recyclerview object
    private RecyclerView mRecyclerView;
    // create arraylist of type User
    ArrayList<User> mContactArrayList;
    // create the recycler adapter object
    private RecyclerAdapter mRecyclerAdapter;
    // create the object of ayns tasj of void, void and List<user>

    public AllUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContactArrayList = new ArrayList<>(); // <User>
        View view = inflater.inflate(R.layout.recyclerview, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        // assign the recyclerview in ths current fragment
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // set the recyclerview object
        mRecyclerView.setLayoutManager(layoutManager);
        // assign asyntask object with following parameters
        // execute the asyntask after running all the previous methods.
        mAsynctask.execute();
        // return the fragment view
        return view;
    }

    private AsyncTask mAsynctask = new AsyncTask<Void, Void, List<User>>() {
        @Override
        // pass the list>user> userlist as a parameter from the doin background method
        protected void onPostExecute(List<User> userList) {
            // set the recycler adapter userlist after calling recycler adapter
            mRecyclerAdapter = new RecyclerAdapter(getActivity(), userList);
            mRecyclerView.setAdapter(mRecyclerAdapter);
        }

        @Override
        // create do in background metod of type User and parameter as void .. params
        protected List<User> doInBackground(Void... params) {
            // call userdatasource and open the database
            UserDataSource userDataSource = new UserDataSource(getActivity());
            userDataSource.open();
            // assign the value of all the users list in users and call the getusers method on userdatasource class.
            List<User> users = userDataSource.getUsers();
            // return all the users
            return users;
        }
    };
}
