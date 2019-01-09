package com.example.sunimali.petrays;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class AppointmentActivity extends AppCompatActivity {


    Button locationb,timeb,send;
    TextView location,time,date;
    int PLACE_PICKER_REQUEST = 1;
    String locationadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        location = (TextView)findViewById(R.id.textViewLocation);
        time = (TextView)findViewById(R.id.textViewTime);
        locationb = ( Button)findViewById(R.id.buttonloca);
        timeb = ( Button)findViewById(R.id.buttonTime);

        locationb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       // PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        Intent intent;
                        try {

                            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                            startActivityForResult(builder.build(AppointmentActivity.this), PLACE_PICKER_REQUEST);

                            //intent = builder.build(AppointmentActivity.this);
                           // startActivityForResult(PLACE_PICKER_REQUEST,RESULT_OK,intent);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );
    }

    public void onActivityResult(int requestCode, int resultCode ,Intent data) {
        if(requestCode==PLACE_PICKER_REQUEST && resultCode==RESULT_OK){
            Place place = PlacePicker.getPlace(data,this);
            locationadd =  String.format("Place: %s", place.getAddress());
            location.setText(locationadd);
            Toast.makeText(this,locationadd,Toast.LENGTH_LONG).show();

        }
    }
}
