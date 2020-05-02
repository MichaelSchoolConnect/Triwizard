package com.lebogang.triwizard.repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.triwizard.networking.NetworkUtils;
import com.lebogang.triwizard.pojo.Characters;
import com.lebogang.triwizard.pojo.Houses;
import com.lebogang.triwizard.pojo.Spells;
import com.lebogang.triwizard.threadexecutor.AppExecutors;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyRepository {

    //URL that points to the houses JSON
    private final static String HOUSES_ENDPOINT =
            "https://www.potterapi.com/v1/houses?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";

    //URL that points to the spells JSON
    private final static String SPELLS_ENDPOINT =
            "https://www.potterapi.com/v1/spells?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";

    //URL that points to the characters JSON
    private final static String CHARACTERS_ENDPOINT =
            "https://www.potterapi.com/v1/characters?key=$2a$10$1JEnmtEF417yBaFZcr51qukRjaKv8d5toEG5DKP/IUZWIVwfsaF7y";


    /**
     * This is an instance of the @class MyRepository class that will be used in the
     *
     * @Class HousesActivity to
     */
    private static MyRepository instance;

    @NonNull
    private MutableLiveData<List<Houses>> mutableHousesLiveData = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<List<Spells>> mutableSpellsLiveData = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<List<Characters>> mutableCharactersLiveData = new MutableLiveData<>();

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
    public LiveData<List<Houses>> getHousesLiveData() {
        return mutableHousesLiveData;
    }

    @NonNull
    public LiveData<List<Spells>> getSpellsLiveData() {
        return mutableSpellsLiveData;
    }

    @NonNull
    public LiveData<List<Characters>> getCharactersLiveData() {
        return mutableCharactersLiveData;
    }

    /**
     *This method gets data off the main thread using an Executor(Runnable object)
     * to read from a multi-dimension array
     * */
    public void getHouses() {
        //I made this into a local variable so it can be killed after calling this method to save resources.
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Houses> data = new ArrayList<>();
                List<Houses> data1 = new ArrayList<>();
                URL url = NetworkUtils.buildUrl(HOUSES_ENDPOINT);
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

                    for (int i = 0; i < jArray.length(); i++) {

                        //Get objects from the JSONArray.
                        JSONObject jsonObject = jArray.getJSONObject(i);

                        //Initialize an object of the class House so we can append data to it.
                        Houses house_data = new Houses();

                        //
                        house_data.name = jsonObject.getString("name");
                        house_data.mascot = jsonObject.getString("mascot");
                        house_data.houseGhost = jsonObject.getString("houseGhost");
                        house_data.members = jsonObject.getString("members");

                        //Store the data into an ArrayList.
                        data.add(house_data);

                        //Post the value(s) of the data to the LiveData Object.
                        mutableHousesLiveData.postValue(data);
                        Log.i("Repo: ", String.valueOf(house_data.name));

                        /*for(int e = 0; e < jArray.length(); e++){
                            JSONObject object = jArray.getJSONObject(e);
                            house_data.HOD = object.getString("values");
                            data1.add(house_data);
                            Log.i("Inner Array: ", String.valueOf(house_data.HOD));
                        }*/
                    }
                } catch (Exception j) {
                    j.printStackTrace();
                }
            }
        });
    }

    public void getSpells(){
        AppExecutors appExecutors = new AppExecutors();
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Spells> data = new ArrayList<>();
                URL url = NetworkUtils.buildUrl(SPELLS_ENDPOINT);
                String result = null;
                try {
                    result = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Spell results: " + result);
                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {

                        //Get objects from the JSONArray.
                        JSONObject jsonObject = jArray.getJSONObject(i);

                        //Initialize an object of the class House so we can append data to it.
                        Spells spells = new Spells();

                        //
                        spells.spell = jsonObject.getString("spell");
                        spells.type = jsonObject.getString("type");
                        spells.effect = jsonObject.getString("effect");

                        //Store the data into an ArrayList.
                        data.add(spells);

                        //Post the value(s) of the data to the LiveData Object.
                        mutableSpellsLiveData.postValue(data);
                        Log.i("Repo: ", String.valueOf(spells.spell));

                    }
                } catch (Exception j) {
                    j.printStackTrace();
                }
            }
        });
    }

    public void getCharacters(){
        AppExecutors appExecutors = new AppExecutors();
        appExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Characters> data = new ArrayList<>();
                URL url = NetworkUtils.buildUrl(CHARACTERS_ENDPOINT);
                String result = null;
                try {
                    result = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Spell results: " + result);
                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {

                        //Get objects from the JSONArray.
                        JSONObject jsonObject = jArray.getJSONObject(i);

                        //Initialize an object of the class House so we can append data to it.
                        Characters characters = new Characters();

                        //
                        characters.name = jsonObject.getString("name");
                        characters.role = jsonObject.getString("role");
                        characters.house = jsonObject.getString("bloodStatus");
                        characters.school = jsonObject.getString("school");

                        //Store the data into an ArrayList.
                        data.add(characters);

                        //Post the value(s) of the data to the LiveData Object.
                        mutableCharactersLiveData.postValue(data);
                        Log.i("Repo: ", String.valueOf(characters.role));

                    }
                } catch (Exception j) {
                    j.printStackTrace();
                }
            }
        });
    }

}

