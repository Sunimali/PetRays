package com.example.sunimali.petrays;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class SignInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        Button button = (Button)findViewById(R.id.signInbutton2);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // go to signIn page
                startActivity(new Intent(SignInActivity.this,ProfileActivity.class));

            }
        });
    }

}
