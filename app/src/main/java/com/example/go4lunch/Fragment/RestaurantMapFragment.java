package com.example.go4lunch.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.go4lunch.R;
import com.example.go4lunch.Viewmodel.RestaurantMapViewModel;
import com.example.go4lunch.Viewmodel.RestaurantViewModel;

public class RestaurantMapFragment extends Fragment implements View.OnClickListener {

    private RestaurantMapViewModel restaurantMapViewModel;
    RestaurantViewModel restaurantViewModel;
    Button button;
    TextView textView;

    public static RestaurantMapFragment newInstance() {
        return new RestaurantMapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_restaurant_map, container, false);
        this.textView = result.findViewById(R.id.textMap);
        result.findViewById(R.id.buttonEssaiRestoMap).setOnClickListener(this);
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //restaurantMapViewModel = new ViewModelProvider(this).get(RestaurantMapViewModel.class);
        restaurantViewModel = new ViewModelProvider(getActivity()).get(RestaurantViewModel.class);
        observeView();
    }

    private void observeView() {
      /*  restaurantViewModel.getTextLiveData().observe(getViewLifecycleOwner(), text -> {
            textView.setText(text);
        });*/

        restaurantViewModel.getTextLiveData().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });
    }

    @Override
    public void onClick(View view) {
        restaurantViewModel.updateTextRestaurant();
    }
}