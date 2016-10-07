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

    FragmentActivity mContext;

    private List<User> mContactList;


    public RecyclerAdapter(FragmentActivity mContext, List<User> mContactList) {
        this.mContext = mContext;
        this.mContactList = mContactList;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_all_user, parent, false);

        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, final int position) {

        final User contact = mContactList.get(position);
        holder.mNameTv.setText(contact.getName());
        holder.mDOBTv.setText(contact.getDob());
        holder.mNumberTv.setText(contact.getNumber());
        holder.mAddress.setText(contact.getAddress());

        holder.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(mContext, "Your contact has been deleted", Toast.LENGTH_SHORT).show();
                removeAt(position);


            }
        });

        holder.mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserFragment fragment = new AddUserFragment(); // replace your custom fragment class
                Bundle bundle = new Bundle();
                FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
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

    public void removeAt(int position) {

        UserDataSource userDataSource = new UserDataSource(mContext);

        mContactList.remove(position);

        userDataSource.removeContact(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();


    }


    @Override
    public int getItemCount() {
        if (mContactList == null)
            return 0;
        return mContactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {

        private TextView mNameTv, mDOBTv, mNumberTv, mAddress;
        private ImageView mDeleteBtn, mUpdateBtn;

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

