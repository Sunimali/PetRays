package com.example.sunimali.petrays;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class EditProfileActivity extends AppCompatActivity {

    //initialize variables

    EditText editTextname,editTextuserName,editTextpassword,editTextaddress,editTextemail,editTextmobile;
    Button update;
    String name,userName,password,address,email,mobile,petOwnerID;
    FirebaseAuth firebaseAuth;
    DatabaseReference databasePetOwners;
    FirebaseUser user;
    PetOwner p;
    String json_string;
    String[] namelist,userNamelist,passwordlist,addresslist,emaillist,mobilelist,petOwnerIDlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //view objects

        editTextname = (EditText)findViewById(R.id.editTextNameU);
        editTextuserName = (EditText)findViewById(R.id.editTextUserNameU);
        editTextpassword = (EditText)findViewById(R.id.editTextPasswordU);
        editTextaddress = (EditText)findViewById(R.id.editTextAddressU);
        editTextemail = (EditText)findViewById(R.id.editTextEmailU);
        editTextmobile = (EditText)findViewById(R.id.editTextMobileU);
        update = (Button)findViewById(R.id.buttonUpdateProfile);

        //load current user

        loadUserInformation();


        //get petowner details from text fields


        //add clicklistener for update buttton
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextemail.getText().toString().trim();
                password = editTextpassword.getText().toString().trim();

                p.setName(String.valueOf(editTextname.getText()));
                p.setUserName(String.valueOf(editTextuserName.getText()));
                p.setPassword(password);
                p.setAddress(String.valueOf(editTextaddress.getText()));
                p.setEmail(email);
                p.setMobileNumber(String.valueOf(editTextmobile.getText()));

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextemail.setError("Please enter a valid email");
                    editTextemail.requestFocus();
                    return;
                }
                //check email field is empty or not
                if (email.isEmpty()) {
                    editTextemail.setError("Email is required");
                    editTextemail.requestFocus();
                    return;
                }

                //check password given or not
                if (password.isEmpty()) {
                    editTextpassword.setError("Password is required");
                    editTextpassword.requestFocus();
                    return;
                }
                //check validation of password
                if (password.length() < 6) {
                    editTextpassword.setError("Minimum lenght of password should be 6");
                    editTextpassword.requestFocus();
                    return;
                }

                //change user profile deltails;
                user.updateEmail(p.getEmail());
                user.updatePassword(p.getPassword());
                user.updateEmail(p.getMobileNumber());

                updatePetOwnerDetails();


            }
        });




    }

    private void loadUserInformation() {
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        petOwnerID = user.getUid();
        Toast.makeText(this,petOwnerID,Toast.LENGTH_LONG).show();
        p = new PetOwner();
        getdata();
       // viewBackgroundTask vb = new viewBackgroundTask(this);
       // vb.execute(petOwnerID);

    }
    public void getdata(){
        viewBackgroundTask v = new viewBackgroundTask(this);
        v.execute();
    }

    private void showData() {

        Toast.makeText(this,namelist[0],Toast.LENGTH_LONG).show();

        editTextname.setText(namelist[0]);
           editTextuserName.setText(userNamelist[0]);
            editTextpassword.setText(passwordlist[0]);
            editTextaddress.setText(addresslist[0]);
        editTextemail.setText(addresslist[0]);
        editTextmobile.setText(mobilelist[0]);


    }

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

            String viewProfileUrl = netConstants.URL_VIEW;
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
            namelist = new String[jsonarray.length()];
            userNamelist = new String[jsonarray.length()];
            emaillist = new String[jsonarray.length()];
            passwordlist = new String[jsonarray.length()];
            mobilelist = new String[jsonarray.length()];
            addresslist = new String[jsonarray.length()];



            Log.d("data", "received");


            for (int i = 0; i <= jsonarray.length(); i++) {

                try {
                    jsonobject = jsonarray.getJSONObject(i);

                    namelist[i] = jsonobject.getString("name");
                    userNamelist[i] = jsonobject.getString("username");
                    emaillist[i] = jsonobject.getString("email");
                    passwordlist[i] = jsonobject.getString("password");
                    mobilelist[i] = jsonobject.getString("mob_no");
                    addresslist[i] = jsonobject.getString("address");


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
            printdata(namelist[0]);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }

    private void printdata(String result) {
        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        editTextmobile.setText(mobilelist[0]);
        editTextemail.setText(emaillist[0]);
        editTextaddress.setText(addresslist[0]);
        editTextname.setText(namelist[0]);
        editTextuserName.setText(userNamelist[0]);
        editTextpassword.setText(passwordlist[0]);
    }



    public void updatePetOwnerDetails(){


        String Method = "update";
        Backgroundtask backgroundTask = new Backgroundtask(this);
        backgroundTask.execute(Method,p.getName(),p.getUserName(),p.getPassword(),p.getAddress(),p.getEmail(),p.getMobileNumber(),petOwnerID);

    }


}
