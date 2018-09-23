package com.example.sunimali.petrays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MyPetsActivity extends AppCompatActivity {

    private ArrayList<String> petsDP;
    private ArrayList<String> petsNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        //get all dogs profile pictures and names
        getDogDpsAndNames();
    }

    public void getDogDpsAndNames(){
        //initialize the recycleView
        initRecycleView();

    }

    public void initRecycleView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.recycleViewForPets);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(petsDP,petsNames,this);
        recyclerView.setAdapter(adapter);
    }


}
