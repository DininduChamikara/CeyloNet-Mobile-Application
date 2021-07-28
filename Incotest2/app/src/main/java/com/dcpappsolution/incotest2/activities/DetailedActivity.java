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
import com.dcpappsolution.incotest2.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView price, rating, description;
    Button addToFavourite;

    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    ViewAllModel viewAllModel = null;

    ///////////
    PopularProductModel popularProductModel = null;
    BuyerFavouriteModel buyerFavouriteModel = null;
    Object object;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        ///
        object = getIntent().getSerializableExtra("detail");
//        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof ViewAllModel){
            viewAllModel = (ViewAllModel) object;
        }

        ///////////
        else if(object instanceof PopularProductModel){
            popularProductModel = (PopularProductModel) object;
        }

        else if(object instanceof BuyerFavouriteModel){
            buyerFavouriteModel = (BuyerFavouriteModel) object;
        }


        detailedImg = findViewById(R.id.detailed_img);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_dec);

//////////

        if(viewAllModel != null){
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            description.setText(viewAllModel.getDescription());
            price.setText("Price :Rs."+viewAllModel.getPrice()+"/kg");



            if(viewAllModel.getType().equals("egg")){
                price.setText("Price :Rs."+viewAllModel.getPrice()+"/dozen");

            }
            if(viewAllModel.getType().equals("milk")){
                price.setText("Price :Rs."+viewAllModel.getPrice()+"/litre");

            }
        }

/////////////

        if(popularProductModel != null){
            Glide.with(getApplicationContext()).load(popularProductModel.getImg_url()).into(detailedImg);
            rating.setText(popularProductModel.getRating());
            description.setText(popularProductModel.getDescription());
            price.setText("Price :"+popularProductModel.getPrice());


        }

        /////////
        if(buyerFavouriteModel != null){
            Glide.with(getApplicationContext()).load(buyerFavouriteModel.getImg_url()).into(detailedImg);
            rating.setText(buyerFavouriteModel.getRating());
            description.setText(buyerFavouriteModel.getDescription());
            price.setText(buyerFavouriteModel.getProductPrice());


        }


        ////////////


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

        if(object instanceof ViewAllModel){
//            cartMap.put("productName", viewAllModel.getName());
//            cartMap.put("productPrice", price.getText().toString());
//            cartMap.put("currentDate", saveCurrentDate);
//            cartMap.put("currentTime", saveCurrentTime);

            cartMap.put("productName", viewAllModel.getName());
            cartMap.put("productPrice", price.getText().toString());
            cartMap.put("description", viewAllModel.getDescription());
            cartMap.put("img_url", viewAllModel.getImg_url());
            cartMap.put("rating", viewAllModel.getRating());
            cartMap.put("type", viewAllModel.getType());

            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);
        }
        else if(object instanceof PopularProductModel){
//            cartMap.put("productName", popularProductModel.getName());
//            cartMap.put("productPrice", price.getText().toString());
//            cartMap.put("currentDate", saveCurrentDate);
//            cartMap.put("currentTime", saveCurrentTime);

            cartMap.put("productName", popularProductModel.getName());
            cartMap.put("productPrice", price.getText().toString());
            cartMap.put("description", popularProductModel.getDescription());
            cartMap.put("img_url", popularProductModel.getImg_url());
            cartMap.put("rating", popularProductModel.getRating());
            cartMap.put("type", popularProductModel.getType());

            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);
        }

//        cartMap.put("productName", viewAllModel.getName());
//     //   temporary commented
//  //      cartMap.put("productPrice", price.getText().toString());
//        cartMap.put("currentDate", saveCurrentDate);
//        cartMap.put("currentTime", saveCurrentTime);


        firestore.collection("AddToFavourite").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added to Favourite", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}