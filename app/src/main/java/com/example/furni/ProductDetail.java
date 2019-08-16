package com.example.furni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.furni.Model.Product;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

public class ProductDetail extends AppCompatActivity {

    TextView name_product, product_description, price_product;
    ImageView image_product;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton add_to_cart;
    ElegantNumberButton amount;

    String productID;
    String Model_SFB;
    String Model_SFA;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference product;

    ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        image_product = findViewById(R.id.image_product);
        product_description = findViewById(R.id.description_product);
        price_product = findViewById(R.id.price_product);
        name_product = findViewById(R.id.name_product);
        amount = findViewById(R.id.amount);
        add_to_cart = findViewById(R.id.add_to_cart);
        collapsingToolbarLayout = findViewById(R.id.collapse);

        firebaseDatabase = FirebaseDatabase.getInstance();
        product = firebaseDatabase.getReference("Product");

        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapseAppBar);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);


        if (getIntent() != null) {
            productID = getIntent().getStringExtra("ProductId");
        }
        if (!productID.isEmpty()) {
            getProductDetail(productID);
        }

    }

    private void getProductDetail(String productID) {
        product.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product p=dataSnapshot.getValue(Product.class);

                Picasso.get().load(p.getImage()).into(image_product);
                price_product.setText(p.getPrice());
                name_product.setText(p.getName());
                product_description.setText(p.getDescription());

                //Model_SFB=dataSnapshot.child("Sfb").getValue().toString();

                Model_SFB="https://github.com/ptv1811/furni/blob/master/app/sample/pod.sfb";
                String tag="hi";
                Log.i(tag,"HMM: "+Model_SFB);


                arFragment=(ArFragment)getSupportFragmentManager().findFragmentById(R.id.ar_fragment);

                arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                    placeModel(hitResult.createAnchor(),Model_SFB);
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void placeModel(Anchor anchor, String model_sfb) {

        ModelRenderable.builder()
                .setSource(this,Uri.parse(model_sfb))
                .setRegistryId(model_sfb)
                .build().thenAccept(modelRenderable -> addNodetoScreen(modelRenderable,anchor))
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                });
    }

    private void addNodetoScreen(ModelRenderable modelRenderable, Anchor anchor) {

        AnchorNode anchorNode=new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}

