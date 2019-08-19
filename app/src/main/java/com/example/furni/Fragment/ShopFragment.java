package com.example.furni.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furni.Interface.ItemClickListener;
import com.example.furni.Model.Product;
import com.example.furni.ProductDetail;
import com.example.furni.R;
import com.example.furni.RecyclerviewAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ShopFragment extends Fragment {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    RecyclerView recyclerView;



    String UID;


    private FirebaseRecyclerOptions<Product> options;
    private FirebaseRecyclerAdapter<Product, RecyclerviewAdapter> adapter;


    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Product");

        Bundle bundle=this.getArguments();
        if (bundle!=null){
            UID= bundle.getString("UID");
        }
        View RootView= inflater.inflate(R.layout.fragment_shop_all,container,false);
        recyclerView=RootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);



       // GridLayoutManager gridLayoutManager=new GridLayoutManager(getContext(),2);
        //recyclerView.setLayoutManager(gridLayoutManager);

        loadProduct();

        adapter.startListening();
        recyclerView.setAdapter(adapter);
        return RootView;
    }

    private void loadProduct() {
        options=new FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(myRef,Product.class).build();

        adapter=new FirebaseRecyclerAdapter<Product, RecyclerviewAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerviewAdapter recyclerviewAdapter, int i, @NonNull Product product) {
                Picasso.get().load(product.getImage()).into(recyclerviewAdapter.product_image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(getContext(), "Could not get image", Toast.LENGTH_SHORT).show();
                    }
                });

                recyclerviewAdapter.product_name.setText(product.getName());
                recyclerviewAdapter.product_price.setText("$ "+product.getPrice());

                recyclerviewAdapter.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Intent detail_intent= new Intent(getActivity(), ProductDetail.class);
                        String TAG="UID";
                        Log.i(TAG,"OLALA"+ UID);
                        detail_intent.putExtra("UserId",UID);
                        detail_intent.putExtra("ProductId",adapter.getRef(i).getKey());
                        Log.i(TAG,"product: "+adapter.getRef(i).getKey());
                        startActivityForResult(detail_intent,1);
                    }
                });
            }

            @NonNull
            @Override
            public RecyclerviewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_recycler,parent,false);

                return new RecyclerviewAdapter(view);
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