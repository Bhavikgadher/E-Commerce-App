package com.example.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyWishlistFragment extends Fragment {
    public MyWishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;

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
        List<WishlistModel> wishlistModelList = new ArrayList<>();
//        wishlistModelList.add( new WishlistModel( R.mipmap.ic_14_plus, "Iphone 14 pro", 1, "5", 145, "Rs.89,999/-", "Rs.98,999/-", "Case on Delivery" ) );

        WishlistAdapter wishlistAdapter = new WishlistAdapter( wishlistModelList,true );
        wishlistRecyclerView.setAdapter( wishlistAdapter );
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }
}