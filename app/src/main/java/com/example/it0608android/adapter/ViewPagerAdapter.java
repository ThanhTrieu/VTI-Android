package com.example.it0608android.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.it0608android.BudgetFragment;
import com.example.it0608android.HomeFragment;
import com.example.it0608android.ProfileFragment;
import com.example.it0608android.ReportsFragment;
import com.example.it0608android.SettingsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new HomeFragment();
        } else if (position == 1) {
            return new BudgetFragment();
        } else if (position == 2) {
            return new SettingsFragment();
        } else if (position == 3) {
            return new ProfileFragment();
        } else {
            return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
