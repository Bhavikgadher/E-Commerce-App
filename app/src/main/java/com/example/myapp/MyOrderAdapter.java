package com.example.myapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.Viewholder> {


    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.my_orders_item_layout, parent, false );
        return new Viewholder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.Viewholder holder, int position) {
        int resource = myOrderItemModelList.get( position ).getProductImage();
        int rating = myOrderItemModelList.get( position ).getRating();
        String title = myOrderItemModelList.get( position ).getProductTitle();
        String deliveryDate = myOrderItemModelList.get( position ).getDeliveryStatus();
        holder.setData( resource, title, deliveryDate, rating );
    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView ordersIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;

        private LinearLayout rateNowContainer;

        public Viewholder(@NonNull View itemView) {
            super( itemView );
            productImage = itemView.findViewById( R.id.product_image );
            ordersIndicator = itemView.findViewById( R.id.orders_indicator );
            productTitle = itemView.findViewById( R.id.product_title );
            deliveryStatus = itemView.findViewById( R.id.order_delivered_date );
            rateNowContainer = itemView.findViewById( R.id.rate_now_container1 );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(),OrderDetailsActivity.class);
                    itemView.getContext().startActivity( orderDetailsIntent );
                }
            } );
        }

        private void setData(int resource, String title, String deliveryDate, int rating) {
            productImage.setImageResource( resource );
            productTitle.setText( title );
            if (deliveryDate.equals( "Cancelled" )) {
                ordersIndicator.setImageTintList( ColorStateList.valueOf( itemView.getContext().getResources().getColor( R.color.red ) ) );
            } else {
                ordersIndicator.setImageTintList( ColorStateList.valueOf( itemView.getContext().getResources().getColor( R.color.green ) ) );
            }
            deliveryStatus.setText( deliveryDate );

            setRating( rating );
            for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
                final int starPosition = x;
                rateNowContainer.getChildAt( x ).setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setRating( starPosition );
                    }
                } );
            }
        }

        private void setRating(int starPosition) {
            for (int x = 0; x < rateNowContainer.getChildCount(); x++) {
                ImageView starBtn = (ImageView) rateNowContainer.getChildAt( x );
                starBtn.setImageTintList( ColorStateList.valueOf( Color.parseColor( "#bebebe" ) ) );
                if (x <= starPosition) {
                    starBtn.setImageTintList( ColorStateList.valueOf( Color.parseColor( "#FBC02D" ) ) );
                }
            }
        }
    }
}
