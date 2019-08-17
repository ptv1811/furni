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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.furni.Model.Order;
import com.example.furni.Model.Product;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {

    TextView name_product, product_description, price_product;
    ImageView image_product;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton add_to_cart;
    ElegantNumberButton amount;

    String productID;
    String Model_SFB;
    int Quantity;
    int order_amount;
    Order order;
    long maxid;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference product;
    private DatabaseReference myRef;
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
        myRef=firebaseDatabase.getReference();

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

                Model_SFB=dataSnapshot.child("Sfb").getValue().toString();
                String tag="hi";

                Log.i(tag,"hello there" + p.getName());
                Log.i(tag,"HMM: "+Model_SFB);
                Quantity=Integer.parseInt(dataSnapshot.child("Quantity").getValue().toString());
                Log.i(tag,"quantity: "+Quantity);
                amount.setRange(1,Quantity);

                Log.i(tag,"amount "+amount.getNumber());

                amount.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //order_amount=Integer.parseInt(amount.getNumber());
                        Log.i(tag,"amount "+order_amount);
                        Log.i(tag,"name" +p.getName());
                        add_to_cart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myRef.child("Users/").child("Orders/").addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()){
                                            Order o=dataSnapshot.getValue(Order.class);
                                            order=new Order(productID,p.getName(),amount.getNumber(),p.getPrice(),p.getImage());
                                            maxid=dataSnapshot.getChildrenCount();
                                            if (o==null){
                                                myRef.child(String.valueOf(maxid+1)).setValue(order);
                                            }
                                            else {
                                                order=o;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                        });

                        Toast.makeText(ProductDetail.this, "Added to cart", Toast.LENGTH_SHORT).show();
                    }
                });

                //order_amount=Integer.parseInt(amount.getNumber());




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
                .setSource(this,RenderableSource.builder()
                .setSource(this,Uri.parse(model_sfb),RenderableSource.SourceType.GLB)
                .setScale(0.5f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build())
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

