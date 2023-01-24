package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapp.databinding.ActivityDeliveryBinding;

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

        CartAdapter cartAdapter = new CartAdapter( DBqueries.cartItemModelList,binding.totalCartAmount,false);
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
    protected void onStart() {
        super.onStart();
        binding.shippingContent.fullName.setText( DBqueries.addressesModelList.get( DBqueries.selectedAddress ).getFullname() );
        binding.shippingContent.address.setText( DBqueries.addressesModelList.get( DBqueries.selectedAddress ).getAddress() );
        binding.shippingContent.pincode.setText( DBqueries.addressesModelList.get( DBqueries.selectedAddress ).getPincode() );
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