package com.example.nhp;



import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btLogin, btResgister;
    EditText edUserName, edPassWord;
    SharedPreferences.Editor editor;
    private final Gson gson = new Gson();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//
        anhxa();
        //
        sharedPreferences = getSharedPreferences(Utils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        //
        taosukien();
    }

    private void taosukien() {
        btLogin.setOnClickListener(v -> checkUserLogin());
        btResgister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, Register.class);
                startActivity(i);
            }
        });
    }

    private void anhxa() {
        btLogin = findViewById(R.id.btnLogin);
        btResgister = findViewById(R.id.btnRegister);
        edUserName = findViewById(R.id.edUsername);
        edPassWord = findViewById(R.id.edPassWord);
    }

    private void checkUserLogin() {
        String usePre = sharedPreferences.getString(Utils.KEY_USER, null);
        User user = gson.fromJson(usePre, User.class);
        // user = null == chưa đăng ký
        if(user == null)
        {
            return;
        }
        //kiểm tra user name và pass co trùng với đối tượng user trong shar pre hay không
        boolean isValid = edUserName.getText().toString().trim().equals(user.getUserName())&& edPassWord.getText().toString().trim().equals(user.getPassword());
        if(isValid)
        {
            Intent intent = new Intent(this,TaikhoanFragment.class);
            // khởi tạo bundle để truyền data qua cho Main
            Bundle bundle = new Bundle();
            //vì user là object nên dùng putSerializable
            bundle.putSerializable(Utils.KEY_USER_PROFILE, user);

            // put bundle cho intent
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }
}