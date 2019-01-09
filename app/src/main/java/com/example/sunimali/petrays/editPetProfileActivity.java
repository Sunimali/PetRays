package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class editPetProfileActivity extends AppCompatActivity{

    String name,age,weight;
    EditText nameText,ageText,weightText;
    Button update;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String petOwnerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet_profile);

        //get the id of owner
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        petOwnerID = firebaseUser.getUid();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        age = intent.getStringExtra("age");
        weight = intent.getStringExtra("weight");


        ageText = (EditText)findViewById(R.id.editTextAgeU);
        weightText = (EditText)findViewById(R.id.editTextWeightU);
        update = (Button) findViewById(R.id.buttonUpdatePet);


        ageText.setText(age);
        weightText.setText(weight);

        //fetch data
       // getData();

        //update button
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updatePet();
            }
        });





    }

    public void updatePet(){
        getData();

        String method = "update";
        BackgroundTaskPets backgroundTaskPets = new BackgroundTaskPets(this);
        backgroundTaskPets.execute(method,petOwnerID,name,age,weight);

        finish();
        startActivity(new Intent(editPetProfileActivity.this,deleteViewEditPetProfileActivity.class));

    }

    public void getData(){
        age = ageText.getText().toString();
        weight = weightText.getText().toString();

    }
}
