package com.dcpappsolution.incotest2.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dcpappsolution.incotest2.R;
import com.dcpappsolution.incotest2.models.BuyerFavouriteModel;
import com.dcpappsolution.incotest2.models.PopularProductModel;
import com.dcpappsolution.incotest2.models.PopularRequestModel;
import com.dcpappsolution.incotest2.models.SellerFavouriteModel;
import com.dcpappsolution.incotest2.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class RequestsDetailedActivity extends AppCompatActivity {

    TextView price, rating, description;
    Button addToFavourite;

    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    ///////////
    PopularRequestModel popularRequestModel = null;
    SellerFavouriteModel sellerFavouriteModel = null;


    Object object;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_detailed);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        object = getIntent().getSerializableExtra("detail");


        if(object instanceof PopularRequestModel){
            popularRequestModel = (PopularRequestModel) object;
        }

        if(object instanceof SellerFavouriteModel){
            sellerFavouriteModel = (SellerFavouriteModel) object;
        }



        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_dec);


        if(popularRequestModel != null){

            rating.setText(popularRequestModel.getRating());
            description.setText(popularRequestModel.getDescription());
            price.setText("Price : Rs."+popularRequestModel.getPrice()+"/-");

        }
///////////////////
        else if(sellerFavouriteModel != null){

            rating.setText(sellerFavouriteModel.getRating());
            description.setText(sellerFavouriteModel.getDescription());
            price.setText(sellerFavouriteModel.getPrice());

        }

        addToFavourite = findViewById(R.id.add_to_favourite);
        addToFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToFavourite();
            }
        });
    }

    private void addedToFavourite() {
        String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String, Object> cartMap = new HashMap<>();


        if (object instanceof PopularRequestModel) {

            cartMap.put("titleNeed", popularRequestModel.getTitleNeed());
            cartMap.put("price", price.getText().toString());
            cartMap.put("description", popularRequestModel.getDescription());
            cartMap.put("rating", popularRequestModel.getRating());
            cartMap.put("type", popularRequestModel.getType());

            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);
        }

        firestore.collection("AddToFavouriteRequest").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(RequestsDetailedActivity.this, "Added to Favourite Requests", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}