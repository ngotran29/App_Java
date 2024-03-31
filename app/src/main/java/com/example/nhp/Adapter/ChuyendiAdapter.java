package com.example.nhp.Adapter;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nhp.ChongheFragment;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhp.Adapter.Chuyendi;
import com.example.nhp.R;

import java.util.List;

public class ChuyendiAdapter extends RecyclerView.Adapter<ChuyendiAdapter.ChuyendiViewHolder> {
    List<Chuyendi> lstchuyendi;
    private OnChuyenSelectedListener listener;

    public interface OnChuyenSelectedListener {
        void onChuyenSelected(Chuyendi chuyendi);
    }

    public ChuyendiAdapter(List<Chuyendi> lstchuyendi, OnChuyenSelectedListener listener) {
        this.lstchuyendi = lstchuyendi;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChuyendiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_chuyendi, parent, false);
        return new ChuyendiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChuyendiViewHolder holder, int position) {
        Chuyendi chuyendi = lstchuyendi.get(position);
        if(chuyendi == null)
        {
            return;
        }
        holder.giodi.setText(chuyendi.getGiodi());
        holder.gioden.setText(chuyendi.getGioden());
        holder.tennhaxe.setText(chuyendi.getTennhaxe());
        holder.tien.setText(String.valueOf(chuyendi.getTienve()));
        holder.noidi.setText(chuyendi.getDiemdi());
        holder.noiden.setText(chuyendi.getDiemden());
        Picasso.get().load(chuyendi.getHinhanh()).into(holder.hinhanh);
        holder.chonchuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onChuyenSelected(chuyendi); // Truyền đối tượng Chuyendi được chọn thông qua callback
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(lstchuyendi != null)
        {
            return lstchuyendi.size();
        }
        return 0;
    }

    public class ChuyendiViewHolder extends RecyclerView.ViewHolder {
        TextView giodi, gioden, tennhaxe, noidi, noiden, tien;
        Button chonchuyen;
        ImageView hinhanh;

        public ChuyendiViewHolder(@NonNull View itemView) {
            super(itemView);
            giodi = itemView.findViewById(R.id.tv_item_gio_di);
            gioden = itemView.findViewById(R.id.tv_item_gio_den);
            noidi = itemView.findViewById(R.id.tv_item_tinh_di);
            noiden = itemView.findViewById(R.id.tv_item_tinh_den);
            tien = itemView.findViewById(R.id.tv_gia_ve);
            tennhaxe = itemView.findViewById(R.id.tv_item_ten_nha_xe);
            hinhanh = itemView.findViewById(R.id.im_item_chuyen_di);
            chonchuyen = itemView.findViewById(R.id.bt_item_chon_chuyen);
        }
    }
}