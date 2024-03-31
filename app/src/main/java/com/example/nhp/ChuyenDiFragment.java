package com.example.nhp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhp.Adapter.Chuyendi;
import com.example.nhp.Adapter.ChuyendiAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChuyenDiFragment extends Fragment implements ChuyendiAdapter.OnChuyenSelectedListener {
    RecyclerView rvchuyendi;
    ChuyendiAdapter chuyendiAdapter;
    ArrayList<Chuyendi> lstchuyen;
    String Ngay, noiden, noidi;
    TextView Noidi, Noiden, Ngaydi;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chuyen_di, container, false);
        rvchuyendi = view.findViewById(R.id.rvchuyendi);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvchuyendi.setLayoutManager(linearLayoutManager);
        lstchuyen = new ArrayList<>();
        chuyendiAdapter = new ChuyendiAdapter(lstchuyen, this::onChuyenSelected);
        rvchuyendi.setAdapter(chuyendiAdapter);

        ChuyenDiFragment chuyendi = new ChuyenDiFragment();
        Noidi = view.findViewById(R.id.tv_noidi_chuyendi);
        Noiden = view.findViewById(R.id.tv_noiden_chuyendi);
        Ngaydi = view.findViewById(R.id.tv_ngay_chuyendi);
        Bundle bundle = getArguments();
        if (bundle != null) {
            noiden = bundle.getString("diemden");
            noidi = bundle.getString("diemdi");
            getListChuyenfromFDT(noiden, noidi);
            Ngay = bundle.getString("Ngay");
            Noidi.setText(noidi);
            Noiden.setText(noiden);
            Ngaydi.setText(Ngay);

        }


        return view;
    }

    @Override
    public void onChuyenSelected(Chuyendi chuyendi) {
        Bundle bundle = new Bundle();
        bundle.putString("Ngay", Ngay);
        bundle.putString("noidi", chuyendi.getDiemdi());
        bundle.putString("noiden", chuyendi.getDiemden());
        bundle.putString("giodi", chuyendi.getGiodi());
        bundle.putString("gioden", chuyendi.getGioden());
        bundle.putString("tennhaxe", chuyendi.getTennhaxe());
        bundle.putInt("giatien", chuyendi.getTienve());
        // Thêm dữ liệu khác nếu cần thiết
        ChongheFragment chongheFragment = new ChongheFragment();
        chongheFragment.setArguments(bundle);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, chongheFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getListChuyenfromFDT(String diemden, String diemdi) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chuyendi");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Chuyendi chuyendi = dataSnapshot.getValue(Chuyendi.class);
                    if ( chuyendi.getDiemdi().contains(noidi) && chuyendi.getDiemden().contains(noiden)) {
                        lstchuyen.add(chuyendi); // Thêm chuyến đi phù hợp vào danh sách
                    }
                }
                chuyendiAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (getContext() == null) {
                    Log.e("ChuyenDiFragment", "getContext() trả về null");
                } else {
                    // Thực hiện các hoạt động khác ở đây
                    Toast.makeText(getContext(), "lấy list không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}