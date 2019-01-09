package com.example.sunimali.petrays;

import android.app.Activity;
import android.app.Dialog;
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


    Button locationb,timeb,send;
    TextView location,time,date;
    int PLACE_PICKER_REQUEST = 1;
    String locationadd;
    CalendarView calendarView;
    String datee;
    int minute;
    int hour;
    String timee;
    static  int DIALOG_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        location = (TextView)findViewById(R.id.textViewLocation);
        time = (TextView)findViewById(R.id.textViewtime);
        locationb = ( Button)findViewById(R.id.buttonloca);
        timeb = ( Button)findViewById(R.id.buttonTime);
        send = ( Button)findViewById(R.id.buttonsend);
        calendarView = (CalendarView)findViewById(R.id.calendarView);



        locationb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       // PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                        Intent intent;
                        try {

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

        calendarView.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {

                        datee = year+"-"+month+"-"+dayOfMonth;

                    }
                }
        );

        send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );
      //  showTimePickerDialog();
        timeb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
    public void showtime(){
        Toast.makeText(this,timee,Toast.LENGTH_LONG).show();
    }

    public void onActivityResult(int requestCode, int resultCode ,Intent data) {
        if(requestCode==PLACE_PICKER_REQUEST && resultCode==RESULT_OK){
            Place place = PlacePicker.getPlace(data,this);
            locationadd =  String.format("Place: %s", place.getAddress());
            location.setText(locationadd);
            Toast.makeText(this,locationadd,Toast.LENGTH_LONG).show();

        }
    }

  /*  public void showTimePickerDialog(){
        timeb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDialog(DIALOG_ID);
                    }
                }
        );
    }*/

    //@Override
   /* protected Dialog onCreateDialog(int id) {
        if(id==DIALOG_ID){
           return new TimePickerDialog(AppointmentActivity.this,kTimepickerListner,hour,minute,false);

        }
        return  null;
    }*/

  /*  protected TimePickerDialog.OnTimeSetListener kTimepickerListner=
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minutee) {
                    hour = hourOfDay;
                    minute = minutee;
                    timee = hour+":"+minute;
                    time.setText(timee);

                }
            };*/

}
