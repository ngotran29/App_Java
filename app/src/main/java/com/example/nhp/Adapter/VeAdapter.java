package com.example.nhp.Adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhp.R;
import com.example.nhp.Vexe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VeAdapter extends RecyclerView.Adapter<VeAdapter.VeViewHolder> {


    List<Vexe> lstve;

    public VeAdapter(List<Vexe> lstve) {
        this.lstve = lstve;
    }

    @NonNull
    @Override
    public VeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_qlve, parent, false);
        return new VeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VeViewHolder holder, int position) {
        Vexe ve = lstve.get(position);
        if (ve == null) {
            return;
        }
        holder.tennguoidung.setText(ve.getTenkhachhang());
        holder.sdt.setText(ve.getSodienthoai());
        holder.noidi.setText(ve.getNoidi());
        holder.noiden.setText(ve.getNoiden());
        holder.xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteVe(v.getContext(),ve);

            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin của vé xe tại vị trí được click
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Vexe ve = lstve.get(adapterPosition);
                    // Hiển thị Dialog chi tiết của vé xe
                    showDetailDialog(v.getContext(), ve);
                }
            }

        });

    }

    private void DeleteVe(Context context, Vexe ve) {
        new AlertDialog.Builder(context)
                .setTitle("Chú ý")
                .setMessage("Bạn có chắc chắn muốn xoá hay không?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("list_ve").child(String.valueOf(ve.getId()));
                        myRef.removeValue();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }


    private void showDetailDialog(Context context, Vexe ve) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_edit_qlv);

        // Ánh xạ các view trong dialog layout
        EditText tennguoidungdialog = dialog.findViewById(R.id.ed_tennguoidung_edit);
        EditText sodienthoai = dialog.findViewById(R.id.ed_sodienthoai_edit);
        EditText noidi = dialog.findViewById(R.id.ed_noidi_edit);
        EditText noiden = dialog.findViewById(R.id.ed_noiden_edit);
        EditText soghe = dialog.findViewById(R.id.ed_sogh_edit);
        Button ok = dialog.findViewById(R.id.btn_ok);
        Button cancel = dialog.findViewById(R.id.btn_cancel);


        // Hiển thị thông tin của vé xe trong dialog
        tennguoidungdialog.setText(ve.getTenkhachhang());
        sodienthoai.setText(ve.getSodienthoai());
        noidi.setText(ve.getNoidi());
        noiden.setText(ve.getNoiden());
        soghe.setText(ve.getMaghe());
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mới từ EditText
                String ID = String.valueOf(ve.getId());
                String newTenNguoiDung = tennguoidungdialog.getText().toString();
                String newSoDienThoai = sodienthoai.getText().toString();
                String newNoiDi = noidi.getText().toString();
                String newNoiDen = noiden.getText().toString();
                String newSoGhe = soghe.getText().toString();
//
//                // Cập nhật thông tin lên Realtime Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("list_ve").child(String.valueOf(ve.getId()));
                Map<String, Object> map = new HashMap<>();
                map.put("tenkhachhang", newTenNguoiDung);
                map.put("sodienthoai", newSoDienThoai);
                map.put("noidi", newNoiDi);
                map.put("noiden", newNoiDen);
                map.put("maghe", newSoGhe);
                myRef.updateChildren(map);
                // Đóng dialog sau khi cập nhật thành công
                dialog.dismiss();
            }
        });

        // Hiển thị Dialog
        dialog.show();
    }

    @Override
    public int getItemCount() {
        if (lstve != null) {
            return lstve.size();
        }
        return 0;
    }

    public class VeViewHolder extends RecyclerView.ViewHolder {

        private TextView tennguoidung, sdt, noidi, noiden;
        private Button edit, xoa;

        public VeViewHolder(@NonNull View itemView) {
            super(itemView);
            tennguoidung = itemView.findViewById(R.id.tv_tennguoidi);
            sdt = itemView.findViewById(R.id.tv_sodienthoai);
            noidi = itemView.findViewById(R.id.tv_noidi_qlv);
            noiden = itemView.findViewById(R.id.tv_noiden_qlv);
            edit = itemView.findViewById(R.id.bt_sua_ve);
            xoa = itemView.findViewById(R.id.bt_xoa_ve);
        }
    }
}