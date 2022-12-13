package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class ProductDetailsAdapter extends FragmentStateAdapter {

    public ProductDetailsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super( fragmentActivity );
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0 :
                return new ProductDescriptionFragment();
            case 1:
                return new ProductSpecificationFragment();
            case 2:
                return new ProductDescriptionFragment();
        }
        return new ProductDescriptionFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
