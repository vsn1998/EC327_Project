package com.EC327Project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TargetActivity extends AppCompatActivity {

    EditText act1;
    EditText act2;
    EditText act3;
    EditText act4;
    EditText act5;

    EditText hrs1;
    EditText hrs2;
    EditText hrs3;
    EditText hrs4;
    EditText hrs5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data3);

        act1 = (EditText)findViewById(R.id.dactivity1);
        act2 = (EditText)findViewById(R.id.dactivity2);
        act3 = (EditText)findViewById(R.id.dactivity3);
        act4 = (EditText)findViewById(R.id.dactivity4);
        act5 = (EditText)findViewById(R.id.dactivity5);

        hrs1 = (EditText)findViewById(R.id.dhours1);
        hrs2 = (EditText)findViewById(R.id.dhours2);
        hrs3 = (EditText)findViewById(R.id.dhours3);
        hrs4 = (EditText)findViewById(R.id.dhours4);
        hrs5 = (EditText)findViewById(R.id.dhours5);

        showData();
    }

    public void showData(){

        String str_hrs = "";
        String str_act = "";


        File dhrs = new File(getFilesDir(),"dhours.txt");
        File dact = new File(getFilesDir(),"dactivities.txt");

        if(!dhrs.exists()||!dact.exists()){
            str_hrs = "2 5 2 2 7";
            str_act = "Work School Gym Food Sleep";
        }else{
            try{
                FileInputStream fis1 = new FileInputStream(dhrs);
                byte[] b1 = new byte[1024];
                int len1 = fis1.read(b1);
                str_hrs = new String(b1,0,len1);
                fis1.close();

                FileInputStream fis2 = new FileInputStream(dact);
                byte[] b2 = new byte[1024];
                int len2 = fis1.read(b2);
                str_act = new String(b2,0,len2);
                fis2.close();

            }catch(IOException e){
                e.printStackTrace();
            }
        }

        String strarray_hrs[] = str_hrs.split(" ");
        String strarray_act[] = str_act.split(" ");

        String dstrarray_hrs[] = {"2","5","2","2","7"};
        String dstrarray_act[] = {"Work","School","Gym","Food","Sleep"};

        if(strarray_act.length<1){
            act1.setText(dstrarray_act[0]);
        }else{
            act1.setText(strarray_act[0]);
        }

        if(strarray_act.length<2){
            act2.setText(dstrarray_act[1]);
        }else{
            act2.setText(strarray_act[1]);
        }

        if(strarray_act.length<3){
            act3.setText(dstrarray_act[2]);
        }else{
            act3.setText(strarray_act[2]);
        }

        if(strarray_act.length<4){
            act4.setText(dstrarray_act[3]);
        }else{
            act4.setText(strarray_act[3]);
        }

        if(strarray_act.length<5){
            act5.setText(dstrarray_act[4]);
        }else{
            act5.setText(strarray_act[4]);
        }

        if(strarray_act.length<1){
            hrs1.setText(dstrarray_hrs[0]);
        }else{
            hrs1.setText(strarray_hrs[0]);
        }

        if(strarray_act.length<2){
            hrs2.setText(dstrarray_hrs[1]);
        }else{
            hrs2.setText(strarray_hrs[1]);
        }

        if(strarray_act.length<3){
            hrs3.setText(dstrarray_hrs[2]);
        }else{
            hrs3.setText(strarray_hrs[2]);
        }

        if(strarray_act.length<4){
            hrs4.setText(dstrarray_hrs[3]);
        }else{
            hrs4.setText(strarray_hrs[3]);
        }

        if(strarray_act.length<5){
            hrs5.setText(dstrarray_hrs[4]);
        }else{
            hrs5.setText(strarray_hrs[4]);
        }

    }

    public void daddClicked(View v){
        switch (v.getId()){
            case(R.id.dbtnSave4add):

                String hours = "";
                String activities = "";


                if(!(((hrs1.getText().toString()).equals(""))||((act1.getText().toString()).equals("")))) {
                    hours = hours + hrs1.getText().toString() + " ";
                    String acta1[] = (act1.getText().toString()).split(" ");
                    for (int i = 0; i < acta1.length; i++) {
                        activities = activities + acta1[i];
                    }
                    activities = activities + " ";
                }

                if(!(((hrs2.getText().toString()).equals(""))||((act2.getText().toString()).equals("")))) {
                    hours = hours + hrs2.getText().toString() + " ";
                    String acta2[] = (act2.getText().toString()).split(" ");
                    for (int i = 0; i < acta2.length; i++) {
                        activities = activities + acta2[i];
                    }
                    activities = activities + " ";
                }

                if(!(((hrs3.getText().toString()).equals(""))||((act3.getText().toString()).equals("")))) {
                    hours = hours + hrs3.getText().toString() + " ";
                    String acta3[] = (act3.getText().toString()).split(" ");
                    for (int i = 0; i < acta3.length; i++) {
                        activities = activities + acta3[i];
                    }
                    activities = activities + " ";
                }

                if(!(((hrs4.getText().toString()).equals(""))||((act4.getText().toString()).equals("")))) {
                    hours = hours + hrs4.getText().toString() + " ";
                    String acta4[] = (act4.getText().toString()).split(" ");
                    for (int i = 0; i < acta4.length; i++) {
                        activities = activities + acta4[i];
                    }
                    activities = activities + " ";
                }

                if(!(((hrs5.getText().toString()).equals(""))||((act5.getText().toString()).equals("")))) {
                    hours = hours + hrs5.getText().toString();
                    String acta5[] = (act5.getText().toString()).split(" ");
                    for (int i = 0; i < acta5.length; i++) {
                        activities = activities + acta5[i];
                    }
                }

                try{
                    File f1 = new File(getFilesDir(), "dhours.txt");
                    if(!f1.exists()){
                        f1.createNewFile();
                    }
                    FileOutputStream fos1 = new FileOutputStream(f1);
                    fos1.write(hours.getBytes());
                    fos1.close();

                }catch(IOException e) {
                    e.printStackTrace();
                }

                try{
                    File f2 = new File(getFilesDir(), "dactivities.txt");
                    if(!f2.exists()){
                        f2.createNewFile();
                    }
                    FileOutputStream fos2 = new FileOutputStream(f2);
                    fos2.write(activities.getBytes());
                    fos2.close();

                }catch(IOException e) {
                    e.printStackTrace();
                }

            case(R.id.dadd2home):
                Intent intent = new Intent(TargetActivity.this, HomeActivity.class);
                startActivity(intent);
        }
    }
}
