package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.myapp.databinding.FragmentMyAccountBinding;


public class MyAccountFragment extends Fragment {

    public MyAccountFragment() {
        // Required empty public constructor
    }

    private FragmentMyAccountBinding binding;
    public static final int MANAGE_ADDRESS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_account, container, false );
        View view = binding.getRoot();

        binding.addressesContent.viewAllAddresseBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAddressesIntent = new Intent(getContext(),MyAddressesActivity.class);
                myAddressesIntent.putExtra( "MODE",MANAGE_ADDRESS );
                startActivity( myAddressesIntent );
            }
        } );
        return view;
    }
}