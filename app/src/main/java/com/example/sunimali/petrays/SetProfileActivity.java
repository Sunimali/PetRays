package com.example.sunimali.petrays;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SetProfileActivity extends AppCompatActivity {


    ArrayList<String> p;
    EditText userName;
    Button nextbutton3;
    ImageButton addProfPicButton;
    static final int CAM_REQUEST = 1;
    String imageFileName;
    ImageView photo;
    File files;
    String url;

    StorageReference Dpreference;
    DatabaseReference databasePetOwners;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        Intent intent = getIntent();
        String mobile = intent.getStringExtra("mobile");
        p = new ArrayList<>();
        p =  intent.getStringArrayListExtra("petowner");

        userName = findViewById(R.id.editTextUserNameU);
        addProfPicButton = findViewById(R.id.imageButtonaddProfPic);
        nextbutton3 = (Button)findViewById(R.id.nextbutton3);
        photo = (ImageView)findViewById(R.id.imageViewPhoto);

        //Took the photo;
        getPhoto();

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


        //getting the reference of petowners node
        databasePetOwners = FirebaseDatabase.getInstance().getReference("PetOwners");

        String username = userName.getText().toString().trim();
        String id = databasePetOwners.push().getKey();

        PetOwner petOwner = new PetOwner(p.get(0),username,p.get(1),p.get(2),p.get(3),p.get(4),id,url);





        //Saving the PetOwner
        databasePetOwners.child(id).setValue(petOwner);

        Toast.makeText(this, "you are sign up with us", Toast.LENGTH_LONG).show();

    }

    private File getFile(){
        File folder = new File("sdcard/PetRays");
        if(!folder.exists()){
            folder.mkdir();
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_" + ".jpg";
        File imageFile = new File(folder,imageFileName);
        return imageFile;
    }
    public void getPhoto(){
        addProfPicButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        files = getFile();
                        camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(files));
                        startActivityForResult(camera,CAM_REQUEST);
                    }
                }
        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //set photo to imageView
        String path =  "sdcard/PetRays/"+imageFileName;
        photo.setImageDrawable(Drawable.createFromPath(path));


        //add photo to cloudStorage
        Uri file = Uri.fromFile(files);
        Dpreference = FirebaseStorage.getInstance().getReference();
        StorageReference storageReference = Dpreference.child("images/"+imageFileName);

        storageReference.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        url = downloadUrl.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(SetProfileActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
