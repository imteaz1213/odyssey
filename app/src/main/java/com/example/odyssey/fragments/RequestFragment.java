package com.example.odyssey.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.odyssey.R;
import com.example.odyssey.api.ApiService;
import com.example.odyssey.api.RetrofitClient;
import com.example.odyssey.models.BookingListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestFragment extends Fragment {
    private TextView textView;
    private SharedPreferences sharedPreferences;
    private String bearerToken;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_request, container, false);

        textView = view.findViewById(R.id.textView);

        sharedPreferences = requireActivity().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        bearerToken = sharedPreferences.getString("authToken", null);

        fetchBookingList();
        return view;
    }


    private void fetchBookingList() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<BookingListResponse> call = apiService.getBookingList("Bearer " + bearerToken);

        call.enqueue(new Callback<BookingListResponse>() {
            @Override
            public void onResponse(Call<BookingListResponse> call, Response<BookingListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookingListResponse bookingListResponse = response.body();
                    Toast.makeText(getContext(), "Fetched -> "+bookingListResponse.getData().get(0).getBooking_id(), Toast.LENGTH_LONG).show();
                    StringBuilder result = new StringBuilder();
                    for (int i = 0; i < bookingListResponse.getData().size(); i++) {
                        result.append("Booking ID: ").append(bookingListResponse.getData().get(i).getBooking_id()).append("\n")
                                .append("Pickup Location: ").append(bookingListResponse.getData().get(i).getPickup_location()).append("\n")
                                .append("Dropoff Location: ").append(bookingListResponse.getData().get(i).getDropoff_location()).append("\n")
                                .append("Booking Status: ").append(bookingListResponse.getData().get(i).getBooking_status()).append("\n\n\n");
                    }
                    textView.setText(result.toString());
                } else {
                    textView.setText("Failed to load data.");
                }
            }


            @Override
            public void onFailure(Call<BookingListResponse> call, Throwable t) {
                Toast.makeText(getContext(), "ERROR: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}