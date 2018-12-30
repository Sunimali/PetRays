package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

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
    ImageView dp;


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
        dp = (ImageView)findViewById(R.id.imageViewDpPhoto);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databasePetOwners = FirebaseDatabase.getInstance().getReference("PetOwners");
        user.getUid();

        //set the textView
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

        //loadDetails();

        buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // go to home page
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

    /*private void loadDetails() {

        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

// Download directly from StorageReference using Glide
// (See MyAppGlideModule for Loader registration)
        Glide.with(this /* context *///)
                /*.load(storageReference)
                .into(dp);
    }*/


}
