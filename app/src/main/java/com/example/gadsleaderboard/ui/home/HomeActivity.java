package com.example.gadsleaderboard.ui.home;

import android.content.Intent;
import android.os.Bundle;

import com.example.gadsleaderboard.databinding.ActivityHomeBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gadsleaderboard.R;
import com.example.gadsleaderboard.ui.submission.SubmissionActivity;
import com.google.android.material.tabs.TabLayoutMediator;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        init();

        binding.submitButton.setOnClickListener(view -> startActivity(new Intent(this, SubmissionActivity.class)));
    }

    // tab titles
    private String[] titles = new String[]{"Learning Leaders", "Skill IQ Leaders"};

    private void init() {
        binding.content.viewPager.setAdapter(new ViewPagerFragmentAdapter(this));

        // attaching tab mediator
        new TabLayoutMediator(binding.content.tabLayout, binding.content.viewPager,
                (tab, position) -> tab.setText(titles[position])).attach();
    }

    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        ViewPagerFragmentAdapter(@NonNull FragmentActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return LearnersFragment.newInstance(false);
                case 1:
                    return LearnersFragment.newInstance(true);
            }
            return LearnersFragment.newInstance(false);
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }

}