package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {

    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemsRecyclerView;
    private Button continueBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_my_cart, container, false );
        continueBtn = view.findViewById( R.id.cart_continue_btn );

        cartItemsRecyclerView = view.findViewById( R.id.rv_cart_items );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() );
        layoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        cartItemsRecyclerView.setLayoutManager( layoutManager );
        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add( new CartItemModel( 0, R.mipmap.ic_12_min, "Iphone 12 Min", 2, "Rs.49,999/-", "Rs.59,999/-", 1, 0, 0 ) );
        cartItemModelList.add( new CartItemModel( 0, R.mipmap.ic_13_plus, "Iphone 13 Plus", 2, "Rs.59,999/-", "Rs.69,999/-", 2, 2, 3 ) );
        cartItemModelList.add( new CartItemModel( 0, R.mipmap.ic_14_plus, "Iphone 14 Plus", 2, "Rs.69,999/-", "Rs.79,999/-", 5, 3, 4 ) );
        cartItemModelList.add( new CartItemModel( 1, "Iphone(3 items)", "Rs.1,20,999/-", "Free", "Rs.1,20,999/-", "Rs.9999/-" ) );

        CartAdapter cartAdapter = new CartAdapter( cartItemModelList );
        cartItemsRecyclerView.setAdapter( cartAdapter );
        cartAdapter.notifyDataSetChanged();

        continueBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent(getContext(),DeliveryActivity.class);
                getContext().startActivity( deliveryIntent );
            }
        } );

        return view;
    }
}