package com.example.dopewallpaper.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dopewallpaper.Common.Common;
import com.example.dopewallpaper.Database.Recents;
import com.example.dopewallpaper.Interface.ItemClickListener;
import com.example.dopewallpaper.Model.WallpaperItem;
import com.example.dopewallpaper.R;
import com.example.dopewallpaper.ViewHolder.ListWallpaperViewHolder;
import com.example.dopewallpaper.ViewWallpaper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<ListWallpaperViewHolder> {

    private Context context;
    private List<Recents> recents;

    public MyRecyclerAdapter(Context context, List<Recents> recents) {
        this.context = context;
        this.recents = recents;
    }

    @NonNull
    @Override
    public ListWallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_wallpaper_item,viewGroup, false);

        int height = viewGroup.getMeasuredHeight()/2;
        itemView.setMinimumHeight(height);
        return new ListWallpaperViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ListWallpaperViewHolder holder, final int i) {
        Picasso.with(context)
                .load(recents.get(i).getImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into( holder.wallpaper, new Callback() {

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(recents.get(i).getImageUrl())
                                .error(R.drawable.ic_terrain_black_24dp)
                                .into(holder.wallpaper, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.e("ERROR_DW", "Could not fetch the image.");
                                    }
                                });
                    }
                });


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                // Toast.makeText(ListWallpaper.this, "Implement soon", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ViewWallpaper.class);
                WallpaperItem wallpaperItem = new WallpaperItem();
                wallpaperItem.setCategoryId(recents.get(i).getCategory());
                wallpaperItem.setImageLink(recents.get(i).getImageUrl());
                Common.select_background = wallpaperItem;
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recents.size();
    }
}
