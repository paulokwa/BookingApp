package com.example.bookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Admin extends AppCompatActivity {

    Button btn_logout_admin, btn_cardio, btn_weight, btn_yoga;
    FirebaseAuth fbAuth;
    CalendarView calendarView2;
    TextView tv_date;  ///


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        btn_logout_admin = findViewById(R.id.btn_logout_admin);
        btn_cardio = findViewById(R.id.btn_cardio);
        btn_weight = findViewById(R.id.btn_weight);
        btn_yoga = findViewById(R.id.btn_yoga);
        calendarView2 = findViewById(R.id.calendarView2);
        fbAuth = FirebaseAuth.getInstance();
        tv_date = findViewById(R.id.tv_date);

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        tv_date.setText(sdf.format(calendarView2.getDate()));

        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

//                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                String sDate = sdf.format(calendar.getTime());
                tv_date.setText(sDate);
            }
        });

        btn_cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email="admin@email.com";
                String date = (String) tv_date.getText();

                Intent i = new Intent(Admin.this, ViewAptsActivity.class);
                Bundle emailExtra = new Bundle();
                emailExtra.putString("EMAIL", email);
                emailExtra.putString("DATE", date);
                emailExtra.putString("ROOM", "CardioRm");
                i.putExtras(emailExtra);
                startActivity(i);

//                finish();
            }
        });

        btn_yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email="admin@email.com";
                String date = (String) tv_date.getText();

                Intent i = new Intent(Admin.this, ViewAptsActivity.class);
                Bundle emailExtra = new Bundle();
                emailExtra.putString("EMAIL", email);
                emailExtra.putString("DATE", date);
                emailExtra.putString("ROOM", "YogaRm");
                i.putExtras(emailExtra);
                startActivity(i);

//                finish();
            }
        });

        btn_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email="admin@email.com";
                String date = (String) tv_date.getText();

                Intent i = new Intent(Admin.this, ViewAptsActivity.class);
                Bundle emailExtra = new Bundle();
                emailExtra.putString("EMAIL", email);
                emailExtra.putString("DATE", date);
                emailExtra.putString("ROOM", "WeightRm");
                i.putExtras(emailExtra);
                startActivity(i);

//                finish();
            }
        });


        btn_logout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });
    }
}