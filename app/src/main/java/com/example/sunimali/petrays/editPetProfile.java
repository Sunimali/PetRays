package com.example.sunimali.petrays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class editPetProfile extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet_profile);

        Spinner mySpinnerSpecies = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> myAdaptorSpecies = new ArrayAdapter<String>(editPetProfile.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.species));
        myAdaptorSpecies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinnerSpecies.setAdapter(myAdaptorSpecies);
    }
}
