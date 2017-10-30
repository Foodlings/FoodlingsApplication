package com.example.sheharyararif.foodlings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SettingsScreen extends AppCompatActivity {

    Button LogOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_screen);

        LogOutButton = (Button) findViewById(R.id.LogOutButton);
        LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(SettingsScreen.this)
                        .setTitle("Log-out Confirmation")
                        .setMessage("Do you want to Log-out?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(SettingsScreen.this, "Logged-out", Toast.LENGTH_SHORT).show();
                                Intent intents = new Intent(SettingsScreen.this, HomeScreen.class);
                                intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intents);
                                finish();
                            }})
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

    }
}
