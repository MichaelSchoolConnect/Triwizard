package com.lebogang.triwizard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lebogang.triwizard.adapter.HousesInfoAdapter;
import com.lebogang.triwizard.networking.NetworkUtils;
import com.lebogang.triwizard.pojo.Characters;
import com.lebogang.triwizard.pojo.CharactersInfo;
import com.lebogang.triwizard.pojo.HousesInfo;
import com.lebogang.triwizard.repo.MyRepository;
import com.lebogang.triwizard.viewmodel.CharactersInfoViewModel;
import com.lebogang.triwizard.viewmodel.HousesInfoViewModel;

import java.util.ArrayList;
import java.util.List;

public class CharactersInfoActivity extends AppCompatActivity {

    //A tag for logging.
    private final String TAG = CharactersInfoActivity.class.getSimpleName();

    //A String object that holds the route that is being called/appended to the API Endpoint.
    private final String ROUTE = "characters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_info);

        final TextView textView = findViewById(R.id.charac_info_tv);

        // Setting up our view model
        CharactersInfoViewModel viewModel =
                ViewModelProviders.of(this).get(CharactersInfoViewModel.class);

        // Observe the view model
        viewModel.getCharactersInfoLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String housesInfoList) {
                //Updating the UI.
                //contentLoadingProgressBar.hide();
                textView.setText(String.valueOf(housesInfoList));

                Log.i(TAG, "Update from CharactersInfoViewModel: " + housesInfoList);
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
