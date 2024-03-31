package com.example.nhp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nhp.Adapter.TinhAdapter;
import com.example.nhp.Adapter.VeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanLyVeActivity extends AppCompatActivity {

    RecyclerView rvlistve;
    VeAdapter veAdapter;
    List<Vexe> lstve;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_ve);

        logout = findViewById(R.id.logout);
        rvlistve = findViewById(R.id.rvlistve);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvlistve.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rvlistve.addItemDecoration(dividerItemDecoration);
        lstve = new ArrayList<>();
        veAdapter = new VeAdapter(lstve);

        rvlistve.setAdapter(veAdapter);
        getListVefromFDB();
        LogOut();
    }

    private void getListVefromFDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_ve");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (lstve != null) {
                    lstve.clear();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Vexe vexe = dataSnapshot.getValue(Vexe.class);
                    lstve.add(vexe);
                }
                veAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }


    private void LogOut(){
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmationDialog();  // Call DiaDelete when btnDelete is clicked
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận đăng xuất");
        builder.setMessage("Bạn có muốn đăng xuất?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Nếu người dùng chọn "Có", thực hiện đăng xuất
                FirebaseAuth.getInstance().signOut(); // Đăng xuất tài khoản
                // Chuyển hướng đến trang LoginActivity
                Intent intent = new Intent(QuanLyVeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại để ngăn người dùng quay lại
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Nếu người dùng chọn "Không", đóng dialog
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }
}


