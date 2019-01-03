package com.example.sunimali.petrays;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static com.example.sunimali.petrays.SetProfileActivity.CAM_REQUEST;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity {

    //initialize variables
    ArrayList<String> p;
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
        Intent intent = getIntent();

        p = new ArrayList<>();
        p =  intent.getStringArrayListExtra("petowner");

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

        //get the bitmap and set image
        byte [] encodeByte=Base64.decode(p.get(5),Base64.DEFAULT);
        Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        dp.setImageBitmap(bitmap);



        //loadDetails();

        buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to home page //
                finish();
                startActivity(new Intent(ProfileActivity.this,HomeActivity.class));

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to update page
                finish();
                startActivity(new Intent(ProfileActivity.this,EditProfileActivity.class));

            }
        });

        //start camera
        buttonCam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,CAM_REQUEST);
            }
        });


    }
    //set image to imgeview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAM_REQUEST) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                dp.setImageBitmap(bitmap);
                String Method = "updateDP";
                Backgroundtask backgroundTask = new Backgroundtask(this);
                backgroundTask.execute(Method,user.getUid(),imageToString(bitmap));
            }
        }
    }

    //convert bitmap into string
    public String imageToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebytes = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(imagebytes, android.util.Base64.DEFAULT);

    }

}
