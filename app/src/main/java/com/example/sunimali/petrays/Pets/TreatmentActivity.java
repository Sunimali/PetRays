package com.example.sunimali.petrays.Pets;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sunimali.petrays.Adapters.RecyclerViewAdapterTreatments;
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

public class TreatmentActivity extends AppCompatActivity {

    private ArrayList<String> description = new ArrayList<>();
    private ArrayList<String> medicine = new ArrayList<>();
    String[] descriptiona,medicinea;
    String petOwnerID;
    String name;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        //get the id of owner
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        petOwnerID = firebaseUser.getUid();

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        //asyctask execute
        viewBackgroundTask viewBackgroundTask = new viewBackgroundTask(this);
        viewBackgroundTask.execute();
    }

    //array dapater......
    private void initRecyclerView(){

        RecyclerView recyclerView = findViewById(R.id.RecycleviewTreatment);
        description= new ArrayList<String>(Arrays.asList(descriptiona));
        medicine = new ArrayList<String>(Arrays.asList(medicinea));
        RecyclerViewAdapterTreatments adapter = new RecyclerViewAdapterTreatments(this, description, medicine);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    //fetch data in database.......................................................................

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

            String viewProfileUrl = netConstants.URL_VIEWTREAT;
            String pet_owner_id = petOwnerID;
            String result = "";
            try{
                //make connection and using bufferwritter and reader
                URL url = new URL(viewProfileUrl+"&pet_owner_id="+pet_owner_id+"&pet_name="+name);
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

        //fetching data using json..............................
        @Override
        protected void onPostExecute(String result) {

            JSONArray jsonarray = null;
            try {
                jsonarray = new JSONArray(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject jsonobject = null;
            descriptiona = new String[jsonarray.length()];
           medicinea= new String[jsonarray.length()];


            Log.d("data", "received");


            for (int i = 0; i <= jsonarray.length(); i++) {

                try {
                    jsonobject = jsonarray.getJSONObject(i);

                    descriptiona[i] = jsonobject.getString("description");
                    medicinea[i] = jsonobject.getString("medicines");


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
            //set adapter
            initRecyclerView();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }
}
