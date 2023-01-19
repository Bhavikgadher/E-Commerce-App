package com.example.myapp;

import static com.example.myapp.MainActivity.showCart;
import static com.example.myapp.RegisterActivity.setSignUpFragment;
import static com.example.myapp.utils.Constants.FB_AVERAGE_RATING;
import static com.example.myapp.utils.Constants.FB_COD;
import static com.example.myapp.utils.Constants.FB_COLLECTION_PRODUCTS;
import static com.example.myapp.utils.Constants.FB_CUTTED_PRICE;
import static com.example.myapp.utils.Constants.FB_FREE_COUPENS;
import static com.example.myapp.utils.Constants.FB_FREE_COUPEN_BODY;
import static com.example.myapp.utils.Constants.FB_LIST_SIZE;
import static com.example.myapp.utils.Constants.FB_MY_RATINGS;
import static com.example.myapp.utils.Constants.FB_MY_WISHLIST;
import static com.example.myapp.utils.Constants.FB_NO_OF_PRODUCT_IMAGES;
import static com.example.myapp.utils.Constants.FB_PRODUCT_DESCRIPTION;
import static com.example.myapp.utils.Constants.FB_PRODUCT_ID;
import static com.example.myapp.utils.Constants.FB_PRODUCT_ID_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_IMAGE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_OTHER_DETALIS;
import static com.example.myapp.utils.Constants.FB_PRODUCT_PRICE;
import static com.example.myapp.utils.Constants.FB_PRODUCT_TITLE;
import static com.example.myapp.utils.Constants.FB_RATING_;
import static com.example.myapp.utils.Constants.FB_SPEC_TITLE_;
import static com.example.myapp.utils.Constants.FB_TOTAL_RATINGS;
import static com.example.myapp.utils.Constants.FB_TOTAL_SPEC_TITLES;
import static com.example.myapp.utils.Constants.FB_USERS;
import static com.example.myapp.utils.Constants.FB_USER_DATA;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PRoductDEtailshActivity extends AppCompatActivity {
    public static ActivityProductDetailshBinding binding;
    public static boolean ALREADY_ADDED_TO_WISHLIST = false;
    private ProductDetailsAdapter productDetailsAdapter;
    private ProductImagesAdapter productImagesAdapter;

    public static boolean running_wishlist_query = false;
    public static boolean running_rating_query = false;

    public static TextView coupenTitle;
    public static TextView coupenExpiryDate;
    public static TextView coupenBody;
    private static RecyclerView coupensRecyclerView;
    private static LinearLayout selectedCoupen;

    public static int initialRating;

    private FirebaseFirestore firebaseFirestore;
    List<String> productImages = new ArrayList<>();
    public String productDescription = "";
    public String productOtherDetalis = "";
    public static ArrayList<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();
    public static int tabPosition = -1;
    private Dialog loadingDialog;
    private Dialog signInDialog;
    private FirebaseUser currentUser;
    public static String productId;
    private DocumentSnapshot documentSnapshot;
    public static FloatingActionButton addToWishListBtn;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_product_detailsh );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( false );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        firebaseFirestore = FirebaseFirestore.getInstance();
        productId = getIntent().getStringExtra( FB_PRODUCT_ID );
        addToWishListBtn = findViewById( R.id.add_to_wishlist_btn );

        initialRating = -1;

        //// loading dialog
        loadingDialog = new Dialog( PRoductDEtailshActivity.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialog );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setBackgroundDrawable( getDrawable( R.drawable.slider_background ) );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();
        //// loading dialog

