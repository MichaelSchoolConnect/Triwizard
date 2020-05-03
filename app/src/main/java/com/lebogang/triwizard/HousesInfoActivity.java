package com.lebogang.triwizard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.lebogang.triwizard.adapter.HousesAdapter;
import com.lebogang.triwizard.adapter.HousesInfoAdapter;
import com.lebogang.triwizard.networking.NetworkUtils;
import com.lebogang.triwizard.pojo.HousesInfo;
import com.lebogang.triwizard.repo.MyRepository;
import com.lebogang.triwizard.viewmodel.HousesInfoViewModel;

import java.util.List;

public class HousesInfoActivity extends AppCompatActivity {

    //A tag for logging.
    private final String TAG = HousesInfoActivity.class.getSimpleName();

    //The HousesActivity context instead of calling .this all the time where a context is wanted.
    private Context context = HousesInfoActivity.this;

    //Recyclerview that shows a list of houses.
    private RecyclerView mRecyclerView;

    //Adapter that binds data read  from the MyRepository class.
    private HousesInfoAdapter mAdapter;

    //A String object that holds the route that is being called/appended to the API Endpoint.
    private final String ROUTE = "houses";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_houses_id);

        //Initialize the Views we are binding data to.

        //Show LoadingProgressBar
        final ContentLoadingProgressBar contentLoadingProgressBar = findViewById(R.id.hid_loadingBar);
        contentLoadingProgressBar.show();

        //initializing the Recyclerview.
        mRecyclerView = findViewById(R.id.house_id_name_rv);

        //Setting the Recyclerview in a Linear fashion layout via the LayoutManager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setting up our view model
        HousesInfoViewModel viewModel = ViewModelProviders.of(this).get(HousesInfoViewModel.class);

        // Observe the view model
        viewModel.getHousesInfoLiveData().observe(this, new Observer<List<HousesInfo>>() {
            @Override
            public void onChanged(List<HousesInfo> housesInfoList) {
                //Updating the UI.
                contentLoadingProgressBar.hide();
                mAdapter = new HousesInfoAdapter(context, housesInfoList);
                mRecyclerView.setAdapter(mAdapter);
                Log.i(TAG, "Update from ViewModel: " + housesInfoList);
            }
        });

        //Receive data sent to this class via a Bundle.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String id = extras.getString("id");
            String url = NetworkUtils.genericEndpoint(ROUTE, id);
            // This will start the off-the-UI-thread work that we want to perform.
            MyRepository.getInstance().getHousesInfo(url);
        }
    }
}
