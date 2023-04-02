package com.example.bookingapp;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Database {
    private static String driver = "com.mysql.jdbc.Driver";
    public static Connection connection = null;
    Context context;

    Database(Context context) {
        this.context = context;
    }

    public void Connect() {

        new Thread(new Runnable() {

            @Override

            public void run() {

                try {

                    Class.forName(driver);

                    connection = DriverManager.getConnection("jdbc:mysql://redteam.cohm64yxxjxy.us-east-1.rds.amazonaws.com/RedTeam?useUnicode=" +
                                    "true&characterEncoding=utf8",
                            "gymCustomer", "inft3000!");

                    Looper.prepare();

                    Toast.makeText(context, "connect successfully", Toast.LENGTH_SHORT).show();

                    Looper.loop();

                } catch (SQLException | ClassNotFoundException e) {

                    Log.d(String.valueOf(this), "connect error:" + e.toString());

                    Toast.makeText(context, "connect failed", Toast.LENGTH_SHORT).show();

                }
            }
        }).start();
    }


    public void insert(String email, String room, String date, String time, String name) {

        new Thread(new Runnable() {
            @Override

            public void run() {

                String SQL = "insert into booking values( '" + email + "','" + room + "','" + date + "','" + time + "','" + name + "');";

                try {
                    Statement statement = connection.createStatement();

                    statement.execute(SQL);

                } catch (SQLException e) {

                    Log.d("insert", "insert failed" + e.toString());

                }
            }
        }).start();
    }

    public void delete(String email, String room, String date, String time) {

        new Thread(new Runnable() {
            @Override

            public void run() {

                String SQL = "DELETE FROM booking WHERE email='" + email + "' and room = '" + room + "' and date ='" + date + "' and time ='" + time + "';";

                try {

                    Statement statement = connection.createStatement();

                    statement.execute(SQL);

                } catch (SQLException e) {

                    Log.d("delete", "delete failed" + e.toString());

                }
            }
        }).start();
    }

    public HashMap<String, Integer> count(String room, String date) {

        HashMap<String, Integer> bookedCount = new HashMap();
        String[] time = new String[1];
        Integer[] count = new Integer[1];

        CustomRunnable runnable = new CustomRunnable() {
            @Override
            public void run() {
                // get back 2 columns of data
                String SQL = "SELECT time, COUNT(*) FROM booking WHERE room = '" + room + "' and date ='" + date + "' GROUP BY time;";

                try {
                    if (connection == null) {
                        Class.forName(driver);

                        connection = DriverManager.getConnection("jdbc:mysql://redteam.cohm64yxxjxy.us-east-1.rds.amazonaws.com/RedTeam?useUnicode="
                                        + "true&characterEncoding=utf8",
                                "gymCustomer", "inft3000!");
                    }

                    Statement statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery(SQL);

                    while (resultSet.next()) {
                        time[0] = resultSet.getString(1);
                        count[0] = resultSet.getInt(2);

                        // create a map with the data
                        bookedCount.put(time[0], count[0]);

                        //Log.d("count", (count[0]+""));
                    }

                } catch (Exception e) {

                    Log.d("bookedcount", " failed" + e.toString());
                }
            }

            @Override
            public HashMap<String, Integer> getMap() {
                return bookedCount;
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return runnable.getMap();
    }


    public int reserved(String email, String room, String date, String time) {

        int[] count = new int[1];
        CustomRunnable myRunnable = new CustomRunnable() {

            @Override
            public void run() {

                String SQL = "select count(*) as idCount from booking where email='" + email + "' and date ='" + date + "' and time ='" + time + "'";

                try {
                    if (connection == null) {
                        Class.forName(driver);

                        connection = DriverManager.getConnection("jdbc:mysql://redteam.cohm64yxxjxy.us-east-1.rds.amazonaws.com/RedTeam?useUnicode="
                                        + "true&characterEncoding=utf8",
                                "gymCustomer", "inft3000!");
                    }

                    Statement statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery(SQL);

                    while (resultSet.next()) {
                        count[0] = resultSet.getInt("idCount");
                        String count1 = "" + count[0];
                        Log.d("count", count1);
                    }

                } catch (Exception e) {

                    Log.d("count", " failed" + e.toString());
                }
            }

            public int getCount() {
                String count1 = "counted in getCount: " + count[0];
                Log.d(String.valueOf(this), count1);

                return count[0];
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();

        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return myRunnable.getCount();
    }

    public List<Booking> usersList(String email) {
        final String[] data = new String[5];
        List<Booking> bookings = new ArrayList<>();

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long timeGetTime = new Date().getTime();  //get current date
        String today = sdf.format(timeGetTime);

        SelectRunnable myRunnable = new SelectRunnable() {

            @Override
            public void run() {

                String SQL = "SELECT  * FROM booking WHERE email = '" + email + "' and date >= '" + today + "';";

                try {
                    if (connection == null) {
                        Class.forName(driver);

                        connection = DriverManager.getConnection("jdbc:mysql://redteam.cohm64yxxjxy.us-east-1.rds.amazonaws.com/RedTeam?useUnicode="
                                        + "true&characterEncoding=utf8",
                                "gymCustomer", "inft3000!");
                    }

                    Statement statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery(SQL);

                    while (resultSet.next()) {
                        data[0] = resultSet.getString(1);
                        data[1] = resultSet.getString(2);
                        data[2] = resultSet.getString(3);
                        data[3] = resultSet.getString(4);
                        data[4] = resultSet.getString(5);

                        Booking booking = new Booking();
                        booking.setEmail(data[0]);
                        booking.setRoom(data[1]);
                        booking.setName(data[4]);
                        booking.setTime(data[3]);
                        booking.setDate(data[2]);

                        bookings.add(booking);
                    }

                } catch (Exception e) {

                    Log.d("Get user's booking list", " failed" + e.toString());
                }
            }

            @Override
            public List<Booking> getList() {
                return bookings;
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();

        try {
            myThread.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return myRunnable.getList();
    }

    public List<Booking> adminList(String date, String room) {
        final String[] data = new String[5];
        List<Booking> bookings = new ArrayList<>();

        SelectRunnable myRunnable = new SelectRunnable() {

            @Override
            public void run() {

                String SQL = "SELECT  * FROM booking WHERE date = '" + date + "' and room = '" + room + "';";

                try {
                    if (connection == null) {
                        Class.forName(driver);

                        connection = DriverManager.getConnection("jdbc:mysql://redteam.cohm64yxxjxy.us-east-1.rds.amazonaws.com/RedTeam?useUnicode="
                                        + "true&characterEncoding=utf8",
                                "gymCustomer", "inft3000!");
                    }

                    Statement statement = connection.createStatement();

                    ResultSet resultSet = statement.executeQuery(SQL);

                    while (resultSet.next()) {
                        data[0] = resultSet.getString(1);
                        data[1] = resultSet.getString(2);
                        data[2] = resultSet.getString(3);
                        data[3] = resultSet.getString(4);
                        data[4] = resultSet.getString(5);

                        Booking booking = new Booking();
                        booking.setEmail(data[0]);
                        booking.setRoom(data[1]);
                        booking.setName(data[4]);
                        booking.setTime(data[3]);
                        booking.setDate(data[2]);

                        bookings.add(booking);
                    }

                } catch (Exception e) {

                    Log.d("Get all bookings", " failed");
                }
            }

            @Override
            public List<Booking> getList() {
                return bookings;
            }
        };

        Thread myThread = new Thread(myRunnable);
        myThread.start();

        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return myRunnable.getList();
    }
}


