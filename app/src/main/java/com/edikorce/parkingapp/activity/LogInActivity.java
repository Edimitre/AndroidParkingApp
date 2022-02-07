package com.edikorce.parkingapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.edikorce.parkingapp.R;
import com.edikorce.parkingapp.apiclient.ApiClient;
import com.edikorce.parkingapp.apiclient.ApiInterface;
import com.edikorce.parkingapp.localdb.Repository;
import com.edikorce.parkingapp.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {


    EditText inputPassword, inputName;
    Button btnLogin;
    TextView btnRegister;
    ApiInterface apiInterface;
    Repository repository;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        loadObjects();


        setListeners();

        try {
            showFirstPage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }




    private void loadObjects(){

        inputPassword = findViewById(R.id.input_password_text);
        inputName = findViewById(R.id.input_name_text);
        btnLogin= findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);
        repository = new Repository(getApplicationContext());
        progressBar = findViewById(R.id.log_in_progres_bar_1);
   }

    private boolean areInputsValid(){

        if(inputName.getText().toString().isEmpty()){
            showToast("Emri nuk mund te jete bosh");
            return false;
        }else if(inputPassword.getText().toString().isEmpty()){
            showToast("Passwordi nuk mund te jete bosh");
            return false;
        }else{
            return true;
        }

    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void setListeners(){

        btnLogin.setOnClickListener(v -> {
            if (areInputsValid()){
                try {
                    saveUserinLocalDb(inputPassword.getText().toString(), inputName.getText().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btnRegister.setOnClickListener(v-> startActivity(new Intent(LogInActivity.this, MainActivity.class)));
    }

    private void showProgresBar(boolean state) {

        if (state) {
            progressBar.setVisibility(View.VISIBLE);
            inputPassword.setVisibility(View.INVISIBLE);
            inputName.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            inputPassword.setVisibility(View.VISIBLE);
            inputName.setVisibility(View.VISIBLE);
            inputPassword.setText("");
            inputName.setText("");

        }
    }

    private boolean hasProfileIbLocalDb() throws InterruptedException {

        User user = repository.getProfileFromLocal();

        return user != null;

    }

    private void showFirstPage() throws InterruptedException {

        if (hasProfileIbLocalDb()) {
            Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else {

        }
    }

    private void saveUserinLocalDb(String password, String name) throws InterruptedException {
        showProgresBar(true);
        Call<User> call = apiInterface.getUserByPasswordAndName(password, name);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if (response.isSuccessful()){
                    User user = response.body();
                    if (!(user == null)){
                        repository.saveUser(user);
                        showProgresBar(false);
                        Intent intent = new Intent(LogInActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }


                }else {
                    showToast("User nuk U Gjend ");
                    showProgresBar(false);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showToast("Dicka nuk shkoi mire ....provoni te fusni te dhenat perseri \nedhe sigurohuni qe ato te jene ne regull");
                showProgresBar(false);
            }
        });




    }
}