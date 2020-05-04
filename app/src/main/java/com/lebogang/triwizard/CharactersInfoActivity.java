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

import com.lebogang.triwizard.adapter.CharacterInfoAdapter;
import com.lebogang.triwizard.networking.NetworkUtils;
import com.lebogang.triwizard.pojo.CharactersInfo;
import com.lebogang.triwizard.repo.MyRepository;
import com.lebogang.triwizard.viewmodel.CharactersInfoViewModel;

import java.util.List;

public class CharactersInfoActivity extends AppCompatActivity {

    //A tag for logging.
    private final String TAG = CharactersInfoActivity.class.getSimpleName();

    //The CharactersAActivity context instead of calling .this all the time where a context is wanted.
    private Context context = CharactersInfoActivity.this;

    //Recyclerview that shows a list of houses.
    private RecyclerView mRecyclerView;

    //Adapter that binds data read  from the MyRepository class.
    private CharacterInfoAdapter mAdapter;

    //A String object that holds the route that is being called/appended to the API Endpoint.
    private final String ROUTE = "characters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_info);

        //Show LoadingProgressBar
        final ContentLoadingProgressBar contentLoadingProgressBar = findViewById(R.id.char_loadingBar);
        contentLoadingProgressBar.show();

        //initializing the Recyclerview.
        mRecyclerView = findViewById(R.id.charac_recyclerView_rv);

        //Setting the Recyclerview in a Linear fashion layout via the LayoutManager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setting up our view model
        CharactersInfoViewModel viewModel =
                ViewModelProviders.of(this).get(CharactersInfoViewModel.class);

        // Observe the view model
        viewModel.getCharactersInfoLiveData().observe(this, new Observer<List<CharactersInfo>>() {
            @Override
            public void onChanged(List<CharactersInfo> housesInfoList) {
                //Updating the UI.
                contentLoadingProgressBar.hide();

                mAdapter = new CharacterInfoAdapter(context, housesInfoList);
                mRecyclerView.setAdapter(mAdapter);
                Log.i(TAG, "Update from ViewModel: ");
            }
        });

        //Receive data sent to this class via a Bundle.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("_id");
            Log.i(TAG, "Log Id: " + id);
            String url = NetworkUtils.genericEndpoint(ROUTE, id);
            // This will start the off-the-UI-thread work that we want to perform.
            MyRepository.getInstance().getCharactersInfo(url);
        }
    }
}
