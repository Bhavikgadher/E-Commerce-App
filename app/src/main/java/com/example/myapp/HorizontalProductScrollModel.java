package com.example.myapp;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class HorizontalProductScrollModel {

    private String productID;
    private String produceImage;
    private String productTitle;
    private String productColor;
    private String productPrice;

    public HorizontalProductScrollModel(String productID, String produceImage, String productTitle, String productColor, String productPrice) {
        this.productID = productID;
        this.produceImage = produceImage;
        this.productTitle = productTitle;
        this.productColor = productColor;
        this.productPrice = productPrice;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProduceImage() {
        return produceImage;
    }

    public void setProduceImage(String produceImage) {
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


        void setProductImage(String resource){
            Glide.with(itemView.getContext()).load( resource ).apply( new RequestOptions().placeholder( R.drawable.ic_baseline_home_24 ) ).into(productImage);
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



