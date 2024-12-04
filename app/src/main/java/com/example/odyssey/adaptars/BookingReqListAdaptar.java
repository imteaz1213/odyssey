package com.example.odyssey.adaptars;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.odyssey.R;
import com.example.odyssey.models.BookingModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BookingReqListAdaptar extends RecyclerView.Adapter<BookingReqListAdaptar.BookingListViewHolder> {
    private List<BookingModel> bookingList;

    public BookingReqListAdaptar(List<BookingModel> bookingList) {
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public BookingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_req_list_item, parent, false);
        return new BookingListViewHolder(view);
    }

    public class BookingListViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView pickupDate;
        private LinearLayout actionContainer;
        private Button acceptbtn;
        private Button declinebtn;
        public BookingListViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            pickupDate = itemView.findViewById(R.id.pickup_date);
            actionContainer = itemView.findViewById(R.id.action_container);
            acceptbtn = itemView.findViewById(R.id.accept_btn);
            declinebtn = itemView.findViewById(R.id.decline_btn);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BookingReqListAdaptar.BookingListViewHolder holder, int position) {
        BookingModel bookingData = bookingList.get(position);
        holder.userName.setText(bookingData.getName());
        holder.pickupDate.setText(convertToDisplayFormat(bookingData.getPickup_datetime()));
        holder.acceptbtn.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Accept Button Clicked", Toast.LENGTH_LONG).show();
        });
        holder.declinebtn.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Decline Button Clicked", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static String convertToDisplayFormat(String datetime) {
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("dd MMM, yyyy");
            LocalDateTime dateTime = LocalDateTime.parse(datetime, inputFormatter);
            return dateTime.format(displayFormatter);
        } catch (Exception e) {
            Log.e("DateConversionError", "Invalid date format: " + datetime, e);
            return "Invalid Date";
        }
    }

}
