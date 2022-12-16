package com.example.myapp;

import static com.example.myapp.DeliveryActivity.SELECT_ADDRESS;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.myapp.databinding.ActivityMyAddressesBinding;

import java.util.ArrayList;
import java.util.List;

public class MyAddressesActivity extends AppCompatActivity {

    private ActivityMyAddressesBinding binding;
    private static AddressesAdapter addressesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_my_addresses );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        getSupportActionBar().setTitle( "My Address" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add( new AddressesModel( "Bhavik", "jamngar", "3610001", true ) );
        addressesModelList.add( new AddressesModel( "Dhinu", "Ahmedabad", "3710001", false ) );
        addressesModelList.add( new AddressesModel( "Harshil", "Rajkot", "3810001", false ) );
        addressesModelList.add( new AddressesModel( "Keval", "Mombay", "3910001", false ) );
        addressesModelList.add( new AddressesModel( "SanjuBaba", "Haveliy", "3510001", false ) );
        addressesModelList.add( new AddressesModel( "MukeshBhai", "Khavdi", "3410001", false ) );

        int mode = getIntent().getIntExtra( "MODE", -1 );
        if(mode == SELECT_ADDRESS){
            binding.deliveryHereBtn.setVisibility( View.VISIBLE );
        }else {
            binding.deliveryHereBtn.setVisibility( View.GONE );
        }
        addressesAdapter = new AddressesAdapter( addressesModelList, mode );
        binding.rvAddresses.setAdapter( addressesAdapter );
        ((SimpleItemAnimator) binding.rvAddresses.getItemAnimator()).setSupportsChangeAnimations( false );
        addressesAdapter.notifyDataSetChanged();

    }

    public static void refreshItem(int deSelect, int select) {

        addressesAdapter.notifyItemChanged( deSelect );

        addressesAdapter.notifyItemChanged( select );

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }
}