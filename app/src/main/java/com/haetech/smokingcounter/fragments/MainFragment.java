package com.haetech.smokingcounter.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.haetech.smokingcounter.R;
import com.haetech.smokingcounter.adapters.RecylcerViewAdapterCigarette;
import com.haetech.smokingcounter.database.CigaretteModel;
import com.haetech.smokingcounter.database.CigaretteModelDatabase;
import com.haetech.smokingcounter.databinding.FragmentMainBinding;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainFragment extends Fragment {

    //ViewBinding
    FragmentMainBinding binding;
    //SharedPreferences and Database
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private CigaretteModelDatabase db;
    //Global variables
    private double priceForPiece = 1.0; //Default price for piece
    private List<CigaretteModel> list;
    private ArrayList<CigaretteModel> todayList;
    private Calendar calendar;
    Date today;
    Date todayStartingDate;
    //UI Elements
    RecyclerView mRecyclerViewCigarettes;
    //Adapters
    RecylcerViewAdapterCigarette recylcerViewAdapterCigarette;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = CigaretteModelDatabase.getInstance(requireActivity().getApplicationContext());

        setSharedPreferences();
        setVariables();
        checkFirstConfigurations();
        checkDayChange();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        checkPriceForPieceExists();
        setListeners();
        setCigarettesViewParams();
        setRecyclerView();

    }

    private void setSharedPreferences() {
        sharedPreferences = requireActivity().getSharedPreferences("day", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        priceForPiece = Double.parseDouble(sharedPreferences.getString("price", "1.0"));
    }

    private void setListeners() {
        binding.mBtnAddOne.setOnClickListener(v -> addCigarette());
        binding.mBtnRemoveOne.setOnClickListener(v -> removeCigarette());
    }

    private void setVariables() {
        todayList = new ArrayList<>();
        calendar = Calendar.getInstance();
        today = new Date(calendar.getTime().getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        todayStartingDate = new Date(calendar.getTime().getTime());
        todayList = (ArrayList<CigaretteModel>) db.cigaretteModelDao().getAllCigarettesByDate(todayStartingDate);
    }

    private void checkFirstConfigurations() {
        list = CigaretteModelDatabase.getInstance(requireActivity().getApplicationContext()).cigaretteModelDao().getAllCigarettes();
        if (list.size() != 0) {
            Log.e("Cigarettes", list.size() + getString(R.string.piece_of_cigarette_has_been_smoked));
        }
    }

    //When the day change, get cigarettes list size from db and set it to previous smoked, other cigarettes smoked today.
    private void checkDayChange() {
        if (sharedPreferences.contains("lastday")) {
            int dayOfMonth = calendar.get(Calendar.DAY_OF_YEAR);
            Log.e("DayOfMonth: ", String.valueOf(dayOfMonth));
            int lastDayOfYear = sharedPreferences.getInt("lastday", -1);
            if (lastDayOfYear != -1) {
                if (dayOfMonth != lastDayOfYear) {
                    //DAY CHANGED !!!
                }
            }
        } else {
            //There is no Last Day, so previous  smoked is 0
        }
    }

    private void setCigarettesViewParams() {
        setTotalText(todayList);
        setDetailedReport(db.cigaretteModelDao().getAllCigarettes());
        Log.e("Today's Size: ", todayList.size() + " piece");
    }

    private void setRecyclerView() {
        mRecyclerViewCigarettes = binding.mRecyclerViewCigarettes;
        recylcerViewAdapterCigarette = new RecylcerViewAdapterCigarette(requireActivity(), todayList);
        mRecyclerViewCigarettes.setAdapter(recylcerViewAdapterCigarette);
        mRecyclerViewCigarettes.setLayoutManager(new GridLayoutManager(requireActivity(), 10));
        notifyRecyclerViewAdapter();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void notifyRecyclerViewAdapter() {
        recylcerViewAdapterCigarette.notifyDataSetChanged();
    }


    private void addCigarette() {
        CigaretteModel cigarette = new CigaretteModel();
        cigarette.setPrice(priceForPiece);
        cigarette.setSmokedDate(today);
        calendar.setTime(today);
        db.cigaretteModelDao().insertOneCigarette(cigarette);
        todayList.add(cigarette);
        setCigarettesViewParams();
        notifyRecyclerViewAdapter();
    }

    private void removeCigarette() {
        CigaretteModel cigarette = db.cigaretteModelDao().getLastCigarette();
        if (cigarette != null) {
            db.cigaretteModelDao().delete(cigarette);
            if (todayList.size() >= 1) {
                todayList.remove(todayList.size() - 1);
            } else {
                list.remove(cigarette);
            }
            setCigarettesViewParams();
            notifyRecyclerViewAdapter();
        } else {
            Snackbar.make(binding.getRoot(), getString(R.string.there_is_no_cigarette_left_removable), 1000).show();
        }
    }

    private void setTotalText(List<CigaretteModel> todayCigarettes) {
        String valueOfTotalPrice;
        if (todayCigarettes.size() != 0){
            valueOfTotalPrice = todayCigarettes.size() + getString(R.string.piece) + new DecimalFormat("##.##").format(calculatePriceForCigarettes(todayCigarettes)) + getString(R.string.TRY) + getString(R.string.cost);
        }else{
            valueOfTotalPrice = todayCigarettes.size() + getString(R.string.piece) + "0.0" + getString(R.string.TRY) + getString(R.string.cost);
        }
        binding.mTextViewTotal.setText(valueOfTotalPrice);
    }

    private void setDetailedReport(List<CigaretteModel> allCigarettes) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getString(R.string.previoues_cigarettes_that_smoked) + allCigarettes.size() + getString(R.string.piece_of_smoked) + "\n");
        Log.e("double", String.valueOf(calculatePriceForCigarettes(allCigarettes)));
        stringBuilder.append(getString(R.string.for_those_cigarettes) + new DecimalFormat("##.##").format(calculatePriceForCigarettes(allCigarettes)) + getString(R.string.TRY) + getString(R.string.spend) + "\n\n");
        stringBuilder.append(getString(R.string.lacked_from_life) + (allCigarettes.size() * 12) + getString(R.string.minute) + "\n\n");
        stringBuilder.append(getString(R.string.illness) + "\n" + "Ağız, gırtlak ve akciğer kanseri." + "\n" + "KOAH");
        binding.mTextViewDetailedReport.setText(stringBuilder.toString());
    }

    private double calculatePriceForCigarettes(List<CigaretteModel> cigaretteModels) {
        double totalPrice = 0;
        for (CigaretteModel cigaretteModel : cigaretteModels) {
            totalPrice += cigaretteModel.getPrice();
        }
        return totalPrice;
    }

    private void checkPriceForPieceExists() {
        if (!sharedPreferences.contains("price")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            final View view = getLayoutInflater().inflate(R.layout.alert_dialog_put_price, binding.getRoot(), false);
            EditText editText = view.findViewById(R.id.mEditTextAlertDialogPrice);
            builder.setTitle(getString(R.string.price))
                    .setMessage(getString(R.string.put_price_message))
                    .setPositiveButton(getString(R.string.confirm), (dialog, which) -> {
                        setDefaultPriceFirstTime(editText.getText().toString());
                        dialog.dismiss();
                    })
                    .setCancelable(false);
        }
    }

    private void setDefaultPriceFirstTime(String priceStr) {
        double price = Double.parseDouble(priceStr);
        editor.putString("price", Double.toString(price));
        if (editor.commit()) {
            Snackbar.make(binding.getRoot(), getString(R.string.new_price_committed), 1000).show();
            priceForPiece = price;
        } else {
            Snackbar.make(binding.getRoot(), getString(R.string.new_price_cannot_committed), 1000).show();
        }
    }
}