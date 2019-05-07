package com.example.dopewallpaper.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dopewallpaper.Interface.ItemClickListener;
import com.example.dopewallpaper.ListWallpaper;
import com.example.dopewallpaper.R;

import org.w3c.dom.Text;

public class ListWallpaperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView image_name ;

    ItemClickListener itemClickListener;

    public ImageView wallpaper;

    public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener = itemClickListener;
    }

    public ListWallpaperViewHolder(View itemView){
        super(itemView);
        wallpaper = (ImageView)itemView.findViewById(R.id.image);
        image_name = (TextView)itemView.findViewById(R.id.name);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition());
    }
}
