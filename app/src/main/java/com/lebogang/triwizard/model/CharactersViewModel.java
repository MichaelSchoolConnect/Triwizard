package com.lebogang.triwizard.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lebogang.triwizard.pojo.Characters;
import com.lebogang.triwizard.pojo.Spells;
import com.lebogang.triwizard.repo.MyRepository;

import java.util.List;

public class CharactersViewModel extends AndroidViewModel {
    @NonNull
    private MyRepository repo = MyRepository.getInstance();

    @NonNull
    private LiveData<List<Characters>> mLiveData;

    public CharactersViewModel(@NonNull Application application) {
        super(application);
        // The local live data needs to reference the repository live data
        mLiveData = repo.getCharactersLiveData();
    }

    @NonNull
    public LiveData<List<Characters>> getCharactersLiveData() {
        return mLiveData;
    }
}
