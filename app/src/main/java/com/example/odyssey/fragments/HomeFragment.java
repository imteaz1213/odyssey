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
import com.example.odyssey.R;
import com.example.odyssey.adaptars.HomeCarItemAdaptar;
import com.example.odyssey.models.HomeCarItemModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeCarItemAdaptar.OnItemClickListener {
    private RecyclerView homeCarItemContainer;
    private HomeCarItemAdaptar carItemAdapter;
    private List<HomeCarItemModel> itemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        homeCarItemContainer = view.findViewById(R.id.home_car_item_container);

        int[] carImages = {
                R.drawable.car1,
                R.drawable.car2,
                R.drawable.car3,
                R.drawable.car4,
                R.drawable.car5,
                R.drawable.car6
        };

        for (int i = 1; i <= 6; i++) {
            itemList.add(new HomeCarItemModel("Car Item " + i, carImages[i-1]));
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        homeCarItemContainer.setLayoutManager(layoutManager);

        carItemAdapter = new HomeCarItemAdaptar(itemList, this);
        homeCarItemContainer.setAdapter(carItemAdapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), CarDetailsActivity.class);
        intent.putExtra("carId", position);
        startActivity(intent);
        Toast.makeText(getContext(), "Clicked item at position " + position, Toast.LENGTH_SHORT).show();
    }
}
