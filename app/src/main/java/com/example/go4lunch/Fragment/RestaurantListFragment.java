package com.example.go4lunch.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.go4lunch.R;
import com.example.go4lunch.Viewmodel.RestaurantListViewModel;
import com.example.go4lunch.Viewmodel.RestaurantViewModel;

public class RestaurantListFragment extends Fragment implements View.OnClickListener {

    private RestaurantListViewModel restaurantListViewModel;
    private RecyclerView recyclerView;
    RestaurantViewModel restaurantViewModel;
    TextView textView;
    Button button;
    RestaurantMapFragment restaurantMapFragment;

    public static RestaurantListFragment newInstance() {
        return new RestaurantListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        this.textView = result.findViewById(R.id.textList);
        result.findViewById(R.id.buttonEssaiRestoList).setOnClickListener(this);
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ViewModel to send and receive data
        //getActivity to use the same data with RestaurantMapFragment
        restaurantViewModel = new ViewModelProvider(getActivity()).get(RestaurantViewModel.class);
        observeText();
    }

    private void observeText() {
        //receive and send the data restaurant between the deux fragments restaurants

        //demander la différence entre les deux

      /*  restaurantViewModel.getTextLiveData().observe(getViewLifecycleOwner(), text -> {

            textView.setText(text);
            //restaurantMapFragment.textView.setText(text);
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