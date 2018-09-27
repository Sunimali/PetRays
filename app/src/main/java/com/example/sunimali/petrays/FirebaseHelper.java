package com.example.sunimali.petrays;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseHelper {

    private FirebaseUser user;
    private DatabaseReference reference;

    public FirebaseHelper(FirebaseUser user, FirebaseDatabase database) {
        this.user = user;
        this.reference = database.getReference().child(user.getUid());
    }
}
