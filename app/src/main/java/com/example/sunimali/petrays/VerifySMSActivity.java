package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VerifySMSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_sms);

        Button button = (Button)findViewById(R.id.nextbutton2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to setprofile page
                startActivity(new Intent(VerifySMSActivity.this,SetProfileActivity.class));

            }
        });
    }
}
