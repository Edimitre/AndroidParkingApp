package com.edikorce.parkingapp.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edikorce.parkingapp.R;
import com.edikorce.parkingapp.activity.ProfileActivity;
import com.edikorce.parkingapp.apiclient.ApiClient;
import com.edikorce.parkingapp.apiclient.ApiInterface;
import com.edikorce.parkingapp.localdb.Repository;
import com.edikorce.parkingapp.model.ParkingSpot;
import com.edikorce.parkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParkingSpotAdapter extends RecyclerView.Adapter<ParkingSpotAdapter.ParkingSpotHolder> {


    private List<ParkingSpot> parkingSpotList = new ArrayList<>();

    private Repository repository;

    private ApiInterface apiInterface;


    @NonNull
    @Override
    public ParkingSpotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        repository = new Repository(parent.getContext());
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        View parkingSpotView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parking_spot_item, parent, false);
        return new ParkingSpotHolder(parkingSpotView);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ParkingSpotHolder holder, int position) {

        ParkingSpot parkingSpot = parkingSpotList.get(position);


        holder.parking_name_text.setText("VendNdodhja : " + parkingSpot.getName());

        holder.available_text.setText("I Lire");

        holder.parkingSpot = parkingSpot;

    }



    @Override
    public int getItemCount() {
        return parkingSpotList.size();
    }

    class ParkingSpotHolder extends RecyclerView.ViewHolder{

        private TextView parking_name_text, available_text;
        ParkingSpot parkingSpot;

        public ParkingSpotHolder(@NonNull View itemView) {
            super(itemView);

            parking_name_text = itemView.findViewById(R.id.parkin_name_text);
            available_text = itemView.findViewById(R.id.available_text);

            itemView.findViewById(R.id.btn_prenoto_parking).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext())
                            .setTitle("ParkingApp")
                            .setMessage("Deshironi ta rezervoni kete Parking ? ")
                            .setPositiveButton("Rezervo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        // get User from localDb
                                        User user = repository.getProfileFromLocal();
                                        // update user to clouddb
                                        Call<User> call = apiInterface.addUserParking(user.getId(), parkingSpot.getId());
                                        call.enqueue(new Callback<User>() {
                                            @Override
                                            public void onResponse(Call<User> call, Response<User> response) {
                                                if (response.isSuccessful()){
                                                    User user1 = response.body();
                                                    repository.deleteProfile();
                                                    repository.saveUser(user1);
                                                    Toast.makeText(itemView.getContext(), "Parkingu u rezervua me sukses", Toast.LENGTH_SHORT).show();
                                                    itemView.getContext().startActivity(new Intent(itemView.getContext(), ProfileActivity.class));
                                                }else {
                                                    Toast.makeText(itemView.getContext(), "Pati nje problem gjate rezervimit te parkingut\nkeni lidhje interneti? ", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<User> call, Throwable t) {

                                            }
                                        });
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                }
                            })
                            .setNegativeButton("Mbyll", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    alertDialog.show();

                }
            });
        }
    }

    // todo repair user not saving parking table

    @SuppressLint("NotifyDataSetChanged")
    public void putParkingSpots(List<ParkingSpot> parkingSpotList){
        this.parkingSpotList = parkingSpotList;
        notifyDataSetChanged();
    }


//    public ParkingSpot getParkingSpotsByPos(int pos){
//        return parkingSpotList.get(pos);


//    }
}
