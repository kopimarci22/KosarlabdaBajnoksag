package com.example.kosarlabdabajnoksag.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kosarlabdabajnoksag.MainActivity;
import com.example.kosarlabdabajnoksag.R;
import com.example.kosarlabdabajnoksag.activities.Interface.User;
import com.example.kosarlabdabajnoksag.activities.Interface.UserImpl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Profil extends AppCompatActivity {

    private static final String LOG_TAG = Profil.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    private FirebaseFirestore db;

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView birthdateTextView;

    private ListenerRegistration userListener;

    private boolean isUserLoggedIn = false;

    public Profil(){
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        nameTextView = findViewById(R.id.name_text_view);
        emailTextView = findViewById(R.id.email_text_view);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            Log.d(LOG_TAG,"Autentikalt user");
        } else {
            Log.d(LOG_TAG,"Nem autentikalt user");
            finish();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Query userQuery = db.collection("User").whereEqualTo("id", mAuth.getUid());
        userListener = userQuery.addSnapshotListener(this, new com.google.firebase.firestore.EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot snapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(LOG_TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    DocumentSnapshot document = snapshot.getDocuments().get(0);
                    User user = document.toObject(UserImpl.class);

                    if (user != null) {
                        nameTextView.setText(user.getName());
                        emailTextView.setText(user.getEmail());
                    }
                }
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        if (userListener != null) {
            userListener.remove();
            userListener = null;
        }
    }
    public void goToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void logOut(View view) {
        if (user != null) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Profil.this, "Előbb jelentkezz be.", Toast.LENGTH_LONG).show();
        }
    }
}
