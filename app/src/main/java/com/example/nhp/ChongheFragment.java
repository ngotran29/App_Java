package com.example.nhp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChongheFragment extends Fragment {
    private int totalPrice = 0;
    List<Boolean> seatStates = new ArrayList<>();
    Button tieptuc;
    TextView tvTotal, tvSelectedSeats, Noidi, Noiden, Ngaydi;

    String Ngay, noiden, noidi, tennhaxe, giodi, gioden;
    int tienve;
    private int selectedSeatCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chonghe, container, false);
        tieptuc = view.findViewById(R.id.bttieptuc);
        Noiden = view.findViewById(R.id.tv_noiden_chonghe);
        Noidi = view.findViewById(R.id.tv_noidi_chonghe);
        Ngaydi = view.findViewById(R.id.ngay_chonghe);
        Bundle bundle = getArguments();
        if (bundle != null) {
            Ngay = bundle.getString("Ngay");
            noidi = bundle.getString("noidi");
            noiden = bundle.getString("noiden");
            tennhaxe = bundle.getString("tennhaxe");
            giodi = bundle.getString("giodi");
            gioden = bundle.getString("gioden");
            tienve = bundle.getInt("giatien");

        }
        Ngaydi.setText(Ngay);
        Noiden.setText(noiden);
        Noidi.setText(noidi);
        tvSelectedSeats = view.findViewById(R.id.tvSelectedSeats);
        tvTotal = view.findViewById(R.id.tvTotal);
        for (int i = 0; i < 25; i++) {
            seatStates.add(false);
        }
        ImageView seat1 = view.findViewById(R.id.ghe1);
        setupSeatClickListener(seat1, 0);
        ImageView seat2 = view.findViewById(R.id.ghe2);
        setupSeatClickListener(seat2, 1);
        ImageView seat3 = view.findViewById(R.id.ghe3);
        setupSeatClickListener(seat3, 2);
        ImageView seat4 = view.findViewById(R.id.ghe4);
        setupSeatClickListener(seat4, 3);
        ImageView seat5 = view.findViewById(R.id.ghe5);
        setupSeatClickListener(seat5, 4);
        ImageView seat6 = view.findViewById(R.id.ghe6);
        setupSeatClickListener(seat6, 5);
        ImageView seat7 = view.findViewById(R.id.ghe7);
        setupSeatClickListener(seat7, 6);
        ImageView seat8 = view.findViewById(R.id.ghe8);
        setupSeatClickListener(seat8, 7);
        ImageView seat9 = view.findViewById(R.id.ghe9);
        setupSeatClickListener(seat9, 8);
        ImageView seat10 = view.findViewById(R.id.ghe10);
        setupSeatClickListener(seat10, 9);
        ImageView seat11 = view.findViewById(R.id.ghe11);
        setupSeatClickListener(seat11, 10);
        ImageView seat12 = view.findViewById(R.id.ghe12);
        setupSeatClickListener(seat12, 11);
        ImageView seat13 = view.findViewById(R.id.ghe13);
        setupSeatClickListener(seat13, 12);
        ImageView seat14 = view.findViewById(R.id.ghe14);
        setupSeatClickListener(seat14, 13);
        ImageView seat15 = view.findViewById(R.id.ghe15);
        setupSeatClickListener(seat15, 14);
        ImageView seat16 = view.findViewById(R.id.ghe16);
        setupSeatClickListener(seat16, 15);
        ImageView seat17 = view.findViewById(R.id.ghe17);
        setupSeatClickListener(seat17, 16);
        ImageView seat18 = view.findViewById(R.id.ghe18);
        setupSeatClickListener(seat18, 17);
        ImageView seat19 = view.findViewById(R.id.ghe19);
        setupSeatClickListener(seat19, 18);
        ImageView seat20 = view.findViewById(R.id.ghe20);
        setupSeatClickListener(seat20, 19);
        ImageView seat21 = view.findViewById(R.id.ghe21);
        setupSeatClickListener(seat21, 20);
        ImageView seat22 = view.findViewById(R.id.ghe22);
        setupSeatClickListener(seat22, 21);
        ImageView seat23 = view.findViewById(R.id.ghe23);
        setupSeatClickListener(seat23, 22);
        ImageView seat24 = view.findViewById(R.id.ghe24);
        setupSeatClickListener(seat24, 23);


