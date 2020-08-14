package com.example.syncvj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<DBcontrol> {

    ArrayList<DBcontrol> list;
    LayoutInflater vi;
    int Resource;


    public ListAdapter(Context context, int resource, ArrayList<DBcontrol> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        list = objects;

    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 500;
    }
    @SuppressLint({"WrongConstant", "ResourceType"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        final ViewHolder holder;

        if (v == null) {
            v = vi.inflate(R.layout.staff_view,parent,false);
            holder = new ViewHolder();
            holder.name = (TextView) v.findViewById(R.id.staffView1);

          //  holder.post = (TextView) v.findViewById(R.id.staffView2);
           // holder.number = (TextView) v.findViewById(R.id.staffView3);
         //   holder.email = (TextView) v.findViewById(R.id.staffView4);
          //  holder.department = (TextView) v.findViewById(R.id.staffView5);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.name.setText(list.get(position).getName());
        holder.name.setTextSize(20);
        holder.name.setTextColor(Color.BLACK);

    /*    holder.post.setText(list.get(position).getPost());
        holder.post.setTextSize(15);
        holder.post.setTextColor(Color.BLACK);
        holder.number.setText("Ph: "+list.get(position).getNumber());
        holder.number.setTextSize(15);
        holder.number.setTextColor(Color.BLACK);
        holder.email.setText("E: "+list.get(position).getEmail());
        holder.email.setTextSize(15);
        holder.email.setTextColor(Color.BLACK);
        holder.department.setText(list.get(position).getDepartment());
        holder.department.setTextSize(15);
        holder.department.setTextColor(Color.BLACK); */



        return v;
    }



    private static class ViewHolder  {

        public TextView name ;
        public TextView email;
        public TextView post;
        public TextView number;
        public TextView department;
        public ImageView arrow;
    }
}
