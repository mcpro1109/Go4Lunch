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
import com.example.go4lunch.Model.EatingWorkmate;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.R;

import java.util.ArrayList;

public class WorkmateFragmentRecyclerViewAdapter extends RecyclerView.Adapter<WorkmateFragmentRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Workmate> workmatesList;
    private ArrayList<EatingWorkmate> eatingWorkmateArrayList;
    private ArrayList<Restaurant> restaurants;

    public WorkmateFragmentRecyclerViewAdapter(ArrayList<Workmate> workmatesList, ArrayList<EatingWorkmate> eatingWorkmates) {
        this.workmatesList = workmatesList;
        this.eatingWorkmateArrayList = eatingWorkmates;
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


        String goToLunch = workmate.getFirstName();
       //todo

       /* for (EatingWorkmate eatingWorkmate : eatingWorkmateArrayList) {
            if (workmate.getId().equals(eatingWorkmate.getWorkmate_id())) {
                holder.textWorkmate.setText(goToLunch + " va manger au restaurant italien");
            } else {
                holder.textWorkmate.setText(goToLunch + " n'a pas encore choisi");
            }

        }*/
        String textEating = " n'a pas encore choisi ";

        String text = goToLunch + textEating;
        holder.textWorkmate.setText(text);

        String photo = "https://ui-avatars.com/api/?name=" + workmate.getFirstName() + workmate.getName();
        Glide.with(holder.avatarWorkmate.getContext())
                .load(photo + "&background=random")
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
