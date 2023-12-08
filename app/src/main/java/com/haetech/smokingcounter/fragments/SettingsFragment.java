package com.haetech.smokingcounter.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.haetech.smokingcounter.R;
import com.haetech.smokingcounter.database.CigaretteModelDatabase;
import com.haetech.smokingcounter.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    FragmentSettingsBinding binding;
    //SharedPreferences
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    //Global variables
    Double priceForPiece;

    @Override
    public void onStart() {
        super.onStart();
        setListeners();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSharedPreferences();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void setListeners() {
        binding.mBtnConfirmSettings.setOnClickListener(v -> setDefaultPrice());
        binding.mBtnClearAllDatas.setOnClickListener(v -> clearAllDatas());
    }

    private void setSharedPreferences() {
        sharedPreferences = requireActivity().getSharedPreferences("day", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        priceForPiece = Double.parseDouble(sharedPreferences.getString("price", "1.0"));
    }

    private void setDefaultPrice() {
        String priceStr = binding.mEditTextPrice.getText().toString();
        double price = Double.parseDouble(priceStr);
        if (editor != null) {
            editor.putString("price", Double.toString(price));
            editor.commit();
            binding.mEditTextPrice.setText("");
            Snackbar.make(binding.getRoot(), getString(R.string.new_price_committed), 1000).show();
        } else {
            Snackbar.make(binding.getRoot(), getString(R.string.new_price_cannot_committed), 1000).show();
        }
    }

    private void clearAllDatas() {
        CigaretteModelDatabase.getInstance(requireActivity()).clearAllTables();
        editor.remove("price");
        if (editor.commit()){
            Snackbar.make(binding.getRoot(), getString(R.string.all_datas_clear_now), 1000).show();
        }
    }

}