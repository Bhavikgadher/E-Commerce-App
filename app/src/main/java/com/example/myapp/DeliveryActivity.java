package com.example.myapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.databinding.ActivityDeliveryBinding;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private ActivityDeliveryBinding binding;
    private RecyclerView deliveryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_delivery );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( "Delivery" );

     deliveryRecyclerView = findViewById( R.id.rv_delivery );

        List<CartItemModel> cartItemModelList = new ArrayList<>();
        cartItemModelList.add( new CartItemModel( 0, R.mipmap.ic_12_min, "Iphone 12 Min", 2, "Rs.49,999/-", "Rs.59,999/-", 1, 0, 0 ) );
        cartItemModelList.add( new CartItemModel( 0, R.mipmap.ic_13_plus, "Iphone 13 Plus", 2, "Rs.59,999/-", "Rs.69,999/-", 2, 2, 3 ) );
        cartItemModelList.add( new CartItemModel( 0, R.mipmap.ic_14_plus, "Iphone 14 Plus", 2, "Rs.69,999/-", "Rs.79,999/-", 5, 3, 4 ) );
        cartItemModelList.add( new CartItemModel( 1, "Iphone(3 items)", "Rs.1,20,999/-", "Free", "Rs.1,20,999/-", "Rs.9999/-" ) );

        CartAdapter cartAdapter = new CartAdapter( cartItemModelList );
        deliveryRecyclerView.setAdapter( cartAdapter );
        cartAdapter.notifyDataSetChanged();

        binding.shippingContent.changOrAddAddressBtn.setVisibility( View.VISIBLE );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }
}