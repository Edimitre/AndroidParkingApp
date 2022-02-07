package com.edikorce.parkingapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edikorce.parkingapp.R;
import com.edikorce.parkingapp.model.Car;

import java.util.ArrayList;
import java.util.List;


public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarHolder> {


    private List<Car> carList= new ArrayList<>();



    @NonNull
    @Override
    public CarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View parkingSpotView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item, parent, false);

        return new CarHolder(parkingSpotView);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CarHolder holder, int position) {

        Car car = carList.get(position);

        holder.carModel_text.setText("Marka : " + car.getCarModel());

        holder.plateNumber_text.setText("Targa : " + car.getPlateNumber());

    }



    @Override
    public int getItemCount() {
        return carList.size();
    }




    class CarHolder extends RecyclerView.ViewHolder{

        private TextView carModel_text, plateNumber_text;

        public CarHolder(@NonNull View itemView) {
            super(itemView);

            carModel_text = itemView.findViewById(R.id.carmodel_text);
            plateNumber_text = itemView.findViewById(R.id.platenumber_text);
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    public void putCarsList(List<Car> carList){
        this.carList = carList;
        notifyDataSetChanged();
    }

    public Car getCarByPos(int pos){
        return carList.get(pos);
    }
}
