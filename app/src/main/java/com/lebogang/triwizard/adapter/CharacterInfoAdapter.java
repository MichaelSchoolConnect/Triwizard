package com.lebogang.triwizard.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.triwizard.CharactersInfoActivity;
import com.lebogang.triwizard.R;
import com.lebogang.triwizard.pojo.CharactersInfo;

import java.util.Collections;
import java.util.List;

public class CharacterInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = CharactersInfoActivity.class.getSimpleName();

    private Context context;

    private LayoutInflater inflater;
    private List<CharactersInfo> data = Collections.emptyList();

    // Create constructor to initialize context and data sent from HousesActivity.
    public CharacterInfoAdapter(Context context, List<CharactersInfo> data) {
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
        View view = null;
        try {
            view = inflater.inflate(R.layout.character_info_item, parent, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new CharacterInfoViewHolder(view);
    }

    // Bind data
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Log.i(TAG, "Binding data.");
        // Get charactersInfo position of item in recyclerview to bind data and assign values from list
        CharacterInfoViewHolder characterInfoViewHolder = (CharacterInfoViewHolder) holder;
        final CharactersInfo charactersInfo = data.get(position);

        characterInfoViewHolder.value_0.setText(charactersInfo.name);
    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Inner class that holds reference to view objects.
     */
    static class CharacterInfoViewHolder extends RecyclerView.ViewHolder {

        TextView value_0;

        // Create constructor to get widget/view reference(s).
        public CharacterInfoViewHolder(View itemView) {
            super(itemView);
            Log.i(TAG, "Creating ViewHolder.");
            value_0 = itemView.findViewById(R.id.char_info_textView1);
        }
    }
}
