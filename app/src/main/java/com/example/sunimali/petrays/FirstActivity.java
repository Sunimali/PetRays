package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        TextView textView = (TextView) findViewById(R.id.signuplable);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to signup page
                startActivity(new Intent(FirstActivity.this,SignUpActivity.class));

            }
        });

        Button button = (Button)findViewById(R.id.signInbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                // go to signIn page
               startActivity(new Intent(FirstActivity.this,SignInActivity.class));

            }
        });


    }
}
