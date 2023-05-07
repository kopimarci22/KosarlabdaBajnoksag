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
import com.example.kosarlabdabajnoksag.activities.Profil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    EditText emailEditText;
    EditText passwordEditText;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        String userId = getIntent().getStringExtra("userId");

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
    }
    public void goToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
    public void login(View view) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(LOG_TAG, "User signed in Successfully.");
                    Toast.makeText(Login.this, "User logged in successfully.", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            navigateToProfile();
                            finish();
                        }
                    }, 2000);
            } else {
                    Log.d(LOG_TAG, "User could not sign in.");
                    Toast.makeText(Login.this, "User could not login.", Toast.LENGTH_LONG).show();

                }
        }
        });

        }
    public void navigateToProfile() {
        Intent intent = new Intent(this, Profil.class);
        startActivity(intent);
    }

}
