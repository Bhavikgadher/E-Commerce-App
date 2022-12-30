package com.example.myapp;

import static com.example.myapp.MainActivity.showCart;
import static com.example.myapp.utils.Constants.FB_AVERAGE_RATING;
import static com.example.myapp.utils.Constants.FB_COD;
import static com.example.myapp.utils.Constants.FB_COLLECTION_PRODUCTS;
import static com.example.myapp.utils.Constants.FB_CUTTED_PRICE;
import static com.example.myapp.utils.Constants.FB_FREE_COUPENS;
import static com.example.myapp.utils.Constants.FB_FREE_COUPEN_BODY;
import static com.example.myapp.utils.Constants.FB_NO_OF_PRODUCT_IMAGES;
import static com.example.myapp.utils.Constants.FB_PRODUCT_DESCRIPTION;
import static com.example.myapp.utils.Constants.FB_PRODUCT_IMAGE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_OTHER_DETALIS;
import static com.example.myapp.utils.Constants.FB_PRODUCT_PRICE;
import static com.example.myapp.utils.Constants.FB_PRODUCT_TITLE;
import static com.example.myapp.utils.Constants.FB_SPEC_TITLE_;
import static com.example.myapp.utils.Constants.FB_TOTAL_RATINGS;
import static com.example.myapp.utils.Constants.FB_TOTAL_SPEC_TITLES;
import static com.example.myapp.utils.Constants.FB_USE_TAB_LAYOUT;
import static com.example.myapp.utils.Constants.FB__FIELD_;
import static com.example.myapp.utils.Constants.FB__NAME;
import static com.example.myapp.utils.Constants.FB__TOTAL_FIELDS;
import static com.example.myapp.utils.Constants.FB__VALUE;
import static com.example.myapp.utils.Constants.FB__star;

import android.annotation.SuppressLint;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.databinding.ActivityProductDetailshBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class PRoductDEtailshActivity extends AppCompatActivity {
    private ActivityProductDetailshBinding binding;
    private static boolean ALREADY_ADDED_TO_WISHLIST = false;
    private ProductDetailsAdapter productDetailsAdapter;
    private ProductImagesAdapter productImagesAdapter;

    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView coupensRecyclerView;
    private static LinearLayout selectedCoupen;

    private FirebaseFirestore firebaseFirestore;
    List<String> productImages = new ArrayList<>();
    public String productDescription="";
    public String productOtherDetalis="";
    public static ArrayList<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();

    public static int tabPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_product_detailsh );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( false );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        firebaseFirestore = FirebaseFirestore.getInstance();
