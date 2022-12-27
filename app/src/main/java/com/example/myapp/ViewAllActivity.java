package com.example.myapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapp.databinding.ActivityViewAllBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewAllActivity extends AppCompatActivity {

    private ActivityViewAllBinding binding;
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();
    public static List<HorizontalProductScrollModel> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_view_all );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        getSupportActionBar().setTitle( getIntent().getStringExtra( "title" ) );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        int layout_code = getIntent().getIntExtra( "layout_code", -1 );
        if (layout_code == 0) {
            binding.rvView.setVisibility( View.VISIBLE );

//            List<WishlistModel> wishlistModelList = new ArrayList<>();
//            wishlistModelList.add( new WishlistModel( R.mipmap.ic_14_plus, "Iphone 14 pro", 1, "5", 145, "Rs.89,999/-", "Rs.98,999/-", "Case on Delivery" ) );
            WishlistAdapter adapter = new WishlistAdapter( wishlistModelList, false );
            binding.rvView.setAdapter( adapter );
            adapter.notifyDataSetChanged();
        } else if (layout_code == 1) {
            binding.gridView.setVisibility( View.VISIBLE );

//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone x", "red", "Rs.24000/-" ) );
            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter( productList );
            binding.gridView.setAdapter( gridProductLayoutAdapter );
            gridProductLayoutAdapter.notifyDataSetChanged();
        }
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