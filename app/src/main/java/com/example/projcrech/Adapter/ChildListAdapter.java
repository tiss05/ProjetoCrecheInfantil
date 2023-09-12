package com.example.projcrech.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projcrech.Model.Child;
import com.example.projcrech.R;

import java.util.ArrayList;

public class ChildListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private ArrayList<Child> childsList;

    public ChildListAdapter(Context context, int layout, ArrayList<Child> childsList) {
        this.context = context;
        this.layout = layout;
        this.childsList = childsList;
    }

    @Override
    public int getCount() {
        return childsList.size();
    }

    @Override
    public Object getItem(int position) {
        return childsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView) row.findViewById(R.id.item_name);
            holder.imageView = (ImageView) row.findViewById(R.id.grid_image);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder) row.getTag();
        }

        Child child = childsList.get(position);

        holder.txtName.setText(child.getNameChild());

        byte[] childImage = child.getImageChild();
        Bitmap bitmap = BitmapFactory.decodeByteArray(childImage, 0, childImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }
}