package com.example.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class ProductSpecificationFragment extends Fragment {

    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    private RecyclerView productSpecificationRecyclerView;
    public List<ProductSpecificationModel> productSpecificationModelList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_product_specification, container, false );
        productSpecificationRecyclerView = view.findViewById( R.id.product_specification_recyclerView );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager( view.getContext() );
        linearLayoutManager.setOrientation( LinearLayoutManager.VERTICAL );

        productSpecificationRecyclerView.setLayoutManager( linearLayoutManager );


//        productSpecificationModelList.add(new ProductSpecificationModel( 0,"General" ));
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add(new ProductSpecificationModel( 1,"Display" ));
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add(new ProductSpecificationModel( 0,"General" ));
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add(new ProductSpecificationModel( 1,"Display" ));
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );
//        productSpecificationModelList.add( new ProductSpecificationModel( 1,"RAM","128GB" ) );


        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter( productSpecificationModelList );
        productSpecificationRecyclerView.setAdapter( productSpecificationAdapter );
        productSpecificationAdapter.notifyDataSetChanged();
        return view;
    }
}