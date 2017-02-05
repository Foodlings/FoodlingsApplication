package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class DashboardScreen extends AppCompatActivity {

    ImageButton SettingsButton;
    Button PortalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);

        //SettingsScreen Button's Click Event
        SettingsButton = (ImageButton)findViewById(R.id.SettingsButton);
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(DashboardScreen.this, SettingsScreen.class)); }
        });

        //Portal Button's Click Event
        PortalButton = (android.widget.Button)findViewById(R.id.PortalButton);
        PortalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(DashboardScreen.this, PortalScreen.class)); }
        });
    }
}
