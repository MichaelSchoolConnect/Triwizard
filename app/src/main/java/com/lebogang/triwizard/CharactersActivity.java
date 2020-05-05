package com.lebogang.triwizard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.triwizard.adapter.CharactersAdapter;
import com.lebogang.triwizard.networking.NetworkUtils;
import com.lebogang.triwizard.pojo.Characters;
import com.lebogang.triwizard.repo.MyRepository;
import com.lebogang.triwizard.viewmodel.CharactersViewModel;

import java.util.List;

public class CharactersActivity extends AppCompatActivity {

    //A String constant for Logging.
    private static final String TAG = CharactersActivity.class.getSimpleName();

    //The HousesActivity context instead of calling .this all the time where a context is wanted.
    private Context context = CharactersActivity.this;

    //Recyclerview that shows a list of houses.
    private RecyclerView mRecyclerView;

    //Adapter that binds data read  from the MyRepository class.
    private CharactersAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);

        //Show LoadingProgressBar
        final ContentLoadingProgressBar contentLoadingProgressBar = findViewById(R.id.charactersLoadingBar);
        contentLoadingProgressBar.show();

        //initializing the Recyclerview.
        mRecyclerView = findViewById(R.id.charac_recyclerView);

        //Setting the Recyclerview in a Linear fashion layout via the LayoutManager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setting up our view model
        CharactersViewModel viewModel = ViewModelProviders.of(this).get(CharactersViewModel.class);

        // Observe the view model
        viewModel.getCharactersLiveData().observe(this, new Observer<List<Characters>>() {
            @Override
            public void onChanged(List<Characters> charactersList) {
                //Updating the UI.
                contentLoadingProgressBar.hide();
                mAdapter = new CharactersAdapter(context, charactersList);
                mRecyclerView.setAdapter(mAdapter);

                Log.i(TAG, "Update from ViewModel: " + charactersList);
            }
        });

        //condition to check whether to request data provided there's an internet connection or not.
        if (NetworkUtils.isInternetAvailable()) {
            // This will start the off-the-UI-thread work that we want to perform.
            MyRepository.getInstance().getCharacters();
        }else{
            //Show AlertDialog to prompt user to get a connection.
            Toast.makeText(context, "No connection", Toast.LENGTH_LONG).show();
        }
    }
}
