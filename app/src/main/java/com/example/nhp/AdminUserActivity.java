    package com.example.nhp;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;

    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.nhp.classes.ProgressHelper;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.auth.SignInMethodQueryResult;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.FirebaseFirestore;
    import com.google.firebase.firestore.QueryDocumentSnapshot;
    import com.google.firebase.firestore.QuerySnapshot;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class AdminUserActivity extends AppCompatActivity {

        Button btnAdd, btnReset, btnOut, btnDelete, btnDelete_dia, btnCancel_dia;
        Button thoat;
        EditText email_add, pass_add, email_dele;
        AlertDialog alertDialog;
        TextView well_admin;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_user);
            initView();
            DiaAdd();

            DiaDele();
            DiaLogOut();
            // Set click listener for btnDelete to show the delete dialog

        }

        void initView() {
            // add
            btnAdd = findViewById(R.id.btnAdd);
            btnReset = findViewById(R.id.btnReset);
            btnOut = findViewById(R.id.btnOut);
            //delete
            btnDelete = findViewById(R.id.btnDelete);
            btnDelete_dia = findViewById(R.id.btnDelete_dia);
            btnCancel_dia = findViewById(R.id.btnCancel_dia);
            //out
            well_admin = findViewById(R.id.well_admin);
            thoat = findViewById(R.id.thoat);
        }

        private void DiaAdd() {
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Show custom dialog
                    showCustomDialog();
                }
            });
        }
        private  void DiaLogOut(){
            thoat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Hiển thị dialog xác nhận trước khi đăng xuất
                    showLogoutConfirmationDialog();
                }
            });
        }
        private void DiaDele(){
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showLoginDialog();  // Call DiaDelete when btnDelete is clicked
                }
            });
        }

