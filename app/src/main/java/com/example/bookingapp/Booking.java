package com.example.bookingapp;

public class Booking {

    private String name;
    private String email;
    private String room;
    private String time;
    private String date;
    public Booking(){};//empty constructor

    public Booking(String name, String email, String room, String time, String date) {
        this.name=name;
        this.email=email;
        this.room=room;
        this.time=time;
        this.date=time;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getRoom(){
        return room;
    }
    public void setRoom(String room){
        this.room = room;
    }

    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }
}
