package com.example.sunimali.petrays;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sunimali.petrays.Database.BackgroundTaskPets;
import com.example.sunimali.petrays.Database.netConstants;
import com.example.sunimali.petrays.Pets.MyPetsActivity;
import com.example.sunimali.petrays.Pets.TreatmentActivity;
import com.example.sunimali.petrays.Pets.editPetProfileActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class deleteViewEditPetProfileActivity extends AppCompatActivity {

    TextView namet,aget,weightt;
    String name,age,weight,special_note,species,colour,image,petOwnerID;
    String [] agelist,weightlist,special_notelist,specieslist,colourlist;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    BackgroundTaskPets backgroundTaskPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_view_edit_pet_profile);

        Intent intent = getIntent();
        name = intent.getStringExtra("image_name");

        //get the id of owner
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        petOwnerID = firebaseUser.getUid();

        //call asynctask class
        viewBackgroundTask v = new viewBackgroundTask(this);
        v.execute();

        backgroundTaskPets = new BackgroundTaskPets(this);

        //view objects
        namet = (TextView)findViewById(R.id.Name);
        aget = (TextView)findViewById(R.id.Age);
        weightt = (TextView)findViewById(R.id.Weight);
        namet.setText(name);
        String method = "find";


        //go to editprofile..............................................................

        Button buttonedit = (Button)findViewById(R.id.buttoneditprofile);
        buttonedit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to edit pet profile page
                Intent intent = new Intent(deleteViewEditPetProfileActivity.this, editPetProfileActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("weight", weight);
                startActivity(intent);

            }
        });

        //go to treatment pages...........................................................................

        Button buttonview = (Button)findViewById(R.id.buttonTreatments);
        buttonview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(deleteViewEditPetProfileActivity.this, TreatmentActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("weight", weight);
                startActivity(intent);


            }
        });

        //remove a pet.....................................................................................

        Button buttondelete = (Button)findViewById(R.id.buttondelete);
        buttondelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // remove pet
               String method = "delete";
               backgroundTaskPets.execute(method,name,petOwnerID);
                startActivity(new Intent(deleteViewEditPetProfileActivity.this,MyPetsActivity.class));

            }
        });
    }

    //aysntask claas..........................

    public class viewBackgroundTask extends AsyncTask<String,Void,String> {

        Context context;

        viewBackgroundTask(Context context){

            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String viewProfileUrl = netConstants.URL_FIND;
            String id = petOwnerID;
            String result = "";
            //get the whole pets
            try{

                //make the connection
                URL url = new URL(viewProfileUrl+"&pet_owner_id="+id+"&name="+name);
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

            //receive data back.......
            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonobject = null;

            //make array
            agelist = new String[jsonarray.length()];
            weightlist = new String[jsonarray.length()];
            special_notelist = new String[jsonarray.length()];
            specieslist = new String[jsonarray.length()];
            colourlist = new String[jsonarray.length()];

            Log.d("data", "received");

            for (int i = 0; i <= jsonarray.length(); i++) {
                try {
                    jsonobject = jsonarray.getJSONObject(i);

                    //get data from json and set in arrays
                    agelist[i] = jsonobject.getString("age");
                    weightlist[i] = jsonobject.getString("weight");
                    specieslist[i] = jsonobject.getString("species");
                    colourlist[i] = jsonobject.getString("colour");
                    special_notelist[i] = jsonobject.getString("special_note");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            //set the text fields
            aget.setText(agelist[0]);
            weightt.setText(weightlist[0]);
            age = agelist[0];
            weight = weightlist[0];


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }


}
