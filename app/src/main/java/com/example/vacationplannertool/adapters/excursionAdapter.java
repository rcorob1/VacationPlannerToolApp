package com.example.vacationplannertool.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vacationplannertool.DetailedExcursion;
import com.example.vacationplannertool.R;
import com.example.vacationplannertool.entity.Excursion;

import java.util.List;

public class excursionAdapter extends RecyclerView.Adapter<excursionAdapter.excursionViewHolder> {
    private List<Excursion> mExcs;
    private final Context context;
    private final LayoutInflater mInfl;

    public excursionAdapter(Context context) {
        mInfl = LayoutInflater.from(context);
        this.context = context;
    }

    public class excursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        public excursionViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemView=itemView.findViewById(R.id.excTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAbsoluteAdapterPosition();
                    final Excursion current = mExcs.get(pos);
                    Intent intent = new Intent(context, DetailedExcursion.class);
                    intent.putExtra("excId", current.getExcId());
                    intent.putExtra("vacId", current.getVacId());
                    intent.putExtra("name", current.getExcName());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public excursionAdapter.excursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInfl.inflate(R.layout.excursionblock, parent, false);
        return new excursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull excursionAdapter.excursionViewHolder holder, int pos) {
        if(mExcs == null) {
            holder.excursionItemView.setText("No excursion name exists!");
        }
        else {
            Excursion cur = mExcs.get(pos);
            String excName = cur.getExcName();
            holder.excursionItemView.setText(excName);
        }
    }

    @Override
    public int getItemCount() {
        if(mExcs == null) {
            return 0;
        }
        else {
            return mExcs.size();
        }
    }

    public void setExcs(List<Excursion> excs) {
        mExcs = excs;
        notifyDataSetChanged();
    }
}
