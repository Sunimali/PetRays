package com.example.sunimali.petrays;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunimali.petrays.Database.Backgroundtask;
import com.example.sunimali.petrays.Database.netConstants;
import com.example.sunimali.petrays.PetOwner.EditProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.sunimali.petrays.SetProfileActivity.CAM_REQUEST;


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
    String ID,ownersName,ownerEmail,petOwnerID;
    String[] imagelist;

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


        //get the current user
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databasePetOwners = FirebaseDatabase.getInstance().getReference("PetOwners");
        petOwnerID =  user.getUid();

        //set the textView
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());

        //set image view
        if(netConstants.DP=="") {
            dpBackgroundTask dpbtask = new dpBackgroundTask(this);
            dpbtask.execute();
        }
        //loadDetails();

        buttonHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to home page //
                startActivity(new Intent(ProfileActivity.this,HomeActivity.class));

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to update page
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
                netConstants.DP = imageToString(bitmap);
                Backgroundtask backgroundTask = new Backgroundtask(this);
                backgroundTask.execute(Method,petOwnerID,imageToString(bitmap));
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

    public class dpBackgroundTask extends AsyncTask<String,Void,String> {

        Context context;

        dpBackgroundTask(Context context){

            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String viewProfileUrl = netConstants.URL_VIEWDP;
            String id = petOwnerID;
            String result = "";
            try{

                URL url = new URL(viewProfileUrl+"&id="+petOwnerID);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                String line;
                while((line=bufferedReader.readLine())!=null){
                    result+=line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;


            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonobject = null;

            imagelist = new String[jsonarray.length()];

            for (int i = 0; i <= jsonarray.length(); i++) {

                try {
                    jsonobject = jsonarray.getJSONObject(i);

                    imagelist[i] = jsonobject.getString("imageurl");


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
            dp.setImageBitmap(StringToBitMap(imagelist[0]));


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}
