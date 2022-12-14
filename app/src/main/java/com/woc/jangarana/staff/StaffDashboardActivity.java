package com.woc.jangarana.staff;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.woc.jangarana.PersonWiseData;
import com.woc.jangarana.R;
import com.woc.jangarana.databinding.ActivityStaffDashboardBinding;
import com.woc.jangarana.familyhead.DashboardActivity;
import com.woc.jangarana.fragments.AddFirstFragment;
import com.woc.jangarana.fragments.StaffDashboardFragment;
import com.woc.jangarana.fragments.StaffProfileFragment;
import com.woc.jangarana.fragments.UserProfileFragment;

public class StaffDashboardActivity extends AppCompatActivity {


    ActivityStaffDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStaffDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, new StaffDashboardFragment(this))
                .commit();

        binding.bottomNavForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bottomNavForm.setStrokeColor(getResources().getColor(R.color.color_primary));
                binding.bottomNavForm.setCardBackgroundColor(getResources().getColor(R.color.highlight_gray));
                binding.bottomNavProfile.setStrokeColor(getResources().getColor(R.color.white));
                binding.bottomNavProfile.setCardBackgroundColor(getResources().getColor(R.color.white));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flFragment,
                               new StaffDashboardFragment(StaffDashboardActivity.this))
                        .commit();
            }
        });

        binding.bottomNavProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.bottomNavProfile.setStrokeColor(getResources().getColor(R.color.color_primary));
                binding.bottomNavProfile.setCardBackgroundColor(getResources().getColor(R.color.highlight_gray));
                binding.bottomNavForm.setStrokeColor(getResources().getColor(R.color.white));
                binding.bottomNavForm.setCardBackgroundColor(getResources().getColor(R.color.white));
                startActivity(new Intent(StaffDashboardActivity.this, PersonWiseData.class));
            }
        });

    }
}