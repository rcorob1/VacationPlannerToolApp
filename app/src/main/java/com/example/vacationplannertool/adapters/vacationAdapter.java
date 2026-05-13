package com.example.vacationplannertool.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationplannertool.DetailedVacation;
import com.example.vacationplannertool.R;
import com.example.vacationplannertool.entity.Vacation;

import java.util.List;

public class vacationAdapter extends RecyclerView.Adapter<vacationAdapter.vacationViewHolder> {
    private List<Vacation> mVacs;
    private final Context context;
    private final LayoutInflater mInfl;

    public vacationAdapter(Context context) {
        mInfl = LayoutInflater.from(context);
        this.context = context;
    }

    public class vacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;
        public vacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView=itemView.findViewById(R.id.vacTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   int pos = getAbsoluteAdapterPosition();
                   final Vacation current = mVacs.get(pos);
                   Intent intent = new Intent(context, DetailedVacation.class);
                   intent.putExtra("id", current.getVacId());
                   intent.putExtra("name", current.getVacName());
                   context.startActivity(intent);
               }
            });
        }
    }

    @NonNull
    @Override
    public vacationAdapter.vacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInfl.inflate(R.layout.vacationblock, parent, false);
        return new vacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull vacationAdapter.vacationViewHolder holder, int pos) {
        if(mVacs == null) {
            holder.vacationItemView.setText("No vacation name exists!");
        }
        else {
            Vacation cur = mVacs.get(pos);
            String vacName = cur.getVacName();
            holder.vacationItemView.setText(vacName);
        }
    }

    @Override
    public int getItemCount() {
        if(mVacs == null) {
            return 0;
        }
        else {
            return mVacs.size();
        }
    }

    public void setVacs(List<Vacation>  vacs) {
        mVacs = vacs;
        notifyDataSetChanged();
    }
}
