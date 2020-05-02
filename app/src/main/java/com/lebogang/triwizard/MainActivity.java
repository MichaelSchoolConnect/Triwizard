package com.lebogang.triwizard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewHousesActivity(View view){
        startActivity(new Intent(this, HousesActivity.class));
    }

    public void viewSpellsActivity(View view){
        startActivity(new Intent(this, SpellsActivity.class));
    }
}
