package com.lebogang.triwizard.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.triwizard.HousesInfoActivity;
import com.lebogang.triwizard.R;
import com.lebogang.triwizard.pojo.Houses;
import com.lebogang.triwizard.pojo.HousesInfo;

import java.util.Collections;
import java.util.List;

public class HousesInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = HousesInfoAdapter.class.getSimpleName();

    private Context context;

    private LayoutInflater inflater;
    private List<HousesInfo> data = Collections.emptyList();

    // Create constructor to initialize context and data sent from HousesActivity.
    public HousesInfoAdapter(Context context, List<HousesInfo> data) {
        Log.i(TAG, "Creating Constructor.");
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when ViewHolder is created.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Inflating the layout.");
        View view = inflater.inflate(R.layout.houses_info_item, parent, false);
        return new HousesInfoViewHolder(view);
    }

    // Bind data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.i(TAG, "Binding data.");
        // Get houses position of item in recyclerview to bind data and assign values from list
        HousesInfoViewHolder housesViewHolder = (HousesInfoViewHolder) holder;
        final HousesInfo houses = data.get(position);

        housesViewHolder.value_0.setText(houses.values_0);
        housesViewHolder.value_1.setText(houses.values_1);
        housesViewHolder.value_2.setText(houses.values_2);
        housesViewHolder.value_3.setText(houses.values_3);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Inner class that holds reference to view objects.
     * */
    static class HousesInfoViewHolder extends RecyclerView.ViewHolder {

        TextView value_0;
        TextView value_1;
        TextView value_2;
        TextView value_3;

        // Create constructor to get widget/view reference(s).
        public HousesInfoViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "Creating ViewHolder.");
            value_0 = itemView.findViewById(R.id.houses_info_tv1);
            value_1 = itemView.findViewById(R.id.houses_info_tv2);
            value_2 = itemView.findViewById(R.id.houses_info_tv3);
            value_3 = itemView.findViewById(R.id.houses_info_tv4);
        }
    }
}
