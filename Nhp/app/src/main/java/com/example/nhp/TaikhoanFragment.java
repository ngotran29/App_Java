package com.example.nhp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;

public class TaikhoanFragment extends Fragment {

    public TaikhoanFragment() {
        // Constructor public rỗng bắt buộc
    }

    public static TaikhoanFragment newInstance() {
        return new TaikhoanFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout cho fragment này
        View view = inflater.inflate(R.layout.fragment_taikhoan, container, false);

        // Tìm các view bằng ID
        CountryCodePicker ccp = view.findViewById(R.id.ccp);
        TextInputEditText edt_phone = view.findViewById(R.id.edt_phone);
        MaterialButton btn_submit = view.findViewById(R.id.btn_submit);
        TextView tvlogin_tk = view.findViewById(R.id.tvlogin_tk);

        // Ví dụ: Đặt gợi ý cho EditText số điện thoại
        edt_phone.setHint("Nhập số điện thoại của bạn");

        // Ví dụ: Đặt sự kiện nghe cho nút gửi
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý sự kiện khi nút được nhấn
                // Bạn có thể thêm logic xử lý ở đây
            }
        });

        tvlogin_tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}