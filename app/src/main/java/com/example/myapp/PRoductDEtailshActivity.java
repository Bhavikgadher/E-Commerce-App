package com.example.myapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.myapp.databinding.ActivityProductDetailshBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class PRoductDEtailshActivity extends AppCompatActivity {
    private ActivityProductDetailshBinding binding;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;
    ProductDetailsAdapter productDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_product_detailsh );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( false );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        List<Integer> productImages = new ArrayList<>();
        productImages.add( R.mipmap.ic_12_min );
        productImages.add( R.mipmap.ic_13_pro );
        productImages.add( R.mipmap.ic_14_plus );
        productImages.add( R.mipmap.ic_13_plus );
        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter( productImages );
        binding.incContent.productImageViewpager.setAdapter( productImagesAdapter );

        new TabLayoutMediator( binding.incContent.viewpagerIndicator, binding.incContent.productImageViewpager, (tab, position) -> {
        } ).attach();

        binding.incContent.addToWishlistBtn.setOnClickListener( view -> {
            if (ALREADY_ADDED_TO_WISHLIST) {
                ALREADY_ADDED_TO_WISHLIST = false;
                binding.incContent.addToWishlistBtn.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#9e9e9e" ) ) );
            } else {
                ALREADY_ADDED_TO_WISHLIST = true;
                binding.incContent.addToWishlistBtn.setSupportImageTintList( ContextCompat.getColorStateList( this, R.color.red ) );
            }
        } );
        productDetailsAdapter = new ProductDetailsAdapter( this );
        binding.productDescriptionContent.productDetalisViewpager.setAdapter( productDetailsAdapter );
        binding.productDescriptionContent.productDetalisTablayout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.productDescriptionContent.productDetalisViewpager.setCurrentItem( tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );

        for(int x = 0 ; x<binding.ratingsContent.rateNowContainer.getChildCount();x++){
            final int starPosition = x;
            binding.ratingsContent.rateNowContainer.getChildAt( x ).setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRating(starPosition);
                }
            } );
        }
        binding.buyNowBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent(PRoductDEtailshActivity.this,DeliveryActivity.class);
                startActivity( deliveryIntent );
            }
        } );
    }

    private void setRating(int starPosition) {
        for(int x = 0; x<binding.ratingsContent.rateNowContainer.getChildCount();x++){
            ImageView starBtn = (ImageView) binding.ratingsContent.rateNowContainer.getChildAt( x );
            starBtn.setImageTintList( ColorStateList.valueOf( Color.parseColor( "#bebebe" ) ) );
            if(x<= starPosition){
                starBtn.setImageTintList( ColorStateList.valueOf( Color.parseColor( "#FBC02D" ) ) );
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.search_and_cart_icon, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            Log.e( "LOG_LOG", "main_search_icon" );
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {
            Log.e( "LOG_LOG", "main_notification_icon" );
//            if (navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment() instanceof HomeFragment) {
//                navController.navigate(R.id.action_home_to_gallery);
//            }else{
//                Log.e("LOG_LOG"," :: you are up !!");
//            }
            return true;
        } else if (id == R.id.main_cart_icon) {
            Log.e( "LOG_LOG", "main_cart_icon" );
            return true;
        }
        return super.onOptionsItemSelected( item );
    }
}
