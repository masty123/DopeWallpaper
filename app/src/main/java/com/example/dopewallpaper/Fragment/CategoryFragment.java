package com.example.dopewallpaper.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dopewallpaper.Common.Common;
import com.example.dopewallpaper.Interface.ItemClickListener;
import com.example.dopewallpaper.Model.CategoryItem;
import com.example.dopewallpaper.R;
import com.example.dopewallpaper.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

/**
 *  A simple {@link Fragment} subclass
 */
public class CategoryFragment extends Fragment {
    //Firebase
    FirebaseDatabase database;
    DatabaseReference categoryBackground;

    //FirebaseUI Adapter
    FirebaseRecyclerOptions<CategoryItem> options;
    FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder> adapter;

    //View
    RecyclerView recyclerView;

    private static CategoryFragment INSTANCE = null;

    public CategoryFragment() {
        database = FirebaseDatabase.getInstance();
        categoryBackground = database.getReference(Common.STR_CATEGORY_BACKGROUND);

        options = new FirebaseRecyclerOptions.Builder<CategoryItem>().setQuery(categoryBackground, CategoryItem.class).build();

        adapter = new FirebaseRecyclerAdapter<CategoryItem, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position, @NonNull final CategoryItem model) {
                Picasso.with(getActivity())
                        .load(model.getImagelink())
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.background_image, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(getActivity())
                                        .load(model.getImagelink())
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(holder.background_image, new Callback() {
                                            @Override
                                            public void onSuccess() {

                                            }

                                            @Override
                                            public void onError() {
                                                Log.e("Error_DW", "Couldn't fetch image");
                                            }
                                        });
                            }
                        });

                holder.category_name.setText(model.getName());

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        // Code late for detail category
                    }
                });
            }



            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category_item, viewGroup, false);
                return new CategoryViewHolder(itemView);
            }
        };
    }

    public static CategoryFragment getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new CategoryFragment();
        }
        return INSTANCE;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_category, container, false);
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_category);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        setCategory();
        return view;
    }

    private void setCategory() {
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    public void onStart(){
        super.onStart();
        if(adapter != null) adapter.startListening();
    }

    public void onStop(){
        if(adapter != null) {
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!= null){
            adapter.startListening();
        }

    }
}
