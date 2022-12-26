package com.example.myapp.ui.home;

import static com.example.myapp.DBqueries.categoryModelList;
import static com.example.myapp.DBqueries.homePageModelList;
import static com.example.myapp.DBqueries.loadCategories;
import static com.example.myapp.DBqueries.loadFragmentData;

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
import com.example.myapp.HomePageAdapter;
import com.example.myapp.R;
import com.example.myapp.databinding.FragmentHomeBinding;

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
            categoryAdapter = new CategoryAdapter( categoryModelList );
            binding.categoryRecyclerview.setAdapter( categoryAdapter );
            if (categoryModelList.size() == 0) {
                loadCategories( categoryAdapter, getContext() );
            } else {
                categoryAdapter.notifyDataSetChanged();
            }
            ///////////categorise///////////

            ///////////Home Page///////////
            adapter = new HomePageAdapter( homePageModelList );
            binding.homePageRecyclerview.setAdapter( adapter );
            if (homePageModelList.size() == 0) {
                loadFragmentData( adapter, getContext() );
            } else {
                adapter.notifyDataSetChanged();
            }
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