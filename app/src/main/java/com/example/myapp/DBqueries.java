package com.example.myapp;

import static com.example.myapp.utils.Constants.FB_AVERAGE_RATING_;
import static com.example.myapp.utils.Constants.FB_BACKGROUND;
import static com.example.myapp.utils.Constants.FB_COD_;
import static com.example.myapp.utils.Constants.FB_COLLECTION_CATEGORIES;
import static com.example.myapp.utils.Constants.FB_CUTTED_PRICE_;
import static com.example.myapp.utils.Constants.FB_DOCUMENT_HOME;
import static com.example.myapp.utils.Constants.FB_FREE_COUPENS_;
import static com.example.myapp.utils.Constants.FB_NO_OF_BANNERS;
import static com.example.myapp.utils.Constants.FB_NO_OF_PRODUCTS;
import static com.example.myapp.utils.Constants.FB_PRODUCT_FULL_TITLE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_ID_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_IMAGE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_IMAGE_Q;
import static com.example.myapp.utils.Constants.FB_PRODUCT_PRICE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_SUBTITLE_;
import static com.example.myapp.utils.Constants.FB_PRODUCT_TITLE_;
import static com.example.myapp.utils.Constants.FB_STRIP_AD_BANNER;
import static com.example.myapp.utils.Constants.FB_SUB_COLLECTION_TD;
import static com.example.myapp.utils.Constants.FB_TOTAL_RATINGS_;
import static com.example.myapp.utils.Constants.FB_VIEW_TYPE;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBqueries {

    public static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    public static ArrayList<CategoryModel> categoryModelList = new ArrayList<>();
    public static List<HomePageModel> homePageModelList = new ArrayList<>();

    public static void loadCategories(final CategoryAdapter categoryAdapter, final Context context) {
        firebaseFirestore.collection( "CATEGORIES" ).orderBy( "index" ).get().addOnCompleteListener( task ->
        {
            if (task.isSuccessful()) {
                Log.e( "flatu", "log" );
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    categoryModelList.add( new CategoryModel( documentSnapshot.get( "categoryName" ).toString(),
                            documentSnapshot.get( "icon" ).toString() ) );
                }
                categoryAdapter.notifyDataSetChanged();
            } else {
                String error = task.getException().getMessage();
                Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
            }
            categoryAdapter.setData( categoryModelList );
        } );
    }

    public static void loadFragmentData(final HomePageAdapter adapter, final Context context) {
        DocumentReference homeDR = firebaseFirestore.collection( FB_COLLECTION_CATEGORIES ).document( FB_DOCUMENT_HOME );
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
                                homePageModelList.add( new HomePageModel( 0, sliderModelList ) );
                            }
                        }
                        //if view type is 1 then inflate view for strip ad banners
                        else if (viewType == 1) {
                            String stripAdBanner = documentSnapshot.get( FB_STRIP_AD_BANNER, String.class );
                            String background = documentSnapshot.get( FB_BACKGROUND, String.class );
                            if (stripAdBanner != null && background != null) {
                                homePageModelList.add( new HomePageModel( 1, stripAdBanner, background ) );
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
                                                documentSnapshot.get( FB_PRODUCT_IMAGE_Q + i, String.class ),
                                                documentSnapshot.get( FB_PRODUCT_FULL_TITLE_ + i, String.class ),
                                                documentSnapshot.get( FB_FREE_COUPENS_ + i, Long.class ),
                                                documentSnapshot.get( FB_AVERAGE_RATING_ + i, String.class ),
                                                documentSnapshot.get( FB_TOTAL_RATINGS_ + i, Long.class ),
                                                documentSnapshot.get( FB_PRODUCT_PRICE_ + i, String.class ),
                                                documentSnapshot.get( FB_CUTTED_PRICE_ + i, String.class ),
                                                documentSnapshot.get( FB_COD_ + i ), Boolean.class ) );
                            }
                            homePageModelList.add( new HomePageModel( 2, documentSnapshot.get( "layout_title" ).toString(),
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
                            homePageModelList.add( new HomePageModel( 3, documentSnapshot.get( "layout_title" ).toString(),
                                    documentSnapshot.get( "background" ).toString(), GridLayoutModelList ) );
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                String error = task.getException().getMessage();
                Toast.makeText( context, error, Toast.LENGTH_SHORT ).show();
            }
        } );

    }
}
