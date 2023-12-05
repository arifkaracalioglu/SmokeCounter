package com.haetech.smokecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.haetech.smokecounter.database.CigaretteModel;
import com.haetech.smokecounter.database.CigaretteModelDatabase;
import com.haetech.smokecounter.databinding.ActivityEntryBinding;

import java.util.List;

public class EntryActivity extends AppCompatActivity {

    ActivityEntryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkDatabaseInstance();
        firstSettings();
    }

    private void checkDatabaseInstance(){
        CigaretteModelDatabase.getInstance(getApplicationContext());
    }

    private void firstSettings() {
        Animation animationScale = AnimationUtils.loadAnimation(EntryActivity.this, R.anim.scale_bigger);
        animationScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(EntryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        binding.mImageViewSplashLogo.setAnimation(animationScale);
        binding.mImageViewSplashLogo.startAnimation(animationScale);
    }
}