package com.example.go4lunch.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.R;

import java.util.ArrayList;

public class WorkmateFragmentRecyclerViewAdapter extends RecyclerView.Adapter<WorkmateFragmentRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Workmate> workmatesList;

    public WorkmateFragmentRecyclerViewAdapter(ArrayList<Workmate> workmatesList) {
        this.workmatesList = workmatesList;
    }

    public void update(ArrayList<Workmate> workmatesList) {
        this.workmatesList = workmatesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WorkmateFragmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmate_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmateFragmentRecyclerViewAdapter.ViewHolder holder, int position) {
        Workmate workmate = workmatesList.get(position);

        holder.textWorkmate.setText(workmate.getFirstName() + " va manger au restaurant italien");

        Glide.with(holder.avatarWorkmate.getContext())
                .load("https://ui-avatars.com/api/?name=" + workmate.getFirstName() + "&background=random")
                .apply(RequestOptions.circleCropTransform())
                .into(holder.avatarWorkmate);
    }

    @Override
    public int getItemCount() {
        return workmatesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatarWorkmate;
        public TextView textWorkmate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarWorkmate = itemView.findViewById(R.id.avatarWorkmate);
            textWorkmate = itemView.findViewById(R.id.textSituationWorkmate);
        }
    }
}
