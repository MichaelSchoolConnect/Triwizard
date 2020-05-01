package com.lebogang.triwizard.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.triwizard.R;
import com.lebogang.triwizard.pojo.Houses;

import java.util.Collections;
import java.util.List;

public class HousesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = HousesAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private List<Houses> data = Collections.emptyList();

    // Create constructor to initialize context and data sent from MainActivity.
    public HousesAdapter(Context context, List<Houses> data){
        Log.i(TAG, "Creating Constructor.");

        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when ViewHolder is created.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Inflating the layout.");
        View view=inflater.inflate(R.layout.houses_item, parent,false);
        return new HousesViewHolder(view);
    }

    // Bind data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "Binding data.");
    // Get current position of item in recyclerview to bind data and assign values from list
        HousesViewHolder myHolder= (HousesViewHolder) holder;
        Houses current = data.get(position);
        myHolder.textFishName.setText(current.name);
        myHolder.textSize.setText(current.mascot);
        myHolder.members.setText(current.members);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    static class HousesViewHolder extends RecyclerView.ViewHolder{

        TextView textFishName;
        TextView textSize;
        TextView members;

        // Create constructor to get widget/views reference(s).
        public HousesViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "Creating ViewHolder.");
            textFishName= (TextView) itemView.findViewById(R.id.textView);
            textSize = (TextView) itemView.findViewById(R.id.textView2);
            members = itemView.findViewById(R.id.textView3);
        }

    }
}
