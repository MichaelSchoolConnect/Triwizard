package com.lebogang.triwizard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lebogang.triwizard.pojo.Houses;
import com.lebogang.triwizard.repo.MyRepository;

import java.util.List;

public class HousesViewModel extends AndroidViewModel {
    @NonNull
    private MyRepository repo = MyRepository.getInstance();

    @NonNull
    private LiveData<List<Houses>> mLiveData;

    public HousesViewModel(@NonNull Application application) {
        super(application);
        // The local live data needs to reference the repository live data
        mLiveData = repo.getHousesLiveData();
    }

    @NonNull
    public LiveData<List<Houses>> getHousesLiveData() {
        return mLiveData;
    }

    /*@NonNull
    public LiveData<List<Spells>> getMyLiveData() {
        return mLiveData;
    }*/
}
