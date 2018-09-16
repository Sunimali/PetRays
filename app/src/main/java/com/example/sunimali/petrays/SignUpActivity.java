package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button button = (Button)findViewById(R.id.SignUpbutton);
        final EditText editTextMobile = findViewById(R.id.editTextmobile);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String mobile = editTextMobile.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() != 12){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }
                //start sign up page
                Intent intent = new Intent(SignUpActivity.this, VerifySMSActivity.class);
                intent.putExtra("mobile", mobile);
                startActivity(intent);


            }
        });
    }
}
