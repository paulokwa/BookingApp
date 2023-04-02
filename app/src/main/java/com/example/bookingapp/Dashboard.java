package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    Button btn_logout_user, btn_book, btn_view_apts;
    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn_logout_user = findViewById(R.id.btn_logout_user);
        fbAuth = FirebaseAuth.getInstance();
        btn_book = findViewById(R.id.btn_book);
        btn_view_apts = findViewById(R.id.btn_view_apts);


        btn_logout_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });


        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //grab the email passed in, keep passing it down to the next activity
                String email = "";
                String name="";

                Bundle emailExtra = getIntent().getExtras();
                if (emailExtra != null) {
                    email = emailExtra.getString("EMAIL");
                    name = emailExtra.getString("NAME");

                    System.out.println("'name' from dashboard");
                    System.out.println(name);
                }

                Intent i = new Intent(Dashboard.this, SelectRoomActivity.class);
                emailExtra.putString("EMAIL", email);
                emailExtra.putString("NAME", name);
                i.putExtras(emailExtra);
                startActivity(i);
                //finish();
            }
        });

        btn_view_apts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //grab the email passed in, keep passing it down to the next activity
                String email = "";

                Bundle emailExtra = getIntent().getExtras();
                if (emailExtra != null) {
                    email = emailExtra.getString("EMAIL");
                }

                Intent i = new Intent(Dashboard.this, ViewAptsActivity.class);
                emailExtra.putString("EMAIL", email);
//                emailExtra.putString("ROOM", "");
//                emailExtra.putString("DATE", "");
                i.putExtras(emailExtra);
                startActivity(i);

                // in order to nav back from the view appointments page, not need to finish the activity here
//                finish();

            }
        });

    }
}

