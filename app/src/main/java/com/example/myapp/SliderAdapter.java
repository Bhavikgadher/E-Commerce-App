package com.example.myapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
    private final List<SliderModel> sliderModelList;

    public SliderAdapter(List<SliderModel> sliderModelList){
        this.sliderModelList = sliderModelList;
    }


    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout,parent,false);
        ConstraintLayout bannerContainer = view.findViewById(R.id.banner_container);
        bannerContainer.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(sliderModelList.get(viewType).getBackgroundColor())));
        return new SliderViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return sliderModelList.size();
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder{
        View itemView;
        public SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
        public void bind(int position){
            SliderModel model = sliderModelList.get(position);
            ImageView banner = itemView.findViewById(R.id.banner_slide);
            banner.setImageResource(model.getBanner());
        }
    }

}
