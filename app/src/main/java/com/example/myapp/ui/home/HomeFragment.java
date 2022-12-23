package com.example.myapp.ui.home;

import static com.example.myapp.utils.Constants.FB_BACKGROUND;
import static com.example.myapp.utils.Constants.FB_COLLECTION_CATEGORIES;
import static com.example.myapp.utils.Constants.FB_DOCUMENT_HOME;
import static com.example.myapp.utils.Constants.FB_NO_OF_BANNERS;
import static com.example.myapp.utils.Constants.FB_NO_OF_PRODUCTS;
import static com.example.myapp.utils.Constants.FB_STRIP_AD_BANNER;
import static com.example.myapp.utils.Constants.FB_SUB_COLLECTION_TD;
import static com.example.myapp.utils.Constants.FB_VIEW_TYPE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapp.CategoryAdapter;
import com.example.myapp.CategoryModel;
import com.example.myapp.HomePageAdapter;
import com.example.myapp.HomePageModel;
import com.example.myapp.HorizontalProductScrollModel;
import com.example.myapp.R;
import com.example.myapp.SliderModel;
import com.example.myapp.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeVM;
    private HomePageAdapter adapter;
    private FirebaseFirestore firebaseFirestore;

    public HomeFragment() {
    }

    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_home, container, false );
        homeVM = new ViewModelProvider( this ).get( HomeViewModel.class );
        ///////////categorise///////////
        categoryAdapter = new CategoryAdapter();
        binding.categoryRecyclerview.setAdapter( categoryAdapter );
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection( "CATEGORIES" ).orderBy( "index" ).get().addOnCompleteListener( task ->
        {
            ArrayList<CategoryModel> categoryModelList = new ArrayList<>();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    categoryModelList.add( new CategoryModel( documentSnapshot.get( "categoryName" ).toString(),
                            documentSnapshot.get( "icon" ).toString() ) );
                }
            } else {
                String error = task.getException().getMessage();
                Toast.makeText( getContext(), error, Toast.LENGTH_SHORT ).show();
            }
            categoryAdapter.setData( categoryModelList );
        } );

        ///////////categorise///////////
        ///////////Home Page///////////
        List<HomePageModel> homePageModelList = new ArrayList<>();
        adapter = new HomePageAdapter( homePageModelList );
        binding.homePageRecyclerview.setAdapter( adapter );

        DocumentReference homeDR = firebaseFirestore.collection( FB_COLLECTION_CATEGORIES ).document( FB_DOCUMENT_HOME );
        homeDR.collection( FB_SUB_COLLECTION_TD ).orderBy( "index" ).get().addOnCompleteListener( task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    Long viewType = documentSnapshot.get(FB_VIEW_TYPE,Long.class);
                    if (viewType!=null){
                        if (viewType == 0) {
                            Long noOfBanners = documentSnapshot.get(FB_NO_OF_BANNERS,Long.class);
                            if (noOfBanners!=null) {
                                List<SliderModel> sliderModelList = new ArrayList<>();
                                for (int i = 1; i <= noOfBanners; i++) {
                                    String banner = documentSnapshot.get( String.format( getString(R.string.banner_id),i ),String.class );
                                    String bannerBackground = documentSnapshot.get( String.format( getString(R.string.banner_id_background),i ),String.class );
                                    if (banner != null && bannerBackground != null) {
                                        sliderModelList.add( new SliderModel( banner, bannerBackground ) );
                                    }
                                }
                                homePageModelList.add( new HomePageModel( 0, sliderModelList ) );
                            }
                        } else if (viewType == 1) {
                            String stripAdBanner = documentSnapshot.get(FB_STRIP_AD_BANNER,String.class);
                            String background = documentSnapshot.get(FB_BACKGROUND,String.class);
                            if (stripAdBanner != null && background != null) {
                                homePageModelList.add( new HomePageModel( 1, stripAdBanner, background ) );
                            }
                        } else if (viewType == 2) {
                            List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
                            Long noOfProducts = documentSnapshot.get(FB_NO_OF_PRODUCTS,Long.class);
                            for (long i = 1; i <= noOfProducts; i++) {
                                horizontalProductScrollModelList.add(
                                        new HorizontalProductScrollModel( documentSnapshot.get( "product_ID_" + i ).toString(),
                                        documentSnapshot.get( "product_image_" + i ).toString(),
                                        documentSnapshot.get( "product_title_" + i ).toString(),
                                        documentSnapshot.get( "product_subtitle_" + i ).toString(),
                                        documentSnapshot.get( "product_price_" + i ).toString() )
                                );
                            }
                            homePageModelList.add( new HomePageModel( 2, documentSnapshot.get( "layout_title" ).toString(),
                                    documentSnapshot.get( "background" ).toString(), horizontalProductScrollModelList ) );
                        } else if (viewType == 3) {
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                String error = task.getException().getMessage();
                Toast.makeText( getContext(), error, Toast.LENGTH_SHORT ).show();
            }
        } );
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}