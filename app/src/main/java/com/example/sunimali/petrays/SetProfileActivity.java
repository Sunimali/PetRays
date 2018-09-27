package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SetProfileActivity extends AppCompatActivity {

   // public static final String ARTIST_NAME = "net.simplifiedcoding.firebasedatabaseexample.artistname";
    //public static final String ARTIST_ID = "net.simplifiedcoding.firebasedatabaseexample.artistid";

    ArrayList<String> p;
    EditText userName;
    Button nextbutton3;
    ImageButton addProfPicButton;

    DatabaseReference databasePetOwners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        p = new ArrayList<>();
        p =  intent.getStringArrayListExtra("petowner");

        userName = findViewById(R.id.editTextUserName);
        addProfPicButton = findViewById(R.id.imageButtonaddProfPic);
        nextbutton3 = (Button)findViewById(R.id.nextbutton3);
        nextbutton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //add petowner
                addPetOwner();
                // go to home page
                startActivity(new Intent(SetProfileActivity.this,ProfileActivity.class));

            }
        });
    }

    private void addPetOwner() {


        //getting the reference of artists node
        databasePetOwners = FirebaseDatabase.getInstance().getReference("PetOwners");

        String username = userName.getText().toString().trim();
        String id = databasePetOwners.push().getKey();

        PetOwner petOwner = new PetOwner(p.get(0),username,p.get(1),p.get(2),p.get(3),p.get(4),id);





        //Saving the PetOwner
        databasePetOwners.child(id).setValue(petOwner);

        Toast.makeText(this, "you are sign up with us", Toast.LENGTH_LONG).show();

    }
}
