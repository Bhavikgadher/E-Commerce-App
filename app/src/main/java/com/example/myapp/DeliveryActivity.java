package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapp.databinding.ActivityDeliveryBinding;

import java.util.ArrayList;
import java.util.List;

public class DeliveryActivity extends AppCompatActivity {

    private ActivityDeliveryBinding binding;
    public static final int SELECT_ADDRESS =  0;
//    private RecyclerView deliveryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_delivery );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( "Delivery" );

//     deliveryRecyclerView = findViewById( R.id.rv_delivery );

        List<CartItemModel> cartItemModelList = new ArrayList<>();

        CartAdapter cartAdapter = new CartAdapter( cartItemModelList );
        binding.rvDelivery.setAdapter( cartAdapter );
        cartAdapter.notifyDataSetChanged();

        binding.shippingContent.changOrAddAddressBtn.setVisibility( View.VISIBLE );
        binding.shippingContent.changOrAddAddressBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAddressesIntent = new Intent(DeliveryActivity.this,MyAddressesActivity.class);
                myAddressesIntent.putExtra( "MODE",SELECT_ADDRESS );
                startActivity( myAddressesIntent );
            }
        } );
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