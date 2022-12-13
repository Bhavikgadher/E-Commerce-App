package com.example.myapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.databinding.RawProductImageBinding;

import java.util.List;

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ProductViewHolder> {

    private List<Integer> productImages;

    public ProductImagesAdapter(List<Integer> productImages) {
        this.productImages = productImages;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RawProductImageBinding binding = RawProductImageBinding.inflate( LayoutInflater.from(  parent.getContext()),parent,false );
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind( position );
    }

    @Override
    public int getItemCount() {
        return productImages.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        RawProductImageBinding binding;
        @SuppressLint("ResourceType")
        public ProductViewHolder(@NonNull RawProductImageBinding binding) {
            super( binding.getRoot() );
            this.binding = binding;
        }

        public void bind(int pos){//tata
            Integer image = productImages.get(pos);
            binding.ivProduct.setImageResource(image);
        }
    }
}
