package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SignUpActivity extends AppCompatActivity {
    ArrayList<String> p;

    //view objects
    EditText editTextname,editTextaddress,editTextpassword,editTextEmail,editTextMobile;
    String name,email,address,password,ID;

    //our database reference object
    DatabaseReference databasePetOwners;

    FirebaseAuth mAuth;

    String mobile = "";
    boolean emailOK,mobileOK;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //getting views
        editTextname = (EditText) findViewById(R.id.editTextName);
        editTextaddress = (EditText) findViewById(R.id.editTextaddress);
        editTextpassword = (EditText) findViewById(R.id.editTextPasswordU);
        editTextEmail = (EditText) findViewById(R.id.editTextemail);
        Button button = (Button) findViewById(R.id.SignUpbutton);
        editTextMobile = findViewById(R.id.editTextmobile);

        mAuth = FirebaseAuth.getInstance();


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String mobile = editTextMobile.getText().toString().trim();
                getdetails();

                //check whether mobile number is valid or not
                if (mobile.isEmpty() || mobile.length() != 12) {
                    editTextMobile.setError("Enter a valid mobile");
                    editTextMobile.requestFocus();
                    return;
                } else {

                    //if it is valid check already is it registered or not
                    DatabaseReference petownerRef = FirebaseDatabase.getInstance().getReference("PetOwners");
                    petownerRef.orderByChild("mobileNumber").equalTo(mobile).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                //it means user already registered
                                editTextMobile.setError("This mobile number is already registered");

                            } else {
                                //It is new users
                                mobileOK = true;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });

                    petownerRef.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                //it means user already registered
                                editTextEmail.setError("This Email is already registered");

                            } else {
                                //It is new users
                                emailOK = true;

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {


                        }
                    });
                }

        if(mobileOK&&emailOK){
            Intent intent = new Intent(SignUpActivity.this, VerifySMSActivity.class);
            intent.putExtra("mobile", mobile);
            intent.putStringArrayListExtra("petowner", p);
            startActivity(intent);

        }


            }
        });






    }



    private void getdetails(){
        //getting the values to save
        name = editTextname.getText().toString().trim();
        address = editTextaddress.getText().toString().trim();
        email = editTextEmail.getText().toString().trim();
        password = editTextpassword.getText().toString().trim();
        mobile = editTextMobile.getText().toString().trim();


        //check email field is empty or not
        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        //check pattern of email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        //check password given or not
        if (password.isEmpty()) {
            editTextpassword.setError("Password is required");
            editTextpassword.requestFocus();
            return;
        }
        //check validation of password
        if (password.length() < 6) {
            editTextpassword.setError("Minimum lenght of password should be 6");
            editTextpassword.requestFocus();
            return;
        }

        //progressBar.setVisibility(View.VISIBLE);


    //add data to arrayList
        p = new ArrayList<>();
        p.add(name);
        p.add(password);
        p.add(address);
        p.add(email);
        p.add(mobile);

    }

}
