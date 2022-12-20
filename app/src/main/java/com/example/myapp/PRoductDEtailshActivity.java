package com.example.myapp;

import static com.example.myapp.MainActivity.showCart;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.databinding.ActivityProductDetailshBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class PRoductDEtailshActivity extends AppCompatActivity {
    private ActivityProductDetailshBinding binding;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;
    ProductDetailsAdapter productDetailsAdapter;

    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView coupensRecyclerView;
    private static LinearLayout selectedCoupen;


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
                binding.productDescriptionContent.productDetalisViewpager.setCurrentItem( tab.getPosition() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );

        for (int x = 0; x < binding.ratingsContent.rateNowContainer.getChildCount(); x++) {
            final int starPosition = x;
            binding.ratingsContent.rateNowContainer.getChildAt( x ).setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setRating( starPosition );
                }
            } );
        }
        binding.buyNowBtn.setOnClickListener( view -> {
            Intent deliveryIntent = new Intent( PRoductDEtailshActivity.this, DeliveryActivity.class );
            startActivity( deliveryIntent );
        } );

        //coupen Dialog
        Dialog checkCoupenpriceDialog = new Dialog( PRoductDEtailshActivity.this );
        checkCoupenpriceDialog.setContentView( R.layout.coupen_redeem_dialog );
        checkCoupenpriceDialog.setCancelable( true );
        checkCoupenpriceDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );

        ImageView toggleRecyclerView = checkCoupenpriceDialog.findViewById( R.id.toggle_coupen );
        coupensRecyclerView = checkCoupenpriceDialog.findViewById( R.id.rv_coupns );
        selectedCoupen = checkCoupenpriceDialog.findViewById( R.id.selected_coupen );
        coupenTitle = checkCoupenpriceDialog.findViewById( R.id.coupen_title );
        coupenExpiryDate = checkCoupenpriceDialog.findViewById( R.id.coupen_validity );
        coupenBody = checkCoupenpriceDialog.findViewById( R.id.coupen_body );
        TextView originalPrice = checkCoupenpriceDialog.findViewById( R.id.original_price );
        TextView discountedPrice = checkCoupenpriceDialog.findViewById( R.id.discounted_price );

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add( new RewardModel( "CashBack", "till 2nd june 2023", "GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "Discount", "till 2nd june 2023", "GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "BUY 1 Get 2 Free ", "till 2nd june 2023", "GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "CashBack", "till 2nd june 2023", "GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "Discount", "till 2nd june 2023", "GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "BUY 1 Get 2 Free ", "till 2nd june 2023", "GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "CashBack", "till 2nd june 2023", "GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "Discount", "till 2nd june 2023", "GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );
        rewardModelList.add( new RewardModel( "BUY 1 Get 2 Free ", "till 2nd june 2023", "GET 20% CASHBACK on any product above Rs.200/- and below Rs.3000/-." ) );

        MyRewardAdapter myRewardAdapter = new MyRewardAdapter( rewardModelList, true );
        coupensRecyclerView.setAdapter( myRewardAdapter );
        myRewardAdapter.notifyDataSetChanged();

        toggleRecyclerView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogRecyclerView();
            }
        } );
        //coupen Dialog
        binding.incContent.coupenRedemptionBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkCoupenpriceDialog.show();
            }
        } );
    }

    public static void showDialogRecyclerView() {
        if (coupensRecyclerView.getVisibility() == View.GONE) {
            coupensRecyclerView.setVisibility( View.VISIBLE );
            selectedCoupen.setVisibility( View.GONE );
        } else {
            coupensRecyclerView.setVisibility( View.GONE );
            selectedCoupen.setVisibility( View.VISIBLE );
        }
    }

    private void setRating(int starPosition) {
        for (int x = 0; x < binding.ratingsContent.rateNowContainer.getChildCount(); x++) {
            ImageView starBtn = (ImageView) binding.ratingsContent.rateNowContainer.getChildAt( x );
            starBtn.setImageTintList( ColorStateList.valueOf( Color.parseColor( "#bebebe" ) ) );
            if (x <= starPosition) {
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
            Intent cartIntent = new Intent( PRoductDEtailshActivity.this, MainActivity.class );
            showCart = true;
            startActivity( cartIntent );

            return true;
        }
        return super.onOptionsItemSelected( item );
    }
}
