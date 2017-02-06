package com.example.sheharyararif.foodlings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VisitorDashboard extends AppCompatActivity {

    Button PortalButton, CommunityButton, RegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visitor_dashboard);

        //Portal Button's Click Event
        PortalButton = (android.widget.Button)findViewById(R.id.PortalButton);
        PortalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(VisitorDashboard.this, PortalScreen.class)); }
        });

        //Community Button's Click Event
        CommunityButton = (Button)findViewById(R.id.CommunityButton);
        CommunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(VisitorDashboard.this, CommunityScreen.class)); }
        });

        //Register Button's Click Event
        RegisterButton = (Button)findViewById(R.id.RegisterButton);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { startActivity(new Intent(VisitorDashboard.this, RegistrationScreen.class)); }
        });
    }
}
