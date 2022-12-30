package com.example.myapp.ui.home;

import static com.example.myapp.DBqueries.categoryModelList;
import static com.example.myapp.DBqueries.lists;
import static com.example.myapp.DBqueries.loadCategories;
import static com.example.myapp.DBqueries.loadFragmentData;
import static com.example.myapp.DBqueries.loadedCategoriesNames;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.myapp.CategoryAdapter;
import com.example.myapp.CategoryModel;
import com.example.myapp.HomePageAdapter;
import com.example.myapp.HomePageModel;
import com.example.myapp.HorizontalProductScrollModel;
import com.example.myapp.R;
import com.example.myapp.SliderModel;
import com.example.myapp.WishlistModel;
import com.example.myapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeVM;
    private HomePageAdapter adapter;

    public HomeFragment() {
    }

    private List<CategoryModel> categoryModelFakeList = new ArrayList<>();
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

    public static SwipeRefreshLayout swipeRefreshLayout;
    private CategoryAdapter categoryAdapter;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_home, container, false );
        homeVM = new ViewModelProvider( this ).get( HomeViewModel.class );
        swipeRefreshLayout = binding.refreshLayout;
        /// category fake
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        categoryModelFakeList.add( new CategoryModel( "null", "" ) );
        /// category fake

        ///home page fake list

        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );

        homePageModelFakeList.add( new HomePageModel( 0, sliderModelFakeList ) );
        homePageModelFakeList.add( new HomePageModel( 1, "", "#ffffff" ) );
        homePageModelFakeList.add( new HomePageModel( 2, "", "#ffffff", horizontalProductScrollModelFakeList, new ArrayList<WishlistModel>() ) );
        homePageModelFakeList.add( new HomePageModel( 3, "", "#ffffff", horizontalProductScrollModelFakeList ) );

        ///home page fake list


        categoryAdapter = new CategoryAdapter( (ArrayList<CategoryModel>) categoryModelFakeList );
        binding.categoryRecyclerview.setAdapter( categoryAdapter );

        adapter = new HomePageAdapter( homePageModelFakeList );
        binding.homePageRecyclerview.setAdapter( adapter );

        connectivityManager = (ConnectivityManager) getActivity().getSystemService( Context.CONNECTIVITY_SERVICE );
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            binding.noInternetConnection.setVisibility( View.GONE );

            ///////////categorise///////////

            if (categoryModelList.size() == 0) {
                loadCategories( binding.categoryRecyclerview, getContext() );
            } else {
                categoryAdapter.notifyDataSetChanged();
            }
            ///////////categorise///////////

            ///////////Home Page///////////
            if (lists.size() == 0) {
                loadedCategoriesNames.add( "HOME" );
                lists.add( new ArrayList<HomePageModel>() );

                loadFragmentData( binding.homePageRecyclerview, getContext(), 0, "HOME" );
            } else {
                adapter = new HomePageAdapter( lists.get( 0 ) );
                adapter.notifyDataSetChanged();
            }

            ///////////Home Page///////////

        } else {
            Glide.with( this ).load( R.drawable.ic_baseline_close_24 ).into( binding.noInternetConnection );
            binding.noInternetConnection.setVisibility( View.VISIBLE );
        }

        /// refresh layout

        binding.refreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.refreshLayout.setColorSchemeResources( R.color.blue );
//                binding.refreshLayout.setColorSchemeColors(getContext().getResources().getColor( R.color.blue ),getContext().getResources().getColor( R.color.blue ),getContext().getResources().getColor( R.color.blue ));
                binding.refreshLayout.setRefreshing( true );

                categoryModelList.clear();
                lists.clear();
                loadedCategoriesNames.clear();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                    binding.noInternetConnection.setVisibility( View.GONE );
                    binding.categoryRecyclerview.setAdapter( categoryAdapter );
                    binding.homePageRecyclerview.setAdapter( adapter );
                    loadCategories( binding.categoryRecyclerview, getContext() );
                    loadedCategoriesNames.add( "HOME" );
                    lists.add( new ArrayList<HomePageModel>() );
                    loadFragmentData( binding.homePageRecyclerview, getContext(), 0, "HOME" );
                } else {
                    Glide.with( getContext() ).load( R.drawable.ic_baseline_close_24 ).into( binding.noInternetConnection );
                    binding.noInternetConnection.setVisibility( View.VISIBLE );
                }

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