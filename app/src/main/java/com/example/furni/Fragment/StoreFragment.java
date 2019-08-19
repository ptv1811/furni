package com.example.furni.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.furni.Model.Shop;
import com.example.furni.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class StoreFragment extends Fragment {

    TextView address,phone;
    String Address,Phone;
    String Longitude,Latitude;

    FirebaseDatabase database;
    private static final int REQUEST_CALL = 1;
    DatabaseReference myRef;
    Shop shop;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView=inflater.inflate(R.layout.fragment_store,container,false);

        address=RootView.findViewById(R.id.address);
        phone=RootView.findViewById(R.id.phone);
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Shop");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shop=dataSnapshot.getValue(Shop.class);

                address.setText(shop.getAddress());
                phone.setText(shop.getPhone());
                Longitude=shop.getLongitude();
                String tag="CC";
                Log.i(tag,"long "+Longitude);
                Latitude=shop.getLatitude();

                phone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makePhonecall();
                    }
                });

                address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMap(Longitude,Latitude);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return RootView;
    }

    private void showMap(String longitude,String latitude){
        Uri uri=Uri.parse("geo:3"+latitude+","+longitude+"?q="+latitude+","+longitude+"(Label+Name)");
        String tag="Hello";
        Log.i(tag,"add: "+uri);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }


    private void makePhonecall() {
        String number = phone.getText().toString();
        if (number.trim().length() > 0) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel: " + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }
        } else {
            Toast.makeText(getContext(),"Enter phone number",Toast.LENGTH_SHORT).show();
        }
    }
}
