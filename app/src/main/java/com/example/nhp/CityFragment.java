package com.example.nhp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhp.Adapter.TinhAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CityFragment extends Fragment implements TinhAdapter.OnItemClickListener {

    RecyclerView rvtinh;
    TinhAdapter tinhAdapter;
    List<Tinhthanh> lstinhthanh;
    private List<String> selectedCities = new ArrayList<>();
    TextView diemdi, diemden;
    Button btnxacnhan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_city, container, false);
        rvtinh = view.findViewById(R.id.rvtinh);
        btnxacnhan = view.findViewById(R.id.bt_xacnhan_city);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvtinh.setLayoutManager(linearLayoutManager);
        lstinhthanh = new ArrayList<>();
        tinhAdapter = new TinhAdapter(lstinhthanh, getParentFragmentManager());
        rvtinh.setAdapter(tinhAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvtinh.addItemDecoration(dividerItemDecoration);
        getListTinhfromFDB();
        if (getArguments() != null && getArguments().containsKey("selectedCities")) {
            selectedCities = getArguments().getStringArrayList("selectedCities");
            setSelectedCities(selectedCities);
        }
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedCities.size() == 2) {
                    sendSelectedCitiesToTimkiemFragment(selectedCities);
                } else {
                    Toast.makeText(getContext(), "Vui lòng chọn đủ hai thành phố", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tinhAdapter.setOnItemClickListener(this);
        return view;
    }


    @Override
    public void onItemClick(String cityName) {
        if (selectedCities.size() < 2) {
            if (selectedCities.contains(cityName)) {
                selectedCities.remove(cityName);
            } else {
                selectedCities.add(cityName);
            }
            updateUI();
        } else {
            Toast.makeText(getContext(), "Chỉ được chọn tối đa 2 thành phố", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI() {
        // Cập nhật màu nền cho các item
        for (Tinhthanh tinhthanh : lstinhthanh) {
            if (selectedCities.contains(tinhthanh.getName())) {
                tinhthanh.setSelected(true);
            } else {
                tinhthanh.setSelected(false);
            }
        }
        tinhAdapter.notifyDataSetChanged();

        // Kiểm tra và kích hoạt hoặc vô hiệu hóa nút xác nhận
        btnxacnhan.setEnabled(selectedCities.size() == 2);
    }


    private void sendSelectedCitiesToTimkiemFragment(List<String> selectedCities) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("selectedCities", new ArrayList<>(selectedCities));

        TimkiemFragment timkiemFragment = new TimkiemFragment();
        timkiemFragment.setArguments(bundle);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, timkiemFragment); // Thay đổi R.id.main_fragment thành id của layout chứa fragment
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void getListTinhfromFDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_tinh");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (lstinhthanh != null) {
                    lstinhthanh.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Tinhthanh tinhthanh = dataSnapshot.getValue(Tinhthanh.class);
                    lstinhthanh.add(tinhthanh);
                }
                tinhAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (getContext() == null) {
                    Log.e("CityFragment", "getContext() trả về null");
                } else {
                    // Thực hiện các hoạt động khác ở đây
                    Toast.makeText(getContext(), "lấy list không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setSelectedCities(List<String> selectedCities) {
// Xử lý danh sách các thành phố đã chọn ở đây
    }
}