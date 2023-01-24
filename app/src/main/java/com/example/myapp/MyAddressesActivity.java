package com.example.myapp;

import static com.example.myapp.DeliveryActivity.SELECT_ADDRESS;
import static com.example.myapp.utils.Constants.FB_MY_ADDRESSES;
import static com.example.myapp.utils.Constants.FB_SELECTED_;
import static com.example.myapp.utils.Constants.FB_USERS;
import static com.example.myapp.utils.Constants.FB_USER_DATA;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.myapp.databinding.ActivityMyAddressesBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MyAddressesActivity extends AppCompatActivity {

    private ActivityMyAddressesBinding binding;
    private Dialog loadingDialog;
    private static AddressesAdapter addressesAdapter;
    private int previousAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_my_addresses );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        getSupportActionBar().setTitle( "My Address" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        loadingDialog = new Dialog( this );
        loadingDialog.setContentView( R.layout.loading_progress_dialog );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setBackgroundDrawable( this.getDrawable( R.drawable.slider_background ) );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );

        previousAddress = DBqueries.selectedAddress;

        int mode = getIntent().getIntExtra( "MODE", -1 );
        if (mode == SELECT_ADDRESS) {
            binding.deliveryHereBtn.setVisibility( View.VISIBLE );
        } else {
            binding.deliveryHereBtn.setVisibility( View.GONE );
        }
        binding.deliveryHereBtn.setOnClickListener( view -> {
            if (DBqueries.selectedAddress != previousAddress) {
                int previousAddressIndex = previousAddress;
                loadingDialog.show();
                Map<String, Object> updateSelection = new HashMap<>();
                updateSelection.put( FB_SELECTED_ + String.valueOf( previousAddress + 1 ), false );
                updateSelection.put( FB_SELECTED_ + String.valueOf( DBqueries.selectedAddress + 1 ), true );

                previousAddress = DBqueries.selectedAddress;

                FirebaseFirestore.getInstance().collection( FB_USERS ).document( FirebaseAuth.getInstance().getUid() )
                        .collection( FB_USER_DATA ).document( FB_MY_ADDRESSES ).update( updateSelection ).addOnCompleteListener( new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    finish();
                                } else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText( MyAddressesActivity.this, error, Toast.LENGTH_SHORT ).show();
                                }
                                loadingDialog.dismiss();
                            }
                        } );
            } else {
                finish();
            }
        } );
        addressesAdapter = new AddressesAdapter( DBqueries.addressesModelList, mode );
        binding.rvAddresses.setAdapter( addressesAdapter );
        ((SimpleItemAnimator) binding.rvAddresses.getItemAnimator()).setSupportsChangeAnimations( false );
        addressesAdapter.notifyDataSetChanged();

        binding.addNewAddressBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addAddressIntent = new Intent( MyAddressesActivity.this, AddAdressActivity.class );
                addAddressIntent.putExtra( "INTENT", "null" );
                startActivity( addAddressIntent );
            }
        } );



    }

    @Override
    protected void onStart() {
        super.onStart();

        binding.addressSaved.setText( String.valueOf( DBqueries.addressesModelList.size() ) + " save addresses" );
    }

    public static void refreshItem(int deSelect, int select) {

        addressesAdapter.notifyItemChanged( deSelect );

        addressesAdapter.notifyItemChanged( select );

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (DBqueries.selectedAddress != previousAddress) {
                DBqueries.addressesModelList.get( DBqueries.selectedAddress ).setSelected( false );
                DBqueries.addressesModelList.get( previousAddress ).setSelected( true );
                DBqueries.selectedAddress = previousAddress;
            }
            finish();
            return true;
        }

        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onBackPressed() {
        if (DBqueries.selectedAddress != previousAddress) {
            DBqueries.addressesModelList.get( DBqueries.selectedAddress ).setSelected( false );
            DBqueries.addressesModelList.get( previousAddress ).setSelected( true );
            DBqueries.selectedAddress = previousAddress;
        }
        super.onBackPressed();
    }
}