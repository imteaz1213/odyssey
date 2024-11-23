package com.example.odyssey.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.odyssey.CarDetailsActivity;
import com.example.odyssey.NotificationActivity;
import com.example.odyssey.R;
import com.example.odyssey.adaptars.HomeCarItemAdaptar;
import com.example.odyssey.api.ApiService;
import com.example.odyssey.api.RetrofitClient;
import com.example.odyssey.models.HomeCarItemModel;
import com.example.odyssey.models.VehicleModel;
import com.example.odyssey.models.VehicleListResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements HomeCarItemAdaptar.OnItemClickListener {
    private RecyclerView homeCarItemContainer;
    private HomeCarItemAdaptar carItemAdapter;
    private FloatingActionButton fab_filter;
    private List<HomeCarItemModel> itemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeCarItemContainer = view.findViewById(R.id.home_car_item_container);
        fab_filter = view.findViewById(R.id.fab_filter);
        fab_filter.setOnClickListener(v -> startActivity(new Intent(getContext(), NotificationActivity.class)));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeCarItemContainer.setLayoutManager(layoutManager);

        carItemAdapter = new HomeCarItemAdaptar(itemList, this);
        homeCarItemContainer.setAdapter(carItemAdapter);

        fetchVehicles();

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), CarDetailsActivity.class);
        intent.putExtra("carId", position);
        startActivity(intent);
        Toast.makeText(getContext(), "Clicked item at position " + position, Toast.LENGTH_SHORT).show();
    }

    private void fetchVehicles() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<VehicleListResponse> call = apiService.getAllVehicles();

        call.enqueue(new Callback<VehicleListResponse>() {

            @Override
            public void onResponse(Call<VehicleListResponse> call, Response<VehicleListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    VehicleListResponse vehicleListResponse = response.body();
                    if ("true".equals(vehicleListResponse.getStatus())) {
                        itemList.clear();

                        for (VehicleModel vehicle : vehicleListResponse.getData()) {
                            itemList.add(new HomeCarItemModel(String.valueOf(vehicle.getDriver_id()), vehicle.getMain_image()));
                        }

                        carItemAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), vehicleListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch vehicles.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VehicleListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
