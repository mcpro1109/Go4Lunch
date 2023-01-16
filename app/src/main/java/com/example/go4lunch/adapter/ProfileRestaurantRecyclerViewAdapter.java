package com.example.go4lunch.adapter;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.R;
import com.example.go4lunch.utils.ContactRestaurant;

import java.util.ArrayList;

public class ProfileRestaurantRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private boolean hasPhone = false;
    private boolean isLiked = false;
    private boolean hasWebsite = false;
    private boolean isLoading = true;
    private int likeColor = Color.RED;

    private ArrayList<Workmate> workmates;
    private ContactRestaurant contactRestaurant;


    public ProfileRestaurantRecyclerViewAdapter(ContactRestaurant contactRestaurant, ArrayList<Workmate> workmates) {
        this.workmates = workmates;
        this.contactRestaurant = contactRestaurant;
    }

    public void update(ArrayList<Workmate> workmatesList) {
        this.workmates = workmatesList;
        notifyDataSetChanged();
    }

    public void updateHeader(int likeColor){
        this.likeColor = likeColor;
        notifyItemChanged(0);
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
        //show header and items in the recyclerview
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bind();
        } else if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).bind(workmates.get(position - 1));
        }
    }


    @Override
    public int getItemCount() {
        return workmates.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }


    public void updateWithDetails(RestaurantDetails restaurantDetails) {
        hasPhone = restaurantDetails.getPhoneNumber() != null;
        hasWebsite = restaurantDetails.getWebsite() != null;
        isLoading = false;
        notifyItemChanged(0);
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar loader;
        public Button buttonPhone;
        public Button buttonLike;
        public Button buttonWeb;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            loader = itemView.findViewById(R.id.progressBar);
            buttonPhone = itemView.findViewById(R.id.phoneButton);
            buttonLike = itemView.findViewById(R.id.likeButton);
            buttonWeb = itemView.findViewById(R.id.webButton);
        }

        public void bind() {
            buttonLike.setBackgroundTintList(ColorStateList.valueOf(likeColor));
            buttonLike.setOnClickListener(view -> contactRestaurant.like());
            buttonPhone.setOnClickListener(view -> contactRestaurant.phoneCall());
            buttonWeb.setOnClickListener(view -> contactRestaurant.websiteOpen());

            loader.setVisibility(isLoading ? View.VISIBLE : View.GONE);

            buttonLike.setVisibility(!isLoading ? View.VISIBLE : View.GONE);
            buttonPhone.setVisibility(!isLoading && hasPhone ? View.VISIBLE : View.GONE);
            buttonWeb.setVisibility(!isLoading && hasWebsite ? View.VISIBLE : View.GONE);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView textWorkmate;
        private ImageView avatarWorkmate;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textWorkmate = itemView.findViewById(R.id.workmatesAddRestaurant);
            avatarWorkmate = itemView.findViewById(R.id.avatarWorkmateAddRestaurant);
        }

        public void bind(Workmate workmate) {
            String name = workmate.getFirstName();
            textWorkmate.setText(name);

            Glide.with(avatarWorkmate)
                    .load("https://ui-avatars.com/api/?name=" + workmate.getFirstName() + "&background=random")
                    .apply(RequestOptions.circleCropTransform())
                    .into(avatarWorkmate);
        }
    }
}
