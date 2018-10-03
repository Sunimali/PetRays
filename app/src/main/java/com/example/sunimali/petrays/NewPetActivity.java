package com.example.sunimali.petrays;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewPetActivity extends AppCompatActivity {

    //initialize variables

    Spinner spinnerSpecies;
    ImageButton camera;
    Button buttonAddPet;
    CircleImageView NewDpOfPetImageView;
    EditText editTextPetName,editTextAge,editTextweight,editTextBreed;
    static final int CAM_REQUEST = 1;
    String imageFileName;

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
        editTextBreed = (EditText)findViewById(R.id.editTextBreed);
        spinnerSpecies = (Spinner)findViewById(R.id.spinnerSpecies);

        //make species drop down list
        ArrayAdapter<String> adapterSpecies = new ArrayAdapter<String>(NewPetActivity.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.species));
        adapterSpecies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecies.setAdapter(adapterSpecies);

        //take a photo of pet
        getPhoto();

        //Add new pet
        addNewPet();

    }

    private void addNewPet() {

        String name = editTextPetName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String specie = spinnerSpecies.getSelectedItem().toString().trim();
        String breed = editTextBreed.getText().toString().trim();
        String dp = "";
        String weight = editTextweight.getText().toString().trim();

        Pet p = new Pet(name,age,specie,breed,dp,weight);


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
        camera.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File file = getFile();
                        camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                        startActivityForResult(camera,CAM_REQUEST);
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //String path = "sdcard/camApp/cam.jpg";
        String path =  "sdcard/VetsApp/"+imageFileName;
        //photo.setImageDrawable(Drawable.createFromPath(path));
    }
}
