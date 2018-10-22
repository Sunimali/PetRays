package com.example.sunimali.petrays;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class HomeVisitRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_visit_request);

        EditText appName = (EditText)findViewById(R.id.app_Name);
        EditText reasonForAppointment = (EditText)findViewById(R.id.reason);


    }
}
