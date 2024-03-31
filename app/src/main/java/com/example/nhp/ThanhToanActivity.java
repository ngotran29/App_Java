package com.example.nhp;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.example.nhp.Zalopay.CreateOrder;

import org.json.JSONObject;


import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {
    TextView txtv1, txtv2, txtv3;
    ImageView img1;
    Button btnZalo,btnHoanTat;
    //CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thanh_toan);
        btnZalo = findViewById(R.id.btnZalo); // Gán giá trị cho btnZalo
        btnHoanTat = findViewById(R.id.btnHoanTat);
        txtv1 = findViewById(R.id.txtv1);
        txtv2 = findViewById(R.id.txtv2);
        txtv3 = findViewById(R.id.txtv3);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ZaloPaySDK.init(2554, Environment.SANDBOX);
        addControls();
        addEvents();
    }

    protected void addEvents() {
        btnZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestPayment();

            }
        });
        btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị thông báo xác nhận thanh toán
                showConfirmationDialog();
            }
        });


    }
    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Bạn có chắc chắn muốn hoàn tất thanh toán?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Chuyển đến MainActivity
                Intent intent = new Intent(ThanhToanActivity.this, MainActivity.class);
                intent.putExtra("payment_success", true); // Gửi thông điệp rằng thanh toán thành công
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại
            }
        });
        builder.setNegativeButton("Không", null);
        builder.show();
    }
    private void IsLoading() {
        txtv1.setVisibility(View.INVISIBLE);
        txtv2.setVisibility(View.INVISIBLE);
        btnZalo.setVisibility(View.INVISIBLE);
    }

    public void RequestPayment() {
        CreateOrder orderApi = new CreateOrder();

        try {
            JSONObject data = orderApi.createOrder("10000");
            String code = data.getString("return_code");


            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                Log.d("test", token);

                ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(ThanhToanActivity.this)
                                        .setTitle("Payment Success")
                                        .setMessage(String.format("TransactionId: %s - TransToken: %s", transactionId, transToken))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setNegativeButton("Cancel", null).show();
                            }

                        });
                        IsLoading();
                    }



                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        // Xử lý khi thanh toán bị hủy
                        new AlertDialog.Builder(ThanhToanActivity.this)
                                .setTitle("User Cancel Payment")
                                .setMessage(String.format("zpTransToken: %s \n", zpTransToken))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        // Xử lý khi có lỗi trong quá trình thanh toán
                        new AlertDialog.Builder(ThanhToanActivity.this)
                                .setTitle("Payment Fail")
                                .setMessage(String.format("ZaloPayErrorCode: %s \nTransToken: %s", zaloPayError.toString(), zpTransToken))
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .setNegativeButton("Cancel", null).show();
                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addControls() {
        txtv1 = findViewById(R.id.txtv1);
        txtv2 = findViewById(R.id.txtv2);
        txtv3 = findViewById(R.id.txtv3);
        img1 = findViewById(R.id.img1);
        btnZalo = findViewById(R.id.btnZalo);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}