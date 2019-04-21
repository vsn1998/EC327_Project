package com.example.googlemaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;



public class MainPage extends AppCompatActivity {

    private int hours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
    }

    public void setTouchEnabled(boolean enabled) {

    }
    public void addHours(View v) {
        // Get the new value from a user input:
        EditText hoursEditText = findViewById(R.id.hours);

        // Update the old value:
        hours = Integer.parseInt (hoursEditText.getText().toString());
        updateChart();
    }



    private void updateChart(){
        // Update the text in a center of the chart:
        //TextView numberOfHours = findViewById(R.id.number_of_hours);
        //numberOfHours.setText(String.valueOf(hours) + " / " + 24);

        // Calculate the slice size and update the pie chart:
        ProgressBar pieChart = findViewById(R.id.stats_progressbar);
        double d = (double) hours / 24;
        int progress = (int) (d * 100);
        pieChart.setProgress(progress);
    }
}
