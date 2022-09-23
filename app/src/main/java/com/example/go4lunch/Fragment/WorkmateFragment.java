package com.example.go4lunch.Fragment;

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
import com.example.go4lunch.Viewmodel.WorkmateViewModel;

public class WorkmateFragment extends Fragment implements View.OnClickListener {

    private WorkmateViewModel workmateViewModel;
    TextView textView;
    Button buttonEssai;

    public static WorkmateFragment newInstance() {
        return new WorkmateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_workmate, container, false);

        this.textView = result.findViewById(R.id.textEssai);
        result.findViewById(R.id.buttonEssai).setOnClickListener(this);
        return result;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workmateViewModel = new ViewModelProvider(this).get(WorkmateViewModel.class);
        observeText();
    }

    private void observeText() {
        workmateViewModel.getTextLiveData().observe(getViewLifecycleOwner(), text -> {
            textView.setText(text);
        });
    }

    @Override
    public void onClick(View view) {
        workmateViewModel.updateText();


    }
}


