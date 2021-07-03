package com.example.demoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity  {




    EditText name,time,date, mdate;
    Button insert, update, delete, view;
    DBHelper DB;
    private NotificationManagerCompat notificationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        notificationManager = NotificationManagerCompat.from(this);

        name = findViewById(R.id.name);
        time = findViewById(R.id.time);
        date = findViewById(R.id.date);
        mdate = findViewById(R.id.mgfdate);

        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnShow);

        name.addTextChangedListener(tw);
        time.addTextChangedListener(tw);
        date.addTextChangedListener(tw);
        mdate.addTextChangedListener(tw);

        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();
                String timeTXT = time.getText().toString();
                String dateTXT = date.getText().toString();
                String mdateTXT = mdate.getText().toString();

                Boolean checkinsertdata = DB.insertmedicinedata(nameTXT, timeTXT, dateTXT, mdateTXT);

                if (checkinsertdata == true) {
                    Toast.makeText(MainActivity2.this, "New Entry inserted", Toast.LENGTH_LONG ).show();
                } else {
                    Toast.makeText(MainActivity2.this, "New Entry not inserted", Toast.LENGTH_LONG ).show();
                }
                sendOnChannel2();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();
                String timeTXT = time.getText().toString();
                String dateTXT = date.getText().toString();
                String mdateTXT = mdate.getText().toString();

                Boolean checkupdatedata = DB.updatemedicinedata(nameTXT, timeTXT, dateTXT, mdateTXT);

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

                sendOnChannel1();
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
                    buffer.append("Name: " +res.getString(0) + "\n");
                    buffer.append("Time: " +res.getString(1) + "\n");
                    buffer.append("Expiry Date: " +res.getString(2) + "\n");
                    buffer.append("MGF Date: " +res.getString(3) + "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setCancelable(true);
                builder.setTitle("Data entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });


    }


    private void sendOnChannel2() {
        String title = name.getText().toString();
        String message = "Medicine Inserted";
        Notification notification = new NotificationCompat.Builder(this, App.channle_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_medical)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(2, notification);
    }

    private void sendOnChannel1() {
        String title = name.getText().toString();
        String message = "Medicine deleted";
        Notification notification = new NotificationCompat.Builder(this, App.channle_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_medical)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

    private TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String nm = name.getText().toString().trim();
            String tm = time.getText().toString().trim();
            String dt = date.getText().toString().trim();
            String mdt = mdate.getText().toString().trim();

            insert.setEnabled(!nm.isEmpty() && !tm.isEmpty() && !dt.isEmpty() && !mdt.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}
