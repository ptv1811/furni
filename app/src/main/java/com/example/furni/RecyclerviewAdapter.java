package com.example.furni;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.furni.Interface.ItemClickListener;

public class RecyclerviewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView product_image;
    public TextView product_name;
    public TextView product_price;

    private ItemClickListener itemClickListener;


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public RecyclerviewAdapter(@NonNull View itemView) {
        super(itemView);

        product_image=itemView.findViewById(R.id.product_image);
        product_name=itemView.findViewById(R.id.product_name);
        product_price=itemView.findViewById(R.id.price);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v,getAdapterPosition(),false);
    }
}
