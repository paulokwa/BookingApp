package com.example.bookingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SelectRoomActivity extends AppCompatActivity {

    ImageButton img_btn_weights, img_btn_cardio, img_btn_yoga;
    Button btn_prev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_room);

        img_btn_weights= findViewById(R.id.img_btn_weights);
        img_btn_cardio = findViewById(R.id.img_btn_cardio);
        img_btn_yoga = findViewById(R.id.img_btn_yoga);
        btn_prev = findViewById(R.id.btn_prev);


        img_btn_weights.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email="";
                String name="";

                Bundle emailExtra = getIntent().getExtras();
                if (emailExtra != null) {
                    email = emailExtra.getString("EMAIL");
                    name = emailExtra.getString("NAME");
                }


                String room = "weights";

                Intent i = new Intent(SelectRoomActivity.this, CalendarActivity.class);
                Bundle extras = new Bundle();
                extras.putString("KEY", room);
                extras.putString("EMAIL", email);
                extras.putString("NAME", name);
                i.putExtras(extras);
                startActivity(i);

//                finish();

            }
        });
//
        img_btn_cardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email="";
                String name="";

                Bundle emailExtra = getIntent().getExtras();
                if (emailExtra != null) {
                    email = emailExtra.getString("EMAIL");
                    name = emailExtra.getString("NAME");
                }

                String room = "cardio";

                Intent i = new Intent(SelectRoomActivity.this, CalendarActivity.class);
                Bundle extras = new Bundle();
                extras.putString("KEY", room);
                extras.putString("EMAIL", email);
                extras.putString("NAME", name);
                i.putExtras(extras);
                startActivity(i);

//                finish();

            }
        });

        img_btn_yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email="";
                String name="";

                Bundle emailExtra = getIntent().getExtras();
                if (emailExtra != null) {
                    email = emailExtra.getString("EMAIL");
                    name = emailExtra.getString("NAME");

                }

                String room = "yoga";

               Intent i = new Intent(SelectRoomActivity.this, CalendarActivity.class);
               Bundle extras = new Bundle();
               extras.putString("KEY", room);
               extras.putString("EMAIL", email);
               extras.putString("NAME", name);
               i.putExtras(extras);
               startActivity(i);

//                finish();

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
                Intent i = new Intent(SelectRoomActivity.this, Dashboard.class);
                extras.putString("EMAIL", email);
                i.putExtras(extras);
                startActivity(i);
            }
        });
    }

}