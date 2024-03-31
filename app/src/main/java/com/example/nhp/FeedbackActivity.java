package com.example.nhp;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackActivity extends AppCompatActivity {

    EditText edt1, edt2;
    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        edt1 = findViewById(R.id.edt1);
        edt2 = findViewById(R.id.edt2);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FeedbackActivity.this, TaikhoanFragment.class);
                startActivity(intent);
            }
        });
    }

    private void sendEmail() {
        String userEmail = getCurrentAccountEmail(FeedbackActivity.this);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"garenagina@gmail.com"}); // Địa chỉ email nhận
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback From App");
        intent.putExtra(Intent.EXTRA_TEXT,  "\nTên: " + edt1.getText() + "\nNội dung: " + edt2.getText());
        try {
            startActivity(Intent.createChooser(intent, "Vui lòng chọn ứng dụng email"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(FeedbackActivity.this, "Không có ứng dụng email", Toast.LENGTH_SHORT).show();
        }
    }

    private String getCurrentAccountEmail(Context context) {
        AccountManager accountManager = AccountManager.get(context);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        if (accounts.length > 0) {
            return accounts[0].name;
        }
        return null;
    }
}
