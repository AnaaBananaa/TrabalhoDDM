package com.ddm.veggie.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ddm.veggie.R;
import com.ddm.veggie.databinding.ActivityAppBinding;
import com.ddm.veggie.view.fragment.HomeFragment;
import com.ddm.veggie.view.fragment.RecipesFragment;
import com.ddm.veggie.view.fragment.SettingsFragment;

public class AppActivity extends AppCompatActivity {
    private final HomeFragment HOME_FRAGMENT;
    private final RecipesFragment RECIPE_FRAGMENT;
    private final SettingsFragment SETTINGS_FRAGMENT;

    public AppActivity() {
        HOME_FRAGMENT = new HomeFragment();
        RECIPE_FRAGMENT = new RecipesFragment();
        SETTINGS_FRAGMENT = new SettingsFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAppBinding binding = ActivityAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(getResources().getColor(R.color.white, getTheme()));
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        if (savedInstanceState == null) {
            replaceFragment(HOME_FRAGMENT);
        }

        binding.appBottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(HOME_FRAGMENT);
                    return true;
                case R.id.recipe:
                    replaceFragment(RECIPE_FRAGMENT);
                    return true;
                case R.id.settings:
                    replaceFragment(SETTINGS_FRAGMENT);
                    return true;
                default:
                    return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.app_frame_layout, fragment);
        transaction.commit();
    }
}