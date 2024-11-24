package com.example.odyssey.adaptars;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.odyssey.R;
import com.example.odyssey.models.VehicleModel;

import java.util.List;

public class HomeCarItemAdaptar extends RecyclerView.Adapter<HomeCarItemAdaptar.HomeCarItemViewHolder> {

    private List<VehicleModel> vehicleList;

    public HomeCarItemAdaptar(List<VehicleModel> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public class HomeCarItemViewHolder extends RecyclerView.ViewHolder {
        TextView homeCarItemTitle;
        TextView carRating;
        ImageView mainImage;

        public HomeCarItemViewHolder(@NonNull View itemView) {
            super(itemView);
            homeCarItemTitle = itemView.findViewById(R.id.home_car_item_title);
            carRating = itemView.findViewById(R.id.car_rating);
            mainImage = itemView.findViewById(R.id.main_image);
        }
    }

    @NonNull
    @Override
    public HomeCarItemAdaptar.HomeCarItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_car_card_item, parent, false);
        return new HomeCarItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCarItemAdaptar.HomeCarItemViewHolder holder, int position) {
        VehicleModel vehicle = vehicleList.get(position);

        holder.homeCarItemTitle.setText(vehicle.getModel());
        holder.carRating.setText(String.valueOf(vehicle.getDriver_id()));
        Glide.with(holder.mainImage.getContext())
                .load(vehicle.getMain_image()) 
                .placeholder(R.drawable.car1)
                .into(holder.mainImage);

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(holder.itemView.getContext(),
                    "Car ID: " + vehicle.getVehicle_id(),
                    Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return vehicleList.size();
    }
}
