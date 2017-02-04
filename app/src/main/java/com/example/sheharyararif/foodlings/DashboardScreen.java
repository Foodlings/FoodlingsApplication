package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashboardScreen extends AppCompatActivity {

    Button PortalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);

        //Portal Button's Click Event
        PortalButton = (android.widget.Button)findViewById(R.id.PortalButton);
        PortalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(DashboardScreen.this, PortalScreen.class)); }
        });
    }
}
