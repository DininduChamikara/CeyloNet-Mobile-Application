package com.dcpappsolution.incotest2.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dcpappsolution.incotest2.MainActivity;
import com.dcpappsolution.incotest2.R;


public class SelectionActivity extends AppCompatActivity {

    Button buyerBtn;
    Button sellerBtn;

    String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        buyerBtn = findViewById(R.id.buyerBtn);
        sellerBtn = findViewById(R.id.sellerBtn);

        buyerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginBuyer();
            }
        });

        sellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSeller();
            }
        });
    }

    private void loginBuyer() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_type", "buyer");
        startActivity(intent);
    }

    private void loginSeller(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("user_type", "seller");
        startActivity(intent);
    }

}