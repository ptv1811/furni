package com.example.furni;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furni.Interface.ItemClickListener;
import com.example.furni.Model.Order;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView name_of_product,price_of_product,amount_of_product;
    public ImageView imageView;


    private ItemClickListener itemClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CartAdapter(@NonNull View itemView) {
        super(itemView);
        name_of_product=itemView.findViewById(R.id.name_of_product);
        price_of_product=itemView.findViewById(R.id.price_of_product);
        amount_of_product=itemView.findViewById(R.id.amount_of_product);
        imageView=itemView.findViewById(R.id.imageView3);

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v,getAdapterPosition(),false);
    }
}
