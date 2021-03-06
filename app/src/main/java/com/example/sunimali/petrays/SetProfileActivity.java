package com.example.sunimali.petrays;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.sunimali.petrays.Database.Backgroundtask;
import com.example.sunimali.petrays.Database.netConstants;
import com.example.sunimali.petrays.models.PetOwner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.io.ByteArrayOutputStream;
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
    Bitmap bitmap;
    String uploadUrl;
    String username;
    Uri photopath;
    String serverUrl;
    private static final String tag = "mytag";
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    //StorageReference Dpreference;
    DatabaseReference databasePetOwners;
    String mobile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        p = new ArrayList<>();
        p =  intent.getStringArrayListExtra("petowner");

        //view objects........................
        userName = findViewById(R.id.editTextUserNameU);
        addProfPicButton = findViewById(R.id.imageButtonaddProfPic);
        nextbutton3 = (Button)findViewById(R.id.nextbutton3);
        photo = (ImageView)findViewById(R.id.imageViewPhoto);

        username = userName.getText().toString().trim();

        //Took the photo;
        getPhoto();

        nextbutton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //add petowner
                addPetOwner();
                // go to home page
                //startActivity(new Intent(SetProfileActivity.this,ProfileActivity.class));
                Intent intent = new Intent(SetProfileActivity.this, ProfileActivity.class);
                intent.putExtra("mobile", mobile);
                netConstants.DP = imageToString(bitmap);
                p.add(imageToString(bitmap));
                intent.putStringArrayListExtra("petowner", p);
                startActivity(intent);

            }
        });
    }

    /*private void addPetOwner() {


      /*  //getting the reference of petowners node
        databasePetOwners = FirebaseDatabase.getInstance().getReference("PetOwners");

        String id = databasePetOwners.push().getKey();

        //PetOwner petOwner = new PetOwner(p.get(0),username,p.get(1),p.get(2),p.get(3),p.get(4),id,url);

        //Saving the PetOwner
        databasePetOwners.child(id).setValue(petOwner);
        Toast.makeText(this, "you are sign up with us", Toast.LENGTH_LONG).show();

    }*/


    //get image file
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
    //get photo using camera
    public void getPhoto(){
        addProfPicButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        startActivityForResult(camera,CAM_REQUEST);
                    }
                }
        );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
       if(resultCode==RESULT_OK){

           if(requestCode==CAM_REQUEST){
               bitmap = (Bitmap) data.getExtras().get("data");
               photo.setImageBitmap(bitmap);
           }
       }

        // Uri file = Uri.fromFile(files);
        //Dpreference = FirebaseStorage.getInstance().getReference();
       // StorageReference storageReference = Dpreference.child("images/"+imageFileName);

        /*storageReference.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getUploadSessionUri();
                        url = downloadUrl.toString();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        Toast.makeText(SetProfileActivity.this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/

    }

    public void addPetOwner(){

        //getting the reference of petowners node
        databasePetOwners = FirebaseDatabase.getInstance().getReference("PetOwners");
        final String id = databasePetOwners.push().getKey();

        PetOwner petOwner = new PetOwner(p.get(3),mobile,id);

        //Saving the PetOwner
        databasePetOwners.child(id).setValue(petOwner);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        String petownerid = user.getUid();

        String Method = "register";
        PetOwner p1 = new PetOwner(p.get(0),username, p.get(1), p.get(2),  p.get(3),  p.get(4),petownerid, imageToString(bitmap));

        //call asyctask exectue
        Backgroundtask backgroundTask = new Backgroundtask(this);
        backgroundTask.execute(Method,p1.getName(),p1.getUserName(),p1.getPassword(),p1.getAddress(),p1.getEmail(),p1.getMobileNumber(),p1.getPetOwnerID(),p1.getUrl());

    }
    //convert bitmap into string
    public String imageToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebytes = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(imagebytes, android.util.Base64.DEFAULT);

    }

}
