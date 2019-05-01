package com.EC327Project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    PieChart pie;
    EditText test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pie = (PieChart) findViewById(R.id.piechart);
        test = (EditText) findViewById(R.id.edt_test);
    }


    public void btnClicked(View v){

        switch (v.getId()){

            case(R.id.home2loc):
                test.setText("loc clicked");
                Intent intent1 = new Intent(HomeActivity.this,MapsActivity.class);
                startActivity(intent1);

            case(R.id.btnAdd):
                Intent intent2 = new Intent(HomeActivity.this,AddDataActivity.class);
                startActivity(intent2);

            case(R.id.btnSync):
                try{
                    FileInputStream fis1 = new FileInputStream("/data/user/0/com.EC327Project/files/hours.txt");
                    byte[] b1 = new byte[1024];
                    int len1 = fis1.read(b1);
                    String dataout_hrs = new String(b1,0,len1);
                    fis1.close();

                    FileInputStream fis2 = new FileInputStream("/data/user/0/com.EC327Project/files/activities.txt");
                    byte[] b2 = new byte[1024];
                    int len2 = fis2.read(b2);
                    String dataout_act = new String(b2,0,len2);
                    fis2.close();

                    List<PieEntry> yVals = new ArrayList<>();
                    List<Integer> colors = new ArrayList<>();

                    String strarray_hrs[] = dataout_hrs.split(" ");
                    String strarray_act[] = dataout_act.split(" ");

                    String strcolor[] = {"#ECA496","#F1C696","#F7E39A","#8BC0BF","#A8D5EF","#ECA496","#F1C696","#F7E39A","#8BC0BF","#A8D5EF"};

                    float value;
                    float total = 0;
                    int length;
                    String testing = "";

                    if(strarray_act.length<strarray_hrs.length)
                        length = strarray_act.length;
                    else
                        length = strarray_hrs.length;

                    for(int i = 0; (i<length);i++){
                        value = Float.parseFloat(strarray_hrs[i]);
                        yVals.add(new PieEntry(value, strarray_act[i]));
                        total += value;
                        colors.add(Color.parseColor(strcolor[i]));
                    }

                    PieDataSet pieDataSet = new PieDataSet(yVals, "");
                    pieDataSet.setColors(colors);
                    PieData pieData = new PieData(pieDataSet);

                    pie.setData(pieData);

                }catch(IOException e){
                    e.printStackTrace();
                }
        }

    }



}
