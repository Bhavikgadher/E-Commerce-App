package com.example.myapp;

import static androidx.fragment.app.FragmentManager.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.myapp.databinding.ActivityDeliveryBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public class DeliveryActivity extends AppCompatActivity implements PaymentResultListener {

    public static List<CartItemModel> cartItemModelList;
    private ActivityDeliveryBinding binding;
    public static final int SELECT_ADDRESS = 0;
    private Dialog loadingDialog;
    private Dialog paymentMethodDialog;
    private ImageButton gpay;
    private boolean successResponse = false;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_delivery );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( "Delivery" );
        Checkout.preload( getApplicationContext() );

        /// loading dialog
        loadingDialog = new Dialog( DeliveryActivity.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialog );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setBackgroundDrawable( getDrawable( R.drawable.slider_background ) );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        /// loading dialog

        /// payment dialog
        paymentMethodDialog = new Dialog( DeliveryActivity.this );
        paymentMethodDialog.setContentView( R.layout.payment_method );
        paymentMethodDialog.setCancelable( true );
        paymentMethodDialog.getWindow().setBackgroundDrawable( getDrawable( R.drawable.slider_background ) );
        paymentMethodDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        gpay = paymentMethodDialog.findViewById( R.id.gpay );
        /// payment dialog


        CartAdapter cartAdapter = new CartAdapter( cartItemModelList, binding.totalCartAmount, false );
        binding.rvDelivery.setAdapter( cartAdapter );
        cartAdapter.notifyDataSetChanged();

        binding.shippingContent.changOrAddAddressBtn.setVisibility( View.VISIBLE );
        binding.shippingContent.changOrAddAddressBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myAddressesIntent = new Intent( DeliveryActivity.this, MyAddressesActivity.class );
                myAddressesIntent.putExtra( "MODE", SELECT_ADDRESS );
                startActivity( myAddressesIntent );
            }
        } );
        binding.cartContinueBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentMethodDialog.show();
            }
        } );
//        binding.getToPay.getSettings().setJavaScriptEnabled( true );
//        binding.getToPay.getSettings().setBuiltInZoomControls( true );
//        binding.getToPay.getSettings().setDisplayZoomControls( true );
//        binding.getToPay.setWebViewClient( new WebViewClient() );
//        binding.getToPay.loadUrl( "https://rzp.io/l/miracleKMC" );

        gpay.setOnClickListener( new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                paymentMethodDialog.dismiss();
                loadingDialog.show();
                if (ContextCompat.checkSelfPermission( DeliveryActivity.this, Manifest.permission.READ_SMS ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions( DeliveryActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101 );
                }
                Checkout checkout = new Checkout();
                checkout.setKeyID( "<YOUR_KEY_ID>" );
                checkout.setImage( R.id.gpay );
                Checkout.sdkCheckIntegration( DeliveryActivity.this );
//                String M_id = "";
//                String customer_id = FirebaseAuth.getInstance().getUid();
                String order_id = UUID.randomUUID().toString().substring( 0, 28 );
//                String url = "https://fibrous-humidity.000webhostapp.com/paytm/sample.php";
//                String callBackUrl = "https://pguat.paytm.com/paytmchecksum...";


                double finalAmount = Float.parseFloat( String.valueOf( binding.totalCartAmount ) ) * 100;

//                RequestQueue requestQueue = Volley.newRequestQueue( DeliveryActivity.this );

//                StringRequest stringRequest = new StringRequest( Request.Method.POST, url, new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            if (jsonObject.has( "CHECKSUMHASH" )){
//                                String CHECKSUMHASE = jsonObject.getString( "CHECKSUMHASH" );
//
//
//
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        loadingDialog.dismiss();
//                        Toast.makeText( DeliveryActivity.this, "Something went wrong !", Toast.LENGTH_SHORT ).show();
//                    }
//                } ) {
//                    @Nullable
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> paramMap = new HashMap<String, String>();
//                        paramMap.put( "MID", M_id );
//                        paramMap.put( "ORDER_ID", order_id );
//                        paramMap.put( "CUST_ID", customer_id );
//                        paramMap.put( "CHANNEL_ID", "WAP" );
//                        paramMap.put( "TXN_AMOUNT", binding.totalCartAmount.getText().toString().substring( 2, binding.totalCartAmount.getText().length() - 2 ) );
//                        paramMap.put( "WEBSITE", "WEBSTAGING" );
//                        paramMap.put( "INDUSTRY_TYPE_ID", "Retail" );
//                        paramMap.put( "CALLBACK_URL", callBackUrl );
//                        return paramMap;
//                    }
//                };
//                requestQueue.add( stringRequest );

                try {
                    JSONObject options = new JSONObject();

                    options.put( "name", "Miracle" );
                    options.put( "description", "Reference No. #123456" );
                    options.put( "image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg" );
                    options.put( "order_id", order_id + "" );//from response of step 3.
                    options.put( "theme.color", "#3399cc" );
                    options.put( "currency", "INR" );
                    options.put( "amount", finalAmount + "" );//pass amount in currency subunits

                    JSONObject retryObj = new JSONObject();
                    retryObj.put( "enabled", true );
                    retryObj.put( "max_count", 4 );
                    options.put( "retry", retryObj );
                    checkout.open( DeliveryActivity.this, options );

                } catch (Exception e) {
                    Log.e( TAG, "Error in starting Razorpay Checkout", e );
                }

            }
        } );
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.shippingContent.fullName.setText( DBqueries.addressesModelList.get( DBqueries.selectedAddress ).getFullname() );
        binding.shippingContent.address.setText( DBqueries.addressesModelList.get( DBqueries.selectedAddress ).getAddress() );
        binding.shippingContent.pincode.setText( DBqueries.addressesModelList.get( DBqueries.selectedAddress ).getPincode() );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }


    @Override
    public void onPaymentSuccess(String s) {
//        Toast.makeText( this, "Payment Success", Toast.LENGTH_SHORT ).show();
//        binding.orderId.setText( "Order ID" + .getString( "order_id") );
        successResponse = true;
        if (MainActivity.mainActivity != null) {
            MainActivity.mainActivity.finish();
            MainActivity.mainActivity = null;
            MainActivity.showCart = false;
        }
        if (PRoductDEtailshActivity.prodcutDetailsActivity != null) {
            PRoductDEtailshActivity.prodcutDetailsActivity.finish();
            PRoductDEtailshActivity.prodcutDetailsActivity = null;
        }
        binding.orderConfirmationLayout.setVisibility( View.VISIBLE );
        binding.continueShoppingBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
    }


    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText( this, "Payment Failure", Toast.LENGTH_SHORT ).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (successResponse){
            finish();
            return;
        }
        super.onBackPressed();
    }
}