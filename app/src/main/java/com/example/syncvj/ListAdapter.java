package com.example.syncvj;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<DBcontrol> implements Filterable {

    ArrayList<DBcontrol> list;
    LayoutInflater vi;
    int Resource;
    public ArrayList<DBcontrol> orig;

    public ListAdapter(Context context, int resource, ArrayList<DBcontrol> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        list = objects;
    }

    @NonNull
    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<DBcontrol> results = new ArrayList<DBcontrol>();
                if(orig == null)
                    orig = list;
                if(charSequence != null){
                    if(orig != null && orig.size() > 0){
                        for(final DBcontrol g : orig){
                            if(g.getName().toLowerCase().contains(charSequence.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<DBcontrol>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
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
    public int getCount() {
        return list.size();
    }

    @Override
    public DBcontrol getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
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
            holder.designation = (TextView) v.findViewById(R.id.staffView0);

            holder.post = (TextView) v.findViewById(R.id.staffView2);
            // holder.number = (TextView) v.findViewById(R.id.staffView3);
            // holder.email = (TextView) v.findViewById(R.id.staffView4);
            //  holder.department = (TextView) v.findViewById(R.id.staffView5);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.name.setText(list.get(position).getName());
        holder.name.setTextSize(20);
        holder.name.setTextColor(Color.BLACK);
        holder.designation.setText(list.get(position).getDesignation());
        holder.designation.setTextSize(20);
        holder.designation.setTextColor(Color.BLACK);

        if((list.get(position).getDepartment()).equals("Accounts") || (list.get(position).getDepartment()).equals("Office") ||(list.get(position).getDepartment()).equals("Library") || (list.get(position).getDepartment()).equals("Maintenance") || (list.get(position).getDepartment()).equals("Placement") ){
            holder.post.setText(list.get(position).getPost());
            holder.post.setTextSize(15);
            holder.post.setTextColor(Color.BLACK);
        }
        else{
            holder.post.setVisibility(View.GONE);
        }

    /*    holder.number.setText("Ph: "+list.get(position).getNumber());
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
        public TextView designation;
        public ImageView arrow;
    }
}
