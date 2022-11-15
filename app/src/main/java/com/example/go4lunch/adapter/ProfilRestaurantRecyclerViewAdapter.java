package com.example.go4lunch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.R;


import java.util.ArrayList;
import java.util.List;

public class ProfilRestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private ArrayList<Workmate> workmates;
    private Context context;

    public ProfilRestaurantRecyclerViewAdapter(Context context, ArrayList<Workmate> workmates) {
        this.context = context;
        this.workmates = workmates;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_listview_item, parent, false);
            return new ItemViewHolder(itemView);
        } else if (viewType == TYPE_HEADER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_header_profil, parent, false);
            return new HeaderViewHolder(itemView);
        } else
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            // setheadersdata_flag = true;
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            headerViewHolder.buttonLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        } else if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
           // final LatestTabModel.ViewItemsModel data = latestlists.get(position-1);
           itemViewHolder.textWorkmate.setText("new lunch");
        }
    }

    @Override
    public int getItemCount() {
        return workmates.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;

    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {
        public Button buttonPhone;
        public Button buttonLike;
        public Button buttonWeb;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            buttonPhone = itemView.findViewById(R.id.phoneButton);
            buttonLike = itemView.findViewById(R.id.likeButton);
            buttonWeb = itemView.findViewById(R.id.webButton);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textWorkmate;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textWorkmate = itemView.findViewById(R.id.workmatesAddRestaurant);
        }
    }
}
