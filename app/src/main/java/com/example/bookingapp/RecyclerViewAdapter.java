package com.example.bookingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter <RecyclerViewAdapter.MyViewHolder>{


    //THIS will be the list of people objects
    List<Booking> list;


    public RecyclerViewAdapter(List<Booking> list)
    {
        this.list = list;  //get the data from the list
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //implement method
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);

        //create object of viewholder -> pass in view
        MyViewHolder myViewHolder = new MyViewHolder(view);

        //RETURN myViewHolder
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //this is where we assign the value to the person view----> or this is where we would assign value from DB

        String email =list.get(position).getEmail();
        String room = list.get(position).getRoom();
        String time = list.get(position).getTime();
        String date = list.get(position).getDate();

        holder.tvName.setText(list.get(position).getName());
        holder.tvEmail.setText(email);
        holder.tvRoom.setText(room);
        holder.tvTime.setText(time);
        holder.tvDateP.setText(date);



        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = holder.btnCancel.getContext();
                Database db =new Database(context);
                db.delete(email, room, date, time);
                Toast.makeText(context, "Booking cancelled.", Toast.LENGTH_LONG).show();

                // refresh the list
                list.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        //this will return the size of the list
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

         TextView tvName, tvEmail, tvRoom, tvTime, tvDateP;
         Button btnCancel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //set the textview to the single view
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvRoom = itemView.findViewById(R.id.tvRoom);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvDateP = itemView.findViewById(R.id.tvDateP);

            btnCancel = itemView.findViewById(R.id.cancel_btn);
        }
    }
}
