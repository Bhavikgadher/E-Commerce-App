package com.example.myapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MyOrdersFragment extends Fragment {

    public MyOrdersFragment() {
        // Required empty public constructor
    }
    private RecyclerView myOrderRecyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate( R.layout.fragment_my_orders, container, false );

        myOrderRecyclerView = view.findViewById( R.id.rv_my_orders );
        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel( R.mipmap.ic_14_plus,2,"Iphone 14 (Blue)","Delivered On Monday,12th Dec 2022" ));
        myOrderItemModelList.add(new MyOrderItemModel( R.mipmap.ic_13_plus,1,"Iphone 13 (Black)","Delivered On Monday,12th Dec 2022" ));
        myOrderItemModelList.add(new MyOrderItemModel( R.mipmap.ic_12_min,0,"Iphone 12 (White)","Cancelled" ));
        myOrderItemModelList.add(new MyOrderItemModel( R.mipmap.ic_13_pro,4,"Iphone 14 (SkyBlue)","Delivered On Monday,12th Dec 2022" ));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter( myOrderItemModelList );
        myOrderRecyclerView.setAdapter( myOrderAdapter );
        myOrderAdapter.notifyDataSetChanged();
        return view;
    }
}