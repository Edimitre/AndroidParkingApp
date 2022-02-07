package com.edikorce.parkingapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edikorce.parkingapp.R;
import com.edikorce.parkingapp.adapter.ParkingSpotAdapter;
import com.edikorce.parkingapp.apiclient.ApiClient;
import com.edikorce.parkingapp.apiclient.ApiInterface;
import com.edikorce.parkingapp.model.ParkingSpot;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingSpotsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ParkingSpotAdapter adapter;
    ApiInterface apiInterface;
    ProgressBar progressBar;
    List<ParkingSpot> parkingSpotList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_spots);

        loadObjects();
        getAllFreeParkingSpots();

        loadRecyclerView();



    }

    private void loadObjects(){
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        progressBar = findViewById(R.id.progres_bar_1);
    }

    private void getAllFreeParkingSpots(){
        showProgresBar(true);
        Call<List<ParkingSpot>> listCall = apiInterface.getAllFreeParkingSpots();
        listCall.enqueue(new Callback<List<ParkingSpot>>() {
            @Override
            public void onResponse(Call<List<ParkingSpot>> call, Response<List<ParkingSpot>> response) {
                if (response.isSuccessful()){
                    parkingSpotList = response.body();
                    if (parkingSpotList.isEmpty()){
                        showProgresBar(false);
                        Toast.makeText(ParkingSpotsActivity.this, "Per Momentin Nuk Ka Vende Parkingu Bosh", Toast.LENGTH_SHORT).show();
                    }else{
                        putListToAdapter(parkingSpotList);
                        showProgresBar(false);

                    }

                }
            }

            @Override
            public void onFailure(Call<List<ParkingSpot>> call, Throwable t) {
                Toast.makeText(ParkingSpotsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void loadRecyclerView(){

        adapter = new ParkingSpotAdapter();
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    private void showProgresBar(boolean state) {

        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);

        }
    }

    private void putListToAdapter(List<ParkingSpot> parkingSpotList){
        adapter.putParkingSpots(parkingSpotList);
    }

}