package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {
    CalendarView calendarView;
    TextView tv_date;
    Button btn_next, btn_prev;
    boolean selectedDay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoga_calendar);

        calendarView = findViewById(R.id.calendarView);
        tv_date = findViewById(R.id.tv_date);
        btn_next = findViewById(R.id.btn_next);
        btn_prev = findViewById(R.id.btn_prev);

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // show today's date by default
        tv_date.setText(sdf.format(calendarView.getDate()));


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {

//                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                String sDate = sdf.format(calendar.getTime());
                tv_date.setText(sDate);
                long timeGetTime =new Date().getTime();  //get current date
                String today = sdf.format(timeGetTime);

                Date tDay = null;                       //Convert to Date format

                try {
                    tDay = sdf.parse(today);

                } catch (ParseException e) {
                    e.printStackTrace();

                }

                Date sDay = null;

                try {
                    sDay = sdf.parse(sDate);

                } catch (ParseException e) {
                    e.printStackTrace();

                }
                //Returns true if the time is earlier than today
                long timeDifference=tDay.getTime()-sDay.getTime();

                if(timeDifference>0)
                {
                    selectedDay=true;
                }else
                    {selectedDay=false;}
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // determine which picture was clicked to get here
                Bundle extras = getIntent().getExtras();
                if (selectedDay) {
                    Toast.makeText(CalendarActivity.this, "Cannot select a previous date", Toast.LENGTH_SHORT).show();
                    System.out.println("false");
                } else {
                    if (extras != null) {
                        String roomName = extras.getString("KEY");
                        String email = extras.getString("EMAIL");
                        String name = extras.getString("NAME");

                        System.out.println("'name' from calendar");
                        System.out.println(name);


                        if (roomName.equals("yoga")) {

                            //this will allow us to pass the date selected from the calender to the correct
                            //activity for booking time slots
                            String date = (String) tv_date.getText();
                            System.out.println("date is"+date);

                            Intent i = new Intent(CalendarActivity.this, YogaRmActivity.class);
                            Bundle dateExtra = new Bundle();
                            dateExtra.putString("DATE", date);
                            dateExtra.putString("EMAIL", email);
                            dateExtra.putString("NAME", name);
                            i.putExtras(dateExtra);
                            startActivity(i);

//                            finish();

                        } else if (roomName.equals("weights")) {

                            String date = (String) tv_date.getText();
                            System.out.println(date);

                            Intent i = new Intent(CalendarActivity.this, WeightRmActivity.class);
                            Bundle dateExtra = new Bundle();
                            dateExtra.putString("DATE", date);
                            dateExtra.putString("EMAIL", email);
                            dateExtra.putString("NAME", name);
                            i.putExtras(dateExtra);
                            startActivity(i);

//                            finish();
                        } else if (roomName.equals("cardio")) {

                            String date = (String) tv_date.getText();
                            System.out.println(date);


                            Intent i = new Intent(CalendarActivity.this, CardioRmActivity.class);
                            Bundle dateExtra = new Bundle();
                            dateExtra.putString("DATE", date);
                            dateExtra.putString("EMAIL", email);
                            dateExtra.putString("NAME", name);
                            i.putExtras(dateExtra);
                            startActivity(i);

//                            finish();
                        }
                    }

                }
            }
        });

        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email="";

                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    email = extras.getString("EMAIL");
                }

                //pass back to maintain email address
                Intent i = new Intent(CalendarActivity.this, SelectRoomActivity.class);
                extras.putString("EMAIL", email);
                i.putExtras(extras);
                startActivity(i);


                finish();
            }
        });
    }
}