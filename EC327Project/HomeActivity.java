package com.EC327Project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    PieChart pie;
    PieChart dpie;

    public String strcolor[] = {"#ECA496","#F1C696","#F7E39A","#8BC0BF","#A8D5EF","#ECA496","#F1C696","#F7E39A","#8BC0BF","#A8D5EF"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pie = (PieChart) findViewById(R.id.piechart);
        dpie = (PieChart) findViewById(R.id.dpiechart);

        toPie();

    }

    public void toPie(){

        File f_hrs = new File(getFilesDir(),"hours.txt");
        File f_act = new File(getFilesDir(),"activities.txt");
        File f_date = new File(getFilesDir(),"stored_date.txt");
        File f_dhrs = new File(getFilesDir(),"dhours.txt");
        File f_dact = new File(getFilesDir(),"dactivities.txt");

        try{
            FileInputStream fis1 = new FileInputStream(f_hrs);
            byte[] b1 = new byte[1024];
            int len1 = fis1.read(b1);
            String dataout_hrs = new String(b1,0,len1);
            fis1.close();

            FileInputStream fis2 = new FileInputStream(f_act);
            byte[] b2 = new byte[1024];
            int len2 = fis2.read(b2);
            String dataout_act = new String(b2,0,len2);
            fis2.close();

            FileInputStream fis3 = new FileInputStream(f_date);
            byte[] b3 = new byte[1024];
            int len3  = fis3.read(b3);
            String dataout_date = new String(b3,0,len3);
            fis3.close();

            List<PieEntry> yVals = new ArrayList<>();
            List<Integer> colors = new ArrayList<>();

            String strarray_hrs[] = dataout_hrs.split(" ");
            String strarray_act[] = dataout_act.split(" ");

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

            String des;
            if(total < 24) {
                yVals.add(new PieEntry(24-total, "Hours Left"));
                colors.add(Color.parseColor("#AAAAAA"));
                des = "<Hour Circle>  ";
                Description description = new Description();
                description.setText(des);
                description.setTextSize(18f);
                pie.setDescription(description);
            }else if(total == 24){
                des = "<Hour Circle>  ";
                Description description = new Description();
                description.setText(des);
                description.setTextSize(18f);
                pie.setDescription(description);
            }else {
                des = "WARNING!! hour sum>24";
                Description description = new Description();
                description.setText(des);
                description.setTextSize(14f);
                pie.setDescription(description);
            }

            pie.setCenterText(dataout_date);
            pie.setCenterTextSize(16f);

            pie.setData(pieData);

        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            FileInputStream fis4 = new FileInputStream(f_dhrs);
            byte[] b1 = new byte[1024];
            int len1 = fis4.read(b1);
            String dataout_hrs = new String(b1, 0, len1);
            fis4.close();

            FileInputStream fis5 = new FileInputStream(f_dact);
            byte[] b2 = new byte[1024];
            int len2 = fis5.read(b2);
            String dataout_act = new String(b2, 0, len2);
            fis5.close();

            List<PieEntry> yVals = new ArrayList<>();
            List<Integer> colors = new ArrayList<>();

            String dstrarray_hrs[] = dataout_hrs.split(" ");
            String dstrarray_act[] = dataout_act.split(" ");

            float value;
            float total = 0;
            int length;

            if (dstrarray_act.length < dstrarray_hrs.length)
                length = dstrarray_act.length;
            else
                length = dstrarray_hrs.length;

            for (int i = 0; (i < length); i++) {
                value = Float.parseFloat(dstrarray_hrs[i]);
                yVals.add(new PieEntry(value, dstrarray_act[i]));
                total += value;
                colors.add(Color.parseColor(strcolor[i]));
            }

            String des;

            if (total < 24) {
                yVals.add(new PieEntry(24 - total, "Hours Left"));
                colors.add(Color.parseColor("#AAAAAA"));
                des = "<Target Hour Circle>";
            } else if (total == 24) {
                des = "<Target Hour Circle>";
            } else {
                des = "WARNING!! hour sum>24";
            }

            Description description = new Description();
            description.setText(des);
            description.setTextSize(14f);
            dpie.setDescription(description);

            PieDataSet pieDataSet = new PieDataSet(yVals, "");
            pieDataSet.setColors(colors);
            PieData pieData = new PieData(pieDataSet);

            dpie.setCenterText("Target");
            dpie.setCenterTextSize(15f);

            dpie.setData(pieData);

        }catch (IOException e){
            e.printStackTrace();

            List<PieEntry> yVals = new ArrayList<>();
            List<Integer> colors = new ArrayList<>();

            String dstrarray_hrs[] = {"2","5","2","2","7"};
            String dstrarray_act[] = {"Work","School","Gym","Food","Sleep"};

            float value;
            float total = 0;
            int length;

            if (dstrarray_act.length < dstrarray_hrs.length)
                length = dstrarray_act.length;
            else
                length = dstrarray_hrs.length;

            for (int i = 0; (i < length); i++) {
                value = Float.parseFloat(dstrarray_hrs[i]);
                yVals.add(new PieEntry(value, dstrarray_act[i]));
                total += value;
                colors.add(Color.parseColor(strcolor[i]));
            }

            String des;

            if (total < 24) {
                yVals.add(new PieEntry(24 - total, "Hours Left"));
                colors.add(Color.parseColor("#AAAAAA"));
                des = "<Default Target Circle>";
            } else if (total == 24) {
                des = "<Default Target Circle>";
            } else {
                des = "WARNING: Your hour sum is larger than 24 hours!";
            }

            Description description = new Description();
            description.setText(des);
            description.setTextSize(14f);
            dpie.setDescription(description);

            PieDataSet pieDataSet = new PieDataSet(yVals, "");
            pieDataSet.setColors(colors);
            PieData pieData = new PieData(pieDataSet);

            dpie.setCenterText("Target");
            dpie.setCenterTextSize(15f);

            dpie.setData(pieData);

        }

    }


    public void btn2loc(View v){
        Intent intent1 = new Intent(HomeActivity.this,MapsActivity.class);
        startActivity(intent1);
    }


    public void btnClicked(View v){
        Intent intent2 = new Intent(HomeActivity.this,AddData.class);
        startActivity(intent2);
    }

    public void syncClicked(View v){

        File f_hrs = new File(getFilesDir(),"hours.txt");
        File f_act = new File(getFilesDir(),"activities.txt");

        try{
            FileInputStream fis1 = new FileInputStream(f_hrs);
            byte[] b1 = new byte[1024];
            int len1 = fis1.read(b1);
            String dataout_hrs = new String(b1,0,len1);
            fis1.close();

            FileInputStream fis2 = new FileInputStream(f_act);
            byte[] b2 = new byte[1024];
            int len2 = fis2.read(b2);
            String dataout_act = new String(b2,0,len2);
            fis2.close();

            File dhrs = new File(getFilesDir(),"dhours.txt");
            File dact = new File(getFilesDir(),"dactivities.txt");

            if(!dact.exists()){
                dact.createNewFile();
            }

            if(!dhrs.exists()){
                dhrs.createNewFile();
            }

            FileOutputStream defact = new FileOutputStream(dact);
            defact.write(dataout_act.getBytes());
            defact.close();

            FileOutputStream defhrs = new FileOutputStream(dhrs);
            defhrs.write(dataout_hrs.getBytes());
            defhrs.close();

        }catch(IOException e){
            e.printStackTrace();
        }

        toPie();

    }

    public void targetClicked(View v){
        Intent intent3 = new Intent(HomeActivity.this,TargetActivity.class);
        startActivity(intent3);
    }




}
