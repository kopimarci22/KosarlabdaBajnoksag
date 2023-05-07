package com.example.kosarlabdabajnoksag.activities.regLog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kosarlabdabajnoksag.MainActivity;
import com.example.kosarlabdabajnoksag.R;
import com.example.kosarlabdabajnoksag.activities.Interface.User;
import com.example.kosarlabdabajnoksag.activities.Profil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registracio extends AppCompatActivity {

    EditText nameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordAgainEditText;
    private static final String LOG_TAG = Registracio.class.getName();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registracio);

        mAuth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.editTextUsername);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        passwordAgainEditText = findViewById(R.id.editTextConfirmPassword);
    }
    public void goToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
   /* public void register(View view) {

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();

        if (!password.equals(passwordAgain)) {
            Log.e(LOG_TAG, "A két jelszo nem egyezik meg.");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        String userUid = user.getUid();
                        Log.d(LOG_TAG, "User created Successfully.");
                        Toast.makeText(Registracio.this, "User created Successfully." + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        saveUserToFirebase(name, userUid, email);
                        navigateToProfile();
                        finish();
                    } else {
                        Log.e(LOG_TAG, "FirebaseUser is null.");
                    }
                } else {
                    Log.d(LOG_TAG, "User was not created Successfully.");
                    Toast.makeText(Registracio.this, "User was not created Successfully." + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }*/

    public void register(View view) {

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String passwordAgain = passwordAgainEditText.getText().toString();

        if(!password.equals(passwordAgain)){
            Log.e(LOG_TAG, "A két jelszo nem egyezik meg.");
        }
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        String userUid = user.getUid();
                        Log.d(LOG_TAG, "User created Successfully.");
                        Toast.makeText(Registracio.this, "User created Successfully." + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                saveUserToFirebase(name, userUid, email);
                                navigateToProfile();
                                finish();
                            }
                        }, 2000);
                    } else {
                        Log.e(LOG_TAG, "FirebaseUser is null.");
                    }
                } else {
                    Log.d(LOG_TAG, "User was not created Successfully.");
                    Toast.makeText(Registracio.this, "User was not created Successfully." + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void saveUserToFirebase(String name, String id, String email) {

        User user = new User() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public String getId() {
                return id;
            }

            @Override
            public String getEmail() {
                return email;
            }

        };

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("User").document(id).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(LOG_TAG, "User data saved to Firebase.");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, "Error saving user data to Firebase.", e);
                    }
                });
    }
    public void navigateToProfile() {
        Intent intent = new Intent(this, Profil.class);
        startActivity(intent);
    }
}
