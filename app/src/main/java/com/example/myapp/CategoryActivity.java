package com.example.myapp;

import static com.example.myapp.DBqueries.lists;
import static com.example.myapp.DBqueries.loadFragmentData;
import static com.example.myapp.DBqueries.loadedCategoriesNames;

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

import com.example.myapp.databinding.ActivityCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    private ActivityCategoryBinding binding;
    private NavHostFragment navHostFragment;
    private NavController navController;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();

    private HomePageAdapter adapter;

    @SuppressLint({"MissingInflatedId", "NotifyDataSetChanged"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_category );
        setSupportActionBar( binding.toolbar );
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        String title = getIntent().getStringExtra( "CategoryName" );
        getSupportActionBar().setTitle( title );
        getSupportActionBar().setDisplayShowTitleEnabled( true );

        ///home page fake list

        List<SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );
        sliderModelFakeList.add( new SliderModel( "null", "#0320FF" ) );

        List<HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );
        horizontalProductScrollModelFakeList.add( new HorizontalProductScrollModel( "", "", "", "", "" ) );

        homePageModelFakeList.add( new HomePageModel( 0, sliderModelFakeList ) );
        homePageModelFakeList.add( new HomePageModel( 1, "", "#ffffff" ) );
        homePageModelFakeList.add( new HomePageModel( 2, "", "#ffffff", horizontalProductScrollModelFakeList, new ArrayList<WishlistModel>() ) );
        homePageModelFakeList.add( new HomePageModel( 3, "", "#ffffff", horizontalProductScrollModelFakeList ) );

        ///home page fake list

        adapter = new HomePageAdapter( homePageModelFakeList );

        int listPosition = 0;
        for (int i = 0; i < loadedCategoriesNames.size(); i++) {
            if (loadedCategoriesNames.get( i ).equals( title.toUpperCase() )) {
                listPosition = i;
            }
        }
        if (listPosition == 0) {
            loadedCategoriesNames.add( title.toUpperCase() );
            lists.add( new ArrayList<HomePageModel>() );
//            adapter = new HomePageAdapter( lists.get( loadedCategoriesNames.size() - 1 ) );
            loadFragmentData( binding.categoryRecylerview, this, loadedCategoriesNames.size() - 1, title );
        } else {
            adapter = new HomePageAdapter( lists.get( listPosition ) );
        }
        binding.categoryRecylerview.setAdapter( adapter );
        adapter.notifyDataSetChanged();
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
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected( item );
    }
}