//        productImages.add( R.mipmap.ic_12_min );
        firebaseFirestore.collection( FB_COLLECTION_PRODUCTS ).document( "XurJQF8EMNyMbLl7AsCV" ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    for (long i = 1; i < (long) documentSnapshot.get( FB_NO_OF_PRODUCT_IMAGES ) + 1; i++) {
                        productImages.add( documentSnapshot.get( FB_PRODUCT_IMAGE_ + i ).toString() );
                    }
                    productImagesAdapter.notifyDataSetChanged();

                    binding.incContent.productTitle.setText( documentSnapshot.get( FB_PRODUCT_TITLE, String.class ) );
                    binding.incContent.tvProductRatingMiniView.setText( documentSnapshot.get( FB_AVERAGE_RATING, String.class ) );
                    binding.incContent.totalRatingsMiniView.setText( "(" + (long) documentSnapshot.get( FB_TOTAL_RATINGS ) + ")ratings" );
                    binding.incContent.productPrice.setText( "Rs." + documentSnapshot.get( FB_PRODUCT_PRICE, String.class ) + "/-" );
                    binding.incContent.cutPrice.setText( "Rs." + documentSnapshot.get( FB_CUTTED_PRICE, String.class ) + "/-" );
                    if ((boolean) documentSnapshot.get( FB_COD )) {
                        binding.incContent.codIndicatorImageView.setVisibility( View.VISIBLE );
                        binding.incContent.tvCodIndicator.setVisibility( View.VISIBLE );
                    } else {
                        binding.incContent.codIndicatorImageView.setVisibility( View.INVISIBLE );
                        binding.incContent.tvCodIndicator.setVisibility( View.INVISIBLE );
                    }
                    binding.rewardContent.tvRewardTitle.setText( (long) documentSnapshot.get( FB_FREE_COUPENS ) + documentSnapshot.get( FB_PRODUCT_TITLE, String.class ) );
                    binding.rewardContent.tvRewardBody.setText( documentSnapshot.get( FB_FREE_COUPEN_BODY, String.class ) );

                    if ((boolean) documentSnapshot.get( FB_USE_TAB_LAYOUT )) {
                        binding.productDescriptionContent.productDetalisTabsContainer.setVisibility( View.VISIBLE );
                        binding.productDetailsOnlyContent.productDetailsContainer.setVisibility( View.GONE );
                        productDescription = documentSnapshot.get( FB_PRODUCT_DESCRIPTION, String.class );
                        productOtherDetalis = documentSnapshot.get( FB_PRODUCT_OTHER_DETALIS, String.class );

                        productSpecificationModelList.clear();
                        for (long i = 1; i < (long) documentSnapshot.get( FB_TOTAL_SPEC_TITLES ) + 1; i++) {
                            productSpecificationModelList.add( new ProductSpecificationModel( 0, documentSnapshot.get( FB_SPEC_TITLE_ + i ,String.class) ) );
                            for (long b = 1; b < (long) documentSnapshot.get( FB_SPEC_TITLE_ + i + FB__TOTAL_FIELDS ) + 1; b++) {
                                productSpecificationModelList.add( new ProductSpecificationModel( 1,  documentSnapshot.get( FB_SPEC_TITLE_ + i + FB__FIELD_ + b + FB__NAME , String.class ),
                                        documentSnapshot.get( FB_SPEC_TITLE_+ i + FB__FIELD_ + b + FB__VALUE, String.class ) ) );
                            }
                        }
                        productDetailsAdapter = new ProductDetailsAdapter( PRoductDEtailshActivity.this ,productDescription,productOtherDetalis,productSpecificationModelList);
                        binding.productDescriptionContent.productDetalisViewpager.setAdapter( productDetailsAdapter );
                    } else {
                        binding.productDescriptionContent.productDetalisTabsContainer.setVisibility( View.GONE );
                        binding.productDetailsOnlyContent.productDetailsContainer.setVisibility( View.VISIBLE );
                        binding.productDetailsOnlyContent.productDetailsBody.setText( documentSnapshot.get( FB_PRODUCT_DESCRIPTION, String.class ) );
                    }

                    binding.ratingsContent.totalRatings.setText( (long) documentSnapshot.get( FB_TOTAL_RATINGS ) + "ratings" );
                    for (int i = 0; i < 5; i++) {

                        TextView rating = (TextView) binding.ratingsContent.ratingsNumbersContainer.getChildAt( i );
                        rating.setText( String.valueOf( (long) documentSnapshot.get( (5 - i) + FB__star ) ) );

                        ProgressBar progressBar = (ProgressBar) binding.ratingsContent.ratingsProgressbarContainer.getChildAt( i );
                        int maxProgress = Integer.parseInt( String.valueOf( (long) documentSnapshot.get( FB_TOTAL_RATINGS ) ) );
                        progressBar.setMax( maxProgress );
                        progressBar.setProgress( Integer.parseInt( String.valueOf( (long) documentSnapshot.get( (5 - i) + FB__star ) ) ) );
                    }
                    binding.ratingsContent.totalRatingsFigure.setText( String.valueOf( (long) documentSnapshot.get( FB_TOTAL_RATINGS ) ) );
                    binding.ratingsContent.averageRating.setText( documentSnapshot.get( FB_AVERAGE_RATING, String.class ) );
                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText( PRoductDEtailshActivity.this, error, Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        productImagesAdapter = new ProductImagesAdapter( productImages );
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


        binding.productDescriptionContent.productDetalisTablayout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabPosition = tab.getPosition();
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
