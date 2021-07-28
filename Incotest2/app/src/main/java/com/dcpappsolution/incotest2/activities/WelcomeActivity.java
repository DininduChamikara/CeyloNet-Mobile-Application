package com.dcpappsolution.incotest2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dcpappsolution.incotest2.MainActivity;
import com.dcpappsolution.incotest2.R;
import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        if(auth.getCurrentUser()!= null){
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            Toast.makeText(this, "please wait you are already logged in", Toast.LENGTH_SHORT).show();
           /////
            progressBar.setVisibility(View.GONE);
            finish();
        }
    }

    public void login(View view) {
        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
    }

    public void registration(View view) {
        startActivity(new Intent(WelcomeActivity.this, RegistrationActivity.class));
    }
}