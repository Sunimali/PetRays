package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.sunimali.petrays.Pets.MyPetsActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


       ImageView pets = (ImageView) findViewById(R.id.pets);
       pets.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // go to pets page
                startActivity(new Intent(HomeActivity.this,MyPetsActivity.class));

            }
        });


        ImageView appointments = (ImageView) findViewById(R.id.appointments);
        appointments.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to appointments page
                startActivity(new Intent(HomeActivity.this,AppointmentActivity.class));

            }
        });


        ImageView doctorinfo = (ImageView) findViewById(R.id.DoctorInfo);
        doctorinfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to doctor info page
                startActivity(new Intent(HomeActivity.this,doctorInfoActivity.class));

            }
        });


    }
}
