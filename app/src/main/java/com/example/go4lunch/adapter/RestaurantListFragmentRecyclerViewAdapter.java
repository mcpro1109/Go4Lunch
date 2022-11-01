package com.example.go4lunch.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.R;
import com.example.go4lunch.RestaurantProfilActivity;
import com.example.go4lunch.api.responses.RestaurantResponse;
import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.firebase.database.collection.LLRBNode;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantListFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantListFragmentRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Restaurant> restaurantArrayList;
    private RestaurantAdapterListener listener;

    public RestaurantListFragmentRecyclerViewAdapter(ArrayList<Restaurant> restaurantArrayList, RestaurantAdapterListener recyclerViewClickListener) {
        this.restaurantArrayList = restaurantArrayList;
        this.listener = recyclerViewClickListener;
    }

    @NonNull
    @Override
    public RestaurantListFragmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListFragmentRecyclerViewAdapter.ViewHolder holder, int position) {
        Restaurant restaurant = restaurantArrayList.get(position);

        String nameRestaurant = restaurant.getName();
        String description = restaurant.getType() + " " + restaurant.getAddress();
        SpannableStringBuilder text = new SpannableStringBuilder(
                nameRestaurant +
                        "   " + restaurant.getDistance() +
                        "m \n" +
                        description +
                        " \n" +
                        restaurant.getHoursOpen()
                        );

        text.setSpan(new StyleSpan(Typeface.BOLD), 0, nameRestaurant.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String text2 = restaurant.getPeople() + " ";

        holder.textRestaurant.setText(text);
        holder.numberWorkmate.setText(text2);

        //rating
       // holder.numberStar.setText("(" + restaurant.getOpinion() + ")");
        holder.ratingBar.setRating((float) restaurant.getOpinion());

        //restaurant picture
        Glide
                .with(holder.imageRestaurant)
                .load(restaurant.getImageURL(400))
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.logo_food)
                .into(holder.imageRestaurant);

        //click on a restaurant to view profil
        holder.itemView.setOnClickListener(view -> listener.onClick(restaurant));
    }

    public void update(ArrayList<Restaurant> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    public interface RestaurantAdapterListener {
        void onClick(Restaurant restaurant);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageRestaurant;
        public TextView textRestaurant;
        public TextView numberWorkmate;
        public RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRestaurant = itemView.findViewById(R.id.imageRestaurant);
            textRestaurant = itemView.findViewById(R.id.textRestaurant);
            numberWorkmate = itemView.findViewById(R.id.numberWorkmate);
            ratingBar=itemView.findViewById(R.id.ratingBar);
        }
    }
}
