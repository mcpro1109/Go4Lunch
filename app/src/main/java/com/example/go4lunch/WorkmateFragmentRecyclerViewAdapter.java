package com.example.go4lunch;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WorkmateFragmentRecyclerViewAdapter extends RecyclerView.Adapter<WorkmateFragmentRecyclerViewAdapter.ViewHolder> {



    @NonNull
    @Override
    public WorkmateFragmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.workmate_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmateFragmentRecyclerViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatarWorkmate;
        public TextView textWorkmate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarWorkmate=itemView.findViewById(R.id.avatarWorkmate);
            textWorkmate=itemView.findViewById(R.id.textSituationWorkmate);
        }
    }
}
