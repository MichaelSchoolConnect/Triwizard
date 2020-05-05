package com.lebogang.triwizard.repo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lebogang.triwizard.networking.NetworkUtils;
import com.lebogang.triwizard.pojo.Characters;
import com.lebogang.triwizard.pojo.CharactersInfo;
import com.lebogang.triwizard.pojo.Houses;
import com.lebogang.triwizard.pojo.HousesInfo;
import com.lebogang.triwizard.pojo.Spells;
import com.lebogang.triwizard.threadexecutor.AppExecutors;

import org.json.JSONArray;
import org.json.JSONException;
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
    private MutableLiveData<List<HousesInfo>> mutableHousesInfoLiveData = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<List<Spells>> mutableSpellsLiveData = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<List<Characters>> mutableCharactersLiveData = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<List<CharactersInfo>> mutableCharactersInfoLiveData = new MutableLiveData<>();

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
    public LiveData<List<HousesInfo>> getHousesInfoLiveData() {
        return mutableHousesInfoLiveData;
    }

    @NonNull
    public LiveData<List<Spells>> getSpellsLiveData() {
        return mutableSpellsLiveData;
    }

    @NonNull
    public LiveData<List<Characters>> getCharactersLiveData() {
        return mutableCharactersLiveData;
    }

    @NonNull
    public LiveData<List<CharactersInfo>> getCharactersInfoLiveData() {
        return mutableCharactersInfoLiveData;
    }

    /**
     * This method gets called from an Activity's onCreate method.
     * It fetches data off the main thread using an Executor(Runnable object)
     */
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

                        //Set data to references.
                        house_data.id = jsonObject.getString("_id");
                        house_data.name = jsonObject.getString("name");
                        house_data.mascot = jsonObject.getString("mascot");
                        house_data.houseGhost = jsonObject.getString("houseGhost");
                        house_data.members = jsonObject.getString("members");

                        //Store the data into an ArrayList.
                        data.add(house_data);

                        //Post the value(s) of the data to the LiveData Object.
                        mutableHousesLiveData.postValue(data);
                        Log.i("Repo: ", String.valueOf(house_data.name));
                    }
                } catch (NullPointerException | JSONException j) {
                    j.printStackTrace();
                }
            }
        });
    }

    public void getSpells() {
        AppExecutors appExecutors = new AppExecutors();
        appExecutors.networkIO().execute(new Runnable() {
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
                } catch (NullPointerException | JSONException j) {
                    j.printStackTrace();
                }
            }
        });
    }

    public void getCharacters() {
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
                System.out.println("Character results: " + result);
                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(result);

                    for (int i = 0; i < jArray.length(); i++) {

                        //Get objects from the JSONArray.
                        JSONObject jsonObject = jArray.getJSONObject(i);

                        //Initialize an object of the class House so we can append data to it.
                        Characters characters = new Characters();

                        //
                        characters._id = jsonObject.getString("_id");
                        characters.name = jsonObject.getString("name");
                        characters.house = jsonObject.getString("bloodStatus");
                        characters.school = jsonObject.getString("species");

                        //Store the data into an ArrayList.
                        data.add(characters);

                        //Post the value(s) of the data to the LiveData Object.
                        mutableCharactersLiveData.postValue(data);
                        Log.i("Characters: ", characters.name);

                    }
                } catch (NullPointerException | JSONException j) {
                    j.printStackTrace();
                }
            }
        });
    }

    /**
     * NOTE: PLEASE USE THIS AS A BENCHMARK FOR THAT ALSO APPLY TO THE ABOVE CALLER METHODS.
     **/
    public void getHousesInfo(final String endPoint) {
        //I made this into a local variable so it can be killed after calling this method to save resources.
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Initialize an object of the class House so we can append data to it.
                HousesInfo house_data = new HousesInfo();

                //A data structure that will hold a collection of the returned JSON objects/data.
                List<HousesInfo> data = new ArrayList<>();

                List<String> data2 = new ArrayList<>();

                //Pass the parameter @endPoint and build it as a URL.
                URL url = NetworkUtils.buildUrl(endPoint);

                //The results that we get from the HTTP Response.
                String result = null;
                //Get the response from HTTP, if null, catch en exception.
                try {
                    result = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Printing out the results to the console for debugging purposes.
                System.out.println("Repo results: " + result);

                //A JSONArray object that we pass the results into for iteration purposes.
                //Set it to null and initialize it later
                JSONArray jArray = null;

                //Try to get the data.
                try {
                    jArray = new JSONArray(result);

                    //Loop through the outter array.
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonObject = jArray.getJSONObject(i);

                        //Get id
                        house_data.id = jsonObject.getString("_id");
                        Log.i("Id", "Id: " + house_data.id);

                        //Access inside arrays
                        for (int e = 0; e < jArray.length(); e++) {
                            JSONObject object = jArray.getJSONObject(e);
                            house_data.values_0 = object.getString("values");

                            /*JSONObject object1 = object.getJSONObject("members");
                            house_data.values_1 = object1.getString("");*/

                            //Store the data into an ArrayList.
                            data.add(house_data);
                        }

                        //Post the value(s) of the data to the LiveData Object.
                        mutableHousesInfoLiveData.postValue(data);
                        Log.i("Houses Info: ", String.valueOf(house_data.values_0));
                    }
                } catch (NullPointerException | JSONException j) {
                    j.printStackTrace();
                }
            }
        });
    }

    public void getCharactersInfo(final String endPoint) {
        //I made this into a local variable so it can be killed after calling this method to save resources.
        AppExecutors executors = new AppExecutors();
        executors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //Initialize an object of the class House so we can append data to it.
                CharactersInfo charactersInfo = new CharactersInfo();

                //A data structure that will hold a collection of the returned JSON objects/data.
                List<CharactersInfo> data = new ArrayList<>();

                //Pass the parameter @endPoint and build it as a URL.
                URL url = NetworkUtils.buildUrl(endPoint);

                //The results that we get from the HTTP Response.
                String result = null;

                //Get the response from HTTP, if null, catch en exception.
                try {
                    result = NetworkUtils.getResponseFromHttpUrl(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //Printing out the results to the console for debugging purposes.
                System.out.println("Results: " + result);

                //Try to get the data.
                try {
                    assert result != null;
                    String id = null;
                    //Initialize JSONObject from JSON string.
                    JSONObject jsonArray = new JSONObject(result);
                    for(int i =0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = new JSONObject(result);
                         charactersInfo.c_id = jsonObject.getString("_id");
                        charactersInfo.role = jsonObject.getString("deathEater");
                        charactersInfo.school = jsonObject.getString("species");
                        charactersInfo.name = jsonObject.getString("name");
                    }

                    //Get id
                     id = jsonArray.getString("_id");
                    Log.i("Id", "Id: " + id);

                    data.add(charactersInfo);

                    //Post the value(s) of the data to the LiveData Object.
                    mutableCharactersInfoLiveData.postValue(data);
                    Log.i("Characters Info: ", charactersInfo.c_id);
                } catch (NullPointerException | JSONException j) {
                    j.printStackTrace();
                }
            }
        });
    }
}

