package com.example.go4lunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.go4lunch.Repository.SettingsRepository;
import com.example.go4lunch.Viewmodel.HomeActivityViewModel;
import com.example.go4lunch.Viewmodel.SettingsActivityViewModel;
import com.example.go4lunch.notification.NotificationReceiver;
import com.example.go4lunch.notification.NotificationService;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat notificationSwitch;
    private TextView notificationText;
    private Toolbar settingsToolbar;
    private SettingsActivityViewModel settingsActivityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settingsActivityViewModel = new ViewModelProvider(this).get(SettingsActivityViewModel.class);

        notificationSwitch=findViewById(R.id.notificationSwitch);
        notificationText=findViewById(R.id.notificationText);
        settingsToolbar=findViewById(R.id.settingsToolbar);

        disabledNotification();

        //back with toolbar
        setSupportActionBar(settingsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settingsToolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void disabledNotification() {
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               settingsActivityViewModel.setNotificationEnabled(isChecked);
            }
        });
        settingsActivityViewModel.getNotificationData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                notificationSwitch.setChecked(aBoolean);
                if (notificationSwitch.isChecked() ) {
                    notificationText.setText("Notification autorisée");
                    Log.e("notif", "switch is disabled true");

                }else{
                    notificationText.setText("Notification suspendue");
                    Log.e("notif", "switch is disabled false" );

                }
            }
        });
    }
}