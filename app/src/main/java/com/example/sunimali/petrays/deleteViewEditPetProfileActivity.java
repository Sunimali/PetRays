package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class deleteViewEditPetProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_view_edit_pet_profile);


        Button buttonedit = (Button)findViewById(R.id.buttoneditprofile);
        buttonedit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to signIn page
                startActivity(new Intent(deleteViewEditPetProfileActivity.this,editPetProfileActivity.class));

            }
        });


        Button buttonview = (Button)findViewById(R.id.buttonViewProfile);
        buttonview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to signIn page
                startActivity(new Intent(deleteViewEditPetProfileActivity.this,viewPetProfileActivity.class));

            }
        });
    }
}
