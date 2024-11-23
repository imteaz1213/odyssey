package com.example.odyssey.adaptars;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.odyssey.R;
import com.example.odyssey.models.HomeCarItemModel;

import java.util.List;

public class HomeCarItemAdaptar extends RecyclerView.Adapter<HomeCarItemAdaptar.HomeCarItemViewHolder> {

    private List<HomeCarItemModel> itemList;
    private final OnItemClickListener listener;

    public HomeCarItemAdaptar(List<HomeCarItemModel> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class HomeCarItemViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView image;

        public HomeCarItemViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.home_car_item_title);
            image = view.findViewById(R.id.home_car_item_image);

            view.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public HomeCarItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_car_card_item, parent, false);
        return new HomeCarItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCarItemViewHolder holder, int position) {
        HomeCarItemModel currentItem = itemList.get(position);
        holder.title.setText(currentItem.getTitle());
//        holder.image.setImageResource(currentItem.getImage());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
