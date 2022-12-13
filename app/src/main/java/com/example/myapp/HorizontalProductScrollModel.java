package com.example.myapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HorizontalProductScrollModel {

    private int produceImage;
    private String productTitle;
    private String productColor;
    private String productPrice;

    public HorizontalProductScrollModel(int produceImage, String productTitle, String productColor, String productPrice) {
        this.produceImage = produceImage;
        this.productTitle = productTitle;
        this.productColor = productColor;
        this.productPrice = productPrice;
    }

    public int getProduceImage() {
        return produceImage;
    }

    public void setProduceImage(int produceImage) {
        this.produceImage = produceImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView productColor;
        private TextView productPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.h_s_product_image);
            productTitle = itemView.findViewById(R.id.h_s_product_title);
            productColor = itemView.findViewById(R.id.h_s_product_color);
            productPrice = itemView.findViewById(R.id.h_s_product_price);

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(),PRoductDEtailshActivity.class);
                    itemView.getContext().startActivity( productDetailsIntent );
                }
            } );
        }

        void setProductImage(int resource) {
            productImage.setImageResource(resource);
        }

        void setProductTitle(String title) {
            productTitle.setText(title);
        }

        void setProductColor(String color){
            productColor.setText(color);
        }
        void  setProductPrice(String price){
            productPrice.setText(price);
        }
    }
}



