package com.example.myapp;

import static com.example.myapp.RegisterActivity.setSignUpFragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapp.databinding.ActivityMainBinding;
import com.example.myapp.ui.gallery.GalleryFragment;
import com.example.myapp.ui.home.HomeFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;
    private FrameLayout frameLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public static Boolean showCart = false;
    private Dialog signInDialog;
    private Window window;
    private FirebaseUser currentUser;
    public static DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_main );
        setSupportActionBar( binding.appBarMain.toolbar );
        window = getWindow();
        window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
        drawerLayout = findViewById( R.id.drawer_layout );

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setDisplayShowTitleEnabled( false );

//        binding.navView.getMenu().getItem( 0 ).setChecked( true );
        binding.navView.setNavigationItemSelectedListener( this );

        if (showCart) {
            binding.drawerLayout.setDrawerLockMode( binding.drawerLayout.LOCK_MODE_LOCKED_CLOSED );
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            gotoFragment( "My Cart", new MyCartFragment() );
        } else {
            actionBarDrawerToggle = new ActionBarDrawerToggle( this, binding.drawerLayout, R.string.lbl_open, R.string.lbl_close );
            binding.drawerLayout.addDrawerListener( actionBarDrawerToggle );
            actionBarDrawerToggle.syncState();
            setFragment( new HomeFragment() );
        }
        signInDialog = new Dialog( MainActivity.this );
        signInDialog.setContentView( R.layout.sign_in_dialog );
        signInDialog.setCancelable( true );

        signInDialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );

        Button dialogSignInBtn = signInDialog.findViewById( R.id.sign_In_dialog_btn );
        Button dialogSignUpBtn = signInDialog.findViewById( R.id.sign_up_dialog_btn );

        Intent registerIntent = new Intent( MainActivity.this, RegisterActivity.class );
        dialogSignInBtn.setOnClickListener( view -> {
            SingInFragment.disableCloseBtn = true;
            SingUpFragment.disableCloseBtn = true;
            signInDialog.dismiss();
            setSignUpFragment = false;
            startActivity( registerIntent );
        } );
        dialogSignUpBtn.setOnClickListener( view -> {
            SingInFragment.disableCloseBtn = true;
            SingUpFragment.disableCloseBtn = true;
            signInDialog.dismiss();
            setSignUpFragment = true;
            startActivity( registerIntent );
        } );

    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            binding.navView.getMenu().getItem( binding.navView.getMenu().size() - 1 ).setEnabled( false );
        } else {
            binding.navView.getMenu().getItem( binding.navView.getMenu().size() - 1 ).setEnabled( true );
        }
        invalidateOptionsMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (getCurrentFragment() instanceof HomeFragment) {
            //getSupportActionBar().setDisplayShowTitleEnabled( false );
            getMenuInflater().inflate( R.menu.main, menu );

            MenuItem cartItem = menu.findItem( R.id.main_cart_icon );
            if (DBqueries.cartList.size() > 0) {
                cartItem.setActionView( R.layout.badge_layout );
                ImageView badgeIcon = cartItem.getActionView().findViewById( R.id.badge_icon );
                badgeIcon.setImageResource( R.drawable.ic_baseline_shopping_cart_24 );
                TextView badgeCount = cartItem.getActionView().findViewById( R.id.badge_count );
                if (DBqueries.cartList.size() < 99) {
                    badgeCount.setText( String.valueOf( DBqueries.cartList.size() ) );
                } else {
                    badgeCount.setText( "99" );
                }

                cartItem.getActionView().setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (currentUser == null) {
                            signInDialog.show();
                        } else {
                            gotoFragment( "My Cart", new MyCartFragment() );
                        }
                    }
                } );
            } else {
                cartItem.setActionView( null );
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected( item )) {
            return false;
        }
        int id = item.getItemId();
        if (id == R.id.main_search_icon) {
            return true;
        } else if (id == R.id.main_notification_icon) {
            if (getCurrentFragment() instanceof HomeFragment) {
                setFragment( new GalleryFragment() );
            }
            return true;
        } else if (id == R.id.main_cart_icon) {
            if (currentUser == null) {
                signInDialog.show();
            } else {
                gotoFragment( "My Cart", new MyCartFragment() );
            }
            return true;
        } else if (id == android.R.id.home) {
            if (showCart) {
                showCart = false;
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected( item );
    }

    private void gotoFragment(String title, Fragment fragment) {
        binding.appBarMain.actionbarLogo.setVisibility( View.GONE );
        getSupportActionBar().setDisplayShowTitleEnabled( true );
        getSupportActionBar().setTitle( title );
        invalidateOptionsMenu();
        setFragment( fragment );
//        binding.navView.getMenu().getItem( 3 ).setChecked( true );
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (currentUser != null) {
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
                FirebaseAuth.getInstance().signOut();
                DBqueries.clearData();
                Intent registerIntent = new Intent( MainActivity.this, RegisterActivity.class );
                startActivity( registerIntent );
                finish();
            }
            binding.drawerLayout.closeDrawer( GravityCompat.START );
            return true;
        } else {
            binding.drawerLayout.closeDrawer( GravityCompat.START );
            signInDialog.show();
            return false;
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace( binding.appBarMain.incMain.mainFrameLayout.getId(), fragment, fragment.getClass().getName() ).commit();
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById( binding.appBarMain.incMain.mainFrameLayout.getId() );
    }

}