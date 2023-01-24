package com.example.myapp;

import static com.example.myapp.utils.Constants.FB_ADDRESS_;
import static com.example.myapp.utils.Constants.FB_FULLNAME_;
import static com.example.myapp.utils.Constants.FB_LIST_SIZE;
import static com.example.myapp.utils.Constants.FB_MY_ADDRESSES;
import static com.example.myapp.utils.Constants.FB_PINCODE_;
import static com.example.myapp.utils.Constants.FB_SELECTED_;
import static com.example.myapp.utils.Constants.FB_USERS;
import static com.example.myapp.utils.Constants.FB_USER_DATA;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapp.databinding.ActivityAddAdressBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAdressActivity extends AppCompatActivity {

    private ActivityAddAdressBinding binding;
    private Dialog loadingDialog;
    private String selectedState;
    private String[] stateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_add_adress );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        getSupportActionBar().setTitle( "Add a new address" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        stateList = getResources().getStringArray( R.array.india_states );
        ArrayAdapter spinnerAdapter = new ArrayAdapter( this, android.R.layout.simple_spinner_item, stateList );
        spinnerAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        loadingDialog = new Dialog( this );
        loadingDialog.setContentView( R.layout.loading_progress_dialog );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setBackgroundDrawable( this.getDrawable( R.drawable.slider_background ) );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );

        binding.stateSpinner.setAdapter( spinnerAdapter );

        binding.stateSpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedState = stateList[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        } );

        binding.saveBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty( binding.city.getText() )) {
                    //show error to city edit text
                } else if (!TextUtils.isEmpty( binding.locality.getText() )) {
                    //show error to locality edit text
                } else if (!TextUtils.isEmpty( binding.flatNo.getText() )) {
                    //show error to flatNo edit text
                } else if (!TextUtils.isEmpty( binding.pincode.getText() )) {
                    //show error to pincode edit text
                } else if (binding.pincode.getText().length() == 6) {
                    //show error to enter valid pincode edit text
                }
                if (!TextUtils.isEmpty( binding.city.getText() )) {
                    if (!TextUtils.isEmpty( binding.locality.getText() )) {
                        if (!TextUtils.isEmpty( binding.flatNo.getText() )) {
                            if (!TextUtils.isEmpty( binding.pincode.getText() ) && binding.pincode.getText().length() == 6) {
                                if (!TextUtils.isEmpty( binding.name.getText() )) {
                                    if (!TextUtils.isEmpty( binding.mobileNo.getText() ) && binding.mobileNo.getText().length() == 10) {
                                        loadingDialog.show();
                                        String fullAddress = binding.flatNo.getText().toString() + " " + binding.locality.getText().toString() + " " + binding.landmark.getText().toString() + " " + binding.city.getText().toString() + " " + selectedState;
                                        String fullName = binding.name.getText().toString() + "-" + binding.mobileNo.getText().toString();

                                        Map<String, Object> addAddress = new HashMap();
                                        addAddress.put( FB_LIST_SIZE, (long) DBqueries.addressesModelList.size() + 1 );
                                        if (TextUtils.isEmpty( binding.alternateMobileNo.getText() )) {
                                            addAddress.put( FB_FULLNAME_ + String.valueOf( (long) DBqueries.addressesModelList.size() + 1 ), fullName );
                                        } else {
                                            addAddress.put( FB_FULLNAME_ + String.valueOf( (long) DBqueries.addressesModelList.size() + 1 ), fullName + " or " + binding.alternateMobileNo.getText().toString() );
                                        }
                                        addAddress.put( FB_ADDRESS_ + String.valueOf( (long) DBqueries.addressesModelList.size() + 1 ), fullAddress );
                                        addAddress.put( FB_PINCODE_ + String.valueOf( (long) DBqueries.addressesModelList.size() + 1 ), binding.pincode.getText().toString() );
                                        addAddress.put( FB_SELECTED_ + String.valueOf( (long) DBqueries.addressesModelList.size() + 1 ), true );
                                        if (DBqueries.addressesModelList.size() > 0) {
                                            addAddress.put( FB_SELECTED_ + (DBqueries.selectedAddress + 1), false );
                                        }
                                        FirebaseFirestore.getInstance().collection( FB_USERS ).document( FirebaseAuth.getInstance().getUid() ).collection( FB_USER_DATA )
                                                .document( FB_MY_ADDRESSES ).update( addAddress ).addOnCompleteListener( new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            if (DBqueries.addressesModelList.size() > 0) {
                                                                DBqueries.addressesModelList.get( DBqueries.selectedAddress ).setSelected( false );
                                                            }
                                                            if (TextUtils.isEmpty( binding.alternateMobileNo.getText() )) {
                                                                DBqueries.addressesModelList.add( new AddressesModel( fullName, fullAddress, binding.pincode.getText().toString(), true ) );
                                                            } else {
                                                                DBqueries.addressesModelList.add( new AddressesModel( fullName + " or " + binding.alternateMobileNo.getText().toString(), fullAddress, binding.pincode.getText().toString(), true ) );
                                                            }
                                                            if (getIntent().getStringExtra( "INTENT" ).equals( "deliveryIntent" )) {
                                                                Intent deliveryIntent = new Intent( AddAdressActivity.this, DeliveryActivity.class );
                                                                startActivity( deliveryIntent );
                                                            }else{
                                                                MyAddressesActivity.refreshItem( DBqueries.selectedAddress,DBqueries.addressesModelList.size()-1 );
                                                            }
                                                            DBqueries.selectedAddress = DBqueries.addressesModelList.size() - 1;
                                                            finish();
                                                        } else {
                                                            String error = task.getException().getMessage();
                                                            Toast.makeText( AddAdressActivity.this, error, Toast.LENGTH_SHORT ).show();
                                                        }
                                                        loadingDialog.dismiss();
                                                    }
                                                } );
                                    } else {
                                        binding.mobileNo.requestFocus();
                                        Toast.makeText( AddAdressActivity.this, "Please Valid No.", Toast.LENGTH_SHORT ).show();
                                    }
                                } else {
                                    binding.name.requestFocus();
                                }
                            } else {
                                binding.pincode.requestFocus();
                                Toast.makeText( AddAdressActivity.this, "Please Valid pincode", Toast.LENGTH_SHORT ).show();
                            }
                        } else {
                            binding.flatNo.requestFocus();
                        }
                    } else {
                        binding.locality.requestFocus();
                    }
                } else {
                    binding.city.requestFocus();
                }
            }
        } );
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
}