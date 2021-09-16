package com.example.a_gregos_capstone;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    PersonCategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.categories_view_pager);
        tabLayout = findViewById(R.id.categories_tabs);
        viewPager.setAdapter(new ViewPagerAdapter(this));
        categoryViewModel = ViewModelProviders.of(this).get(PersonCategoryViewModel.class);
        List<String> categoryNames = categoryViewModel.getCategoryNames();

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(categoryNames.get(position))).attach();
    }
}