package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    //initialize variables

    Toolbar toolbar;
    TextView name;
    TextView email;
    TextView editProfile;
    ImageButton buttonHome,buttonNotification,buttonLogout,buttonCam;
    FirebaseAuth firebaseAuth;
    DatabaseReference databasePetOwners;
    FirebaseUser user;
    String ID,ownersName,ownerEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_profile);

            //view objects

        buttonHome = (ImageButton)findViewById(R.id.imageButtonhome);
        //ImageButton buttonNotification = (ImageButton)findViewById(R.id.imageButtonhome);
        buttonLogout = (ImageButton)findViewById(R.id.imageButtonLogout);
        buttonCam = (ImageButton)findViewById(R.id.imageButtoneditphoto);
        name = (TextView)findViewById(R.id.textViewName);
        email = (TextView)findViewById(R.id.textViewEmail);
        editProfile =  (TextView)findViewById(R.id.textViewEditProfile);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databasePetOwners = FirebaseDatabase.getInstance().getReference("PetOwners");
        user.getUid();

        //set the textView
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

        buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // go to signIn page
                startActivity(new Intent(ProfileActivity.this,HomeActivity.class));

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // go to update page
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));

            }
        });



    }












}
