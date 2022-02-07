package com.edikorce.parkingapp.apiclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "http://192.168.1.8:8080/";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(){

        if (retrofit == null){

            Gson gson = new GsonBuilder()
                    .setLenient() //building as lenient mode`enter code here`
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
