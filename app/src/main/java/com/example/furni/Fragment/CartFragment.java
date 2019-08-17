package com.example.furni.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furni.CartAdapter;
import com.example.furni.Model.Order;
import com.example.furni.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TextView totalPrice;
    Button confirm;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference request;

    List<Order> cart=new ArrayList<>();
    CartAdapter cartAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View RootView= inflater.inflate(R.layout.fragment_cart,container,false);
        recyclerView=getView().findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        totalPrice=getView().findViewById(R.id.totalPrice);
        confirm=getView().findViewById(R.id.confirm);

        firebaseDatabase=FirebaseDatabase.getInstance();
        request=firebaseDatabase.getReference();

        loadListProduct();



        return RootView;
    }

    private void loadListProduct() {


    }
}
