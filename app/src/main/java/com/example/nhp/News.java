package com.example.nhp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class News extends RecyclerView.Adapter<News.ImageViewHolder> {
    private Context mContext;

    private int[] imgIDs ;
    private String[] Urls;

    public News (Context context, int [] imgIDs, String [] Urls)
    {
        this.mContext=context;
        this.imgIDs=imgIDs;
        this.Urls=Urls;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.news_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imageView.setImageResource(imgIDs[position]);

        holder.imageView.setOnClickListener(v -> {
            String url = Urls[position];
            Intent i =new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return imgIDs.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.NewsImgView);
        }
    }
}

