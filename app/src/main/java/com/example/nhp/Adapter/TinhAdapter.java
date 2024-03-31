package com.example.nhp.Adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhp.R;
import com.example.nhp.TimkiemFragment;
import com.example.nhp.Tinhthanh;

import java.util.ArrayList;
import java.util.List;

public class TinhAdapter extends RecyclerView.Adapter<TinhAdapter.TinhViewHoler> {
    List<Tinhthanh> lstinhthanh;
    TinhAdapter tinhAdapter;
    private OnItemClickListener listener;
    private FragmentManager fragmentManager;
    private List<Integer> selectedPositions; // Danh sách các vị trí của các item được chọn
    private List<String> selectedCities;

    public interface OnItemClickListener {
        void onItemClick( String cityname);
    }

    public TinhAdapter(List<Tinhthanh> lstinhthanh, FragmentManager fragmentManager) {
        this.lstinhthanh = lstinhthanh;
        this.fragmentManager = fragmentManager;
        selectedCities = new ArrayList<>();
        selectedPositions = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public TinhViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_tinh, parent, false);
        return new TinhViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TinhViewHoler holder, int position) {
        Tinhthanh tinhthanh = lstinhthanh.get(position);
        if (tinhthanh == null) {
            return;
        }
        holder.tentinh.setText(tinhthanh.getName());

        // Kiểm tra xem vị trí hiện tại có trong danh sách các vị trí được chọn không
        if (selectedPositions.contains(position)) {
            // Nếu có, cập nhật màu nền cho item tương ứng
            if (position == selectedPositions.get(0)) {
                // Item đầu tiên được chọn có màu vàng
                holder.itemView.setBackgroundColor(Color.YELLOW);
            } else {
                // Item thứ hai được chọn có màu xanh
                holder.itemView.setBackgroundColor(Color.GREEN);
            }
        } else {
            // Nếu không, sử dụng màu nền mặc định
            holder.itemView.setBackgroundColor(Color.WHITE);
        }
    }


    @Override
    public int getItemCount() {
        if (lstinhthanh != null) {
            return lstinhthanh.size();
        }
        return 0;
    }
    public class TinhViewHoler extends RecyclerView.ViewHolder {

        TextView tentinh;

        public TinhViewHoler(@NonNull View itemView) {
            super(itemView);
            tentinh = itemView.findViewById(R.id.tv_item_ten_tinh);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (selectedPositions.contains(position)) {
                            // Nếu item đã được chọn, hủy bỏ chọn bằng cách loại bỏ vị trí khỏi danh sách
                            selectedPositions.remove(Integer.valueOf(position));
                        } else {
                            // Nếu chưa được chọn, kiểm tra xem đã chọn đủ 2 item chưa
                            if (selectedPositions.size() < 2) {
                                // Nếu chưa đủ 2 item, thêm vị trí vào danh sách
                                selectedPositions.add(position);
                            } else {
                                // Nếu đã đủ 2 item, không thêm vị trí mới
                                Toast.makeText(itemView.getContext(), "Chỉ có thể chọn tối đa 2 thành phố", Toast.LENGTH_SHORT).show();
                            }
                        }
                        // Cập nhật giao diện người dùng
                        notifyItemChanged(position);
                        // Gửi thông tin thành phố đến activity hoặc fragment
                        String cityName = lstinhthanh.get(position).getName();
                        listener.onItemClick(cityName);
                    }
                }
            });
        }
    }


    public void setSelectedCities(List<String> selectedCities) {
        this.selectedCities = selectedCities;
        notifyDataSetChanged();
    }

    public void addSelectedCity(String cityName) {
        selectedCities.add(cityName);
        notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
    }

    public void removeSelectedCity(String cityName) {
        selectedCities.remove(cityName);
        notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
    }

    public List<String> getSelectedCities() {
        return selectedCities;
    }
}
