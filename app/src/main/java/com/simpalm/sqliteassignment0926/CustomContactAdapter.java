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

public class CustomContactAdapter extends BaseAdapter {

    Context mContext;
    List<User> mUser;

    public CustomContactAdapter(Context mContext, List<User> mUser) {
        this.mContext = mContext;
        this.mUser = mUser;
    }

    @Override
    public int getCount() {
        return mUser.size();
    }

    @Override
    public Object getItem(int position) {
        return mUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mUser.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Viewholder viewholder = null;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.fragment_fragment_user_from_contact, null);
            viewholder = new Viewholder();

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
*/
        User user = mUser.get(position);
        viewholder.mNameTv.setText(user.getName());
        viewholder.mNumberTv.setText(user.getNumber());


        return convertView;

    }

    private class Viewholder {

        TextView mNameTv, mNumberTv;
        ImageView mAddContactBtn;


    }
}
