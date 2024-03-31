package com.example.nhp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;


public class TimkiemFragment extends Fragment {


    TextView ntext, ntext1, ntext2, ngaydi, noidi, noiden;
    String Ngay;
    Button btnTimkiem;

    private String noiditext;
    ViewPager2 vp, vp1, vp2;
    Bundle bundle;
    int[] imgIDs = new int[]{R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four};
    String[] Urls = new String[]{"https://vexere.com/vi-VN/bai-viet/nha-xe-giam-gia-hai-phong-travel",
            "https://vexere.com/vi-VN/bai-viet/dat-ve-xe-khach-khu-hoi",
            "https://vexere.com/vi-VN/referral?utm_source=banner&utm_medium=main&utm_campaign=referral_main_banner_2023",
            "https://vexere.com/vi-VN/bai-viet/uu-dai-bat-ngo"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            noiditext = savedInstanceState.getString("noiditext");
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("noiditext", noiditext);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timkiem, container, false);
        Button bttimkiem = view.findViewById(R.id.bttimkiem);

        //Phần tin tức, ưu đãi


        ntext = view.findViewById(R.id.NewsTxt);
        ntext1 = view.findViewById(R.id.NewsTxt1);
        ntext2 = view.findViewById(R.id.NewsTxt2);
        // Initialize ViewPager2
        vp = view.findViewById(R.id.News);
        vp1 = view.findViewById(R.id.News1);
        vp2 = view.findViewById(R.id.News2);

        // Create News adapters
        News n = new News(getContext(), imgIDs, Urls);
        News n1 = new News(getContext(), imgIDs, Urls);
        News n2 = new News(getContext(), imgIDs, Urls);
        vp.setAdapter(n);
        vp1.setAdapter(n1);
        vp2.setAdapter(n2);
        noidi = view.findViewById(R.id.tvNoidi);
        noiden = view.findViewById(R.id.tvNoiden);
        ngaydi = view.findViewById(R.id.tvNgaydi);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("selectedCities")) {
            ArrayList<String> selectedCities = bundle.getStringArrayList("selectedCities");
            if (selectedCities != null && selectedCities.size() >= 2) {
                noidi.setText(selectedCities.get(0));
                noiden.setText(selectedCities.get(1));
            }
        }

        noidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến fragment CityFragment
                noiditext = noidi.getText().toString();
                Fragment cityFragment = new CityFragment();
                Bundle bundle = new Bundle();
                bundle.putString("selectediemdi", "DiemDi");
                // Gửi thông điệp cho CityFragment biết là đang chọn điểm đi
                cityFragment.setArguments(bundle);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment, cityFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ngaydi = view.findViewById(R.id.tvNgaydi);
        ngaydi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        btnTimkiem = view.findViewById(R.id.bttimkiem);
        btnTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noiditext = noidi.getText().toString().trim();
                String noidentext = noiden.getText().toString().trim();
                if (noiditext.isEmpty()) {
                    // Hiển thị thông báo hoặc thông tin lỗi cho người dùng rằng noidi không được để trống
                    // Ví dụ:
                    noidi.setError("Vui lòng chọn nơi đi");
                    return;
                }

                if (noidentext.isEmpty()) {
                    // Hiển thị thông báo hoặc thông tin lỗi cho người dùng rằng noiden không được để trống
                    // Ví dụ:
                    noiden.setError("Vui lòng chọn nơi đến");
                    return;
                }

                if (Ngay==null||Ngay.isEmpty()) {
                    // Hiển thị thông báo hoặc thông tin lỗi cho người dùng rằng Ngay không được để trống
                    // Ví dụ:
                    ngaydi.setError("Vui lòng chọn ngày");
                    return;
                }

                Bundle bundle2 = new Bundle();
                bundle2.putString("diemdi", noiditext);
                bundle2.putString("diemden", noidentext);
                bundle2.putString("Ngay", Ngay);

                ChuyenDiFragment chuyenDiFragment = new ChuyenDiFragment();
                chuyenDiFragment.setArguments(bundle2);
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment, chuyenDiFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
    private void openCityFragment() {
        Fragment cityFragment = new CityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("selectedTextView", "noidi");
        cityFragment.setArguments(bundle);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment, cityFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showDatePickerDialog() {
        // Tạo DatePickerDialog
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Set ngày đã chọn vào TextView
                        ngaydi.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                        Ngay = ngaydi.getText().toString().trim();
                    }
                }, year, month, dayOfMonth);
        //Chọn ngày từ hiện tại trở đi
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        // Hiển thị DatePickerDialog
        datePickerDialog.show();
    }
}