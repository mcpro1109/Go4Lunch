package com.example.go4lunch.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.go4lunch.Fragment.RestaurantMapFragment;
import com.example.go4lunch.Model.Restaurant;
import com.example.go4lunch.R;
import com.example.go4lunch.RestaurantProfilActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomWindowInfoAdapter implements GoogleMap.InfoWindowAdapter {


    private Context context;


    public CustomWindowInfoAdapter(Context context) {
        this.context = context;

    }


    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_info_contents, null);
        TextView textViewTitle = (TextView) view.findViewById(R.id.titleInfo);
        TextView textViewSnippet = (TextView) view.findViewById(R.id.snippet);

        textViewTitle.setText(marker.getTitle());

        String infos = "infos:" + marker.getSnippet();
        textViewSnippet.setText(infos);

        return view;
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {

        return null;
    }

}
