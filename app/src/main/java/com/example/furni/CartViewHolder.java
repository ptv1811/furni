package com.example.furni;

import android.content.Context;
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

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView pname,pprice,pamount;
    public ImageView pimage;

    private ItemClickListener itemClickListener;

    public void setPname(TextView pname){
        this.pname=pname;
    }

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        pname=itemView.findViewById(R.id.name_of_product);
        pamount=itemView.findViewById(R.id.amount_of_product);
        pprice=itemView.findViewById(R.id.price_of_product);
        pimage=itemView.findViewById(R.id.imageView3);
    }

    @Override
    public void onClick(View v) {

    }
}


