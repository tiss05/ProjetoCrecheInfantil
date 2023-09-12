package com.example.projcrech.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projcrech.Model.Occurrence;
import com.example.projcrech.R;

import java.util.ArrayList;

public class OccListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Occurrence> occList;

    public OccListAdapter(Context context, int layout, ArrayList<Occurrence> occList) {
        this.context = context;
        this.layout = layout;
        this.occList = occList;
    }

    @Override
    public int getCount() {
        return occList.size();
    }

    @Override
    public Object getItem(int position) {
        return occList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtName, txtContent, txtSchedule;
        ImageButton mModify, mDelete;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            //holder.txtName = (TextView) row.findViewById(R.id.item_name);
            holder.txtContent = (TextView) row.findViewById(R.id.txtContent1);
            holder.txtSchedule = (TextView) row.findViewById(R.id.txtSchedule1);
            holder.mModify = (ImageButton) row.findViewById(R.id.buttonModify);
            holder.mDelete = (ImageButton) row.findViewById(R.id.buttonDelete);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Occurrence occ = occList.get(position);

        holder.txtContent.setText(occ.getContentOcc());
        holder.txtSchedule.setText(occ.getDateOcc());

        return row;
    }


}