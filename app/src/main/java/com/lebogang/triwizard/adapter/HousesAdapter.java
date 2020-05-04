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

import java.util.Collections;
import java.util.List;

public class HousesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = HousesAdapter.class.getSimpleName();

    private Context context;

    private LayoutInflater inflater;
    private List<Houses> data = Collections.emptyList();

    // Create constructor to initialize context and data sent from HousesActivity.
    public HousesAdapter(Context context, List<Houses> data) {
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
        View view = inflater.inflate(R.layout.houses_item, parent, false);
        return new HousesViewHolder(view);
    }

    // Bind data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.i(TAG, "Binding data.");
        // Get houses position of item in recyclerview to bind data and assign values from list
        HousesViewHolder housesViewHolder = (HousesViewHolder) holder;
        final Houses houses = data.get(position);
        housesViewHolder.textFishName.setText(houses.name);
        housesViewHolder.textSize.setText(houses.mascot);
        housesViewHolder.members.setText(houses.school);

        //Set the Recyclerview onClick and pass data to an Intent
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Position: " + position + "House Name: " + houses.name
                + " House Id: " + houses.id);
                Intent intent = new Intent(context, HousesInfoActivity.class);
                intent.putExtra("id", houses.id);
                context.startActivity(intent);
            }
        });
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Inner class that holds reference to view objects.
     * */
    static class HousesViewHolder extends RecyclerView.ViewHolder {

        TextView textFishName;
        TextView textSize;
        TextView members;

        // Create constructor to get widget/view reference(s).
        public HousesViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "Creating ViewHolder.");
            textFishName = itemView.findViewById(R.id.spells);
            textSize = itemView.findViewById(R.id.type);
            members = itemView.findViewById(R.id.effect);
        }
    }
}
