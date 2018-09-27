package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MyPetsActivity extends AppCompatActivity {

    private ArrayList<String> petsDP;
    private ArrayList<String> petsNames;
    FloatingActionButton addNewPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        //get the views
        addNewPet = (FloatingActionButton)findViewById(R.id.floatingActionButtonAddNewPet);

        //start NewPetActivity
        addNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPetsActivity.this,NewPetActivity.class));
            }
        });

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
