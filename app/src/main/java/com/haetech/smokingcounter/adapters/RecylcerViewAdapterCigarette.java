package com.haetech.smokingcounter.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.haetech.smokingcounter.R;
import com.haetech.smokingcounter.database.CigaretteModel;

import java.util.ArrayList;

public class RecylcerViewAdapterCigarette extends RecyclerView.Adapter<RecylcerViewAdapterCigarette.RecyclerViewHolder> {

    private Activity activity;
    private ArrayList<CigaretteModel> cigaretteList;
    
    public RecylcerViewAdapterCigarette(Activity activity, ArrayList<CigaretteModel> list) {
        this.activity = activity;
        this.cigaretteList = list;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = activity.getLayoutInflater().inflate(R.layout.recyclerview_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return cigaretteList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageViewCigaretteRCV;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewCigaretteRCV = itemView.findViewById(R.id.mImageViewCigaretteRC);
        }
    }
}