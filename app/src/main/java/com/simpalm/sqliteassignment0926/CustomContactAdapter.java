package com.simpalm.sqliteassignment0926;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simpalm on 10/6/16.
 */
// create a new adapter class to populate the contact list and attach to the adapter
public class CustomContactAdapter extends BaseAdapter {
    // create the context
    private Context mContext;

    // create a List of type user
   private  List<User> mUser;

    // create constructor
    public CustomContactAdapter(Context mContext, List<User> mUser) {
        this.mContext = mContext;
        this.mUser = mUser;
    }

    // return the muser size in get count
    @Override
    public int getCount() {
        return mUser.size();
    }

    // return the position of user
    @Override
    public Object getItem(int position) {
        return mUser.get(position);
    }

    // return the user item by using index of position
    @Override
    public long getItemId(int position) {
        return mUser.indexOf(getItem(position));
    }

    // return the view of the user using position, convertview and viewgroup
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// declare iewholder variable and assign null to it
        Viewholder viewholder; // = null
// create a layout inflator for recycler view
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
// if view is null then inflate the layout from the user from contact xml file
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_user_from_contact, null);

            // create viewholder object and assign to the view golder class
            viewholder = new Viewholder();
// set the values to the UI widgets in the layout
            viewholder.mNameTv = (TextView) convertView.findViewById(R.id.name_addusercontact_tv);
            viewholder.mNumberTv = (TextView) convertView.findViewById(R.id.number_addusercontact_tv);
            viewholder.mAddContactBtn = (ImageView) convertView.findViewById(R.id.add_user_contact_btn);


            // this is to set a tag to view so that we can get it anytime.

            convertView.setTag(viewholder);


        } else {

            viewholder = (Viewholder) convertView.getTag();


        }
       /* viewholder.mImageview.setImageResource(R.drawable.download);
        viewholder.mDescription.setText("This is my contact description");
        viewholder.mTitle.setText("My contact name");

        // get the position of the user using the user class and get position method
*/
        User user = mUser.get(position);

        // set the text of user in the name tv
        viewholder.mNameTv.setText(user.getName());

        // set the number of the user in the number tv
        viewholder.mNumberTv.setText(user.getNumber());
// return the view

        return convertView;

    }

    // create viewholder class to declare the variables for the widgets

    private class Viewholder {

        TextView mNameTv, mNumberTv;
        ImageView mAddContactBtn;


    }
}
