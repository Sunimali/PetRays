package com.example.sunimali.petrays;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;
import java.util.ArrayList;


public class SignUpActivity extends AppCompatActivity {
    ArrayList<String> p;

    //view objects
    EditText editTextname,editTextaddress,editTextpassword,editTextEmail,editTextMobile;

    //we will use these constants later to pass the artist name and id to another activity
    public static final String ARTIST_NAME = "net.simplifiedcoding.firebasedatabaseexample.artistname";
    public static final String ARTIST_ID = "net.simplifiedcoding.firebasedatabaseexample.artistid";

    //our database reference object
    DatabaseReference databasePetOwners;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //getting views
        editTextname = (EditText) findViewById(R.id.editTextName);
        editTextaddress = (EditText) findViewById(R.id.editTextaddress);
        editTextpassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        Button button = (Button)findViewById(R.id.SignUpbutton);
        editTextMobile = findViewById(R.id.editTextmobile);





        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String mobile = editTextMobile.getText().toString().trim();
                getdetails();

                if(mobile.isEmpty() || mobile.length() != 12){
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                }
                //start sign up page
               // Intent intent = new Intent(SignUpActivity.this, VerifySMSActivity.class);
                Intent intent = new Intent(SignUpActivity.this, VerifySMSActivity.class);
                intent.putExtra("mobile", mobile);
                intent.putStringArrayListExtra("petowner",p);
                startActivity(intent);


            }
        });
    }

    private void getdetails() {
        //getting the values to save
        String name = editTextname.getText().toString().trim();
        String address = editTextaddress.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String mobile = editTextMobile.getText().toString().trim();

        p = new ArrayList<>();
        p.add(name);
        p.add(password);
        p.add(address);
        p.add(email);
        p.add(mobile);


    }

}
