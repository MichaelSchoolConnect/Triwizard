package com.lebogang.triwizard.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lebogang.triwizard.pojo.Characters;
import com.lebogang.triwizard.pojo.CharactersInfo;
import com.lebogang.triwizard.repo.MyRepository;

import java.util.List;

public class CharactersInfoViewModel extends AndroidViewModel {
    @NonNull
    private MyRepository repo = MyRepository.getInstance();

    @NonNull
    private LiveData<String> mLiveData;

    public CharactersInfoViewModel(@NonNull Application application) {
        super(application);
        // The local live data needs to reference the repository live data
        mLiveData = repo.getCharactersInfoLiveData();
    }

    @NonNull
    public LiveData<String> getCharactersInfoLiveData() {
        return mLiveData;
    }
}
