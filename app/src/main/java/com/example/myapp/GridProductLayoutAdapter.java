package com.example.myapp;

import static com.example.myapp.utils.Constants.FB_PRODUCT_ID;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridProductLayoutAdapter extends BaseAdapter {

    List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    public GridProductLayoutAdapter(List<HorizontalProductScrollModel> horizontalProductScrollModelList) {
        this.horizontalProductScrollModelList = horizontalProductScrollModelList;
    }

    @Override
    public int getCount() {
        return horizontalProductScrollModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.horizontal_scroll_item_layout,null);
            view.setElevation(0);
            view.setBackgroundColor(Color.parseColor("#ffffff"));

            view.setOnClickListener( view1 -> {
                Intent productDetailsIntent = new Intent(viewGroup.getContext(),PRoductDEtailshActivity.class);
                productDetailsIntent.putExtra( FB_PRODUCT_ID,horizontalProductScrollModelList.get( i ).getProductID() );
                viewGroup.getContext().startActivity( productDetailsIntent );
            } );

            ImageView productImage = view.findViewById(R.id.h_s_product_image);
            TextView productTitle = view.findViewById(R.id.h_s_product_title);
            TextView productColor = view.findViewById(R.id.h_s_product_color);
            TextView productPrice = view.findViewById(R.id.h_s_product_price);

            Glide.with(viewGroup.getContext()).load(horizontalProductScrollModelList.get(i).getProduceImage()).apply( new RequestOptions().placeholder( R.drawable.ic_baseline_image_24 ) ).into( productImage );
            productTitle.setText(horizontalProductScrollModelList.get(i).getProductTitle());
            productColor.setText(horizontalProductScrollModelList.get(i).getProductColor());
            productPrice.setText("Rs." + horizontalProductScrollModelList.get(i).getProductPrice() + "/-");
        } else {
            view = convertView;
        }
        return view;
    }
}
