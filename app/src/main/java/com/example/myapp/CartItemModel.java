package com.example.myapp;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class CartItemModel {
    public static final int CART_ITEM = 0;
    public static final int TOTAL_AMOUNT = 1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;

    //////cart item
    private String productID;
    private String productImage;
    private String productTitle;
    private Long freeCoupens;
    private String productPrice;
    private String cuttedPrice;
    private Long productQuantiy;
    private Long offersApplied;
    private Long copensApplied;

    public CartItemModel(int type, String productID, String productImage, String productTitle, Long freeCoupens, String productPrice, String cuttedPrice, Long productQuantiy, Long offersApplied, Long copensApplied) {
        this.type = type;
        this.productID = productID;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.productQuantiy = productQuantiy;
        this.offersApplied = offersApplied;
        this.copensApplied = copensApplied;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public Long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(Long freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public Long getProductQuantiy() {
        return productQuantiy;
    }

    public void setProductQuantiy(Long productQuantiy) {
        this.productQuantiy = productQuantiy;
    }

    public Long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(Long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public Long getCopensApplied() {
        return copensApplied;
    }

    public void setCopensApplied(Long copensApplied) {
        this.copensApplied = copensApplied;
    }

    //////cart item

    //////cart total
    private String totalItems;
    private String totalItemPrice;
    private String saveAmount;
    private String deliveryPrice;
    private String totalAmount;

    public CartItemModel(int type, String totalItems, String totalItemPrice, String saveAmount, String deliveryPrice, String totalAmount) {
        this.type = type;
        this.totalItems = totalItems;
        this.totalItemPrice = totalItemPrice;
        this.saveAmount = saveAmount;
        this.deliveryPrice = deliveryPrice;
        this.totalAmount = totalAmount;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(String totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public String getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(String saveAmount) {
        this.saveAmount = saveAmount;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
//////cart total

    public static class CartItemViewholder extends RecyclerView.ViewHolder {
        private ImageView productImage;
        private ImageView freeCopensIcon;
        private TextView productTitle;
        private TextView freeCopens;
        private TextView productPrice;
        private TextView cuttedPrice;
        private TextView offersApplied;
        private TextView copenApplied;
        private TextView productQuantity;

        private LinearLayout deleteBtn;

        public CartItemViewholder(@NonNull View itemView) {
            super( itemView );
            productImage = itemView.findViewById( R.id.product_image );
            productTitle = itemView.findViewById( R.id.product_title );
            freeCopensIcon = itemView.findViewById( R.id.free_coupen_icon );
            freeCopens = itemView.findViewById( R.id.tv_free_coupen );
            productPrice = itemView.findViewById( R.id.product_prices );
            cuttedPrice = itemView.findViewById( R.id.cutted_price );
            offersApplied = itemView.findViewById( R.id.offers_applied );
            copenApplied = itemView.findViewById( R.id.coupens_applied );
            productQuantity = itemView.findViewById( R.id.product_quantity );
            deleteBtn = itemView.findViewById( R.id.remove_item_btn );
        }

        void setItemDetails(String productID, String resource, String title, Long freeCopensNo, String productPriceText, String cuttedPriceText, Long offersAppliedNo, Long offersApplied,int position) {
            Glide.with( itemView.getContext() ).load( resource ).apply( new RequestOptions().placeholder( R.drawable.ic_baseline_image_24 ) ).into( productImage );
            productTitle.setText( title );
            if (freeCopensNo > 0) {
                freeCopensIcon.setVisibility( View.VISIBLE );
                freeCopens.setVisibility( View.VISIBLE );
                if (freeCopensNo == 1) {
                    freeCopens.setText( " free " + freeCopensNo + " Coupen " );
                } else {
                    freeCopens.setText( " free " + freeCopensNo + " Coupen " );
                }
            } else {
                freeCopensIcon.setVisibility( View.INVISIBLE );
                freeCopens.setVisibility( View.INVISIBLE );
            }
            productPrice.setText( productPriceText );
            cuttedPrice.setText( cuttedPriceText );
            if (offersAppliedNo > 0) {
                this.offersApplied.setVisibility( View.VISIBLE );
                this.offersApplied.setText( offersAppliedNo + " offers applied " );
            } else {
                this.offersApplied.setVisibility( View.INVISIBLE );
            }
            productQuantity.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog quantityDialog = new Dialog( itemView.getContext() );
                    quantityDialog.setContentView( R.layout.quantity_dialog );
                    quantityDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
                    quantityDialog.setCancelable( false );
                    EditText quantityNo = quantityDialog.findViewById( R.id.quantity_no );
                    Button cancelBtn = quantityDialog.findViewById( R.id.quantity_cancel_btn );
                    Button okBtn = quantityDialog.findViewById( R.id.quantity_ok_btn );

                    cancelBtn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            quantityDialog.dismiss();
                        }
                    } );
                    okBtn.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            productQuantity.setText( "Qty:" + quantityNo.getText() );
                            quantityDialog.dismiss();
                        }
                    } );
                    quantityDialog.show();
                }
            } );

            deleteBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!PRoductDEtailshActivity.running_cart_query) {
                        PRoductDEtailshActivity.running_cart_query = true;

                        DBqueries.removeFromCart( position,itemView.getContext() );

                    }
                }
            } );
        }
    }

    public static class CartTotalAmountViewholder extends RecyclerView.ViewHolder {
        private TextView totalItems;
        private TextView totalItemsPrice;
        private TextView deliveryPrice;
        private TextView totalAmount;
        private TextView savedAmount;

        public CartTotalAmountViewholder(@NonNull View itemView) {
            super( itemView );

            totalItems = itemView.findViewById( R.id.total_items );
            totalItemsPrice = itemView.findViewById( R.id.total_item_price );
            deliveryPrice = itemView.findViewById( R.id.delivery_price );
            totalAmount = itemView.findViewById( R.id.total_price );
            savedAmount = itemView.findViewById( R.id.saved_amount );
        }

        void setTotalAmount(String totalItemText, String totalItemPriceText, String deliveryPriceText, String totalAmountText, String savedAmountText) {
            totalItems.setText( totalItemText );
            totalItemsPrice.setText( totalItemPriceText );
            deliveryPrice.setText( deliveryPriceText );
            totalAmount.setText( totalAmountText );
            savedAmount.setText( savedAmountText );
        }
    }
}
