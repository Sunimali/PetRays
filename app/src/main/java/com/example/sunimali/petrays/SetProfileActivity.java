package com.example.sunimali.petrays;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                startActivity(new Intent(SetProfileActivity.this,ProfileActivity.class));

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
                        //files = getFile();
                        //photopath = Uri.fromFile(files);
                        //camera.putExtra(MediaStore.EXTRA_OUTPUT, photopath);
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




        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("name",p.get(0));
                params.put("username", username);
                params.put("password", p.get(1));
                params.put("address", p.get(2));
                params.put("email", p.get(3));
                params.put("mobile", p.get(4));
                params.put("id",id);
                params.put("imageURL",imageToString(bitmap));

                //returing the response
                //Toast.makeText(SetProfileActivity.this,"sunimali", Toast.LENGTH_SHORT).show();
                //s = requestHandler.sendPostRequest(netConstants.URL_REGISTER, params);
                return requestHandler.sendPostRequest(netConstants.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
               // progressBar = (ProgressBar) findViewById(R.id.progressBar);
               // progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                //progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    //s= getJSONUrl(url);  //<< get json string from server
                    //s = get
                    JSONObject obj = new JSONObject(s);
                    //JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("PetOwner");

                        //creating a new user object
                         PetOwner petOwner1 = new PetOwner(
                                userJson.getString("name"),
                                userJson.getString("username"),
                                userJson.getString("password"),
                                userJson.getString("address"),
                                 userJson.getString("email"),
                                 userJson.getString("mobile"),
                                 userJson.getString("id"),
                                 userJson.getString("imageURL")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(petOwner1);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
        Log.e(tag, "addPetOwner: ."+username);
    }

    public String imageToString(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imagebytes = byteArrayOutputStream.toByteArray();
        return android.util.Base64.encodeToString(imagebytes, android.util.Base64.DEFAULT);

    }

}
