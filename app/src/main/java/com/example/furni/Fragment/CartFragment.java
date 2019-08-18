package com.example.furni.Fragment;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furni.CartAdapter;
import com.example.furni.Model.Cart;
import com.example.furni.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView totalPrice;
    Button confirm;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference request;
    DatabaseReference myRef;

    String CHANNEL_ID="Channel 1";

    String UID;
    int totalprice=0;

    private FirebaseRecyclerOptions<Cart> option;
    private FirebaseRecyclerAdapter<Cart, CartAdapter> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle=this.getArguments();
        if (bundle!=null){
            UID=bundle.getString("UID");
        }
        String tag="hello";
        Log.i(tag,"uidcart: "+UID);

       View RootView=inflater.inflate(R.layout.fragment_cart,container,false);

        totalPrice=RootView.findViewById(R.id.totalPrice);
        confirm=RootView.findViewById(R.id.confirm);

        firebaseDatabase=FirebaseDatabase.getInstance();
        request=firebaseDatabase.getReference();
        myRef=request.child("Users/").child(UID+"/").child("orders/");

        recyclerView=RootView.findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);

        loadListProduct();


        GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),1);
        //layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(gridLayoutManager);


        adapter.startListening();
        recyclerView.setAdapter(adapter);

        return RootView;
    }

    private void loadListProduct() {
        option=new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(myRef,Cart.class).build();
        adapter=new FirebaseRecyclerAdapter<Cart, CartAdapter>(option) {
            @Override
            protected void onBindViewHolder(@NonNull CartAdapter cartAdapter, int i, @NonNull Cart cart) {
                Picasso.get().load(cart.getProductImage()).into(cartAdapter.imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), "Could not get image", Toast.LENGTH_SHORT).show();
                    }
                });

                String tag="sdsf";
                Log.i(tag,"cart "+ cart.getProductAmount());
//                int quantity=Integer.parseInt(cart.getProductAmout());
                //Log.i(tag,"quantity: "+quantity);

                cartAdapter.name_of_product.setText(cart.getProductName());
                cartAdapter.amount_of_product.setText("Amount: "+cart.getProductAmount());
                cartAdapter.price_of_product.setText("$ "+ cart.getProductPrice());

                int price,amount;
                amount=Integer.parseInt(cart.getProductAmount());
                price=Integer.parseInt(cart.getProductPrice());
                totalprice+=amount*price;
                Log.i(tag,"productid " + cart.getProductId());
                request=request.child("Product/").child(cart.getOrderID()+"/");

                int am=Integer.parseInt(cart.getProductAmount());

                totalPrice.setText("$"+String.valueOf(totalprice));

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            NotificationChannel channel1=new NotificationChannel(CHANNEL_ID,"Channel1",NotificationManager.IMPORTANCE_HIGH);
                            channel1.setDescription("Hello");
                            NotificationManager manager=getContext().getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel1);
                        }

                        NotificationCompat.Builder builder= new NotificationCompat.Builder(getContext(),CHANNEL_ID);
                        Intent myIntent=new Intent(getContext(), CartFragment.class);
                        PendingIntent pendingIntent= PendingIntent.getActivity(getContext(),0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT);

                        builder.setAutoCancel(true)
                                .setDefaults(Notification.DEFAULT_ALL)
                                .setChannelId(CHANNEL_ID)
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.mipmap.ic_launcher_round)
                                .setContentTitle("Thank you for shopping with us")
                                .setContentIntent(pendingIntent)
                                .setContentText("Your order has been confirmed")
                                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                                .setContentInfo("Info");

                        NotificationManager notificationManager= (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1,builder.build());
                        recyclerView.setVisibility(View.GONE);

                        totalPrice.setText("$ 0");
                        myRef.removeValue();


                    }
                });
            }

            @NonNull
            @Override
            public CartAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout,parent,false);

                return new CartAdapter(view);
            }
        };

}
    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null){
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        if(adapter!=null){
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null){
            adapter.startListening();
        }
    }
}