//        private void DiaDelete() {
//            // Create dialog builder
//            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//
//            // Inflate custom layout for delete dialog
//            LayoutInflater inflater = getLayoutInflater();
//            View dialogView = inflater.inflate(R.layout.dialog_delete, null);
//
//            // Find buttons and views within the delete dialog layout
//            Button btnDeleteDia = dialogView.findViewById(R.id.btnDelete_dia);
//            Button btnCancelDia = dialogView.findViewById(R.id.btnCancel_dia);
//            EditText emailDele = dialogView.findViewById(R.id.email_dele);
//
//            // Set click listener for btnDeleteDia
//            btnDeleteDia.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // Call method to delete the user or perform deletion logic
////                    deleteUser(emailDele.getText().toString().trim());
//                    alertDialog.dismiss(); // Dismiss the delete dialog
//                }
//            });
//
//            // Set click listener for btnCancelDia
//            btnCancelDia.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    alertDialog.dismiss(); // Dismiss the delete dialog
//                }
//            });
//
//            // Set custom layout for the delete dialog
//            dialogBuilder.setView(dialogView);
//
//            // Create the dialog
//            alertDialog = dialogBuilder.create();
//
//            // Set click listener for btnDelete to show the delete dialog
//            btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    alertDialog.show(); // Show the delete dialog when btnDelete is clicked
//                }
//            });
//        }

        private void showCustomDialog() {
            // Create dialog builder
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            // Inflate custom layout
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_adduser, null);

            // Initialize EditTexts
            email_add = dialogView.findViewById(R.id.email_add);
            pass_add = dialogView.findViewById(R.id.pass_add);

            // Find buttons within the dialog view
            Button btnReset = dialogView.findViewById(R.id.btnReset_add);
            Button btnOut = dialogView.findViewById(R.id.btnOut);

            // Set click listener for btnReset
            btnReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Call method to create new account
                    createNewAccount();
                }
            });

            // Set click listener for btnOut
            btnOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Dismiss the dialog when btnOut is clicked
                    alertDialog.dismiss();
                }
            });

            // Set custom layout for the dialog
            dialogBuilder.setView(dialogView);

            // Create and show the dialog
            alertDialog = dialogBuilder.create();
            alertDialog.show();
        }
        // Method to handle user deletion or perform deletion logic

        private void showLoginDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setMessage("Nhập tài khoản mà bạn muốn xóa");
            // Add EditTexts for email and password input
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_login, null);
            EditText emailInput = dialogView.findViewById(R.id.email_input);
            EditText passwordInput = dialogView.findViewById(R.id.password_input);
            builder.setView(dialogView);
            builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    String email = emailInput.getText().toString().trim();
                    String password = passwordInput.getText().toString().trim();
                    // Thực hiện xác thực đăng nhập
                    authenticateAndDeleteUser(email, password);
                }
            });
            builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // Đóng dialog nếu người dùng chọn Hủy
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }

        private void authenticateAndDeleteUser(String email, String password) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Đăng nhập thành công, tiến hành xóa tài khoản
                            deleteUser(FirebaseAuth.getInstance().getCurrentUser());
                        } else {
                            // Đăng nhập thất bại, thông báo cho người dùng
                            Toast.makeText(AdminUserActivity.this, "Đăng nhập thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        void deleteUser(FirebaseUser user) {
            if (user != null) {
                user.delete()
                        .addOnCompleteListener(deleteTask -> {
                            if (deleteTask.isSuccessful()) {
                                // Người dùng đã được xóa thành công
                                // Tiếp tục xóa dữ liệu của người dùng từ Firestore (nếu cần)
                                deleteUserDataFromFirestore(user.getEmail());
                                Toast.makeText(AdminUserActivity.this, "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                // Xóa người dùng không thành công
                                Toast.makeText(AdminUserActivity.this, "Xóa người dùng thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                // Không có người dùng hiện đang đăng nhập
                Toast.makeText(AdminUserActivity.this, "Không tìm thấy người dùng hiện đang đăng nhập", Toast.LENGTH_SHORT).show();
            }
        }

        void deleteUserDataFromFirestore(String email) {
            // Truy vấn Firestore để xóa dữ liệu người dùng
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .whereEqualTo("email", email)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            // Lặp qua các tài liệu được tìm thấy
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                // Xóa từ Firestore
                                document.getReference().delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // Tài liệu người dùng đã được xóa thành công từ Firestore
                                                Toast.makeText(AdminUserActivity.this, "Xóa dữ liệu người dùng từ Firestore thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                // Xử lý lỗi khi xóa tài liệu người dùng từ Firestore
                                                Toast.makeText(AdminUserActivity.this, "Xóa dữ liệu người dùng từ Firestore thất bại", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý lỗi khi truy vấn Firestore
                            Toast.makeText(AdminUserActivity.this, "Truy vấn Firestore thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
        }




        void createNewAccount() {
            // Check input validity
            if (!checkInput()) {
                return;
            }

            ProgressHelper.showDialog(AdminUserActivity.this, "Vui lòng chờ trong giây lát...");

            String email = email_add.getText().toString();
            String password = pass_add.getText().toString();

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(AdminUserActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                ProgressHelper.dismissDialog();
                                Log.d("FirebaseAuthen", "createUserWithEmail:success");
                                firebaseRegisterNewUser();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                // updateUI(user);
                                Toast.makeText(AdminUserActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("FirebaseAuthen", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(AdminUserActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        boolean checkInput() {
            if (email_add == null || pass_add == null) {
                return false;
            }
            if (email_add.getText().toString().isEmpty()) {
                email_add.setError("Email is required.");
                return false;
            }
            if (pass_add.getText().toString().isEmpty()) {
                pass_add.setError("Password is required.");
                return false;
            }
            return true;
        }

        void firebaseRegisterNewUser() {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("email", email_add.getText().toString().trim());
            user.put("password", pass_add.getText().toString());

            // Add a new document with a generated ID
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            ProgressHelper.dismissDialog();
                            Log.d("Register", "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            ProgressHelper.dismissDialog();
                            Log.w("Register", "Error adding document", e);
                        }
                    });
        }
        //logout
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
                    Intent intent = new Intent(AdminUserActivity.this, LoginActivity.class);
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