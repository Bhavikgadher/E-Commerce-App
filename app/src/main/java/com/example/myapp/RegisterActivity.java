package com.example.myapp;

import android.os.Bundle;
import android.view.KeyEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class RegisterActivity extends AppCompatActivity {

    public static boolean onRestPassword = false;
    public static boolean setSignUpFragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if(setSignUpFragment){
            setSignUpFragment = false;
            addFragment( new SingUpFragment(),false );
        }else {
            addFragment( new SingInFragment(), false );
        }

    }

    public void addFragment(Fragment fragment, Boolean isAddToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().add(R.id.register_framelayout, fragment, fragment.getClass().getName());
        if (isAddToBackStack) {
            ft.addToBackStack(fragment.getClass().getName());
        }
        ft.commit();
    }

    public void replaceFragment(Fragment fragment, Boolean isAddToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim, R.anim.pop_enter_anim, R.anim.pop_exit_anim);
        ft.replace(R.id.register_framelayout, fragment, fragment.getClass().getName());
        if (isAddToBackStack) {
            ft.addToBackStack(fragment.getClass().getName());
        }
        ft.commit();
    }

    public void replaceDefaultFragment(Fragment fragment, Boolean isAddToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim, R.anim.pop_enter_anim, R.anim.pop_exit_anim);
        ft.replace(R.id.register_framelayout,fragment, fragment.getClass().getName());
        if (isAddToBackStack) {
            ft.addToBackStack(fragment.getClass().getName());
        }
        ft.commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            SingInFragment.disableCloseBtn=false;
            SingUpFragment.disableCloseBtn = false;
            if(onRestPassword){
                onRestPassword = false;
                replaceDefaultFragment(new SingInFragment(),true);
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}