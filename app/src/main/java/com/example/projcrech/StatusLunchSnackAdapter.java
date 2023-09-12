package com.example.projcrech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


public class StatusLunchSnackAdapter extends BaseAdapter {
    private Context context;
    private List<StatusLunchSnack> statusList;

    public StatusLunchSnackAdapter(Context context, List<StatusLunchSnack> statusList) {
        this.context = context;
        this.statusList = statusList;
    }

    @Override
    public int getCount() {
        return statusList != null ? statusList.size() : 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView = LayoutInflater.from(context)
                .inflate(R.layout.list_item, viewGroup, false);

        TextView txtName = rootView.findViewById(R.id.name);
        ImageView image = rootView.findViewById(R.id.image);

        txtName.setText(statusList.get(i).getName());
        image.setImageResource(statusList.get(i).getImage());

        return rootView;
    }
}