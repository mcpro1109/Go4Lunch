package com.example.go4lunch.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.go4lunch.Model.Workmate;
import com.example.go4lunch.R;
import com.example.go4lunch.Viewmodel.WorkmateViewModel;
import com.example.go4lunch.WorkmateFragmentRecyclerViewAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class WorkmateFragment extends Fragment implements View.OnClickListener {

    private WorkmateViewModel workmateViewModel;
    TextView textView;
    Button buttonEssai;
    RecyclerView recyclerView;
    List<Workmate> workmates = new ArrayList<>();
    WorkmateFragmentRecyclerViewAdapter workmateFragmentRecyclerViewAdapter = new WorkmateFragmentRecyclerViewAdapter(workmates);
    FirebaseFirestore firebaseFirestore;


    public static WorkmateFragment newInstance() {
        return new WorkmateFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_workmate, container, false);

        this.textView = result.findViewById(R.id.textEssai);
        recyclerView = result.findViewById(R.id.recyclerViewWorkmate);
        result.findViewById(R.id.buttonEssai).setOnClickListener(this);

        Context context = result.getContext();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(workmateFragmentRecyclerViewAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();

        return result;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        workmateViewModel = new ViewModelProvider(this).get(WorkmateViewModel.class);
        observeText();
        // firebaseFirestore = FirebaseFirestore.getInstance();
        workmateViewModel.addDataToFirestore();
        workmateViewModel.updateWorkmates();
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


