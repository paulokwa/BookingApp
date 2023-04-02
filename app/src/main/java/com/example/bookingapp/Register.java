package com.example.bookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText et_name, et_password, et_email, et_confirm_password;
    Button btn_register, btn_go_login;
    boolean validInput =true;
    FirebaseAuth fbAuth;  //this is for authentication
    FirebaseFirestore fbStore; //this is the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //instance firebase auth and store
        fbAuth = FirebaseAuth.getInstance();
        fbStore = FirebaseFirestore.getInstance();

        et_name=findViewById(R.id.et_name);
        et_password=findViewById(R.id.et_password);
        et_email = findViewById(R.id.et_email);
        btn_register = findViewById(R.id.btn_register);
        btn_go_login = findViewById(R.id.btn_go_login);
        et_confirm_password = findViewById(R.id.et_confirm_password);

        //go to the login page
        btn_go_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //register new user
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForInput(et_name);
                checkForInput(et_email);
                checkForInput(et_password);

                if(et_password.getText().toString().equals( et_confirm_password.getText().toString())){

                    //if valid, begin registering user
                    if(validInput){

                        //creates the new user with email and password
                        fbAuth.createUserWithEmailAndPassword(et_email.getText().toString(),
                                et_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //This gets triggered when the user successfully creates account
                                Toast.makeText(Register.this, "Account Created", Toast.LENGTH_LONG).show();  //message to user

                                FirebaseUser user = fbAuth.getCurrentUser(); //get the info of newly created user
                                DocumentReference docRef = fbStore.collection("Users").document(user.getUid());  //get user id

                                //select data to store
                                Map<String, Object> userInfo = new HashMap<>();
                                userInfo.put("Name", et_name.getText().toString());
                                userInfo.put("Email", et_email.getText().toString());

                                //determine if user is admin
                                userInfo.put("isUser", "1"); //normal user, non admin

                                docRef.set(userInfo); //save to DB


                                startActivity(new Intent(getApplicationContext(), MainActivity.class));  //currently sending everyone to main

                                finish(); // this stops users from going back to register activity
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Register.this, "Failed to Create Account", Toast.LENGTH_LONG).show();
                            }
                        });
                    }//end if
                }else{
                    Toast.makeText(Register.this, "Passwords do not match, try again", Toast.LENGTH_LONG).show();
                }


            }
        }); //end on click
    }

    //check to make sure user put in input
    public boolean checkForInput(EditText input){
        if(input.getText().toString().isEmpty()){
            input.setText("Input is required!");
            validInput = false;
        }else{
            validInput = true;
        }
        return validInput;
    }
}