package com.example.go4lunch.adapter;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.R;

import java.util.ArrayList;

public class RestaurantListFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantListFragmentRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Restaurant> restaurantArrayList;

    public RestaurantListFragmentRecyclerViewAdapter(ArrayList<Restaurant> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
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

        //configuration of the restaurant description
        String nameRestaurant = restaurant.name;
        String description = restaurant.type + " " + restaurant.adresse + " " + restaurant.people;
        SpannableStringBuilder text = new SpannableStringBuilder(nameRestaurant + " \n" + description);

        text.setSpan(new StyleSpan(Typeface.BOLD), 0, nameRestaurant.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.textRestaurant.setText(text);

        Glide.with(holder.imageRestaurant.getContext())
                .load("https://ui-avatars.com/api/?name=" + restaurant.getName() + "&background=random")
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageRestaurant);
    }

    public void update(ArrayList<Restaurant> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageRestaurant;
        public TextView textRestaurant;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRestaurant = itemView.findViewById(R.id.imageRestaurant);
            textRestaurant = itemView.findViewById(R.id.textRestaurant);
        }
    }
}
