package com.example.myapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapp.databinding.ActivityMainBinding;
import com.example.myapp.ui.gallery.GalleryFragment;
import com.example.myapp.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private FrameLayout frameLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        setSupportActionBar( binding.appBarMain.toolbar );
        window = getWindow();
        window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );

        actionBarDrawerToggle = new ActionBarDrawerToggle( this, binding.drawerLayout, R.string.lbl_open, R.string.lbl_close );
        binding.drawerLayout.addDrawerListener( actionBarDrawerToggle );
        actionBarDrawerToggle.syncState();
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowTitleEnabled( false );

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        binding.navView.getMenu().getItem( 0 ).setChecked( true );
        binding.navView.setNavigationItemSelectedListener( this );
        setFragment( new HomeFragment() );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (getCurrentFragment() instanceof HomeFragment) {
            //getSupportActionBar().setDisplayShowTitleEnabled( false );
            getMenuInflater().inflate( R.menu.main, menu );
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected( item )) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.main_search_icon) {
            Log.e( "LOG_LOG", "main_search_icon" );
            return true;
        } else if (id == R.id.main_notification_icon) {
            Log.e( "LOG_LOG", "main_notification_icon" );
            if (getCurrentFragment() instanceof HomeFragment) {
                setFragment( new GalleryFragment() );
            }
            return true;
        } else if (id == R.id.main_cart_icon) {
            gotoFragment( "My Cart", new MyCartFragment() );
            Log.e( "LOG_LOG", "main_cart_icon" );
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void gotoFragment(String title, Fragment fragment) {
        binding.appBarMain.actionbarLogo.setVisibility( View.GONE );
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        getSupportActionBar().setTitle( title );
        invalidateOptionsMenu();
        setFragment( fragment );
//        navigationView.getMenu().getItem( 3 ).setChecked( true );
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (binding.drawerLayout.isDrawerOpen( GravityCompat.START )) {
            binding.drawerLayout.closeDrawer( GravityCompat.START );
        }
        int id = item.getItemId();
        if (id == R.id.nav_my_app) {
            getSupportActionBar().setDisplayShowTitleEnabled( false );
            binding.appBarMain.actionbarLogo.setVisibility( View.VISIBLE );
            setFragment( new HomeFragment() );
        } else if (id == R.id.nav_my_orders) {
            gotoFragment( "My Orders", new MyOrdersFragment() );
        } else if (id == R.id.nav_my_cart) {
            gotoFragment( "My Cart", new MyCartFragment() );
        } else if (id == R.id.nav_my_wishlist) {
            gotoFragment( "My Wishlist", new MyWishlistFragment() );
        } else if (id == R.id.nav_my_rewards) {
            gotoFragment( "My Rewards", new MyRewardsFragment() );
        } else if (id == R.id.nav_my_account) {
            gotoFragment( "My Account", new MyAccountFragment() );
        } else if (id == R.id.nav_sign_out) {
        }
        return true;
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace( binding.appBarMain.incMain.mainFrameLayout.getId(), fragment, fragment.getClass().getName() ).commit();
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById( binding.appBarMain.incMain.mainFrameLayout.getId() );
    }

}