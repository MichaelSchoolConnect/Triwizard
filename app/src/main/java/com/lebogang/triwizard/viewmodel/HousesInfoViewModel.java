package com.lebogang.triwizard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lebogang.triwizard.pojo.HousesInfo;
import com.lebogang.triwizard.repo.MyRepository;

import java.util.List;

public class HousesInfoViewModel extends AndroidViewModel {
    @NonNull
    private MyRepository repo = MyRepository.getInstance();

    @NonNull
    private LiveData<List<HousesInfo>> mLiveData;

    public HousesInfoViewModel(@NonNull Application application) {
        super(application);
        // The local live data needs to reference the repository live data
        mLiveData = repo.getHousesInfoLiveData();
    }

    @NonNull
    public LiveData<List<HousesInfo>> getHousesInfoLiveData() {
        return mLiveData;
    }

}
