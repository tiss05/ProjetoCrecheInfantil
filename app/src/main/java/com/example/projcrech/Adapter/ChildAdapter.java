package com.example.projcrech.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projcrech.Model.Child;
import com.example.projcrech.R;

import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildViewHolder> {

    private List<Child> childList;

    public ChildAdapter(List<Child> ChildList) {
        this.childList = ChildList;
    }

    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ChildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildViewHolder holder, int position) {
        holder.mName.setText(childList.get(position).getNameChild());
        /*holder.mContent.setText(childList.get(position).getDateOcc());
        holder.mEdit.setVisibility(View.INVISIBLE);
        holder.mDelete.setVisibility(View.INVISIBLE);*/
    }

    @Override
    public int getItemCount() {
        return childList.size();
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder {

        private TextView mName, mContent;
        private ImageButton mEdit,mDelete;

        public ChildViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.item_name);
            /*mContent = itemView.findViewById(R.id.txtSchedule1);
            mEdit = itemView.findViewById(R.id.buttonModify);
            mDelete = itemView.findViewById(R.id.buttonDelete);*/
        }
    }
}
