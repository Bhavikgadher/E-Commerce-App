package com.example.myapp;

import static com.example.myapp.DBqueries.cartItemModelList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class MyCartFragment extends Fragment {

    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemsRecyclerView;
    private Button continueBtn;
    private Dialog loadingDialog;
    public static CartAdapter cartAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_my_cart, container, false );
        continueBtn = view.findViewById( R.id.cart_continue_btn );

        loadingDialog = new Dialog( getContext() );
        loadingDialog.setContentView( R.layout.loading_progress_dialog );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setBackgroundDrawable( getContext().getDrawable( R.drawable.slider_background ) );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();

        cartItemsRecyclerView = view.findViewById( R.id.rv_cart_items );
        if (cartItemModelList.size() == 0){
            DBqueries.cartList.clear();
            DBqueries.loadCartList( getContext(),loadingDialog,true,new TextView( getContext() ) );
        }else {
            loadingDialog.dismiss();
        }

        cartAdapter = new CartAdapter( cartItemModelList );
        cartItemsRecyclerView.setAdapter( cartAdapter );
        cartAdapter.notifyDataSetChanged();

        continueBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent( getContext(), AddAdressActivity.class );
                getContext().startActivity( deliveryIntent );
            }
        } );

        return view;
    }
}