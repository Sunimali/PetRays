package com.example.sunimali.petrays;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewPetActivity extends AppCompatActivity {

    //initialize variables

    Spinner spinnerSpecies;
    ImageButton camera;
    Button buttonAddPet;
    CircleImageView NewDpOfPetImageView;
    EditText editTextPetName,editTextAge,editTextweight,editTextColour;
    static final int IMG_REQUEST = 1;
    Bitmap bitmap;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String petOwnerID;
    String dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pet);

        //get the views
        NewDpOfPetImageView = (CircleImageView)findViewById(R.id.NewDpOfPetImageView);
        camera = (ImageButton) findViewById(R.id.imageButtonCamera);
        buttonAddPet = (Button)findViewById(R.id.buttonAddPet);
        editTextPetName = (EditText)findViewById(R.id.editTextPetName);
        editTextAge = (EditText)findViewById(R.id.editTextAge);
        editTextweight = (EditText)findViewById(R.id.editTextWeight);
        editTextColour = (EditText)findViewById(R.id.editTextColour);
        spinnerSpecies = (Spinner)findViewById(R.id.spinnerSpecies);

        //make species drop down list
        ArrayAdapter<String> adapterSpecies = new ArrayAdapter<String>(NewPetActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.species));
        adapterSpecies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecies.setAdapter(adapterSpecies);

        //take a photo of pet
        getPhoto();

        //Add new pet
        buttonAddPet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        addNewPet();
                    }
                }
        );


    }

    private void addNewPet() {

        //fetching data from view
        String age = editTextAge.getText().toString().trim();
        String name = editTextPetName.getText().toString().trim();
        String weight = editTextweight.getText().toString().trim();
        String specie = spinnerSpecies.getSelectedItem().toString().trim();
        String colour = editTextColour.getText().toString().trim();
        //dp = imageToString(bitmap);

        //create pet
        Pet p = new Pet(name,age,specie,colour,dp,weight);

        //get the id of owner
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        petOwnerID = firebaseUser.getUid();

        //execute asyctask class
        String Method = "add";
        BackgroundTaskPets bp = new BackgroundTaskPets(this);
        bp.execute(Method,p.getAge(),p.getName(),p.getWeight(),p.getSpecie(),colour,p.getDp(),petOwnerID);

        //go back to my pets
        finish();
        startActivity(new Intent(NewPetActivity.this,MyPetsActivity.class));



    }

    //take phot from gallery
    public void getPhoto(){
        camera.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent,IMG_REQUEST);

                    }
                }
        );
    }

    //set photo to image view
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK &&data!=null){
            Uri path = data.getData();
            dp = path.getPath();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                NewDpOfPetImageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    //convert bitmap into string
    public String imageToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imagebytes, Base64.DEFAULT);

    }
}
