package com.example.nhp;



import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private EditText edUserNameC;
    private EditText edPasswordC;
    private EditText edConfirmPassWordC;
    private EditText edEmailC;
    private EditText edPhoneNumberC;
    private Button btRegister;
    private ImageButton imBack;
    private RadioGroup rbSex;
    private SharedPreferences.Editor editor;

    SharedPreferences sharedPreferences;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setContentView(R.layout.activity_register);

        //khai báo share Pre
        sharedPreferences = getSharedPreferences(Utils.SHARE_PREFERENCES_APP, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        //laydulieu
        laydulieu();
        taosukien();
    }

    void laydulieu() {
        edUserNameC = findViewById(R.id.edUserNameRe);
        edPasswordC = findViewById(R.id.edPassWordRE);
        edConfirmPassWordC = findViewById(R.id.ed_confirm_pass);
        edEmailC = findViewById(R.id.edEmailRE);
        edPhoneNumberC = findViewById(R.id.edPhoneRE);
        rbSex = findViewById(R.id.rg_gioitinh);
        btRegister = findViewById(R.id.btRegisterRE);
        imBack = findViewById(R.id.imbBack);
    }

    void taosukien() {
        btRegister.setOnClickListener(v -> sukienRegister());
        imBack.setOnClickListener(v -> finish());
    }

    void sukienRegister() {
        String userName = edUserNameC.getText().toString().trim();
        String pass = edPasswordC.getText().toString().trim();
        String confimPass = edConfirmPassWordC.getText().toString().trim();
        String email = edEmailC.getText().toString().trim();
        String phone = edPhoneNumberC.getText().toString().trim();
        //nếu sex = 1 là nam, sex =0 là nữ
        int sex = 1;
        boolean isvaid = checkUserName(userName)&& checkPassWord(pass, confimPass) ;
        if(isvaid){
            // nếu dữ liệu hợp lệ, tạo đối tượng user để lưu vô share pre
            User userNew = new User();
            userNew.setUserName(userName);
            userNew.setPassword(pass);
            userNew.setEmail(email);
            userNew.setPhoneNumber(phone);
            // lấy radio button id đang được checked
            int sexSelected = rbSex.getCheckedRadioButtonId();
            if(sexSelected == R.id.rbNu)
            {
                sex = 0;
            }
            userNew.setSex(sex);
            // vì user là object nên convert qua string với format là json để lưu vào share pre

            String userStr = gson.toJson(userNew);
            editor.putString(Utils.KEY_USER,userStr);
            editor.commit();
            // dùng Toast đẻ show thông báo đăng ký thành công
            Toast.makeText(Register.this, "Đăng ký tài khoản thành công", Toast.LENGTH_LONG).show();
            //finish
            finish();
        }

    }

    private boolean checkUserName(String userName) {
        if (userName.isEmpty()) {
            edUserNameC.setError("Vui lòng nhập tên đăng nhập");
            return false;
        }
        if (userName.length() <= 5) {
            edUserNameC.setError("Tên đăng nhập phải ít nhất có 6 ký tự");
            return false;
        }
        return true;
    }
    private boolean checkPassWord(String passWord, String confimpass){
        if(passWord.isEmpty()){
            edPasswordC.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        if (passWord.length()<=5)
        {
            edPasswordC.setError("Mật khẩu phải có ít nhất 6 ký tự");
            return false;
        }
        if(!passWord.equals(confimpass))
        {
            edConfirmPassWordC.setError("Xác nhận mật khẩu không khớp");
            return false;
        }
        return true;
    }



}