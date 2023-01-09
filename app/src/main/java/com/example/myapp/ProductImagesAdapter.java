package com.example.myapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapp.databinding.RawProductImageBinding;

import java.util.List;

public class ProductImagesAdapter extends RecyclerView.Adapter<ProductImagesAdapter.ProductViewHolder> {

    private List<String> productImages;

    public ProductImagesAdapter(List<String> productImages) {
        this.productImages = productImages;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RawProductImageBinding binding = RawProductImageBinding.inflate( LayoutInflater.from( parent.getContext() ), parent, false );
        Glide.with( parent.getContext() ).load( productImages.get( viewType ) ).apply( new RequestOptions().placeholder( R.drawable.ic_baseline_image_24 ) ).into( binding.ivProduct );
        return new ProductViewHolder( binding );
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

        public void bind(int pos) {
            String image = productImages.get( pos );
//            binding.ivProduct.setImageResource( image );

        }
    }
}
