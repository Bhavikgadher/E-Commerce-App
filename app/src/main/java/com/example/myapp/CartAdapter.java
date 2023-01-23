package com.example.myapp;

import static java.lang.Integer.parseInt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {
    private List<CartItemModel> cartItemModelList;
//    private int lastPosition = -1;

    public CartAdapter(List<CartItemModel> cartItemModelList) {
        this.cartItemModelList = cartItemModelList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (cartItemModelList.get( position ).getType()) {
            case 0:
                return CartItemModel.CART_ITEM;
            case 1:
                return CartItemModel.TOTAL_AMOUNT;
            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case CartItemModel.CART_ITEM:
                View cartItemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.cart_item_layout, parent, false );
                return new CartItemModel.CartItemViewholder( cartItemView );
            case CartItemModel.TOTAL_AMOUNT:
                View cartTotalView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.cart_total_amount, parent, false );
                return new CartItemModel.CartTotalAmountViewholder( cartTotalView );
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (cartItemModelList.get( position ).getType()) {
            case CartItemModel.CART_ITEM:
                String productID = cartItemModelList.get( position ).getProductID();
                String resource = cartItemModelList.get( position ).getProductImage();
                String title = cartItemModelList.get( position ).getProductTitle();
                Long freeCopens = cartItemModelList.get( position ).getFreeCoupens();
                String productPrice = cartItemModelList.get( position ).getProductPrice();
                String cuttedPrice = cartItemModelList.get( position ).getCuttedPrice();
                Long productQuantiy = cartItemModelList.get( position ).getProductQuantiy();
                Long offersApplied = cartItemModelList.get( position ).getOffersApplied();

                ((CartItemModel.CartItemViewholder) holder).setItemDetails( productID, resource, title, freeCopens, productPrice, cuttedPrice, productQuantiy, offersApplied, position );
                break;
            case CartItemModel.TOTAL_AMOUNT:
                int totalItems = 0;
                int totalItemsPrice = 0;
                String deliveryPrice;
                int totalAmount;
                int saveAmount = 0 ;
                for (int i = 0; i < cartItemModelList.size(); i++) {
                    if (cartItemModelList.get( i ).getType() == CartItemModel.CART_ITEM) {
                        totalItems++;
                        totalItemsPrice = parseInt( totalItemsPrice + cartItemModelList.get( i ).getProductPrice() );//, na chale okk try again done done birooo
                    }
                }
                if (totalItemsPrice > 500) {
                    deliveryPrice = "Free";
                    totalAmount = totalItemsPrice;
                } else {
                    deliveryPrice = "60";
                    totalAmount = totalItemsPrice + 60;
                }
                ((CartItemModel.CartTotalAmountViewholder) holder).setTotalAmount( totalItems, totalItemsPrice, deliveryPrice, totalAmount, saveAmount );
                break;
            default:
                return;
        }
//        if (lastPosition < position) {
//            Animation animation = AnimationUtils.loadAnimation( holder.itemView.getContext(), R.anim.fade_in );
//            holder.itemView.setAnimation( animation );
//            lastPosition = position;
//        }
    }

    @Override
    public int getItemCount() {
        return cartItemModelList.size();
    }
}
