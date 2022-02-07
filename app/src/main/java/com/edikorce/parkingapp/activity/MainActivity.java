package com.edikorce.parkingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edikorce.parkingapp.R;
import com.edikorce.parkingapp.apiclient.ApiClient;
import com.edikorce.parkingapp.apiclient.ApiInterface;
import com.edikorce.parkingapp.localdb.Repository;
import com.edikorce.parkingapp.model.Car;
import com.edikorce.parkingapp.model.Role;
import com.edikorce.parkingapp.model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    Repository repository;

    ProgressBar progressBar;

    EditText name, password, carModel, plateNumber;
    TextView signUpText, btnLogin;
    Button btnSaveProfile;
    CheckBox checkAdmin, checkUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadObjects();
        setListeners();
        showProgresBar(false);
        try {
            showFirstPage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void loadObjects() {
        name = findViewById(R.id.name_text);
        password = findViewById(R.id.password_text);
        carModel = findViewById(R.id.car_model_text);
        plateNumber = findViewById(R.id.plate_number_text);
        btnSaveProfile = findViewById(R.id.btn_save_profile);
        btnLogin = findViewById(R.id.btn_login_2);
        progressBar = findViewById(R.id.progres_bar);
        signUpText = findViewById(R.id.signup_text);
        repository = new Repository(getApplicationContext());
        checkAdmin = findViewById(R.id.check_admin);
        checkUser = findViewById(R.id.check_user);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);


    }

    private void setListeners() {

        btnSaveProfile.setOnClickListener(v -> {
            try {
                saveUserInCloudDb();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(intent); }

        );
    }

    private boolean areInputsValid() {

        if (name.getText().toString().isEmpty()) {
            showToast("Emri nuk mund te jete bosh");
            return false;
        } else if (password.getText().toString().isEmpty()) {
            showToast("Passwordi nuk mund te jete bosh");
            return false;
        } else if (carModel.getText().toString().isEmpty()) {
            showToast("Marka makines nuk mund te jete bosh");
            return false;
        } else if (plateNumber.getText().toString().isEmpty()) {
            showToast("Targa makines nuk mund te jete bosh");
            return false;
        } else if (!checkAdmin.isChecked() && !checkUser.isChecked()) {
            showToast("Roli nuk mund te jete bosh ...zgjidhni nje rol");
            return false;
        } else if (checkAdmin.isChecked() && checkUser.isChecked()){
            showToast("Ju lutemi zgjidhni vetem njerin nga rolet\nOse admin ose user ..te dyja nuk mund te jene ");
            return false;
        }else{
            return true;
        }

    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void saveUserInCloudDb() throws IOException, InterruptedException {

        if (areInputsValid()) {

            showProgresBar(true);

            User user = new User();
            user.setName(name.getText().toString().trim());
            user.setPassword(password.getText().toString().trim());

            if (checkAdmin.isChecked()) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }



            Car car = new Car();
            car.setCarModel(carModel.getText().toString().trim());
            car.setPlateNumber(plateNumber.getText().toString().trim());
            


            user.getCarList().add(car);
            // register user in cloud
            Call<User> registerInCloud = apiInterface.createUser(user);
            registerInCloud.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()){
                        // register user in local db
                        User user2 = response.body();
                        repository.saveUser(user2);
                        showToast("profili u regjistrua me sukses");

                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        showProgresBar(false);
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    showToast("Ndodhi nje problem gjate regjistrimit");
                    showProgresBar(false);
                }

            });


        }

    }

    private void showProgresBar(boolean state) {

        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            signUpText.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            signUpText.setVisibility(View.VISIBLE);
        }
    }

    private boolean hasProfileIbLocalDb() throws InterruptedException {

        User user = repository.getProfileFromLocal();

        return user != null;

    }

    private void showFirstPage() throws InterruptedException {

        if (hasProfileIbLocalDb()) {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}