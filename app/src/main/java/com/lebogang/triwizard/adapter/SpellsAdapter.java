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
import com.lebogang.triwizard.pojo.Spells;

import java.util.Collections;
import java.util.List;

public class SpellsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = SpellsAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private List<Spells> data = Collections.emptyList();

    // Create constructor to initialize context and data sent from HousesActivity.
    public SpellsAdapter(Context context, List<Spells> data){
        Log.i(TAG, "Creating Spell Constructor.");
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when ViewHolder is created.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Inflating the Spell layout.");
        View view = inflater.inflate(R.layout.spells_item, parent,false);
        return new SpellsViewHolder(view);
    }

    // Bind data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "Binding Spell data.");
        // Get spells position of item in recyclerview to bind data and assign values from list
        SpellsViewHolder myHolder= (SpellsViewHolder) holder;
        Spells spells = data.get(position);
        myHolder.spell.setText(spells.spell);
        myHolder.type.setText(spells.type);
        myHolder.effect.setText(spells.effect);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    static class SpellsViewHolder extends RecyclerView.ViewHolder{

        TextView spell;
        TextView type;
        TextView effect;

        // Create constructor to get widget/views reference(s).
        public SpellsViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "Creating Spell ViewHolder.");
            spell = itemView.findViewById(R.id.spells);
            type = itemView.findViewById(R.id.type);
            effect = itemView.findViewById(R.id.effect);
        }

    }
}
