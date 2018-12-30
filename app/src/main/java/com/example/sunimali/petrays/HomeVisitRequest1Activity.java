package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeVisitRequest1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_visit_request1);

        Button button = (Button)findViewById(R.id.buttonnextforpayment);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to signIn page
                startActivity(new Intent(HomeVisitRequest1Activity.this,homeVisitRequest2Activity.class));

            }
        });
    }
}
