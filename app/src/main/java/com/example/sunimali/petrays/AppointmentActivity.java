package com.example.sunimali.petrays;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class AppointmentActivity extends AppCompatActivity {

//initialize varibles
    Button locationb,timeb,next;
    TextView location,time,date;
    int PLACE_PICKER_REQUEST = 1;
    String locationadd, datee,timee;
    CalendarView calendarView;
    int minute,hour;
    static  int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        //get the views
        location = (TextView)findViewById(R.id.textViewLocation);
        time = (TextView)findViewById(R.id.textViewtime);
        locationb = ( Button)findViewById(R.id.buttonloca);
        timeb = ( Button)findViewById(R.id.buttonTime);
        next = ( Button)findViewById(R.id.buttonnsend);
        calendarView = (CalendarView)findViewById(R.id.calendarView);


        //get the location
        locationb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent;
                        try {
                            //select the place
                            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                            startActivityForResult(builder.build(AppointmentActivity.this), PLACE_PICKER_REQUEST);

                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }

                    }
                }
        );

        //get the date
        calendarView.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                        datee = year+"-"+month+"-"+dayOfMonth;

                    }
                }
        );

        //go to next activity
         next.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //parse data
                        Intent intent = new Intent(AppointmentActivity.this, ConfirmActivity.class);
                        intent.putExtra("time", timee);
                        intent.putExtra("date", datee);
                        intent.putExtra("address", locationadd);
                        startActivity(intent);
                    }
                }
        );

         //get the time
        timeb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //show time picker dialog
                        TimePickerDialog timePickerDialog = new TimePickerDialog(AppointmentActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                                timee = hourOfDay + ":" + minutes;
                                time.setText(hourOfDay + ":" + minutes);
                                showtime();

                            }
                        }, 0, 0, false);
                        timePickerDialog.show();

                    }
                }
        );
    }
    //make tosat to see time
    public void showtime(){
        Toast.makeText(this,timee,Toast.LENGTH_LONG).show();
    }

    //after place picked get the address
    public void onActivityResult(int requestCode, int resultCode ,Intent data) {
        if(requestCode==PLACE_PICKER_REQUEST && resultCode==RESULT_OK){
            Place place = PlacePicker.getPlace(data,this);
            locationadd =  String.format("Place: %s", place.getAddress());
            location.setText(locationadd);
            Toast.makeText(this,locationadd,Toast.LENGTH_LONG).show();

        }
    }



}
