package com.example.googlemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Button;



public class MainPage extends AppCompatActivity {
    private Button locationButton;
    private Button dataButton;
    public int hours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        locationButton = (Button) findViewById(R.id.locationButton);
        dataButton = (Button) findViewById(R.id.dataButton);

        locationButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (hours == 0) {
                    openLocationPage();
                }
            }
        });

        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hours != 0) {
                    openDataPage();
                }
            }
        });
    }

    public void openLocationPage() {
        Intent intent1 = new Intent(this, MapsActivity.class);
        startActivity(intent1);
    }

    public void openDataPage() {
        Intent intent2 = new Intent(this, DataPage.class);
        startActivity(intent2);
    }




}

