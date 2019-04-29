package com.example.dopewallpaper.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dopewallpaper.Common.Common;
import com.example.dopewallpaper.Interface.ItemClickListener;
import com.example.dopewallpaper.Model.WallpaperItem;
import com.example.dopewallpaper.R;
import com.example.dopewallpaper.ViewHolder.ListWallpaperViewHolder;
import com.example.dopewallpaper.ViewWallpaper;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingFragment extends Fragment {

    private static TrendingFragment INSTANCE=null;

    RecyclerView recyclerView;

    FirebaseDatabase database;
    DatabaseReference categoryBackground;

    FirebaseRecyclerOptions<WallpaperItem> options;
    FirebaseRecyclerAdapter<WallpaperItem, ListWallpaperViewHolder> adapter;



    public TrendingFragment() {
        // Required empty public constructor
        database = FirebaseDatabase.getInstance();
        categoryBackground = database.getReference(Common.STR_WALLPAPER);
        //get 10 item that have biggest view count.
        //orderBychild will sort the item by "viewCount".
        Query query = categoryBackground.orderByChild("viewCount").limitToLast(10);

        options = new FirebaseRecyclerOptions.Builder<WallpaperItem>()
                .setQuery(query, WallpaperItem.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<WallpaperItem, ListWallpaperViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ListWallpaperViewHolder holder, int position, @NonNull final WallpaperItem model) {
//                Picasso.get()
                  Picasso.with(getContext())
                        .load(model.getImageLink())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.wallpaper, new Callback() {

                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

//                            public void onError(Exception ex) {
//                                Picasso.get()
                                  Picasso.with(getContext())

                                        .load(model.getImageLink())
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(holder.wallpaper, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {

//                                            public void onError(Exception ex) {
                                                Log.e("ERROR_DW", "Could not fetch the image.");
                                            }
                                        });
                            }
                        });


                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        // Toast.makeText(ListWallpaper.this, "Implement soon", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), ViewWallpaper.class);
                        Common.select_background =  model ;
                        Common.select_background_key = adapter.getRef(position).getKey();
                        startActivity(intent);
                    }
                });
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
        };
    }

    public static TrendingFragment getInstance(){
        if (INSTANCE == null)
                INSTANCE = new TrendingFragment();
        return INSTANCE;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trending, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_trending);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //Firebase return ascending sortlist, must reverse
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        recyclerView.setLayoutManager(linearLayoutManager);

        loadingTrendingList();
        return view;
    }

    private void loadingTrendingList() {
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        if(adapter != null)
            adapter.stopListening();
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null);
            adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        if(adapter != null)
            adapter.startListening();

    }
}
