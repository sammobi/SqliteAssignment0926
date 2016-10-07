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

    private RecyclerView mRecyclerView;

    ArrayList<User> mContactArrayList;

    private RecyclerAdapter mRecyclerAdapter;
    AsyncTask<Void, Void, List<User>> mAsynctask;


    public AllUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContactArrayList = new ArrayList<User>();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recyclerview, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(layoutManager);


        mAsynctask = new AsyncTask<Void, Void, List<User>>() {


            @Override
            protected void onPostExecute(List<User> userList) {
                mRecyclerAdapter = new RecyclerAdapter(getActivity(), userList);
                mRecyclerView.setAdapter(mRecyclerAdapter);


            }

            @Override
            protected List<User> doInBackground(Void... params) {

                UserDataSource userDataSource = new UserDataSource(getActivity());
                userDataSource.open();
                List<User> users = userDataSource.getUsers();


                return users;
            }
        };

        mAsynctask.execute();

        return view;


    }

}
