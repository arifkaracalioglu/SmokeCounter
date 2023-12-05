package com.haetech.smokecounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import android.os.Bundle;
import android.util.Log;

import com.haetech.smokecounter.database.CigaretteModel;
import com.haetech.smokecounter.database.CigaretteModelDatabase;
import com.haetech.smokecounter.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

}