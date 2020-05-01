package com.lebogang.triwizard.repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.triwizard.networking.NetworkUtils;
import com.lebogang.triwizard.pojo.Houses;
import com.lebogang.triwizard.threadexecutor.AppExecutors;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyRepository {

    final static String HARRY_POTTER_API =
            "https://www.potterapi.com/v1/houses?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";
    /**
     * This is an instance of the @class MyRepository class that will be used in the
     *
     * @Class MainActivity to
     */
    private static MyRepository instance;

    @NonNull
    private MutableLiveData<List<Houses>> mutableLiveData = new MutableLiveData<>();

    public static MyRepository getInstance() {
        if (instance == null) {
            synchronized (MyRepository.class) {
                if (instance == null) {
                    instance = new MyRepository();
                }
            }
        }
        return instance;
    }

    // This ensures that only the repository can cause a change
    @NonNull
    public LiveData<List<Houses>> getMyLiveData() {
        return mutableLiveData;
    }

    /**
     *This method gets data off the main thread using an Executor(Runnable object)
     * */
    public void getHouses() {
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Houses> data = new ArrayList<>();
                URL url = NetworkUtils.buildUrl(HARRY_POTTER_API);
                String result = null;
                try {
                    result = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Repo results: " + result);
                JSONArray jArray = null;

                try {
                    jArray = new JSONArray(result);
                    outerloop:
                    for (int i = 0; i < jArray.length(); i++) {

                        //Get objects from the JSONArray.
                        JSONObject jsonObject = jArray.getJSONObject(i);

                        //Initialize an object of the class House so we can append data to it.
                        Houses house_data = new Houses();

                        //
                        house_data.name = jsonObject.getString("name");
                        house_data.mascot = jsonObject.getString("mascot");

                        //Store the data into an ArrayList.
                        data.add(house_data);

                        //Post the value(s) of the data to the LiveData Object.
                        mutableLiveData.postValue(data);
                        Log.i("Repo: ", String.valueOf(house_data.name));

                        for(int e = 0; e < jArray.length(); e++){
                            JSONObject object = jArray.getJSONObject(e);
                            house_data.HOD = object.getString("members");
                            Log.i("Inner Array: ", String.valueOf(house_data.HOD));

                        }

                    }
                } catch (Exception j) {
                    j.printStackTrace();
                }
            }
        });
    }
}

