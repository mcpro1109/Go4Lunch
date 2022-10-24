package com.example.go4lunch.adapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
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
import com.example.go4lunch.RestaurantProfilActivity;
import com.example.go4lunch.api.responses.RestaurantResponse;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantListFragmentRecyclerViewAdapter extends RecyclerView.Adapter<RestaurantListFragmentRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Restaurant> restaurantArrayList;
    private RecyclerViewClickListener listener;
    private RestaurantResponse restaurantResponse;

    public RestaurantListFragmentRecyclerViewAdapter(ArrayList<Restaurant> restaurantArrayList, RecyclerViewClickListener recyclerViewClickListener) {
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

        //configuration of the restaurant description

        // Drawable drawable=  AppCompatResources.getDrawable(MyApp.app,R.drawable.ic_baseline_people_24 );


        String nameRestaurant = restaurant.getName();
        String description = restaurant.getType() + " " + restaurant.getAddress();
        SpannableStringBuilder text = new SpannableStringBuilder(
                nameRestaurant +
                        "   " + restaurant.getDistance() +
                        "m \n" +
                        description +
                        " \n" +
                        restaurant.getHoursOpen() +
                        "   rate " +
                        restaurant.getOpinion());

        text.setSpan(new StyleSpan(Typeface.BOLD), 0, nameRestaurant.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        String text2 = restaurant.getPeople() + " ";

        holder.textRestaurant.setText(text);
        holder.numberWorkmate.setText(text2);


        //photo restaurant
        if (restaurant.getImageURL() != null && !restaurant.getImageURL().isEmpty()) {
            Glide.with(holder.imageRestaurant.getContext())
                    .load(restaurant.getImageURL())
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.imageRestaurant);

      /* Glide.with(holder.imageRestaurant.getContext())
               .load(restaurant.getImageURL())
               .into(holder.imageRestaurant);*/
        } else {
            Glide.with(holder.imageRestaurant.getContext())
                    .load("https://ui-avatars.com/api/?name=" + restaurant.getName() + "&background=random")
                    .into(holder.imageRestaurant);

        }

        /*Glide.with(holder.imageRestaurant.getContext())
                .load("https://ui-avatars.com/api/?name=" + restaurant.getName() + "&background=random")
                .apply(RequestOptions.circleCropTransform())
                .into(holder.imageRestaurant);*/

        //click on a restaurant to view profil
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RestaurantProfilActivity.class);
                intent.putExtra("restaurant", (Serializable) restaurant);
                view.getContext().startActivity(intent);
            }
        });
    }


    public void update(ArrayList<Restaurant> restaurantArrayList) {
        this.restaurantArrayList = restaurantArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageRestaurant;
        public TextView textRestaurant;
        public TextView numberWorkmate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRestaurant = itemView.findViewById(R.id.imageRestaurant);
            textRestaurant = itemView.findViewById(R.id.textRestaurant);
            numberWorkmate = itemView.findViewById(R.id.numberWorkmate);
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
