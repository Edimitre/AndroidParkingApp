package com.edikorce.parkingapp.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edikorce.parkingapp.R;
import com.edikorce.parkingapp.adapter.CarAdapter;
import com.edikorce.parkingapp.adapter.UserParkingAdapter;
import com.edikorce.parkingapp.apiclient.ApiClient;
import com.edikorce.parkingapp.apiclient.ApiInterface;
import com.edikorce.parkingapp.localdb.Repository;
import com.edikorce.parkingapp.model.ParkingSpot;
import com.edikorce.parkingapp.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileActivity extends AppCompatActivity {

    User user;

    Repository repository;

    ApiInterface apiInterface;
    TextView nameText;

    FloatingActionButton btnWatchParkingSpots;

    CarAdapter carAdapter;

    UserParkingAdapter parkingSpotAdapter;

    RecyclerView carsRecyclerView;
    RecyclerView parikingSpotRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        try {
            loadObjects();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showProfile();
        loadCarsRecyclerView();
        loadParkingRecyclerView();
        setListeners();
    }

    private void loadObjects() throws InterruptedException {

        repository = new Repository(getApplicationContext());

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

        user = repository.getProfileFromLocal();

        nameText = findViewById(R.id.name_text);
        btnWatchParkingSpots = findViewById(R.id.btn_watch_parking);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.btn_delete_profile) {
            AlertDialog.Builder aleretDialog = new AlertDialog.Builder(this)
                    .setTitle("ParkingApp")
                    .setMessage("Kujdes Ketu Dilni Nga Profili Juaj \nDeshironi Te Vazhdoni ?")
                    .setPositiveButton("Vazhdo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteProfile();
                            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Mbyll", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            aleretDialog.show();

        }else if (item.getItemId() == R.id.btn_add_parking){

            EditText inputValue = new EditText(getApplication());
            inputValue.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);

            AlertDialog.Builder inputParkingDialog = new AlertDialog.Builder(this)
                    .setView(inputValue)
                    .setTitle("ParkingApp")
                    .setMessage("Shto Parking")
                    .setPositiveButton("Shto ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String parkingName = inputValue.getText().toString();

                            ParkingSpot parkingSpot = new ParkingSpot();
                            parkingSpot.setName(parkingName);
                            parkingSpot.setTaken(false);
                            if (!parkingName.isEmpty()){
                                Call<ParkingSpot> addParkingSpot = apiInterface.addParkingSpot(parkingSpot);

                                addParkingSpot.enqueue(new Callback<ParkingSpot>() {
                                    @Override
                                    public void onResponse(Call<ParkingSpot> call, Response<ParkingSpot> response) {
                                        if(response.isSuccessful()){
                                            ParkingSpot parkingSpot1 = response.body();
                                            AlertDialog.Builder alb = new AlertDialog.Builder(ProfileActivity.this)
                                                    .setMessage("Vendi i parkingut me emer\n" + parkingSpot1.getName() + "\n u shtua me sukses")
                                                    .setNegativeButton("mbyll", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {

                                                        }
                                                    });
                                            alb.show();


                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ParkingSpot> call, Throwable t) {

                                    }
                                });
                            }
                        }
                    })
                    .setNegativeButton("Mbyll", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            inputParkingDialog.show();

        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteProfile(){

        repository.deleteProfile();
        Toast.makeText(this, "Dalja Me Sukses", Toast.LENGTH_SHORT).show();


    }

    private void setListeners(){

        btnWatchParkingSpots.setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, ParkingSpotsActivity.class));
        });

    }

    @SuppressLint("SetTextI18n")
    private void showProfile(){
        nameText.setText("Pershendetje : " + user.getName());

    }

    private void loadCarsRecyclerView(){

        carAdapter = new CarAdapter();
        carAdapter.putCarsList(user.getCarList());

        carsRecyclerView= findViewById(R.id.user_cars_recycler_view);
        carsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        carsRecyclerView.setHasFixedSize(true);
        carsRecyclerView.setAdapter(carAdapter);

    }

    private void loadParkingRecyclerView(){

        parkingSpotAdapter = new UserParkingAdapter();
        parkingSpotAdapter.putParkingSpots(user.getParkingSpotList());

        parikingSpotRecyclerView = findViewById(R.id.parking_recycler_view);
        parikingSpotRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        parikingSpotRecyclerView.setHasFixedSize(true);
        parikingSpotRecyclerView.setAdapter(parkingSpotAdapter);

    }



}