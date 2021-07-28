package com.dcpappsolution.incotest2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.dcpappsolution.incotest2.models.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String userType;

    int count;

    FirebaseAuth auth;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

    //    userType = getIntent().getStringExtra("user_type");

        userType = "buyer";

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //////////////////////
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);


        navController.navigate(R.id.nav_buyer_home);

        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_seller_home).setVisible(false);
        nav_Menu.findItem(R.id.nav_seller_profile).setVisible(false);
        nav_Menu.findItem(R.id.nav_seller_favourite).setVisible(false);
        nav_Menu.findItem(R.id.nav_add_prod_service).setVisible(false);
        nav_Menu.findItem(R.id.nav_my_prod_service).setVisible(false);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_buyer_home, R.id.nav_buyer_profile, R.id.nav_buyer_favourite, R.id.nav_add_request, R.id.nav_my_request)
                .setDrawerLayout(drawer)
                .build();

        userType = "seller";

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userType.equalsIgnoreCase("seller")){


                    navController.navigate(R.id.nav_seller_home);

                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_buyer_home).setVisible(false);
                    nav_Menu.findItem(R.id.nav_buyer_profile).setVisible(false);
                    nav_Menu.findItem(R.id.nav_buyer_favourite).setVisible(false);
                    nav_Menu.findItem(R.id.nav_add_request).setVisible(false);
                    nav_Menu.findItem(R.id.nav_my_request).setVisible(false);

                    nav_Menu.findItem(R.id.nav_seller_home).setVisible(true);
                    nav_Menu.findItem(R.id.nav_seller_profile).setVisible(true);
                    nav_Menu.findItem(R.id.nav_seller_favourite).setVisible(true);
                    nav_Menu.findItem(R.id.nav_add_prod_service).setVisible(true);
            //        nav_Menu.findItem(R.id.nav_my_prod_service).setVisible(true);

                    mAppBarConfiguration = new AppBarConfiguration.Builder(
                            R.id.nav_seller_home, R.id.nav_seller_profile, R.id.nav_seller_favourite, R.id.nav_add_prod_service, R.id.nav_my_prod_service)
                            .setDrawerLayout(drawer)
                            .build();

                    userType = "buyer";
                    //////////////////////
                    NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, mAppBarConfiguration);
                    NavigationUI.setupWithNavController(navigationView, navController);


                }
                else{


                    navController.navigate(R.id.nav_buyer_home);

                    Menu nav_Menu = navigationView.getMenu();
                    nav_Menu.findItem(R.id.nav_seller_home).setVisible(false);
                    nav_Menu.findItem(R.id.nav_seller_profile).setVisible(false);
                    nav_Menu.findItem(R.id.nav_seller_favourite).setVisible(false);
                    nav_Menu.findItem(R.id.nav_add_prod_service).setVisible(false);
                    nav_Menu.findItem(R.id.nav_my_prod_service).setVisible(false);

                    nav_Menu.findItem(R.id.nav_buyer_home).setVisible(true);
                    nav_Menu.findItem(R.id.nav_buyer_profile).setVisible(true);
                    nav_Menu.findItem(R.id.nav_buyer_favourite).setVisible(true);
                    nav_Menu.findItem(R.id.nav_add_request).setVisible(true);
                //    nav_Menu.findItem(R.id.nav_my_request).setVisible(true);


                    mAppBarConfiguration = new AppBarConfiguration.Builder(
                            R.id.nav_buyer_home, R.id.nav_buyer_profile, R.id.nav_buyer_favourite, R.id.nav_add_request, R.id.nav_my_request)
                            .setDrawerLayout(drawer)
                            .build();
                    userType = "seller";

                    //////////////////////
                    NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, mAppBarConfiguration);
                    NavigationUI.setupWithNavController(navigationView, navController);

                    //////////////////

                }



            }
        });

        /////////// ---- try to inside if else loops---------////////////
        /////////// ---- try to inside if else loops----successful-----////////////
        View headerView = navigationView.getHeaderView(0);
        TextView headerName = headerView.findViewById(R.id.nav_header_name);
        TextView headerEmail = headerView.findViewById(R.id.nav_header_email);
        CircleImageView headerImg = headerView.findViewById(R.id.nav_header_img);

        database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserModel userModel = snapshot.getValue(UserModel.class);

                        headerName.setText(userModel.getName());
                        headerEmail.setText(userModel.getEmail());

                        Glide.with(MainActivity.this).load(userModel.getProfileImg()).into(headerImg);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        
    }


}