package com.example.syncvj;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter_intercomm extends ArrayAdapter {
    ArrayList<DBcontrol_intercom> list;
    LayoutInflater vi;
    int Resource;


    public ListAdapter_intercomm(Context context, int resource, ArrayList<DBcontrol_intercom> objects) {
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
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        final ListAdapter_intercomm.ViewHolder holder;

        if (v == null) {
            v = vi.inflate(R.layout.staff_view_intercomm,parent,false);
            holder = new ListAdapter_intercomm.ViewHolder();
            holder.name = (TextView) v.findViewById(R.id.staffView1_intercomm);
            holder.post = (TextView) v.findViewById(R.id.staffView2_intercomm);
            holder.int_comm = (TextView) v.findViewById(R.id.staffView3_intercomm);
            holder.department = (TextView) v.findViewById(R.id.staffView4_intercomm);
            v.setTag(holder);
        } else {
            holder = (ListAdapter_intercomm.ViewHolder) v.getTag();
        }

        holder.name.setText(list.get(position).getName());
        holder.name.setTextSize(20);
        holder.name.setTextColor(Color.BLACK);
        holder.post.setText(list.get(position).getPost());
        holder.post.setTextSize(15);
        holder.post.setTextColor(Color.BLACK);
        holder.int_comm.setText("Intercom: "+list.get(position).getInt_comm());
        holder.int_comm.setTextSize(15);
        holder.int_comm.setTextColor(Color.BLACK);
        holder.department.setText(list.get(position).getDepartment());
        holder.department.setTextSize(15);
        holder.department.setTextColor(Color.BLACK);


        return v;
    }



    private static class ViewHolder  {

        public TextView name ;
        public TextView post;
        public TextView int_comm;
        public TextView department;
    }
}
