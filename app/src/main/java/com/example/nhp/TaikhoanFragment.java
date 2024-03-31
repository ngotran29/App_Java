    package com.example.nhp;

    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.TextView;

    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.fragment.app.FragmentTransaction;

    import com.bumptech.glide.Glide;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;

    public class TaikhoanFragment extends Fragment {
        private static final String ARG_USERNAME = "username";
        TextView tv_tv, tv_email;
        ImageView imageButton;
        FirebaseAuth mAuth;
        Button button, button2, button3, btnProfile, imlog_out;

        public TaikhoanFragment() {
            // Constructor public rỗng bắt buộc
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_taikhoan, container, false);
            imlog_out = view.findViewById(R.id.imlog_out);
            tv_tv = view.findViewById(R.id.tv_tv);
            mAuth = FirebaseAuth.getInstance();
            tv_email = view.findViewById(R.id.tv_email);
            imageButton = view.findViewById(R.id.imageButton);
            btnProfile = view.findViewById(R.id.btnProfile);
            button3=view.findViewById(R.id.button3);
            showInforUser();
            tv_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Chuyển đến LoginActivity khi tvlogin_tk được nhấp
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Khởi tạo FragmentManager
                    FragmentManager fragmentManager = getParentFragmentManager();

                    // Khởi tạo FragmentTransaction
                    FragmentTransaction transaction = fragmentManager.beginTransaction();

                    // Thay thế Fragment hiện tại bằng MyProfileFragment
                    transaction.replace(R.id.fragment_container, new MyProfileFragment());

                    // Thêm transaction vào back stack để quay lại Fragment trước đó nếu cần
                    transaction.addToBackStack(null);

                    // Hoàn thành và thực hiện thay đổi
                    transaction.commit();
                }
            });



            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(),FeedbackActivity.class);
                    startActivity(intent);

                }
            });

            imlog_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Gọi phương thức để xử lý đăng xuất
                    //                handleLogout();
                    mAuth.signOut();
                    signoutUser();
//                    showLogoutConfirmationDialog();
                }
            });
            return view;
        }

        private void signoutUser() {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Bạn có muốn đăng xuất?");
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "Có",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mAuth.signOut();
                            // Proceed with sign out
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });

            builder.setNegativeButton(
                    "Không",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = builder.create();

            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    AlertDialog dialog = (AlertDialog) dialogInterface;
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                    // Set custom styles for the buttons
                    positiveButton.setTextColor(getResources().getColor(R.color.black));
                    negativeButton.setTextColor(getResources().getColor(R.color.black));
                }
            });

            alertDialog.show();
        }


        //    private void handleLogout() {
        //        // Hiển thị hộp thoại xác nhận đăng xuất
        //        showLogoutConfirmationDialog();
        //    }



        void loadFragment(Fragment fragment) {
            FragmentTransaction fmTran = requireActivity().getSupportFragmentManager().beginTransaction();
            fmTran.replace(R.id.main_fragment, fragment);
            fmTran.addToBackStack(null);
            fmTran.commit();
        }
        public void showInforUser() {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser == null) {
                imlog_out.setVisibility(View.GONE);
                return  ;

            }
    //        FirebaseFirestore db = FirebaseFirestore.getInstance();
    //        db.collection("cities").document("LA")
    //                .get()
    //                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    //                    @Override
    //                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
    //                        if (task.isSuccessful()) {
    //                            DocumentSnapshot document = task.getResult();
    //                            if (document.exists()) {
    //                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
    //                            } else {
    //                                Log.d(TAG, "No such document");
    //                            }
    //                        } else {
    //                            Log.d(TAG, "get failed with ", task.getException());
    //                        }
    //                    }
    //                });
            String email = currentUser.getEmail();
            String name = currentUser.getDisplayName();
//            Uri photoUrl = currentUser.getPhotoUrl();

            tv_tv.setText(name);
            tv_email.setText(email);
//            Glide.with(this).load(photoUrl).error(R.drawable.user).into(imageButton);

        }


    }
