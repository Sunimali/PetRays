package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppointmentMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_menu);

        Button request = (Button)findViewById(R.id.buttonrequest);
        request.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to signIn page
                startActivity(new Intent(AppointmentMenuActivity.this,HomeVisitRequest1Activity.class));

            }
        });

        Button history = (Button)findViewById(R.id.buttonhistoryAppointment);
        history.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to signIn page
                startActivity(new Intent(AppointmentMenuActivity.this,AppointmentHistoryActivity.class));

            }
        });


    }
}
