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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_view_all );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        getSupportActionBar().setTitle( "Deals Of The Day" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        int layout_code = getIntent().getIntExtra( "layout_code", -1 );
        if (layout_code == 0) {
            binding.rvView.setVisibility( View.VISIBLE );

            List<WishlistModel> wishlistModelList = new ArrayList<>();
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_14_plus, "Iphone 14 pro", 1, "5", 145, "Rs.89,999/-", "Rs.98,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_13_pro, "Iphone 13 pro", 2, "5", 145, "Rs.79,999/-", "Rs.88,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_12_min, "Iphone 12 min", 5, "5", 145, "Rs.69,999/-", "Rs.78,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_13_plus, "Iphone 13 plus", 6, "5", 145, "Rs.59,999/-", "Rs.68,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_14_plus, "Iphone 14 pro", 1, "5", 145, "Rs.89,999/-", "Rs.98,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_13_pro, "Iphone 13 pro", 2, "5", 145, "Rs.79,999/-", "Rs.88,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_12_min, "Iphone 12 min", 5, "5", 145, "Rs.69,999/-", "Rs.78,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_13_plus, "Iphone 13 plus", 6, "5", 145, "Rs.59,999/-", "Rs.68,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_14_plus, "Iphone 14 pro", 1, "5", 145, "Rs.89,999/-", "Rs.98,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_13_pro, "Iphone 13 pro", 2, "5", 145, "Rs.79,999/-", "Rs.88,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_12_min, "Iphone 12 min", 5, "5", 145, "Rs.69,999/-", "Rs.78,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_13_plus, "Iphone 13 plus", 6, "5", 145, "Rs.59,999/-", "Rs.68,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_14_plus, "Iphone 14 pro", 1, "5", 145, "Rs.89,999/-", "Rs.98,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_13_pro, "Iphone 13 pro", 2, "5", 145, "Rs.79,999/-", "Rs.88,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_12_min, "Iphone 12 min", 5, "5", 145, "Rs.69,999/-", "Rs.78,999/-", "Case on Delivery" ) );
            wishlistModelList.add( new WishlistModel( R.mipmap.ic_13_plus, "Iphone 13 plus", 6, "5", 145, "Rs.59,999/-", "Rs.68,999/-", "Case on Delivery" ) );

            WishlistAdapter adapter = new WishlistAdapter( wishlistModelList, false );
            binding.rvView.setAdapter( adapter );
            adapter.notifyDataSetChanged();
        } else if (layout_code == 1) {
            binding.gridView.setVisibility( View.VISIBLE );

            List<HorizontalProductScrollModel> productList = new ArrayList<>();
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone x", "red", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_black_iphone_24, "iphone", "black", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_phone_iphone_24, "iphone11", "blue", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_green_iphone_24, "iphone12", "green", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone11min", "blueBlack", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_power_settings_new_24, "iphone13", "yellow", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_favorite_24, "iphone14", "1blue", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_home_24, "iphone11pro", "pink", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone11min", "blueBlack", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone x", "red", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_black_iphone_24, "iphone", "black", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_phone_iphone_24, "iphone11", "blue", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_green_iphone_24, "iphone12", "green", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone11min", "blueBlack", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_power_settings_new_24, "iphone13", "yellow", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_favorite_24, "iphone14", "1blue", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_home_24, "iphone11pro", "pink", "Rs.24000/-" ) );
//            productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone11min", "blueBlack", "Rs.24000/-" ) );
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