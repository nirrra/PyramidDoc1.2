package com.example.wdy.pyramiddoc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User>{
    private int resourcedId;
    public UserAdapter(Context context, int textViewResourceId,List<User> objects){
        super(context,textViewResourceId,objects);
        resourcedId=textViewResourceId;
    }
    @Override
    public View getView(int position,View convertView,ViewGroup parent){
        User muser=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourcedId,parent,false);
        TextView userID=(TextView) view.findViewById(R.id.Bed_num);
        TextView userName=(TextView)view.findViewById(R.id.Name);
        TextView userSex=(TextView)view.findViewById(R.id.Sex);

        userID.setText(muser.getBed_num());
        userName.setText(muser.getName());
        userSex.setText(muser.getSex());

        return view;
    }

}
