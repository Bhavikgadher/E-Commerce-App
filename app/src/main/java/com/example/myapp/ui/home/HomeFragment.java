package com.example.myapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeVM;
    public HomeFragment() {
    }

    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false );
        homeVM = new ViewModelProvider( this ).get( HomeViewModel.class );

        categoryAdapter = new CategoryAdapter( provideFakeCategoryList() );
        binding.categoryRecyclerview.setAdapter( categoryAdapter );


        List<HomePageModel> homePageModelList = new ArrayList<>();
        homePageModelList.add( new HomePageModel( 0, provideFakeSliderList() ) );
        homePageModelList.add( new HomePageModel( 1, R.mipmap.demo_slide, "#FFFFFFFF" ) );
        homePageModelList.add( new HomePageModel( 2, "Deals Of The Day", provideFakeProductList() ) );
        homePageModelList.add( new HomePageModel( 3, "Deals Of The Day", provideFakeProductList() ) );

        HomePageAdapter adapter = new HomePageAdapter( homePageModelList );
        binding.homePageRecyclerview.setAdapter( adapter );


        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public List<CategoryModel> provideFakeCategoryList(){
        List<CategoryModel> categoryModelList = new ArrayList<>();
        categoryModelList.add( new CategoryModel( "Home", "link" ) );
        categoryModelList.add( new CategoryModel( "Electronics", "link" ) );
        categoryModelList.add( new CategoryModel( "Appliances", "link" ) );
        categoryModelList.add( new CategoryModel( "Furniture", "link" ) );
        categoryModelList.add( new CategoryModel( "Fashion", "link" ) );
        categoryModelList.add( new CategoryModel( "Toys", "link" ) );
        categoryModelList.add( new CategoryModel( "Wall Arts", "link" ) );
        categoryModelList.add( new CategoryModel( "Book", "link" ) );
        return categoryModelList;
    }
    public List<SliderModel> provideFakeSliderList(){
        List<SliderModel> sliderModelList = new ArrayList<>();
        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_mark_email_read_24, "#0320FF" ) );
        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_email_24, "#0320FF" ) );
        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_add_24, "#0320FF" ) );
        sliderModelList.add( new SliderModel( R.mipmap.demo_slide, "#0320FF" ) );
        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_search_24, "#0320FF" ) );
        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_close_24, "#0320FF" ) );
        sliderModelList.add( new SliderModel( R.drawable.ic_menu_camera, "#0320FF" ) );
        return sliderModelList;
    }

    public List<HorizontalProductScrollModel> provideFakeProductList(){
        List<HorizontalProductScrollModel> productList = new ArrayList<>();
        productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone x", "red", "Rs.24000/-" ) );
        productList.add( new HorizontalProductScrollModel( R.drawable.ic_black_iphone_24, "iphone", "black", "Rs.24000/-" ) );
        productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_phone_iphone_24, "iphone11", "blue", "Rs.24000/-" ) );
        productList.add( new HorizontalProductScrollModel( R.drawable.ic_green_iphone_24, "iphone12", "green", "Rs.24000/-" ) );
        productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone11min", "blueBlack", "Rs.24000/-" ) );
        productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_power_settings_new_24, "iphone13", "yellow", "Rs.24000/-" ) );
        productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_favorite_24, "iphone14", "1blue", "Rs.24000/-" ) );
        productList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_home_24, "iphone11pro", "pink", "Rs.24000/-" ) );
        productList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone11min", "blueBlack", "Rs.24000/-" ) );
        return productList;
    }
}