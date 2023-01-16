package com.example.go4lunch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.go4lunch.notification.NotificationReceiver;
import com.example.go4lunch.notification.NotificationService;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat notificationSwitch;
    private TextView notificationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        notificationSwitch=findViewById(R.id.notificationSwitch);
        notificationText=findViewById(R.id.notificationText);

        disabledNotification();
    }

    private void disabledNotification() {
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                suppressNotifications();
            }
        });
    }


    public void suppressNotifications(){
        if (notificationSwitch.isChecked()) {

            notificationSwitch.setChecked(true);
            notificationText.setText("Notification autorisée");
            Log.d("notif", "swithc is disabled true");
        }else{
            notificationSwitch.setChecked(false);
            notificationText.setText("Notification suspendue");
            Log.d("notif", "switch is disabled false" );

            //TODO Enregister dans les sharedPreferences si on veut la notif ou non
        }
    }
       /* Boolean enabledNotif=false;
        if (enabledNotif){
            notificationService.onCancel(this);
            Toast.makeText(this, "notification are enabled", Toast.LENGTH_SHORT).show();
            enabledNotif=true;
        }else{
            enabledNotif=false;
        }

        return enabledNotif;
    }*/
}