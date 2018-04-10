package com.makarioworks.app.medmanager.ui.mainview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.makarioworks.app.medmanager.R;
import com.makarioworks.app.medmanager.model.Medication;

import java.util.List;

/**
 * Created by ndu on 4/5/18.
 */

public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.ViewHolder> {
    public List<Medication> medications;
    public Context context;

    public MedicationListAdapter(Context context, List<Medication> medications){
        this.context = context;
        this.medications = medications;
    }


    @Override
    public MedicationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_med_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicationListAdapter.ViewHolder holder, int position) {
        // Bind the values to the view
        // Get the medication at this position
        Medication medication = medications.get(position);
        // Get and bind the values to my holder
        holder.nameText.setText(medication.getName());
        holder.descrText.setText(medication.getDesc());
        holder.durationText.setText(medication.getFreq());
        holder.startText.setText(String.valueOf(medication.getStart()));
        holder.endText.setText(String.valueOf(medication.getEnd()));
        
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText, descrText, durationText, startText, endText;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.layout_name);
            descrText = itemView.findViewById(R.id.layout_desc);
            durationText = itemView.findViewById(R.id.layout_duration);
            startText = itemView.findViewById(R.id.layout_start_date);
            endText = itemView.findViewById(R.id.layout_end_date);
        }
    }
}
