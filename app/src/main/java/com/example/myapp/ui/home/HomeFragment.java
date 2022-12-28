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

import com.bumptech.glide.Glide;
import com.example.myapp.CategoryAdapter;
import com.example.myapp.CategoryModel;
import com.example.myapp.HomePageAdapter;
import com.example.myapp.HomePageModel;
import com.example.myapp.R;
import com.example.myapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private HomeViewModel homeVM;
    private HomePageAdapter adapter;

    public HomeFragment() {
    }

    private CategoryAdapter categoryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_home, container, false );
        homeVM = new ViewModelProvider( this ).get( HomeViewModel.class );

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() == true) {
            binding.noInternetConnection.setVisibility( View.GONE );

            ///////////categorise///////////
            categoryAdapter = new CategoryAdapter( (ArrayList<CategoryModel>) categoryModelList );
            binding.categoryRecyclerview.setAdapter( categoryAdapter );
            if (categoryModelList.size() == 0) {
                loadCategories( categoryAdapter, getContext() );
            } else {
                categoryAdapter.notifyDataSetChanged();
            }
            ///////////categorise///////////

            ///////////Home Page///////////
            if (lists.size() == 0) {
                loadedCategoriesNames.add( "HOME" );
                lists.add( new ArrayList<HomePageModel>() );
                adapter = new HomePageAdapter( lists.get( 0 ) );
                loadFragmentData( adapter, getContext() ,0,"HOME");
            } else {
                adapter = new HomePageAdapter( lists.get( 0 ) );
                adapter.notifyDataSetChanged();
            }
            binding.homePageRecyclerview.setAdapter( adapter );
            ///////////Home Page///////////

        } else {
            Glide.with( this ).load( R.drawable.ic_baseline_close_24 ).into( binding.noInternetConnection );
            binding.noInternetConnection.setVisibility( View.VISIBLE );
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}