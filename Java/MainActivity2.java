package com.example.demoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText name,time,date;
    Button insert, update, delete, view;
    DBHelper DB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        name = findViewById(R.id.name);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);

        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnShow);

        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();
                String timeTXT = time.getText().toString();
                String dateTXT = date.getText().toString();

                Boolean checkinsertdata = DB.insertmedicinedata(nameTXT, timeTXT, dateTXT);

                if (checkinsertdata == true) {
                    Toast.makeText(MainActivity2.this, "New Entry inserted", Toast.LENGTH_LONG ).show();
                } else {
                    Toast.makeText(MainActivity2.this, "New Entry not inserted", Toast.LENGTH_LONG ).show();
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();
                String timeTXT = time.getText().toString();
                String dateTXT = date.getText().toString();

                Boolean checkupdatedata = DB.updatemedicinedata(nameTXT, timeTXT, dateTXT);

                if (checkupdatedata == true) {
                    Toast.makeText(MainActivity2.this, "Entry updated", Toast.LENGTH_LONG ).show();
                } else {
                    Toast.makeText(MainActivity2.this, " Entry not updated", Toast.LENGTH_LONG ).show();
                }
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();

                Boolean checkdeletedata = DB.deletedata(nameTXT);

                if (checkdeletedata == true) {
                    Toast.makeText(MainActivity2.this, "Entry deleted", Toast.LENGTH_LONG ).show();
                } else {
                    Toast.makeText(MainActivity2.this, " Entry not deleted", Toast.LENGTH_LONG ).show();
                }
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getdata();
                if(res.getCount()==0) {
                    Toast.makeText(MainActivity2.this, "No entry exists", Toast.LENGTH_LONG).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()) {
                    buffer.append("Name:" +res.getString(0) + "\n");
                    buffer.append("Time:" +res.getString(1) + "\n");
                    buffer.append("Date:" +res.getString(2) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setCancelable(true);
                builder.setTitle("Data entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });


    }
}