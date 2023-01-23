package com.example.myapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;


public class MyWishlistFragment extends Fragment {
    public MyWishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;
    private Dialog loadingDialog;
    public static WishlistAdapter wishlistAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_my_wishlist, container, false );

        wishlistRecyclerView= view.findViewById( R.id.rv_my_wishlist );
        /// loading
        loadingDialog = new Dialog( getContext() );
        loadingDialog.setContentView( R.layout.loading_progress_dialog );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setBackgroundDrawable( getContext().getDrawable( R.drawable.slider_background ) );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();
        /// loading

//        wishlistModelList.add( new WishlistModel( R.mipmap.ic_14_plus, "Iphone 14 pro", 1, "5", 145, "Rs.89,999/-", "Rs.98,999/-", "Case on Delivery" ) );

        if (DBqueries.wishlistModelList.size() == 0){
            DBqueries.wishList.clear();
            DBqueries.loadWishlist( getContext(),loadingDialog,true );
        }else {
            loadingDialog.dismiss();
        }
        wishlistAdapter = new WishlistAdapter( DBqueries.wishlistModelList,true );
        wishlistRecyclerView.setAdapter( wishlistAdapter );
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }
}