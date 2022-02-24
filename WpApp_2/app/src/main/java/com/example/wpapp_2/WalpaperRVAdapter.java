package com.example.wpapp_2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WalpaperRVAdapter extends RecyclerView.Adapter<WalpaperRVAdapter.ViewHolder> {
    private ArrayList<String> walpaperRVArrayList;
    private Context context;

    public WalpaperRVAdapter(ArrayList<String> walpaperRVArrayList, Context context) {
        this.walpaperRVArrayList = walpaperRVArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public WalpaperRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.walpaper_rv_item,parent,false);
        return  new WalpaperRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalpaperRVAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(walpaperRVArrayList.get(position)).into(holder.wallpaperIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,WallpaperActivity.class);
                i.putExtra("imgUrl",walpaperRVArrayList.get(position));
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {

        return walpaperRVArrayList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        private ImageView wallpaperIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wallpaperIV=itemView.findViewById(R.id.idIVWallpaper);
        }
    }
}
