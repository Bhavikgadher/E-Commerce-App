package com.example.myapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HorizontalProductScrollAdapter extends RecyclerView.Adapter<HorizontalProductScrollModel.ViewHolder> {

    private final List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public HorizontalProductScrollAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @NonNull
    @Override
    public HorizontalProductScrollModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.horizontal_scroll_item_layout, parent, false );
        return new HorizontalProductScrollModel.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalProductScrollModel.ViewHolder holder, int position) {
        String resource = horizontalProductScrollModelList.get( position ).getProduceImage();
        String title = horizontalProductScrollModelList.get( position ).getProductTitle();
        String color = horizontalProductScrollModelList.get( position ).getProductColor();
        String price = horizontalProductScrollModelList.get( position ).getProductPrice();
        String productId = horizontalProductScrollModelList.get( position ).getProductID();
        holder.setData( productId,resource, title, color, price );
    }

    @Override
    public int getItemCount() {

        if (horizontalProductScrollModelList.size() > 8) {
            return 8;
        } else {
            return horizontalProductScrollModelList.size();
        }
    }
}
