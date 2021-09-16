package com.example.a_gregos_capstone;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    List<String> categories;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        PersonCategoryViewModel categoryViewModel = new PersonCategoryViewModel(fragmentActivity.getApplication());
        categories = categoryViewModel.getCategoryNames();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        fragment = new PeopleFragment(categories.get(position));

        return fragment;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

}