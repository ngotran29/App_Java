package com.example.nhp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class ThongtinNguoidiFragment extends Fragment {

    int tamtinh, maghe;
    TextView Noidi, Noiden, ngaydi;
    String Ngay, tennhaxe, noidi, giodi, noiden;
    ArrayList<Integer> selectedSeatIds;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongtin_nguoidi, container, false);
        Button thongtin = view.findViewById(R.id.bttieptucthongtin);
        EditText tennguoidi = view.findViewById(R.id.edtennguoidi);
        EditText sdt = view.findViewById(R.id.edsdt);

        Bundle bundle = getArguments();
        if (bundle != null) {
            selectedSeatIds = bundle.getIntegerArrayList("selectedSeatIds");
            if (selectedSeatIds != null) {
                for (int seatId : selectedSeatIds) ;
            }
            Ngay = bundle.getString("Ngay");
            tennhaxe = bundle.getString("tennhaxe");
            noidi = bundle.getString("noidi");
            noiden = bundle.getString("noiden");
            giodi = bundle.getString("giodi");
            maghe = bundle.getInt("soLuong");
            tamtinh = bundle.getInt("soTien");
        }
        Noidi = view.findViewById(R.id.tv_noidi_thongtin);
        Noiden = view.findViewById(R.id.tv_noiden_thongtin);
        ngaydi = view.findViewById(R.id.ngaydi_thongtin);
        Noidi.setText(noidi);
        Noiden.setText(noiden);
        ngaydi.setText(Ngay);
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenNguoidi = tennguoidi.getText().toString().trim();
                String SDT = sdt.getText().toString().trim();
                // Kiểm tra xem các trường nhập liệu đã được điền đầy đủ hay không
                if (tenNguoidi.isEmpty() || SDT.isEmpty()) {
                    // Hiển thị thông báo yêu cầu nhập đầy đủ thông tin
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                ArrayList<Integer> danhsanhghe = selectedSeatIds;
                Bundle bundle = new Bundle();
                bundle.putString("Ngay", Ngay);
                bundle.putString("giodi", giodi);
                bundle.putString("tennhaxe", tennhaxe);
                bundle.putString("noidi", noidi);
                bundle.putString("noiden", noiden);
                bundle.putString("tennguoidi", tenNguoidi);
                bundle.putIntegerArrayList("danhsachghe", danhsanhghe);
                bundle.putString("sdt", SDT);
                bundle.putInt("maghe", maghe);
                bundle.putInt("tamtinh", tamtinh);
                XacnhanveFragment xacnhanveFragment = new XacnhanveFragment();
                xacnhanveFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment, xacnhanveFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
}