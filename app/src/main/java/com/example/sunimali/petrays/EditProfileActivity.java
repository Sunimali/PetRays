package com.example.sunimali.petrays;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {

    //initialize variables

    EditText editTextname,editTextuserName,editTextpassword,editTextaddress,editTextemail,editTextmobile;
    Button update;
    String name,userName,password,address,email,mobile,petOwnerID;
    FirebaseAuth firebaseAuth;
    DatabaseReference databasePetOwners;
    FirebaseUser user;
    PetOwner p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //view objects

        editTextname = (EditText)findViewById(R.id.editTextNameU);
        editTextuserName = (EditText)findViewById(R.id.editTextUserNameU);
        editTextpassword = (EditText)findViewById(R.id.editTextPasswordU);
        editTextaddress = (EditText)findViewById(R.id.editTextAddressU);
        editTextemail = (EditText)findViewById(R.id.editTextEmailU);
        editTextmobile = (EditText)findViewById(R.id.editTextMobileU);
        update = (Button)findViewById(R.id.buttonUpdateProfile);

        //load current user

        loadUserInformation();


        //add clicklistener for update buttton
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTextemail.setError("Please enter a valid email");
                    editTextemail.requestFocus();
                    return;
                }
                //check email field is empty or not
                if (email.isEmpty()) {
                    editTextemail.setError("Email is required");
                    editTextemail.requestFocus();
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

                //change user profile deltails;
                user.updateEmail(p.getEmail());
                user.updatePassword(p.getPassword());
                user.updateEmail(p.getMobileNumber());

            }
        });




    }

    private void loadUserInformation() {
        user = firebaseAuth.getCurrentUser();

        if (user != null) {
            /*if (user.getPhotoUrl() != null) {
                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into(imageView);
            }*/

            databasePetOwners = FirebaseDatabase.getInstance().getReference("PetOwners");
            databasePetOwners.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    showData(dataSnapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void showData(DataSnapshot ds) {
        //get the currentuser's Id
        petOwnerID = user.getUid();

       /* p = new PetOwner();
        p.setName(ds.child(petOwnerID).getValue(PetOwner.class).getName());
        p.setUserName(ds.child(petOwnerID).getValue(PetOwner.class).getUserName());
        p.setPassword(ds.child(petOwnerID).getValue(PetOwner.class).getPassword());
        p.setAddress(ds.child(petOwnerID).getValue(PetOwner.class).getAddress());
        p.setEmail(ds.child(petOwnerID).getValue(PetOwner.class).getEmail());
        p.setMobileNumber(ds.child(petOwnerID).getValue(PetOwner.class).getMobileNumber());*/

        String Method = "view";
        Backgroundtask backgroundTask = new Backgroundtask(this);
        backgroundTask.execute(Method,user.getUid());

        //set the textfields
        editTextname.setText(p.getName());
        editTextuserName.setText(p.getUserName());
        editTextpassword.setText(p.getPassword());
        editTextaddress.setText(p.getAddress());
        editTextemail.setText(p.getEmail());
        editTextmobile.setText(p.getMobileNumber());

    }


}
