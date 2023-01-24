package com.example.myapp;

import static com.example.myapp.DBqueries.cartItemModelList;

import android.app.Dialog;
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
    private TextView totalAmount;


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
        totalAmount = view.findViewById( R.id.total_cart_amount );

        loadingDialog = new Dialog( getContext() );
        loadingDialog.setContentView( R.layout.loading_progress_dialog );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setBackgroundDrawable( getContext().getDrawable( R.drawable.slider_background ) );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();

        cartItemsRecyclerView = view.findViewById( R.id.rv_cart_items );
        if (cartItemModelList.size() == 0) {
            DBqueries.cartList.clear();
            DBqueries.loadCartList( getContext(), loadingDialog, true, new TextView( getContext() ) );
        } else {
            loadingDialog.dismiss();
        }

        cartAdapter = new CartAdapter( cartItemModelList, totalAmount,true );
        cartItemsRecyclerView.setAdapter( cartAdapter );
        cartAdapter.notifyDataSetChanged();

        continueBtn.setOnClickListener( view1 -> {
            loadingDialog.show();
            DBqueries.loadAddresses( getContext(),loadingDialog );
        } );

        return view;
    }
}