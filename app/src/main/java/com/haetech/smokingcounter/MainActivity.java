package com.haetech.smokingcounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.haetech.smokingcounter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    private int currentDestination = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null){
            navController = navHostFragment.getNavController();
        }
        setListeners();
    }

    private void setListeners() {
        binding.mBtnNavigationHome.setOnClickListener(v -> navigate());
    }

    private void navigate() {
        if (navController != null){
            switch (currentDestination){
                case 0:
                    navController.navigate(R.id.mainToSettings);
                    currentDestination = 1;
                    setNavigationButtonView();
                    break;
                case 1:
                    navController.navigate(R.id.settingsToMain);
                    currentDestination = 0;
                    setNavigationButtonView();
                    break;
                default:
                    navController.navigate(R.id.mainToSettings);
                    currentDestination = 1;
                    setNavigationButtonView();
                    break;
            }
        }
    }

    private void setNavigationButtonView(){

        switch (currentDestination){
            case 0:
                binding.mBtnNavigationHome.setBackgroundResource(R.drawable.btn_nav_settings);
                break;
            case 1:
                binding.mBtnNavigationHome.setBackgroundResource(R.drawable.btn_nav_home);
                break;
        }
    }
}