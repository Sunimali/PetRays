package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MyPets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);


        ImageButton button = (ImageButton)findViewById(R.id.imageButtondog);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to signIn page
                startActivity(new Intent(MyPets.this,deleteViewEditPetProfile.class));

            }
        });
    }
}
