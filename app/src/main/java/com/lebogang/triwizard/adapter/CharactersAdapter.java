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
import com.lebogang.triwizard.pojo.Characters;
import com.lebogang.triwizard.pojo.Houses;

import java.util.Collections;
import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = CharactersAdapter.class.getSimpleName();

    private LayoutInflater inflater;
    private List<Characters> data = Collections.emptyList();

    // Create constructor to initialize context and data sent from HousesActivity.
    public CharactersAdapter(Context context, List<Characters> data) {
        Log.i(TAG, "Creating Constructor.");

        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    // Inflate the layout when ViewHolder is created.
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i(TAG, "Inflating the layout.");
        View view = inflater.inflate(R.layout.characters_item, parent, false);
        return new CharactersViewHolder(view);
    }

    // Bind data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i(TAG, "Binding data.");
        // Get characters position of item in recyclerview to bind data and assign values from list
        CharactersViewHolder myHolder = (CharactersViewHolder) holder;
        Characters characters = data.get(position);
        myHolder.name.setText(characters.name);
        myHolder.role.setText(characters.role);
        myHolder.house.setText(characters.house);
        myHolder.school.setText(characters.school);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CharactersViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView role;
        TextView house;
        TextView school;

        // Create constructor to get widget/views reference(s).
        public CharactersViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "Creating ViewHolder.");
            name = itemView.findViewById(R.id.characters_name_tv);
            role = itemView.findViewById(R.id.characters_role_tv);
            house = itemView.findViewById(R.id.characters_house_tv);
            school = itemView.findViewById(R.id.characters_school_tv);
        }

    }
}
