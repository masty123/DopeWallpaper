package com.example.dopewallpaper.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dopewallpaper.Interface.ItemClickListener;
import com.example.dopewallpaper.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView category_name ;
    public ImageView background_image;

    ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public CategoryViewHolder(View itemView) {
        super(itemView);
        background_image = (ImageView) itemView.findViewById(R.id.image);
        category_name = (TextView) itemView.findViewById(R.id.name);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }


}
