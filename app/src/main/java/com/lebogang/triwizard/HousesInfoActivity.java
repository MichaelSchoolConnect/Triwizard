package com.lebogang.triwizard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lebogang.triwizard.networking.NetworkUtils;
import com.lebogang.triwizard.pojo.HousesInfo;
import com.lebogang.triwizard.repo.MyRepository;
import com.lebogang.triwizard.viewmodel.HousesInfoViewModel;

import java.util.List;

public class HousesInfoActivity extends AppCompatActivity {

    //A tag for logging.
    private final String TAG = HousesInfoActivity.class.getSimpleName();

    //A String object that holds the route that is being called/appended to the API Endpoint.
    private final String ROUTE = "house";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses_id);

        //Initialize the Views we are binding data to.
        TextView name_tv = findViewById(R.id.house_id_name_tv);

        // Setting up our view model
        HousesInfoViewModel viewModel = ViewModelProviders.of(this).get(HousesInfoViewModel.class);

        // Observe the view model
        viewModel.getHousesInfoLiveData().observe(this, new Observer<List<HousesInfo>>() {
            @Override
            public void onChanged(List<HousesInfo> housesList) {
                //Updating the UI.
                //contentLoadingProgressBar.hide();

                Log.i(TAG, "Update from ViewModel: " + housesList);
            }
        });

        //Receive data sent to this class via a Bundle.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("id");
            name_tv.setText(id);
            String url = NetworkUtils.genericEndpoint(ROUTE, id);
            // This will start the off-the-UI-thread work that we want to perform.
            MyRepository.getInstance().getHousesInfo(url);
        }
    }
}