//
        tieptuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tieptuc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean hasSelectedSeat = false;
                        for (boolean isSelected : seatStates) {
                            if (isSelected) {
                                hasSelectedSeat = true;
                                break;
                            }
                        }
                        if (!hasSelectedSeat) {
                            // Hiển thị thông báo yêu cầu chọn ít nhất một ghế
                            Toast.makeText(getActivity(), "Vui lòng chọn ít nhất một ghế", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<Integer> selectedSeatIds = new ArrayList<>();
                        for (int i = 0; i < seatStates.size(); i++) {
                            if (seatStates.get(i)) {
                                selectedSeatIds.add(i + 1); // Mã ghế bắt đầu từ 1
                            }
                        }
                        Bundle bundle = new Bundle();
                        bundle.putString("tennhaxe", tennhaxe);
                        bundle.putString("giodi", giodi);
                        bundle.putString("noidi", noidi);
                        bundle.putString("noiden", noiden);
                        bundle.putString("Ngay", Ngay);
                        bundle.putInt("soLuong", selectedSeatCount);
                        bundle.putInt("soTien", totalPrice);
                        bundle.putIntegerArrayList("selectedSeatIds", (ArrayList<Integer>) selectedSeatIds);
                        ThongtinNguoidiFragment thongtinNguoidiFragment = new ThongtinNguoidiFragment();
                        thongtinNguoidiFragment.setArguments(bundle);
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.main_fragment, thongtinNguoidiFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        });
        return view;
    }

    private void updateTotalPrice() {
        // Tính giá tiền dựa trên trạng thái của ghế
        totalPrice = calculateTotalPrice(seatStates);

        // Hiển thị giá tiền mới lên giao diện
        tvTotal.setText(String.format("%d", totalPrice));
    }

    private void updateSeatState(int seatIndex) {
        boolean currentState = seatStates.get(seatIndex);
        seatStates.set(seatIndex, !currentState);
        // Cập nhật số ghế được chọn
        if (!currentState) {
            selectedSeatCount++;
        } else {
            selectedSeatCount--;
        }
        // Cập nhật giá tiền khi trạng thái của ghế thay đổi
        updateTotalPrice();
        updateSelectedSeatsText();
    }

    private void setupSeatClickListener(ImageView seat, final int seatIndex) {
        seat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSeatState(seatIndex);
            }
        });
    }


    private void updateSelectedSeatsText() {
        StringBuilder selectedSeatsText = new StringBuilder("Ghế đã chọn: ");
        boolean isFirstSeat = true;
        for (int i = 0; i < seatStates.size(); i++) {
            if (seatStates.get(i)) {
                if (!isFirstSeat) {
                    selectedSeatsText.append(", ");
                }
                selectedSeatsText.append(i + 1); // Số ghế bắt đầu từ 1, không phải từ 0
                isFirstSeat = false;
            }
        }
        tvSelectedSeats.setText(selectedSeatsText.toString());

        // Hiển thị hoặc ẩn TextView tùy thuộc vào việc có ghế nào đã được chọn hay không
        if (selectedSeatsText.length() > 11) { // Độ dài của "Ghế đã chọn: " là 11
            tvSelectedSeats.setVisibility(View.VISIBLE);
        } else {
            tvSelectedSeats.setVisibility(View.GONE);
        }
    }

    private int calculateTotalPrice(List<Boolean> seatStates) {

        int totalSeats = 0;

        // Đếm số ghế được chọn
        for (boolean isSelected : seatStates) {
            if (isSelected) {
                totalSeats++;
            }
        }

        // Tính giá tiền tạm tính
        return tienve * totalSeats;
    }
}