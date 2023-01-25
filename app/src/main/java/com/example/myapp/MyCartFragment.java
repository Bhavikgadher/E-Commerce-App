package com.example.myapp;

import static com.example.myapp.DBqueries.cartItemModelList;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
            DBqueries.loadCartList( getContext(), loadingDialog, true, new TextView( getContext() ),totalAmount );
        } else {
            if (cartItemModelList.get( cartItemModelList.size() - 1 ).getType() == CartItemModel.TOTAL_AMOUNT) {
                LinearLayout parent = (LinearLayout) totalAmount.getParent().getParent();
                parent.setVisibility( View.VISIBLE );
            }
            loadingDialog.dismiss();
        }

        cartAdapter = new CartAdapter( cartItemModelList, totalAmount, true );
        cartItemsRecyclerView.setAdapter( cartAdapter );
        cartAdapter.notifyDataSetChanged();

        continueBtn.setOnClickListener( view1 -> {
            DeliveryActivity.cartItemModelList = new ArrayList<>();
            for (int i = 0; i < DBqueries.cartItemModelList.size(); i++) {
                CartItemModel cartItemModel = cartItemModelList.get( i );
                if (cartItemModel.isInStock()) {
                    DeliveryActivity.cartItemModelList.add( cartItemModel );
                }
            }
            DeliveryActivity.cartItemModelList.add( new CartItemModel( CartItemModel.TOTAL_AMOUNT ) );
            loadingDialog.show();
            if (DBqueries.addressesModelList.size() == 0) {
                DBqueries.loadAddresses( getContext(), loadingDialog );
            } else {
                loadingDialog.dismiss();
                Intent deliveryIntent = new Intent( getContext(), DeliveryActivity.class );
                startActivity( deliveryIntent );
            }
        } );
        return view;
    }
}