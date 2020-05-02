package com.lebogang.triwizard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.triwizard.adapter.SpellsAdapter;
import com.lebogang.triwizard.model.SpellsViewModel;
import com.lebogang.triwizard.pojo.Spells;
import com.lebogang.triwizard.repo.MyRepository;

import java.util.List;

public class SpellsActivity extends AppCompatActivity {

    //A String constant for Logging.
    private static final String TAG = SpellsActivity.class.getSimpleName();

    //The HousesActivity context instead of calling .this all the time where a context is wanted.
    private Context context = SpellsActivity.this;

    //Recyclerview that shows a list of houses.
    private RecyclerView mRecyclerView;

    //Adapter that binds data read  from the MyRepository class.
    private SpellsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_spells);

            //Show LoadingProgressBar
            final ContentLoadingProgressBar contentLoadingProgressBar = findViewById(R.id.spellsLoadingBar);
            contentLoadingProgressBar.show();

            //initializing the Recyclerview.
            mRecyclerView = findViewById(R.id.spells_recyclerView);

            //Setting the Recyclerview in a Linear fashion layout via the LayoutManager.
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            // Setting up our view model
            SpellsViewModel viewModel = ViewModelProviders.of(this).get(SpellsViewModel.class);

            // Observe the view model
            viewModel.getSpellsLiveData().observe(this, new Observer<List<Spells>>() {
                @Override
                public void onChanged(List<Spells> spellsList) {
                    //Updating the UI.
                    contentLoadingProgressBar.hide();
                    mAdapter = new SpellsAdapter(context, spellsList);
                    mRecyclerView.setAdapter(mAdapter);

                    Log.i(TAG, "Update from ViewModel: " + spellsList);
                }
            });

            // This will start the off-the-UI-thread work that we want to perform.
            MyRepository.getInstance().getSpells();

        }catch (Exception e){
            e.printStackTrace();
        }
            }
}
