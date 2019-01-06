package com.example.sunimali.petrays;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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

        //set image view
        String id = user.getUid();
        dpBackgroundTask dpbtask = new dpBackgroundTask(this);
        dpbtask.execute(id);

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

    public class dpBackgroundTask extends AsyncTask<String,Void,String> {

        Context context;
        String json_string;
        dpBackgroundTask(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            //progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


                try{
                    String viewProfileUrl = netConstants.URL_VIEWDP;
                    String id = params[0];
                    URL url = new URL(viewProfileUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String PostData = URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode(id,"UTF-8");

                    bufferedWriter.write(PostData);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result = "";
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
                } catch (IOException e) {
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

           // if(progressDialog.isShowing()) {
               // progressDialog.dismiss();
                json_string = result;
//                getPhoto(json_string);
          //  }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        public void getPhoto(String json_string){
            try {
                JSONObject jsonObject = new JSONObject(json_string);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String image = null;
                while(count<jsonArray.length()){
                    JSONObject jo = jsonArray.getJSONObject(count);
                    image = jo.getString("image");

                }
                //convert string to bitmap
                byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
                Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                dp.setImageBitmap(bitmap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
