package com.example.myapp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myapp.databinding.FragmentProductDescriptionBinding;


public class ProductDescriptionFragment extends Fragment {

    public String body;
    private FragmentProductDescriptionBinding binding;

    public ProductDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_product_description, container, false );
        View view = binding.getRoot();
        binding.tvProductDescription.setText( body );
        return view;
    }
}