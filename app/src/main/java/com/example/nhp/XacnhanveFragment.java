package com.example.nhp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class XacnhanveFragment extends Fragment {
    TextView noidi, noiden, nhaxe, giodi, ngaydi, maghe, tamtinh, soluong, tenkhach, sdt, header;
    Button datve;
    String Ngay, Noidi, Giodi, Noiden, Tennhaxe;
    ArrayList<Integer> danhsachmaghe;
    int idtemp = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_xacnhanve, container, false);
        noiden = view.findViewById(R.id.tv_noiden_xacnhan);
        noidi = view.findViewById(R.id.tv_noidi_xacnhan);
        nhaxe = view.findViewById(R.id.tv_ten_nha_xe);
        giodi = view.findViewById(R.id.tv_gio_di);
        ngaydi = view.findViewById(R.id.tv_ngay_di);
        maghe = view.findViewById(R.id.tv_ma_ghe);// lay
        tamtinh = view.findViewById(R.id.tv_tam_tinh);// lay
        tenkhach = view.findViewById(R.id.tv_ten_hanh_khach);// lay
        soluong = view.findViewById(R.id.tv_so_luong);
        header = view.findViewById(R.id.ten_nha_xe);
        sdt = view.findViewById(R.id.tv_sdt);// lay
        Button datve = view.findViewById(R.id.bt_xac_nhan_ve);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Ngay = bundle.getString("Ngay");
            Noidi = bundle.getString("noidi");
            Noiden = bundle.getString("noiden");
            Tennhaxe = bundle.getString("tennhaxe");
            Giodi = bundle.getString("giodi");
            danhsachmaghe = bundle.getIntegerArrayList("danhsachghe");
            maghe.setText(String.valueOf(danhsachmaghe));
            String tenNguoiDi = bundle.getString("tennguoidi");
            String soDienThoai = bundle.getString("sdt");
            int maghe1 = bundle.getInt("maghe");
            int tamtinh1 = bundle.getInt("tamtinh");
            // Hiển thị dữ liệu trong Fragment mới
            tenkhach.setText(tenNguoiDi);
            noidi.setText(Noidi);
            noiden.setText(Noiden);
            nhaxe.setText(Tennhaxe);
            header.setText(Tennhaxe);
            giodi.setText(Giodi);
            sdt.setText(soDienThoai);
            soluong.setText(String.valueOf(maghe1));
            tamtinh.setText(String.valueOf(tamtinh1));
            ngaydi.setText(Ngay);
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            idtemp = sharedPreferences.getInt("idtemp", 1);
        }

        datve = view.findViewById(R.id.bt_xac_nhan_ve);
        datve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPushdata();
//                ThanhToanActivity chonghe = new ThanhToanActivity();
//                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
//                transaction.replace(R.id.main_fragment, chonghe);
//                transaction.addToBackStack(null);
//                transaction.commit();
                Intent intent = new Intent(getActivity(),ThanhToanActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    private void onClickPushdata() {
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int idtemp = sharedPreferences.getInt("idtemp", 1);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_ve");//key
        Vexe ve = new Vexe();
        ve.noidi = noidi.getText().toString().trim();
        ve.noiden = noiden.getText().toString().trim();
        ve.nhaxe = nhaxe.getText().toString().trim();
        ve.gio = giodi.getText().toString().trim();
        ve.ngaydi = ngaydi.getText().toString().trim();
        ve.maghe = maghe.getText().toString().trim();
        ve.sodienthoai = sdt.getText().toString().trim();
        ve.tenkhachhang = tenkhach.getText().toString().trim();
        String tamtinhText = tamtinh.getText().toString().trim();
        int tamtinhValues = Integer.parseInt(tamtinhText);
        ve.tamtinh = tamtinhValues;
        String soluongText = soluong.getText().toString().trim();
        int soluongValues = Integer.parseInt(soluongText);
        ve.soluong = soluongValues;
        ve.id = idtemp;
        idtemp++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idtemp", idtemp);
        editor.apply();


        String path = String.valueOf(ve.getId());
        myRef.child(path).setValue((ve), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @androidx.annotation.NonNull DatabaseReference ref) {
                Toast.makeText(getContext(), "Xác nhận thành công", Toast.LENGTH_SHORT).show();
            }
        });//values


    }
}