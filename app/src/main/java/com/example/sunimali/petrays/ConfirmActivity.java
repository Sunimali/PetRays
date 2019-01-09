package com.example.sunimali.petrays;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConfirmActivity extends AppCompatActivity {

    Spinner spinnertypes;
    String [] types={"DirectVisit","HomeVisit"};
    EditText description;
    Button send;
    String type,desc,time,date,address,petOwnerID;
    String method;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        spinnertypes = (Spinner)findViewById(R.id.spinner2);
        send = (Button)findViewById(R.id.buttonnsend);
        description = (EditText)findViewById(R.id.editText);

        //make types drop down list
        ArrayAdapter<String> adaptertypes = new ArrayAdapter<String>(ConfirmActivity.this,android.R.layout.simple_list_item_1,types);
        adaptertypes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertypes.setAdapter(adaptertypes);

        Intent intent = getIntent();
        time = intent.getStringExtra("time");
        date = intent.getStringExtra("date");
        address = intent.getStringExtra("address");

        //get the id of owner
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        petOwnerID = firebaseUser.getUid();


        send.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        type = (String) spinnertypes.getSelectedItem();
                        desc =  description.getText().toString();
                       setAppointment();
                    }
                }
        );
    }

    public  void setAppointment(){
        method = "appointment";
        BackgroundTaskPets b = new BackgroundTaskPets(this);
        b.execute(method,time,date,address,type,desc,petOwnerID);


    }
}
