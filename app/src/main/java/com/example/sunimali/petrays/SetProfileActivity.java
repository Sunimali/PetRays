package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile);

        Button button = (Button)findViewById(R.id.nextbutton3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to home page
                startActivity(new Intent(SetProfileActivity.this,HomeActivity.class));

            }
        });
    }
}
