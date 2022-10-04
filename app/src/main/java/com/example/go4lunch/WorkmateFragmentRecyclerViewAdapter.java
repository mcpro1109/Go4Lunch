package com.example.go4lunch;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Model.Workmate;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class WorkmateFragmentRecyclerViewAdapter extends RecyclerView.Adapter<WorkmateFragmentRecyclerViewAdapter.ViewHolder> {

    private List<Workmate> workmates;
    private FirebaseFirestore firebaseFirestore;

    public WorkmateFragmentRecyclerViewAdapter(List<Workmate> workmates) {
        this.workmates = workmates;
    }


    @NonNull
    @Override
    public WorkmateFragmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workmate_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkmateFragmentRecyclerViewAdapter.ViewHolder holder, int position) {
        Workmate workmate= workmates.get(position);
        holder.textWorkmate.setText(workmate.getName());
        Log.d("nom", workmate.getName() );
    }

    @Override
    public int getItemCount() {
        return workmates.size();
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
