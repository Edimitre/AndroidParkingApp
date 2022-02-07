package com.edikorce.parkingapp.adapter;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.edikorce.parkingapp.R;
import com.edikorce.parkingapp.activity.ProfileActivity;
import com.edikorce.parkingapp.apiclient.ApiClient;
import com.edikorce.parkingapp.apiclient.ApiInterface;
import com.edikorce.parkingapp.localdb.Repository;
import com.edikorce.parkingapp.model.ParkingSpot;
import com.edikorce.parkingapp.model.Receipt;
import com.edikorce.parkingapp.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserParkingAdapter extends RecyclerView.Adapter<UserParkingAdapter.UserSpotHolder> {


    private List<ParkingSpot> parkingSpotList = new ArrayList<>();

    private Repository repository;

    private ApiInterface apiInterface;


    @NonNull
    @Override
    public UserSpotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        repository = new Repository(parent.getContext());
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        View userspot = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_parking_item, parent, false);

        return new UserSpotHolder(userspot);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UserSpotHolder holder, int position) {

        ParkingSpot parkingSpot = parkingSpotList.get(position);


        holder.parking_name_text.setText("VendNdodhja : " + parkingSpot.getName());

        holder.bookedText.setText(parkingSpot.getBookedAt());

        holder.parkingSpot = parkingSpot;

    }



    @Override
    public int getItemCount() {
        return parkingSpotList.size();
    }

    class UserSpotHolder extends RecyclerView.ViewHolder{

        private TextView parking_name_text, bookedText;
        ParkingSpot parkingSpot;

        public UserSpotHolder(@NonNull View itemView) {
            super(itemView);

            parking_name_text = itemView.findViewById(R.id.user_parking_name);
            bookedText = itemView.findViewById(R.id.hour_booked_text);

            itemView.findViewById(R.id.btn_free_parking).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext())
                            .setMessage("Deshironi ta lironi parkingun")
                            .setTitle("ParkingApp")
                            .setPositiveButton("Po", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    User user = null;
                                    try {
                                        user = repository.getProfileFromLocal();

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }

                                    Call<Receipt> getParkingReceipt = apiInterface.generateUserReceipt(user.getId(), parkingSpot.getId());
                                    getParkingReceipt.enqueue(new Callback<Receipt>() {
                                        @Override
                                        public void onResponse(Call<Receipt> call, Response<Receipt> response) {
                                            if (response.isSuccessful()){
                                                Receipt receipt = response.body();
                                                User user1 = receipt.getUser();
                                                repository.deleteProfile();
                                                repository.saveUser(user1);

                                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(itemView.getContext())
                                                        .setTitle("Kohezgjatja e Qendrimit : " + receipt.getMinutesStayed() + " Minuta")
                                                        .setMessage("Vlera per te Paguar : " + receipt.getCost() + " Lek")
                                                        .setNegativeButton("Mbyll", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                Intent intent = new Intent(itemView.getContext(), ProfileActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                itemView.getContext().startActivity(intent);
                                                            }
                                                        });
                                                alertDialog.show();

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Receipt> call, Throwable t) {

                                        }
                                    });

                                }
                            })
                            .setNegativeButton("Jo", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            });
                    alertDialog.show();

                }
            });
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void putParkingSpots(List<ParkingSpot> parkingSpotList){
        this.parkingSpotList = parkingSpotList;
        notifyDataSetChanged();
    }


}
