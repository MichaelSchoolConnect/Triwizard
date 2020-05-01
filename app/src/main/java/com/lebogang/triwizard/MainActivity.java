package com.lebogang.triwizard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lebogang.triwizard.adapter.HousesAdapter;
import com.lebogang.triwizard.pojo.Houses;
import com.lebogang.triwizard.model.HousesViewModel;
import com.lebogang.triwizard.repo.MyRepository;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //A String constant for Logging.
    private static final String TAG = MainActivity.class.getSimpleName();

    //The MainActivity context instead of calling .this all the time where a context is wanted.
    private Context context = MainActivity.this;

    //Recyclerview that shows a list of houses.
    private RecyclerView mRecyclerView;

    //Adapter that binds data read  from the MyRepository class.
    private HousesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the Recyclerview.
        mRecyclerView = findViewById(R.id.housesRecyclerView);

        //Setting the Recyclerview in a Linear fashion layout via the LayoutManager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setting up our view model
        HousesViewModel viewModel = ViewModelProviders.of(this).get(HousesViewModel.class);

        // Observe the view model
        viewModel.getMyLiveData().observe(this, new Observer<List<Houses>>() {
            @Override
            public void onChanged(List<Houses> housesList) {
                //Updating the UI.
                mAdapter = new HousesAdapter(context, housesList);
                mRecyclerView.setAdapter(mAdapter);

                Log.i(TAG, "Update from ViewModel: " + housesList);
            }
        });

        // This will start the off-the-UI-thread work that we want to perform.
        MyRepository.getInstance().getHouses();
    }
}
