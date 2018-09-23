package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//            setSupportActionBar(myToolbar);
            setContentView(R.layout.activity_profile);

        ImageButton button = (ImageButton)findViewById(R.id.imageButtonhome);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                // go to signIn page
                startActivity(new Intent(ProfileActivity.this,HomeActivity.class));

            }
        });



        }



}
