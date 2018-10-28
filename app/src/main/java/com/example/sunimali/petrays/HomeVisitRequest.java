package com.example.sunimali.petrays;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class HomeVisitRequest extends AppCompatActivity {

    EditText app_name, reason ,date,time ;
    TextView Name,Date,Time,ReasonForAppointment;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_visit_request);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        app_name=(EditText)findViewById(R.id.app_Name);
        reason=(EditText)findViewById(R.id.reason);
        date=(EditText)findViewById(R.id.date);
        time=(EditText)findViewById(R.id.time);
        submit=(Button)findViewById(R.id.submit);

        submit.setOnClickListener (new View.OnClickListener() {
            public void onClick(View v) {


                ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("Name", app_name.getText().toString()));
                postParameters.add(new BasicNameValuePair("Reason For Appointment", reason.getText().toString()));
                postParameters.add(new BasicNameValuePair("Date", date.getText().toString()));
                postParameters.add(new BasicNameValuePair("Time", time.getText().toString()));



                //String valid = "1";
                String response = null;

                try {
                    response = CustomHttpClient.executeHttpPost("http://localhost/appointment.php", postParameters);
                    String res=response.toString();
                    res= res.replaceAll("\\s+","");

                    if(res.equals("1"))  {


                        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Logsignup.this);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("userName", scnm.getText().toString());
                        editor.commit();


                        // fetchContacts();// get contact from phone and print to databse

                        Intent i = new Intent(getApplicationContext(),Home.class);
                        startActivity(i);

                    } else{

                        Toast toast=Toast.makeText(getApplicationContext(), "Sorry Something went wrong contact our customer service officer", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                } catch (Exception e) {
                    resul.setText(e.toString());
                }


            }
        });



    }









}
