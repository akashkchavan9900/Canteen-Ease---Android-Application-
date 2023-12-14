package com.example.canteenpr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canteenpr.R;
import com.example.canteenpr.model.HistoryModel;
import com.example.canteenpr.model.MenuModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>{
    private Context mContext;
    List<HistoryModel> historyModels = new ArrayList<HistoryModel>();

    public HistoryAdapter(Context mContext, List<HistoryModel> historyModels) {
        this.mContext = mContext;
        this.historyModels = historyModels;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final HistoryAdapter.ViewHolder viewHolder, final int i) {
     HistoryModel historyModel=historyModels.get(i);
        viewHolder.name.setText("Paid To : \t"+historyModel.getReceiver_name());
        viewHolder.date.setText("Date : \t"+historyModel.getCreated_date());
        viewHolder.amount.setText("Amount : \t"+historyModel.getAmount());

    }

    @Override
  public int getItemCount() {
        return historyModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,date,amount;
        public ViewHolder(View view) {
            super(view);
            name= (TextView) view.findViewById(R.id.name);
            date= (TextView) view.findViewById(R.id.date);
            amount= (TextView) view.findViewById(R.id.amount);
        }
    }
}
