package com.simpalm.sqliteassignment0926;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simpalm on 7/21/16.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ContactViewHolder> {

    // create fragment activity context
    private FragmentActivity mContext;
// create contact list of type user
    private List<User> mContactList;


    public RecyclerAdapter(FragmentActivity mContext, List<User> mContactList) {
        this.mContext = mContext;
        this.mContactList = mContactList;
    }

    @Override

    // overrite the recycler adapter methods
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_all_user, parent, false);

        // set the view in itemview and set it in the recyclerview

        return new ContactViewHolder(itemView);
    }

    @Override

    // pass the contactview holder and position of the recyclerview
    public void onBindViewHolder(ContactViewHolder holder, final int position) {


        // get the user contact list position
        final User contact = mContactList.get(position);

        // set all the values of the contact list object and get it from contact class
        holder.mNameTv.setText(contact.getName());
        holder.mDOBTv.setText(contact.getDob());
        holder.mNumberTv.setText(contact.getNumber());
        holder.mAddress.setText(contact.getAddress());
// delete a user on button click listener
        holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(mContext, "Your contact has been deleted", Toast.LENGTH_SHORT).show();

                // remove the selected contact by getting the position of the selected list item
                removeAt(position);


            }
        });

        holder.mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // take user to the add user fragment view
                AddUserFragment fragment = new AddUserFragment(); // replace your custom fragment class

                // get the value from the bundle and show it on the text widgets
                Bundle bundle = new Bundle();
                FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
                bundle.putInt("id", contact.getId());

                bundle.putString("name", contact.getName());
                bundle.putString("number", contact.getNumber());

                bundle.putString("dob", contact.getDob());

                bundle.putString("address", contact.getAddress());

                // use as per your need
                fragment.setArguments(bundle);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();


            }
        });


    }
// create method to remove the selected item
    private  void removeAt(int position) {

        // call the userdatasource class and pass context

        UserDataSource userDataSource = new UserDataSource(mContext);

// call the remove contact method in that class and get the position and the id for the specific item list .
        userDataSource.removeContact(mContactList.get(position).getId());


        // remove the contact list from that position
        mContactList.remove(position);
// notify item repoved and data set changed.
        notifyItemRemoved(position);
        notifyDataSetChanged();


    }

// get the item count if mcontact list is null
    @Override
    public int getItemCount() {
        if (mContactList == null)
            return 0;
        return mContactList.size();
    }

    // define all the textview widgets
    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTv, mDOBTv, mNumberTv, mAddress;
        private ImageView mDeleteBtn, mUpdateBtn;


        // create contactview holder class and find all the item view
        public ContactViewHolder(View itemView) {

            super(itemView);

            mNameTv = (TextView) itemView.findViewById(R.id.nametv);
            mDOBTv = (TextView) itemView.findViewById(R.id.dobtv);
            mNumberTv = (TextView) itemView.findViewById(R.id.numbertv);
            mAddress = (TextView) itemView.findViewById(R.id.addresstv);
            mDeleteBtn = (ImageView) itemView.findViewById(R.id.delete_user_btn);
            mUpdateBtn = (ImageView) itemView.findViewById(R.id.modify_user_btn);


        }

    }
}

