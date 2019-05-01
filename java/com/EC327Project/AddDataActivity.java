package com.EC327Project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddDataActivity extends AppCompatActivity {

    EditText edhrs;
    EditText edact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        edhrs = findViewById(R.id.edt_hours);
        edact = findViewById(R.id.edt_activities);

    }

    public void saveClicked(View v){
        switch (v.getId()){
            case(R.id.btn_datasave):
                //String hours = edhrs.getText().toString();
                //String activities = edact.getText().toString();

                try{
                    File f1 = new File(getFilesDir(), "hours.txt");
                    if(!f1.exists()){
                        f1.createNewFile();
                    }
                    FileOutputStream fos1 = new FileOutputStream(f1);
                    fos1.write(edhrs.getText().toString().getBytes());
                    fos1.close();
                    edhrs.setText("Hours saved!");
                }catch(IOException e) {
                    e.printStackTrace();
                }

                try{
                    File f2 = new File(getFilesDir(), "activities.txt");
                    if(!f2.exists()){
                        f2.createNewFile();
                    }
                    FileOutputStream fos2 = new FileOutputStream(f2);
                    fos2.write(edact.getText().toString().getBytes());
                    fos2.close();
                    edact.setText("Activities saved!");
                }catch(IOException e) {
                    e.printStackTrace();
                }

            case(R.id.add2home):
                Intent intent = new Intent(AddDataActivity.this, HomeActivity.class);
                startActivity(intent);
        }
    }

}
