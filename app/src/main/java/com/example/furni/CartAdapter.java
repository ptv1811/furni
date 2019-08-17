package com.example.furni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furni.Model.Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listOrder= new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listOrder, Context context) {
        this.listOrder = listOrder;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.cart_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.pname.setText(listOrder.get(position).getProductName());
        holder.pamount.setText(listOrder.get(position).getProductAmount());
        holder.pprice.setText(listOrder.get(position).getProductPrice());
        Picasso.get().load(listOrder.get(position).getProductImage()).into(holder.pimage);
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }
}
