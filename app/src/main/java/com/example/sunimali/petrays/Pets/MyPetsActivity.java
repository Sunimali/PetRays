package com.example.sunimali.petrays.Pets;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.sunimali.petrays.Adapters.RecyclerViewAdapter;
import com.example.sunimali.petrays.Database.netConstants;
import com.example.sunimali.petrays.R;
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
import java.util.ArrayList;
import java.util.Arrays;

public class MyPetsActivity extends AppCompatActivity {

    private ArrayList<String> petsDP;
    private ArrayList<String> petsNames;
    FloatingActionButton addNewPet;
    String petOwnerID;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String [] age,name,weight,special_note,species,colour,image;

    //array for adapdter
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        //get the views
        addNewPet = (FloatingActionButton)findViewById(R.id.floatingActionButtonAddNewPet);

        //start NewPetActivity
        addNewPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPetsActivity.this,NewPetActivity.class));
            }
        });

        //get all dogs profile pictures and names
        getDogDpsAndNames();

    }

    public void getDogDpsAndNames(){
        //get the id of owner
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        petOwnerID = firebaseUser.getUid();

        //call asynctask class
        viewBackgroundTask v = new viewBackgroundTask(this);
        v.execute();

    }

    //call recyclerview adapeter.............

    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.recycleViewForPets);
         mNames= new ArrayList<String>(Arrays.asList(name));
         mImageUrls = new ArrayList<String>(Arrays.asList(image));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    //get pet details asyctask class.......................................................................

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

            //set url and make connection
            String viewProfileUrl = netConstants.URL_VIEWPETS;
            String id = petOwnerID;
            String result = "";
            try{

                URL url = new URL(viewProfileUrl+"&pet_owner_id="+id);
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
        //fetch data using json
            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonobject = null;
            age = new String[jsonarray.length()];
            name = new String[jsonarray.length()];
            weight = new String[jsonarray.length()];
            species = new String[jsonarray.length()];
            colour = new String[jsonarray.length()];
            image = new String[jsonarray.length()];

            Log.d("data", "received");


            for (int i = 0; i <= jsonarray.length(); i++) {

                try {
                    jsonobject = jsonarray.getJSONObject(i);

                    age[i] = jsonobject.getString("age");
                    name[i] = jsonobject.getString("name");
                    weight[i] = jsonobject.getString("weight");
                    species[i] = jsonobject.getString("species");
                    colour[i] = jsonobject.getString("colour");
                    image[i] = jsonobject.getString("image");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            //call method to set adapter
            initRecyclerView();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }



}
