package com.example.myapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder> {

    private List<WishlistModel> wishlistModelList;
    private static Boolean wishlist;

    public WishlistAdapter(List<WishlistModel> wishlistModelList, Boolean wishlist) {
        this.wishlistModelList = wishlistModelList;
        this.wishlist = wishlist;
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.wishlist_item_layout, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapter.ViewHolder holder, int position) {
        String resourece = wishlistModelList.get( position ).getProductImage();
        String title = wishlistModelList.get( position ).getProductTitle();
        long freeCopens = wishlistModelList.get( position ).getFreeCoupens();
        String rating = wishlistModelList.get( position ).getRating();
        long totalRatings = wishlistModelList.get( position ).getTotalRating();
        String productPrice = wishlistModelList.get( position ).getProductPrice();
        String cuttedPrice = wishlistModelList.get( position ).getCuttedPrice();
        boolean paymentMethod = wishlistModelList.get( position ).isCOD();
        holder.setData( resourece, title, freeCopens, rating, totalRatings, productPrice, cuttedPrice, paymentMethod );
    }

    @Override
    public int getItemCount() {
        return wishlistModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView coupenIcon;
        private TextView productTitle;
        private TextView freeCopens;
        private TextView rating;
        private TextView totalRatings;
        private View priceCutter;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView paymentMethod;
        private ImageButton deleteBtn;


        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            productImage = itemView.findViewById( R.id.product_image );
            coupenIcon = itemView.findViewById( R.id.coupen_icon );
            productTitle = itemView.findViewById( R.id.product_title );
            freeCopens = itemView.findViewById( R.id.free_coupen );
            rating = itemView.findViewById( R.id.tv_product_rating_miniView );
            totalRatings = itemView.findViewById( R.id.total_ratings );
            priceCutter = itemView.findViewById( R.id.price_cutter );
            productPrice = itemView.findViewById( R.id.product_price );
            cuttedPrice = itemView.findViewById( R.id.cutted_price );
            paymentMethod = itemView.findViewById( R.id.payment_method );
            deleteBtn = itemView.findViewById( R.id.delete_btn );
        }

        private void setData(String resource, String title, long freeCopensNo, String averageRate, long totalRatingsNo, String price, String cuttedPriceValue, boolean COD) {
            Glide.with( itemView.getContext() ).load( resource ).apply( new RequestOptions().placeholder( R.drawable.ic_baseline_home_24 ) ).into( productImage );
            productTitle.setText( title );
            if (freeCopensNo != 0) {
                coupenIcon.setVisibility( View.VISIBLE );
                if (freeCopensNo == 1) {
                    freeCopens.setText( " Free " + freeCopensNo + " Coupen " );
                } else {
                    freeCopens.setText( " Free " + freeCopensNo + " Coupens " );
                }
            } else {
                coupenIcon.setVisibility( View.INVISIBLE );
                freeCopens.setVisibility( View.INVISIBLE );
            }
            rating.setText( averageRate );
            totalRatings.setText("(" + totalRatingsNo + ") ratings" );
            productPrice.setText("Rs" + price + "/-" );
            cuttedPrice.setText( "Rs" + cuttedPriceValue + "/-" );
            if(COD){
                paymentMethod.setVisibility( View.VISIBLE );
            }else {
                paymentMethod.setVisibility( View.INVISIBLE );
            }
            if (wishlist) {
                deleteBtn.setVisibility( View.VISIBLE );
            } else {
                deleteBtn.setVisibility( View.GONE );
            }
            deleteBtn.setOnClickListener( view -> Toast.makeText( itemView.getContext(), "delete", Toast.LENGTH_SHORT ).show() );
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent productDetailsIntent = new Intent(itemView.getContext(),PRoductDEtailshActivity.class);
                    itemView.getContext().startActivity( productDetailsIntent );
                }
            } );
        }
    }
}
