    package com.example.nhp;

    import androidx.activity.result.ActivityResult;
    import androidx.activity.result.ActivityResultCallback;
    import androidx.activity.result.ActivityResultLauncher;
    import androidx.activity.result.contract.ActivityResultContracts;
    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;

    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.res.Resources;
    import android.graphics.Bitmap;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.FrameLayout;
    import android.widget.ImageView;
    import android.widget.RelativeLayout;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.nhp.classes.ProgressHelper;
    import com.example.nhp.Utils;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.android.material.bottomsheet.BottomSheetBehavior;
    import com.google.android.material.bottomsheet.BottomSheetDialog;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.auth.UserProfileChangeRequest;
    import com.google.firebase.firestore.DocumentReference;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.io.IOException;
    import java.util.HashMap;
    import java.util.Map;

    public class Register extends AppCompatActivity {
    //    ImageView imgAvatar;
        RelativeLayout relativeLayout;
        EditText edUsername, edPass, edConfirmPass;
        Button btSignUp, btForgotPass;
        Toolbar toolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
            initView();
            initAction();
        }

        void initView() {
    //
    //        imgAvatar = findViewById(R.id.imgAvatar2);
    //        imgAvatar.setImageBitmap(Utils.loadBitmapFromAssets(this, "select_avatar_placeholder.png"));

            toolbar = findViewById(R.id.materialToolbarSignUp);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //
            edUsername = findViewById(R.id.editTextUserName);

            edPass = findViewById(R.id.editTextPassword);
            edConfirmPass = findViewById(R.id.editTextConfirmPassword);
            btSignUp = findViewById(R.id.buttonRegister);
            btForgotPass = findViewById(R.id.buttonForgotPassword);
        }

        void initAction() {
    //        imgAvatar.setOnClickListener(new View.OnClickListener() {
    //            @Override
    //            public void onClick(View view) {
    //                displayBottomSheet();
    //            }
    //        });

            btForgotPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });

            btSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //
                    if (checkInput() == true) {
                        ProgressHelper.showDialog(Register.this,"Vui lòng chờ trong giây lát...");

                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edUsername.getText().toString(),
                                        edConfirmPass.getText().toString())
                                .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            ProgressHelper.dismissDialog();
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("FirebaseAuthen", "createUserWithEmail:success");
                                            firebaseRegisterNewUser();
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    //                                        updateUI(user);
                                            goHomeActivity();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("FirebaseAuthen", "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(Register.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });


                    }


                    //
                }
            });
        }

        void goHomeActivity(){
            Intent i = new Intent(Register.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        void updateUserProfile(){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(edUsername.getText().toString())
                    .setPhotoUri(Uri.parse(""))
                    .build();

            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("", "User profile updated.");
                            }
                        }
                    });
        }


        void firebaseRegisterNewUser(){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            // Create a new user with a first and last name
            Map<String, Object> user = new HashMap<>();
            user.put("email", edUsername.getText().toString().trim());

            user.put("password", edPass.getText().toString());
    //        user.put("avatar", "");


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
        boolean checkInput() {
            if (edUsername.getText().toString().length() < 6) {
                edUsername.setError("Invalid mobile number / email.");
                return false;
            }
    //
    //        if (edFullName.getText().toString().length() < 3) {
    //            edFullName.setError("Invalid full name.");
    //            return false;
    //        }

            if (edPass.getText().toString().length() < 3) {
                edPass.setError("Invalid password.");
                return false;
            }

            if (!edPass.getText().toString().equals(edConfirmPass.getText().toString())) {
                edConfirmPass.setError("Password not match.");
                return false;
            }

            return true;
        }

    //    private void displayBottomSheet() {
    //
    //        // creating a variable for our bottom sheet dialog.
    //        final BottomSheetDialog bottomSheetTeachersDialog = new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);
    //
    //        // passing a layout file for our bottom sheet dialog.
    //        View layout = LayoutInflater.from(this).inflate(R.layout.select_photo_bottom_sheet_layout, relativeLayout, false);
    //
    //        // passing our layout file to our bottom sheet dialog.
    //        bottomSheetTeachersDialog.setContentView(layout);
    //
    //        // below line is to set our bottom sheet dialog as cancelable.
    //        bottomSheetTeachersDialog.setCancelable(false);
    //
    //        // below line is to set our bottom sheet cancelable.
    //        bottomSheetTeachersDialog.setCanceledOnTouchOutside(true);
    //        bottomSheetTeachersDialog.setOnShowListener(dialogInterface -> {
    //            BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
    //
    //            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
    //            BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
    //        });
    //
    //        // below line is to display our bottom sheet dialog.
    //        bottomSheetTeachersDialog.show();
    //
    //        // initializing our text views and image views.
    //        Button btGallery = layout.findViewById(R.id.buttonSelectGallery);
    //        btGallery.setOnClickListener(view -> {
    //            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
    //            photoPickerIntent.setType("image/*");
    //            imagePickerActivityResult.launch(photoPickerIntent);
    //        });
    //        Button btCapture = layout.findViewById(R.id.buttonSelectCapture);
    //        btCapture.setOnClickListener(view -> {
    //            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    //            imageCaptureActivityResult.launch(intent);
    //        });
    //        // creating a variable for document reference.
    //
    //    }
    //
    //    ActivityResultLauncher<Intent> imagePickerActivityResult = registerForActivityResult(
    //            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    //                @Override
    //                public void onActivityResult(ActivityResult result) {
    //                    if (result != null) {
    //                        Intent data = result.getData();
    //                        Uri imageUri = data.getData();
    //                        Bitmap bitmap = null;
    //                        try {
    //                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
    //                            imgAvatar.setImageBitmap(bitmap);
    //                        } catch (IOException e) {
    //                            e.printStackTrace();
    //                        }
    //
    //                    }
    //                }
    //            }
    //    );
    //
    //    ActivityResultLauncher<Intent> imageCaptureActivityResult = registerForActivityResult(
    //            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
    //                @Override
    //                public void onActivityResult(ActivityResult result) {
    //                    if (result != null) {
    //                        Intent intent = result.getData();
    //                        Bitmap photo = (Bitmap) intent.getExtras().get("data");
    //                        imgAvatar.setImageBitmap(photo);
    //                    }
    //                }
    //            }
    //    );

        @Override
        public boolean onSupportNavigateUp() {
            finish();
            return super.onSupportNavigateUp();
        }
    }