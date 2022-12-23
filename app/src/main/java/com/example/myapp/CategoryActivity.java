package com.example.myapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapp.databinding.ActivityMainBinding;

import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;


    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_category );

        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        setSupportActionBar( binding.appBarMain.toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayShowTitleEnabled( true );
        String title = getIntent().getStringExtra( "CategoryName" );
        getSupportActionBar().setTitle( title );
        getSupportActionBar().setDisplayShowTitleEnabled( true );

//        ////banner code
//
//        List<SliderModel> sliderModelList = new ArrayList<>();
//
//        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_close_24, "#0320FF" ) );
//        sliderModelList.add( new SliderModel( R.drawable.ic_menu_camera, "#0320FF" ) );
//        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_mark_email_read_24, "#0320FF" ) );
//
//        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_email_24, "#0320FF" ) );
//        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_add_24, "#0320FF" ) );
//        sliderModelList.add( new SliderModel( R.mipmap.demo_slide, "#0320FF" ) );
//        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_search_24, "#0320FF" ) );
//        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_close_24, "#0320FF" ) );
//
//        sliderModelList.add( new SliderModel( R.drawable.ic_menu_camera, "#0320FF" ) );
//        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_mark_email_read_24, "#0320FF" ) );
//        sliderModelList.add( new SliderModel( R.drawable.ic_baseline_email_24, "#0320FF" ) );
//
//        ////banner code

        ////////Horizontal product layout

//        List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
//        horizontalProductScrollModelList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone x", "red", "Rs.24000/-" ) );
//        horizontalProductScrollModelList.add( new HorizontalProductScrollModel( R.drawable.ic_black_iphone_24, "iphone", "black", "Rs.24000/-" ) );
//        horizontalProductScrollModelList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_phone_iphone_24, "iphone11", "blue", "Rs.24000/-" ) );
//        horizontalProductScrollModelList.add( new HorizontalProductScrollModel( R.drawable.ic_green_iphone_24, "iphone12", "green", "Rs.24000/-" ) );
//        horizontalProductScrollModelList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_power_settings_new_24, "iphone13", "yellow", "Rs.24000/-" ) );
//        horizontalProductScrollModelList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_favorite_24, "iphone14", "1blue", "Rs.24000/-" ) );
//        horizontalProductScrollModelList.add( new HorizontalProductScrollModel( R.drawable.ic_baseline_home_24, "iphone11pro", "pink", "Rs.24000/-" ) );
//        horizontalProductScrollModelList.add( new HorizontalProductScrollModel( R.drawable.ic_phone_iphone_24, "iphone11min", "blueBlack", "Rs.24000/-" ) );
//        ////////Horizontal product layout


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.search_icon, menu );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.main_search_icon) {
            Log.e( "LOG_LOG", "main_search_icon" );
            return true;
        }
        else if (id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }
}