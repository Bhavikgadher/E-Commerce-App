package com.example.myapp;

import static com.example.myapp.PRoductDEtailshActivity.addToWishListBtn;
import static com.example.myapp.PRoductDEtailshActivity.initialRating;
import static com.example.myapp.PRoductDEtailshActivity.productId;
import static com.example.myapp.PRoductDEtailshActivity.setRating;
import static com.example.myapp.utils.Constants.FB_ADDRESS_;
import static com.example.myapp.utils.Constants.FB_AVERAGE_RATING;
import static com.example.myapp.utils.Constants.FB_AVERAGE_RATING_;
import static com.example.myapp.utils.Constants.FB_BACKGROUND;
import static com.example.myapp.utils.Constants.FB_COD;
import static com.example.myapp.utils.Constants.FB_COD_;
import static com.example.myapp.utils.Constants.FB_COLLECTION_CATEGORIES;
import static com.example.myapp.utils.Constants.FB_COLLECTION_PRODUCTS;
import static com.example.myapp.utils.Constants.FB_CUTTED_PRICE;
import static com.example.myapp.utils.Constants.FB_CUTTED_PRICE_;
import static com.example.myapp.utils.Constants.FB_FREE_COUPENS;
import static com.example.myapp.utils.Constants.FB_FREE_COUPENS_;
import static com.example.myapp.utils.Constants.FB_FULLNAME_;
import static com.example.myapp.utils.Constants.FB_IN_STOCK;
import static com.example.myapp.utils.Constants.FB_LIST_SIZE;
import static com.example.myapp.utils.Constants.FB_MY_ADDRESSES;
import static com.example.myapp.utils.Constants.FB_MY_CART;
import static com.example.myapp.utils.Constants.FB_MY_RATINGS;
import static com.example.myapp.utils.Constants.FB_MY_WISHLIST;
import static com.example.myapp.utils.Constants.FB_NO_OF_BANNERS;
import static com.example.myapp.utils.Constants.FB_NO_OF_PRODUCTS;
import static com.example.myapp.utils.Constants.FB_PINCODE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_FULL_TITLE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_ID_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_IMAGE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_IMAGE_Q;
import static com.example.myapp.utils.Constants.FB_PRODUCT_PRICE;
import static com.example.myapp.utils.Constants.FB_PRODUCT_PRICE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_SUBTITLE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_TITLE;
import static com.example.myapp.utils.Constants.FB_PRODUCT_TITLE_;
import static com.example.myapp.utils.Constants.FB_RATING_;
import static com.example.myapp.utils.Constants.FB_SELECTED_;
import static com.example.myapp.utils.Constants.FB_STRIP_AD_BANNER;
import static com.example.myapp.utils.Constants.FB_SUB_COLLECTION_TD;
import static com.example.myapp.utils.Constants.FB_TOTAL_RATINGS;
import static com.example.myapp.utils.Constants.FB_TOTAL_RATINGS_;
import static com.example.myapp.utils.Constants.FB_USERS;
import static com.example.myapp.utils.Constants.FB_USER_DATA;
import static com.example.myapp.utils.Constants.FB_VIEW_TYPE;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DBqueries {

    public static boolean addressesSelected = false;

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public static List<CategoryModel> categoryModelList = new ArrayList<>();

    public static List<List<HomePageModel>> lists = new ArrayList<>();
    public static List<String> loadedCategoriesNames = new ArrayList<>();

    public static List<String> wishList = new ArrayList<>();
    public static List<WishlistModel> wishlistModelList = new ArrayList<>();

    public static List<String> cartList = new ArrayList<>();
    public static List<CartItemModel> cartItemModelList = new ArrayList<>();

    public static int selectedAddress = -1;
    public static List<AddressesModel> addressesModelList = new ArrayList<>();


    public static List<String> myRatedIds = new ArrayList<>();
    public static List<Long> myRating = new ArrayList<>();


    public static void loadCategories(RecyclerView categoryRecyclerView, Context context) {
        categoryModelList.clear();
        firebaseFirestore.collection( FB_COLLECTION_CATEGORIES ).orderBy( "index" ).get().addOnCompleteListener( task ->
        {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    categoryModelList.add( new CategoryModel( documentSnapshot.get( "categoryName" ).toString(),
                            documentSnapshot.get( "icon" ).toString() ) );
                }
            } else {
                String error = task.getException().getMessage();
                Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
            }
            CategoryAdapter categoryAdapter = new CategoryAdapter( (ArrayList<CategoryModel>) categoryModelList );
            categoryRecyclerView.setAdapter( categoryAdapter );
            categoryAdapter.setData( (ArrayList<CategoryModel>) categoryModelList );
        } );
    }

    public static void loadFragmentData(RecyclerView homepageRecyclerView, Context context, int index, String categoryName) {
        DocumentReference homeDR = firebaseFirestore.collection( FB_COLLECTION_CATEGORIES ).document( categoryName.toUpperCase() );
        homeDR.collection( FB_SUB_COLLECTION_TD ).orderBy( "index" ).get().addOnCompleteListener( task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    //get view type from firestore to inflate view accordingly and if value return null then show empty screen
                    Long viewType = documentSnapshot.get( FB_VIEW_TYPE, Long.class );
                    if (viewType != null) {
                        //if view type is 0 then inflate view for banners
                        if (viewType == 0) {
                            Long noOfBanners = documentSnapshot.get( FB_NO_OF_BANNERS, Long.class );
                            if (noOfBanners != null) {
                                List<SliderModel> sliderModelList = new ArrayList<>();
                                for (int i = 1; i <= noOfBanners; i++) {
                                    String banner = documentSnapshot.get( String.format( context.getString( R.string.banner_id ), i ), String.class );
                                    String bannerBackground = documentSnapshot.get( String.format( context.getString( R.string.banner_id_background ), i ), String.class );
                                    if (banner != null && bannerBackground != null) {
                                        sliderModelList.add( new SliderModel( banner, bannerBackground ) );
                                    }
                                }
                                lists.get( index ).add( new HomePageModel( 0, sliderModelList ) );
                            }
                        }
                        //if view type is 1 then inflate view for strip ad banners
                        else if (viewType == 1) {
                            String stripAdBanner = documentSnapshot.get( FB_STRIP_AD_BANNER, String.class );
                            String background = documentSnapshot.get( FB_BACKGROUND, String.class );
                            if (stripAdBanner != null && background != null) {
                                lists.get( index ).add( new HomePageModel( 1, stripAdBanner, background ) );
                            }
                        }
                        //if view type is 2 then inflate horizontal list view for products
                        else if (viewType == 2) {
                            List<WishlistModel> viewAllProductList = new ArrayList<>();
                            List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                            Long noOfProducts = documentSnapshot.get( FB_NO_OF_PRODUCTS, Long.class );
                            for (long i = 1; i <= noOfProducts; i++) {
                                horizontalProductScrollModelList.add(
                                        new HorizontalProductScrollModel(
                                                documentSnapshot.get( FB_PRODUCT_ID_ + i, String.class ),
                                                documentSnapshot.get( FB_PRODUCT_IMAGE_ + i, String.class ),
                                                documentSnapshot.get( FB_PRODUCT_TITLE_ + i, String.class ),
                                                documentSnapshot.get( FB_PRODUCT_SUBTITLE_ + i, String.class ),
                                                documentSnapshot.get( FB_PRODUCT_PRICE_ + i, String.class ) )
                                );
                                viewAllProductList.add(
                                        new WishlistModel(
                                                documentSnapshot.get( FB_PRODUCT_ID_ + i, String.class ),
                                                documentSnapshot.get( FB_PRODUCT_IMAGE_Q + i, String.class ),
                                                documentSnapshot.get( FB_PRODUCT_FULL_TITLE_ + i, String.class ),
                                                documentSnapshot.get( FB_FREE_COUPENS_ + i, Long.class ),
                                                documentSnapshot.get( FB_AVERAGE_RATING_ + i, String.class ),
                                                documentSnapshot.get( FB_TOTAL_RATINGS_ + i, Long.class ),
                                                documentSnapshot.get( FB_PRODUCT_PRICE_ + i, String.class ),
                                                documentSnapshot.get( FB_CUTTED_PRICE_ + i, String.class ),
                                                documentSnapshot.get( FB_COD_ + i ), Boolean.class ) );
                            }
                            lists.get( index ).add( new HomePageModel( 2, documentSnapshot.get( "layout_title" ).toString(),
                                    documentSnapshot.get( "layout_background" ).toString(), horizontalProductScrollModelList, viewAllProductList ) );
                        }
                        //if view type is 3 then inflate grid list view for products
                        else if (viewType == 3) {
                            List<HorizontalProductScrollModel> GridLayoutModelList = new ArrayList<>();
                            Long noOfProducts = documentSnapshot.get( FB_NO_OF_PRODUCTS, Long.class );
                            for (long i = 1; i <= noOfProducts; i++) {
                                GridLayoutModelList.add(
                                        new HorizontalProductScrollModel( documentSnapshot.get( "product_ID_" + i ).toString(),
                                                documentSnapshot.get( "product_image_" + i ).toString(),
                                                documentSnapshot.get( "product_title_" + i ).toString(),
                                                documentSnapshot.get( "product_subtitle_" + i ).toString(),
                                                documentSnapshot.get( "product_price_" + i ).toString() )
                                );
                            }
                            lists.get( index ).add( new HomePageModel( 3, documentSnapshot.get( "layout_title" ).toString(),
                                    documentSnapshot.get( "background" ).toString(), GridLayoutModelList ) );
                        }
                    }
                }
                HomePageAdapter homePageAdapter = new HomePageAdapter( lists.get( index ) );
                homepageRecyclerView.setAdapter( homePageAdapter );
                homePageAdapter.setData( lists.get( index ) );
                HomeFragment.swipeRefreshLayout.setRefreshing( false );
            } else {
                String error = task.getException().getMessage();
                Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
            }
        } );

    }

    public static void loadWishlist(Context context, Dialog dialog, boolean loadProductData) {
        wishList.clear();
        firebaseFirestore.collection( FB_USERS ).document( FirebaseAuth.getInstance().getUid() ).collection( FB_USER_DATA ).document( FB_MY_WISHLIST )
                .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (long i = 0; i < (long) task.getResult().get( FB_LIST_SIZE ); i++) {
                                wishList.add( task.getResult().get( FB_PRODUCT_ID_ + i, String.class ) );

                                if (DBqueries.wishList.contains( productId )) {
                                    PRoductDEtailshActivity.ALREADY_ADDED_TO_WISHLIST = true;
                                    if (addToWishListBtn != null) {
                                        addToWishListBtn.setSupportImageTintList( ContextCompat.getColorStateList( context, R.color.red ) );
                                    }
                                } else {
                                    if (addToWishListBtn != null) {
                                        addToWishListBtn.setSupportImageTintList( ContextCompat.getColorStateList( context, R.color.gray ) );
                                    }
                                    PRoductDEtailshActivity.ALREADY_ADDED_TO_WISHLIST = false;
                                }

                                if (loadProductData) {
                                    wishlistModelList.clear();
                                    String productId = task.getResult().get( FB_PRODUCT_ID_ + i, String.class );
                                    firebaseFirestore.collection( FB_COLLECTION_PRODUCTS ).document( Objects.requireNonNull( productId ) )
                                            .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        wishlistModelList.add(
                                                                new WishlistModel(
                                                                        productId,
                                                                        task.getResult().get( "product_image_1", String.class ),
                                                                        task.getResult().get( "product_title_1", String.class ),
                                                                        task.getResult().get( FB_FREE_COUPENS, Long.class ),
                                                                        task.getResult().get( FB_AVERAGE_RATING, String.class ),
                                                                        task.getResult().get( FB_TOTAL_RATINGS, Long.class ),
                                                                        task.getResult().get( FB_PRODUCT_PRICE, String.class ),
                                                                        task.getResult().get( FB_CUTTED_PRICE, String.class ),
                                                                        task.getResult().get( FB_COD ), Boolean.class ) );
                                                        MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();

                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
                                                    }
                                                }
                                            } );
                                }
                            }
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
                        }
                        dialog.dismiss();
                    }
                } );

    }

    public static void removeFromWishlist(int index, Context context) {
        String removedProductId = wishList.get( index );
        wishList.remove( index );
        Map<String, Object> updateWishlist = new HashMap<>();
        for (int i = 0; i < wishList.size(); i++) {
            updateWishlist.put( FB_PRODUCT_ID_ + i, wishList.get( i ) );
        }
        updateWishlist.put( FB_LIST_SIZE, (long) wishList.size() );

        firebaseFirestore.collection( FB_USERS ).document( FirebaseAuth.getInstance().getUid() ).collection( FB_USER_DATA )
                .document( FB_MY_WISHLIST ).set( updateWishlist ).addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        if (wishlistModelList.size() != 0) {
                            wishlistModelList.remove( index );
                            MyWishlistFragment.wishlistAdapter.notifyDataSetChanged();
                        }
                        PRoductDEtailshActivity.ALREADY_ADDED_TO_WISHLIST = false;
                        Toast.makeText( context, "Removed Successfully!", Toast.LENGTH_SHORT ).show();
                    } else {
                        if (addToWishListBtn != null) {
                            addToWishListBtn.setSupportImageTintList( context.getResources().getColorStateList( R.color.red ) );
                        }
                        wishList.add( index, removedProductId );
                        String error = task.getException().getMessage();
                        Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
                    }
                    PRoductDEtailshActivity.running_wishlist_query = false;
                } );
    }

    public static void loadRatingList(Context context) {
        if (!PRoductDEtailshActivity.running_rating_query) {
            PRoductDEtailshActivity.running_rating_query = true;
            myRatedIds.clear();
            myRating.clear();
            firebaseFirestore.collection( FB_USERS ).document( FirebaseAuth.getInstance().getUid() ).collection( FB_USER_DATA )
                    .document( FB_MY_RATINGS ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                for (long i = 0; i < (long) task.getResult().get( FB_LIST_SIZE ); i++) {
                                    myRatedIds.add( task.getResult().get( FB_PRODUCT_ID_ + i, String.class ) );
                                    myRating.add( (Long) task.getResult().get( FB_RATING_ + i ) );

                                    if (task.getResult().get( FB_PRODUCT_ID_ + i, String.class ).equals( productId )) {
                                        initialRating = Integer.parseInt( String.valueOf( (Long) task.getResult().get( FB_RATING_ + i ) ) ) - 1;
                                        if (PRoductDEtailshActivity.binding.ratingsContent.rateNowContainer != null) {
                                            setRating( initialRating );
                                        }
                                    }

                                }
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
                            }
                            PRoductDEtailshActivity.running_rating_query = false;
                        }
                    } );
        }
    }

    public static void loadCartList(Context context, Dialog dialog, boolean loadProductData, TextView badgeCount) {
        cartList.clear();
        firebaseFirestore.collection( FB_USERS ).document( FirebaseAuth.getInstance().getUid() ).collection( FB_USER_DATA ).document( FB_MY_CART )
                .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            for (long i = 0; i < (long) task.getResult().get( FB_LIST_SIZE ); i++) {
                                cartList.add( task.getResult().get( FB_PRODUCT_ID_ + i, String.class ) );

                                if (DBqueries.cartList.contains( productId )) {
                                    PRoductDEtailshActivity.ALREADY_ADDED_TO_CART = true;
                                } else {
                                    PRoductDEtailshActivity.ALREADY_ADDED_TO_CART = false;
                                }
                                if (loadProductData) {
                                    cartItemModelList.clear();
                                    String productId = task.getResult().get( FB_PRODUCT_ID_ + i, String.class );
                                    firebaseFirestore.collection( FB_COLLECTION_PRODUCTS ).document( Objects.requireNonNull( productId ) )
                                            .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        int index = 0;
                                                        if (cartList.size() >= 2) {
                                                            index = cartList.size() - 2;
                                                        }
                                                        cartItemModelList.add( index,
                                                                new CartItemModel( CartItemModel.CART_ITEM,
                                                                        productId,
                                                                        task.getResult().get( "product_image_1", String.class ),
                                                                        task.getResult().get( FB_PRODUCT_TITLE, String.class ),
                                                                        task.getResult().get( FB_FREE_COUPENS, Long.class ),
                                                                        task.getResult().get( FB_PRODUCT_PRICE, String.class ),
                                                                        task.getResult().get( FB_CUTTED_PRICE, String.class ),
                                                                        (long) 1,
                                                                        (long) 0,
                                                                        (long) 0,
                                                                        (boolean) task.getResult().get( FB_IN_STOCK ) ) );
                                                        if (cartList.size() == 1) {
                                                            cartItemModelList.add( new CartItemModel( CartItemModel.TOTAL_AMOUNT ) );
                                                        }
                                                        if (cartList.size() == 0) {
                                                            cartItemModelList.clear();
                                                        }
                                                        MyCartFragment.cartAdapter.notifyDataSetChanged();

                                                    } else {
                                                        String error = task.getException().getMessage();
                                                        Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
                                                    }
                                                }
                                            } );
                                }
                            }
                            if (cartList.size() != 0) {
                                badgeCount.setVisibility( View.VISIBLE );
                            } else {
                                badgeCount.setVisibility( View.INVISIBLE );
                            }
                            if (DBqueries.cartList.size() < 99) {
                                badgeCount.setText( String.valueOf( DBqueries.cartList.size() ) );
                            } else {
                                badgeCount.setText( "99" );
                            }
                        } else {
                            String error = task.getException().getMessage();
                            Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
                        }
                        dialog.dismiss();
                    }
                } );


    }

    public static void removeFromCart(int index, Context context) {
        String removedProductId = cartList.get( index );
        cartList.remove( index );
        Map<String, Object> updateCartList = new HashMap<>();
        for (int i = 0; i < cartList.size(); i++) {
            updateCartList.put( FB_PRODUCT_ID_ + i, cartList.get( i ) );
        }
        updateCartList.put( FB_LIST_SIZE, (long) cartList.size() );

        firebaseFirestore.collection( FB_USERS ).document( FirebaseAuth.getInstance().getUid() ).collection( FB_USER_DATA )
                .document( FB_MY_CART ).set( updateCartList ).addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        if (cartItemModelList.size() != 0) {
                            cartItemModelList.remove( index );
                            MyCartFragment.cartAdapter.notifyDataSetChanged();
                        }
                        if (cartList.size() == 0) {
                            cartItemModelList.clear();
                        }
//                        PRoductDEtailshActivity.ALREADY_ADDED_TO_CART = false;
                        Toast.makeText( context, "Removed Successfully!", Toast.LENGTH_SHORT ).show();
                    } else {
                        cartList.add( index, removedProductId );
                        String error = task.getException().getMessage();
                        Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
                    }
                    PRoductDEtailshActivity.running_cart_query = false;
                } );
    }

    public static void loadAddresses(Context context, Dialog loadingDialog) {
        addressesModelList.clear();
        firebaseFirestore.collection( FB_USERS ).document( FirebaseAuth.getInstance().getUid() ).collection( FB_USER_DATA )
                .document( FB_MY_ADDRESSES ).get().addOnCompleteListener( task -> {
                    if (task.isSuccessful()) {
                        Intent deliveryIntent;
                        DocumentSnapshot result = task.getResult();
                        if (result != null && result.contains( FB_LIST_SIZE ) && result.getLong( FB_LIST_SIZE ) != 0) {
                            for (long i = 1; i < result.getLong( FB_LIST_SIZE ) + 1; i++) {
                                addressesModelList.add( new AddressesModel( task.getResult().get( FB_FULLNAME_ + i, String.class ),
                                        task.getResult().get( FB_ADDRESS_ + i, String.class ),
                                        task.getResult().get( FB_PINCODE_ + i, String.class ),
                                        (Boolean) task.getResult().get( FB_SELECTED_ + i ) ) );
                                if ((Boolean) task.getResult().get( FB_SELECTED_ + i )) {
                                    selectedAddress = Integer.parseInt( String.valueOf( i - 1 ) );
                                }
                            }
                            deliveryIntent = new Intent( context, DeliveryActivity.class );
                        } else {
                            deliveryIntent = new Intent( context, AddAdressActivity.class );
                            deliveryIntent.putExtra( "INTENT", "deliveryIntent" );
                        }
                        context.startActivity( deliveryIntent );
                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
                    }
                    loadingDialog.dismiss();
                } );
    }

    public static void clearData() {
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        wishList.clear();
        wishlistModelList.clear();
        cartItemModelList.clear();
    }

}
