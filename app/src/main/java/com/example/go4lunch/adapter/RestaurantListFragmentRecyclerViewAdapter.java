package com.example.go4lunch.adapter;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
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
import com.example.go4lunch.Model.RestaurantDetails;
import com.example.go4lunch.R;

import java.util.ArrayList;

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
                        " \n"
        );

        text.setSpan(new StyleSpan(Typeface.BOLD), 0, nameRestaurant.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String text2 = restaurant.getPeople() + " ";

        holder.textRestaurant.setText(text);
        holder.numberWorkmate.setText(text2);

        //rating
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
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
