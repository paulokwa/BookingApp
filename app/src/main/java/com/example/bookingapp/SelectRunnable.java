package com.example.bookingapp;

import java.util.List;

public class SelectRunnable  implements Runnable{

    private volatile List<Booking> bookings;

    @Override
    public void run() {
        bookings =null;
    }

    // to get value
    public List<Booking> getList() {
        return bookings;
    }

}

