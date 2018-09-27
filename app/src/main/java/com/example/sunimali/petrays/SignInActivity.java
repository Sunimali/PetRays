package com.example.sunimali.petrays;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class SignInActivity extends Activity {

    //initialize variables
    DatabaseReference databasePetOwners;
    Button signin;
    FirebaseAuth firebaseAuth;
    EditText editTextemail,editTextPassword;
    ProgressBar progressBar;
    //String email ,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //get view objects
        signin = (Button)findViewById(R.id.signInbutton2);
        editTextPassword = (EditText)findViewById(R.id.editTextpassword);
        editTextemail = (EditText)findViewById(R.id.editTextemail);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        //set progressbar invisible
        progressBar.setVisibility(View.INVISIBLE);

        //call login method
        userLogin();



    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    private void userLogin() {


        signin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //get input
                String email = editTextemail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                //check validation
               if (email.isEmpty()) {
                    editTextemail.setError("Email is required");
                    editTextemail.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextemail.setError("Please enter a valid email");
                    editTextemail.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

               firebaseAuth = FirebaseAuth.getInstance();

                //signIn using email and password
               firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            //if login is succesfull then go to profile
                            finish();
                            Intent intent = new Intent(SignInActivity.this, ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            //else show an error message
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }});
    }



}