//        productImages.add( R.mipmap.ic_12_min );
        firebaseFirestore.collection( FB_COLLECTION_PRODUCTS ).document( productId ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    documentSnapshot = task.getResult();
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
                            productSpecificationModelList.add( new ProductSpecificationModel( 0, documentSnapshot.get( FB_SPEC_TITLE_ + i, String.class ) ) );
                            for (long b = 1; b < (long) documentSnapshot.get( FB_SPEC_TITLE_ + i + FB__TOTAL_FIELDS ) + 1; b++) {
                                productSpecificationModelList.add( new ProductSpecificationModel( 1, documentSnapshot.get( FB_SPEC_TITLE_ + i + FB__FIELD_ + b + FB__NAME, String.class ),
                                        documentSnapshot.get( FB_SPEC_TITLE_ + i + FB__FIELD_ + b + FB__VALUE, String.class ) ) );
                            }
                        }
                        productDetailsAdapter = new ProductDetailsAdapter( PRoductDEtailshActivity.this, productDescription, productOtherDetalis, productSpecificationModelList );
                        binding.productDescriptionContent.productDetalisViewpager.setAdapter( productDetailsAdapter );
                    } else {
                        binding.productDescriptionContent.productDetalisTabsContainer.setVisibility( View.GONE );
                        binding.productDetailsOnlyContent.productDetailsContainer.setVisibility( View.VISIBLE );
                        binding.productDetailsOnlyContent.productDetailsBody.setText( documentSnapshot.get( FB_PRODUCT_DESCRIPTION, String.class ) );
                    }

                    binding.ratingsContent.totalRatings.setText( (long) documentSnapshot.get( FB_TOTAL_RATINGS ) + "  ratings" );
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

                    if (currentUser != null) {
                        if (DBqueries.myRating.size() == 0) {
                            DBqueries.loadRatingList( PRoductDEtailshActivity.this );
                        }
                        if (DBqueries.wishList.size() == 0) {
                            DBqueries.loadWishlist( PRoductDEtailshActivity.this, loadingDialog, false );
                        } else {
                            loadingDialog.dismiss();
                        }
                    } else {
                        loadingDialog.dismiss();
                    }

                    if (DBqueries.myRatedIds.contains( productId )) {
                        int index = DBqueries.myRatedIds.indexOf( productId );
                        initialRating = Integer.parseInt( String.valueOf( DBqueries.myRating.get( index ) ) ) - 1;
                        setRating( initialRating );
                    }
                    if (DBqueries.wishList.contains( productId )) {
                        ALREADY_ADDED_TO_WISHLIST = true;
                        binding.incContent.addToWishlistBtn.setSupportImageTintList( ContextCompat.getColorStateList( PRoductDEtailshActivity.this, R.color.red ) );
                    } else {
                        binding.incContent.addToWishlistBtn.setSupportImageTintList( ContextCompat.getColorStateList( PRoductDEtailshActivity.this, R.color.gray ) );
                        ALREADY_ADDED_TO_WISHLIST = false;
                    }
                } else {
                    loadingDialog.dismiss();
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
            if (currentUser == null) {
                signInDialog.show();
            } else {
//                addToWishListBtn.setEnabled( false );
                if (!running_wishlist_query) {
                    running_wishlist_query = true;
                    if (ALREADY_ADDED_TO_WISHLIST) {
                        int index = DBqueries.wishList.indexOf( productId );
                        if (index == -1) {
                            Toast.makeText( this, "Product not found with id : " + productId, Toast.LENGTH_SHORT ).show();
                        } else {
                            DBqueries.removeFromWishlist( index, PRoductDEtailshActivity.this );
                            binding.incContent.addToWishlistBtn.setSupportImageTintList( ContextCompat.getColorStateList( this, R.color.gray ) );
                        }
                    } else {
                        binding.incContent.addToWishlistBtn.setSupportImageTintList( ContextCompat.getColorStateList( PRoductDEtailshActivity.this, R.color.red ) );
                        Map<String, Object> addProduct = new HashMap<>();
                        addProduct.put( FB_PRODUCT_ID_ + String.valueOf( DBqueries.wishList.size() ), productId );

                        firebaseFirestore.collection( FB_USERS ).document( currentUser.getUid() ).collection( FB_USER_DATA ).document( FB_MY_WISHLIST ).update( addProduct ).addOnCompleteListener( task -> {
                            if (task.isSuccessful()) {
                                Map<String, Object> updateListSize = new HashMap<>();
                                updateListSize.put( (String) FB_LIST_SIZE, (long) (DBqueries.wishList.size() + 1) );
                                firebaseFirestore.collection( FB_USERS ).document( currentUser.getUid() ).collection( FB_USER_DATA ).document( FB_MY_WISHLIST ).update( updateListSize ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            if (DBqueries.wishlistModelList.size() != 0) {
                                                DBqueries.wishlistModelList.add(
                                                        new WishlistModel(
                                                                productId, documentSnapshot.get( "product_image_1", String.class ),
                                                                documentSnapshot.get( "product_title_1", String.class ),
                                                                documentSnapshot.get( FB_FREE_COUPENS, Long.class ),
                                                                documentSnapshot.get( FB_AVERAGE_RATING, String.class ),
                                                                documentSnapshot.get( FB_TOTAL_RATINGS, Long.class ),
                                                                documentSnapshot.get( FB_PRODUCT_PRICE, String.class ),
                                                                documentSnapshot.get( FB_CUTTED_PRICE, String.class ),
                                                                documentSnapshot.get( FB_COD ), Boolean.class ) );
                                            }

                                            ALREADY_ADDED_TO_WISHLIST = true;
                                            binding.incContent.addToWishlistBtn.setSupportImageTintList( ContextCompat.getColorStateList( PRoductDEtailshActivity.this, R.color.red ) );
                                            DBqueries.wishList.add( productId );
                                            Toast.makeText( PRoductDEtailshActivity.this, "Added to Wishlist Successfully!", Toast.LENGTH_SHORT ).show();
                                        } else {
                                            binding.incContent.addToWishlistBtn.setSupportImageTintList( ColorStateList.valueOf( Integer.parseInt( "#9e9e9e" ) ) );
                                            String error = task.getException().getMessage();
                                            Toast.makeText( PRoductDEtailshActivity.this, error, Toast.LENGTH_SHORT ).show();
                                        }
                                        running_wishlist_query = false;
                                    }
                                } );
                            } else {
                                running_wishlist_query = false;
                                String error = task.getException().getMessage();
                                Toast.makeText( PRoductDEtailshActivity.this, error, Toast.LENGTH_SHORT ).show();
                            }
                        } );
                    }
                }
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


        /// rating layout

        for (int x = 0; x < binding.ratingsContent.rateNowContainer.getChildCount(); x++) {
            final int starPosition = x;
            binding.ratingsContent.rateNowContainer.getChildAt( x ).setOnClickListener( view -> {
                if (currentUser == null) {
                    signInDialog.show();
                } else {
                    if (starPosition != initialRating){
                        if (!running_rating_query) {
                            running_rating_query = true;

                            setRating( starPosition );
                            Map<String, Object> updateRating = new HashMap<>();
                            if (DBqueries.myRatedIds.contains( productId )) {
                                TextView oldRating = (TextView) binding.ratingsContent.ratingsNumbersContainer.getChildAt( 5 - initialRating - 1 );
                                TextView finalRating = (TextView) binding.ratingsContent.ratingsNumbersContainer.getChildAt( 5 - starPosition - 1 );

                                updateRating.put( initialRating + 1 + FB__star, Long.parseLong( (String) oldRating.getText() ) - 1 );
                                updateRating.put( starPosition + 1 + FB__star, Long.parseLong( (String) finalRating.getText() ) + 1 );
                                updateRating.put( FB_AVERAGE_RATING, calculateAverageRating( (long) starPosition - initialRating, true ) );
                            } else {
                                updateRating.put( starPosition + 1 + FB__star, (long) documentSnapshot.get( starPosition + 1 + FB__star ) + 1 );
                                updateRating.put( FB_AVERAGE_RATING, calculateAverageRating( (long) starPosition + 1, false ) );
                                updateRating.put( FB_TOTAL_RATINGS, (long) documentSnapshot.get( FB_TOTAL_RATINGS ) + 1 );
                            }
                            firebaseFirestore.collection( FB_COLLECTION_PRODUCTS ).document( productId ).
                                    update( updateRating ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Map<String, Object> myRating = new HashMap<>();
                                                if (DBqueries.myRatedIds.contains( productId )) {
                                                    myRating.put( FB_RATING_ + DBqueries.myRatedIds.indexOf( productId ), (long) starPosition + 1 );
                                                } else {
                                                    myRating.put( FB_LIST_SIZE, (long) DBqueries.myRatedIds.size() + 1 );
                                                    myRating.put( FB_PRODUCT_ID_ + DBqueries.myRatedIds.size(), productId );
                                                    myRating.put( FB_RATING_ + DBqueries.myRatedIds.size(), (long) starPosition + 1 );
                                                }
                                                firebaseFirestore.collection( FB_USERS ).document( currentUser.getUid() ).collection( FB_USER_DATA ).document( FB_MY_RATINGS )
                                                        .update( myRating ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {

                                                                    if (DBqueries.myRatedIds.contains( productId )) {

                                                                        DBqueries.myRating.set( DBqueries.myRatedIds.indexOf( productId ), (long) starPosition + 1 );

                                                                        TextView oldRating = (TextView) binding.ratingsContent.ratingsNumbersContainer.getChildAt( 5 - initialRating - 1 );
                                                                        TextView finalRating = (TextView) binding.ratingsContent.ratingsNumbersContainer.getChildAt( 5 - starPosition - 1 );
                                                                        oldRating.setText( String.valueOf( Integer.parseInt( oldRating.getText().toString() ) - 1 ) );
                                                                        finalRating.setText( String.valueOf( Integer.parseInt( finalRating.getText().toString() ) + 1 ) );

                                                                    } else {
                                                                        DBqueries.myRatedIds.add( productId );
                                                                        DBqueries.myRating.add( (long) (starPosition + 1) );
                                                                        TextView rating = (TextView) binding.ratingsContent.ratingsNumbersContainer.getChildAt( 5 - starPosition - 1 );
                                                                        rating.setText( String.valueOf( Integer.parseInt( rating.getText().toString() ) + 1 ) );

                                                                        binding.incContent.totalRatingsMiniView.setText( "(" + ((long) documentSnapshot.get( FB_TOTAL_RATINGS ) + 1) + ")ratings" );
                                                                        binding.ratingsContent.totalRatings.setText( (long) documentSnapshot.get( FB_TOTAL_RATINGS ) + 1 + " ratings" );
                                                                        binding.ratingsContent.totalRatingsFigure.setText( String.valueOf( (long) documentSnapshot.get( FB_TOTAL_RATINGS ) + 1 ) );
                                                                        Toast.makeText( PRoductDEtailshActivity.this, "Thank you for rating .", Toast.LENGTH_SHORT ).show();
                                                                    }
                                                                    for (int i = 0; i < 5; i++) {

                                                                        TextView ratingFigures = (TextView) binding.ratingsContent.ratingsNumbersContainer.getChildAt( i );

                                                                        ProgressBar progressBar = (ProgressBar) binding.ratingsContent.ratingsProgressbarContainer.getChildAt( i );
                                                                        int maxProgress = Integer.parseInt( binding.ratingsContent.totalRatingsFigure.getText().toString() );
                                                                        progressBar.setMax( maxProgress );
                                                                        progressBar.setProgress( Integer.parseInt( ratingFigures.getText().toString() ) );
                                                                    }
                                                                    initialRating = starPosition;
                                                                    binding.ratingsContent.averageRating.setText( calculateAverageRating( 0L, true ) );
                                                                    binding.incContent.tvProductRatingMiniView.setText( calculateAverageRating( 0L, true ) );

                                                                    if (DBqueries.wishList.contains( productId ) && DBqueries.wishlistModelList.size() != 0) {
                                                                        int index = DBqueries.wishList.indexOf( productId );
                                                                        DBqueries.wishlistModelList.get( index ).setRating( binding.ratingsContent.averageRating.getText().toString() );
                                                                        DBqueries.wishlistModelList.get( index ).setTotalRating( Long.parseLong( binding.ratingsContent.totalRatingsFigure.getText().toString() ) );

                                                                    }

                                                                } else {
                                                                    setRating( initialRating );
                                                                    String error = task.getException().getMessage();
                                                                    Toast.makeText( PRoductDEtailshActivity.this, error, Toast.LENGTH_SHORT ).show();
                                                                }
                                                                running_rating_query = false;
                                                            }
                                                        } );
                                            } else {
                                                running_rating_query = false;
                                                setRating( initialRating );
                                                String error = task.getException().getMessage();
                                                Toast.makeText( PRoductDEtailshActivity.this, error, Toast.LENGTH_SHORT ).show();
                                            }
                                        }
                                    } );
                        }
                    }
                }

            } );
        }

        /// rating layout


        binding.buyNowBtn.setOnClickListener( view -> {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent deliveryIntent = new Intent( PRoductDEtailshActivity.this, DeliveryActivity.class );
                startActivity( deliveryIntent );
            }
        } );

        binding.addToCartBtn.setOnClickListener( view -> {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                /////
            }
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

        toggleRecyclerView.setOnClickListener( view -> showDialogRecyclerView() );
        //coupen Dialog
        binding.incContent.coupenRedemptionBtn.setOnClickListener( view -> checkCoupenpriceDialog.show() );
        //// sign Dialog
        signInDialog = new Dialog( PRoductDEtailshActivity.this );
        signInDialog.setContentView( R.layout.sign_in_dialog );
        signInDialog.setCancelable( true );
        signInDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );

        Button dialogSignInBtn = signInDialog.findViewById( R.id.sign_In_dialog_btn );
        Button dialogSignUpBtn = signInDialog.findViewById( R.id.sign_up_dialog_btn );

        Intent registerIntent = new Intent( PRoductDEtailshActivity.this, RegisterActivity.class );
        dialogSignInBtn.setOnClickListener( view -> {
            SingInFragment.disableCloseBtn = true;
            SingUpFragment.disableCloseBtn = true;
            signInDialog.dismiss();
            setSignUpFragment = false;
            startActivity( registerIntent );
        } );
        dialogSignUpBtn.setOnClickListener( view -> {
            SingInFragment.disableCloseBtn = true;
            SingUpFragment.disableCloseBtn = true;
            signInDialog.dismiss();
            setSignUpFragment = true;
            startActivity( registerIntent );
        } );

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            binding.incContent.coupenRedemptionLayout.setVisibility( View.GONE );
        } else {
            binding.incContent.coupenRedemptionLayout.setVisibility( View.VISIBLE );
        }
        if (currentUser != null) {
            if (DBqueries.myRating.size() == 0) {
                DBqueries.loadRatingList( PRoductDEtailshActivity.this );
            }
            if (DBqueries.wishList.size() == 0) {
                DBqueries.loadWishlist( PRoductDEtailshActivity.this, loadingDialog, false );
            } else {
                loadingDialog.dismiss();
            }
        } else {
            loadingDialog.dismiss();
        }

        if (DBqueries.myRatedIds.contains( productId )) {
            int index = DBqueries.myRatedIds.indexOf( productId );
            initialRating = Integer.parseInt( String.valueOf( DBqueries.myRating.get( index ) ) ) - 1;
            setRating( initialRating );
        }

        if (DBqueries.wishList.contains( productId )) {
            ALREADY_ADDED_TO_WISHLIST = true;
            binding.incContent.addToWishlistBtn.setSupportImageTintList( ContextCompat.getColorStateList( PRoductDEtailshActivity.this, R.color.red ) );
        } else {
            binding.incContent.addToWishlistBtn.setSupportImageTintList( ContextCompat.getColorStateList( this, R.color.gray ) );
            ALREADY_ADDED_TO_WISHLIST = false;
        }
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

    public static void setRating(int starPosition) {
        for (int x = 0; x < binding.ratingsContent.rateNowContainer.getChildCount(); x++) {
            ImageView starBtn = (ImageView) binding.ratingsContent.rateNowContainer.getChildAt( x );
            starBtn.setImageTintList( ColorStateList.valueOf( Color.parseColor( "#bebebe" ) ) );
            if (x <= starPosition) {
                starBtn.setImageTintList( ColorStateList.valueOf( Color.parseColor( "#FBC02D" ) ) );
            }
        }
    }

    private String calculateAverageRating(Long currentUserRating, boolean update) {
        Double totalStars = Double.valueOf( 0 );
        for (int i = 1; i < 6; i++) {
            TextView ratingNo = (TextView) binding.ratingsContent.ratingsNumbersContainer.getChildAt( 5 - i );
            totalStars = totalStars + (Long.parseLong( ratingNo.getText().toString() ) * i);
        }
        totalStars = totalStars + currentUserRating;
        if (update) {
            return String.valueOf( totalStars / Long.parseLong( binding.ratingsContent.totalRatingsFigure.getText().toString() ) ).substring( 0, 3 );
        } else {
            return String.valueOf( totalStars / Long.parseLong( (binding.ratingsContent.totalRatingsFigure.getText().toString()) + 1 ) ).substring( 0, 3 );
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
            finish();
            return true;
        } else if (id == R.id.main_search_icon) {

//            if (navHostFragment.getChildFragmentManager().getPrimaryNavigationFragment() instanceof HomeFragment) {
//                navController.navigate(R.id.action_home_to_gallery);
//            }else{
//                Log.e("LOG_LOG"," :: you are up !!");
//            }
            return true;
        } else if (id == R.id.main_cart_icon) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                Intent cartIntent = new Intent( PRoductDEtailshActivity.this, MainActivity.class );
                showCart = true;
                startActivity( cartIntent );
                return true;
            }
        }
        return super.onOptionsItemSelected( item );
    }
}
