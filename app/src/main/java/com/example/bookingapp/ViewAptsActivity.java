package com.example.bookingapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewAptsActivity extends AppCompatActivity {


    //bring in recyclerView
    private RecyclerView recyclerView;
    Database db=new Database(ViewAptsActivity.this);
    //bring in layout manager
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;

    //temp list to test class  --- THIS IS WHERE WILL WILL NEED TO DO DB WORK --> query if they have admin privilege or not,
    //then do the return for the email of the user if not admin, if admin, query all appointments and return





    //instantiate list to hold our people;
    List<Booking> list = new ArrayList<>();
    List<Booking> list1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_apts);
        db.Connect();
        String email="";
        String room="";
        String date="";

        Bundle emailExtra = getIntent().getExtras();
        if (emailExtra != null) {

            email = emailExtra.getString("EMAIL");
            date = emailExtra.getString("DATE");
            room= emailExtra.getString("ROOM");


        }





        if (!email.equals("admin@email.com")){  /// so any user would fall under here
            list=db.usersList(email);

        }else {
            System.out.println("date and room: "+ date + " " +room);

            //this is for admins where all appointments are shown based off room and day selected
            //FILTERING will need to be applied when the database is queried...for prototype code
            //just adding all the people to the list
            list=db.adminList(date,room);
        }



        recyclerView=findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);

        //set the recycler view layout manager
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(list);  //PASS the adapter the list
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}