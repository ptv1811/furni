package com.example.furni;

import android.content.Intent;
import android.os.Bundle;

import com.example.furni.Fragment.AboutUsFragment;
import com.example.furni.Fragment.CartFragment;
import com.example.furni.Fragment.ShopFragment;
import com.example.furni.Fragment.StoreFragment;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.util.Log;
import android.view.MenuItem;

import com.example.furni.Model.Shop;
import com.example.furni.Model.User;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.widget.Toast;

public class MainApp extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private User nuser;
    private FirebaseDatabase mDB;
    private DatabaseReference myRef;

    Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

       /* if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new ShopFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_shop);

        }*/



        mDB=FirebaseDatabase.getInstance();
        myRef=mDB.getReference();

        final String userID=getIntent().getStringExtra("UserID");
        final String email=getIntent().getStringExtra("nusername");
        final String password=getIntent().getStringExtra("npassword");

        myRef.child("Users/").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user= dataSnapshot.getValue(User.class);

                if (user==null) {
                    nuser = new User(email, password);
                    myRef.child("Users/").child(userID + "/").setValue(nuser);
                }
                else
                    nuser=user;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        mDB=FirebaseDatabase.getInstance();
        myRef=mDB.getReference("Shop");



        int id = item.getItemId();
        final String userID=getIntent().getStringExtra("UserID");
        final String email=getIntent().getStringExtra("nusername");
        final String password=getIntent().getStringExtra("npassword");
        String tag="Hello";
        Log.i(tag,email);
        Bundle bundle=new Bundle();
        bundle.putString("UID",userID);
        //bundle.putString("Address",shop.getAddress());
        //bundle.putString("Phone",shop.getPhone());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shop=dataSnapshot.getValue(Shop.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        switch(id){
            case R.id.nav_shop:
                Log.i(tag,"BUNDLE" + bundle);
                ShopFragment shopFragment=new ShopFragment();
                shopFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,shopFragment).commit();
                break;
            case R.id.nav_about_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,new AboutUsFragment()).commit();
                break;
            case R.id.nav_store:
                StoreFragment storeFragment=new StoreFragment();
                storeFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,storeFragment).commit();
                break;
            case R.id.nav_cart:
                CartFragment cartFragment=new CartFragment();
                cartFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment,cartFragment).commit();
                break;
            case R.id.nav_sign_out:
                Toast.makeText(this, "Signing out", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(MainApp.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
