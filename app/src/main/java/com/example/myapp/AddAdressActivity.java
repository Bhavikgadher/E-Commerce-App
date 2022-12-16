package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapp.databinding.ActivityAddAdressBinding;

public class AddAdressActivity extends AppCompatActivity {

    private ActivityAddAdressBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_adress );
        setSupportActionBar( binding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        getSupportActionBar().setTitle( "Add a new address" );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        binding.saveBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntent = new Intent(AddAdressActivity.this,DeliveryActivity.class);
                startActivity( deliveryIntent );
                finish();
            }
        } );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }
}