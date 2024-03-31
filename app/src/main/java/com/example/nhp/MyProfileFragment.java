package com.example.nhp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.ktx.Firebase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MyProfileFragment extends Fragment {

    ImageView imgAvatar;
    EditText edtEmail_pro, edtPass_pro, edtName_pro;
    Button btnUpdate;
    View view;
    RelativeLayout relativeLayout;
    private Uri selectedImageUri;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        initView();
        intitAction();

        setUserInformation();

        return view;
    }

    void initView() {
        imgAvatar = view.findViewById(R.id.imgAvatar);
        imgAvatar.setImageBitmap(Utils.loadBitmapFromAssets(getContext(), "select_avatar_placeholder.png"));

        edtName_pro = view.findViewById(R.id.edtName_pro);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        edtEmail_pro = view.findViewById(R.id.edtEmail_pro);


    }

    void intitAction() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayBottomSheet();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserProfile();


            }
        });

    }

    void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        edtName_pro.setText(user.getDisplayName());
        edtEmail_pro.setText(user.getEmail());
//        Glide.with(this).load(user.getPhotoUrl()).error(R.drawable.user).into(imgAvatar);
    }


    private void displayBottomSheet() {

        // creating a variable for our bottom sheet dialog.
        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(getContext(), R.style.BottomSheetDialogTheme);


        // passing a layout file for our bottom sheet dialog.
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.select_photo_bottom_sheet_layout, relativeLayout, false);


        // passing our layout file to our bottom sheet dialog.
        bottomSheetTeachersDialog.setContentView(layout);

        // below line is to set our bottom sheet dialog as cancelable.
        bottomSheetTeachersDialog.setCancelable(false);

        // below line is to set our bottom sheet cancelable.
        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeachersDialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog d = (BottomSheetDialog) dialogInterface;

            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        // below line is to display our bottom sheet dialog.
        bottomSheetTeachersDialog.show();

        // initializing our text views and image views.
        Button btGallery = layout.findViewById(R.id.buttonSelectGallery);
        btGallery.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            imagePickerActivityResult.launch(photoPickerIntent);
        });
        Button btCapture = layout.findViewById(R.id.buttonSelectCapture);
        btCapture.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageCaptureActivityResult.launch(intent);
        });
        // creating a variable for document reference.

    }

    ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        Intent data = result.getData();
                        Uri imageUri = data.getData();
                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);

                            imgAvatar.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
    );

    ActivityResultLauncher<Intent> imageCaptureActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result != null) {
                        Intent intent = result.getData();
                        Bitmap photo = (Bitmap) intent.getExtras().get("data");
                        imgAvatar.setImageBitmap(photo);
                    }
                }
            }
    );
    // Hàm để lấy URI từ ảnh Bitmap
    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    void updateUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(edtName_pro.getText().toString())
                .setPhotoUri(selectedImageUri) // Sử dụng selectedImageUri trực tiếp
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                           Toast.makeText(getActivity(),"Cập nhật thành công",
                                   Toast.LENGTH_SHORT).show();
                            // Close MyProfileFragment
                            getParentFragmentManager().beginTransaction().remove(MyProfileFragment.this).commit();
                        }
                    }
                });

    }
}

