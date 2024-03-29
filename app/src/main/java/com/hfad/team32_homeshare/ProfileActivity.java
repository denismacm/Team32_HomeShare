package com.hfad.team32_homeshare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hfad.team32_homeshare.databinding.ActivityProfileBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;

    private ActionBar actionBar;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore db;
    private ImageView profilePic;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ProgressDialog progressDialog;
//    private boolean imageChange;

    private String prevFullName;
    private String prevPhone;
    private String prevClassStanding;
    private String prevGender;
    private String prevBiography;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBar = getSupportActionBar();
        actionBar.setTitle("Manage Your Profile");

        prevFullName = "";
        prevPhone = "";
        prevClassStanding = "";
        prevGender = "";
        prevBiography = "";

        Spinner classSpinner = (Spinner) findViewById(R.id.class_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(this, R.array.class_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        classSpinner.setAdapter(classAdapter);

        Spinner genderSpinner = (Spinner) findViewById(R.id.gender_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderSpinner.setAdapter(genderAdapter);

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
        checkUser();

//        imageChange = false;
        profilePic = binding.profilePic;


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();
            }
        });

        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = binding.fullNameEt.getText().toString().trim();
                String email = binding.emailEt.getText().toString().trim();
                String phone = binding.phoneNumEt.getText().toString().trim();
                String classStanding = binding.classSpinner.getSelectedItem().toString();
                String gender = binding.genderSpinner.getSelectedItem().toString();
                String biography = binding.biographyEt.getText().toString().trim();

                if (TextUtils.isEmpty(fullName)) {
                    binding.fullNameEt.setError("Name cannot be empty.");
                } else if (!fullName.contains(" ")) {
                    binding.fullNameEt.setError("Name must contain at least a first and last name.");
                } else if (TextUtils.isEmpty(phone)) {
                    binding.phoneNumEt.setError("Phone cannot be empty.");
                } else if (!Patterns.PHONE.matcher(phone).matches()) {
                    binding.phoneNumEt.setError("Invalid phone format");
                } else if (TextUtils.isEmpty(email)) {
                    binding.emailEt.setError("Email cannot be empty.");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.emailEt.setError("Invalid email format");
                } else if (TextUtils.isEmpty(biography)) {
                    binding.biographyEt.setError("Biography cannot be empty.");
                } else if (profilePic.getDrawable() == null) {
                    Toast.makeText(ProfileActivity.this, "Image needs to be uploaded.", Toast.LENGTH_SHORT).show();

                } else {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    DocumentReference ref = db.collection("users").document(firebaseUser.getUid());
                    if (!firebaseUser.getEmail().equals(email)) {
                        ref.update("email", email);
                        firebaseUser.updateEmail(email);
                    }
                    if (!prevFullName.equals(fullName)) {
                        ref.update("fullName", fullName);
                        ref.update("fullNameLower", fullName.toLowerCase());
                    }
                    if (!prevPhone.equals(phone)) {
                        ref.update("phone", phone);
                    }
                    if (!prevBiography.equals(biography)) {
                        ref.update("biography", biography);
                    }
                    if (!prevClassStanding.equals(classStanding)) {
                        ref.update("classStanding", classStanding);
                        ArrayList<String> arr = new ArrayList<>();
                        arr.add("freshman");
                        arr.add("sophomore");
                        arr.add("junior");
                        arr.add("senior");
                        arr.add("masters");
                        arr.add("doctorate");
                        arr.add("employee");
                        ref.update("classStandingNum", arr.indexOf(classStanding)+1);
                    }
                    if (!prevGender.equals(gender)) {
                        ref.update("gender", gender);
                    }
//                    if (imageChange) {
//                        uploadPicture();
//                    }
                    Toast.makeText(ProfileActivity.this, "Submitted changes for " + email, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
//                    finish();
                }
            }
        });
    }

    private void checkUser() {
        // check if user is already logged in, if so then open profile activity

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            // user is already logged in
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        } else {
            progressDialog.setTitle("Fetching profile...");
            progressDialog.show();
            db.collection("users").document(firebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                DocumentSnapshot snapshot = task.getResult();

                                if (snapshot.exists()) {
                                    if (snapshot.getData().get("fullName") != null) {
                                        prevFullName = snapshot.getData().get("fullName").toString();
                                        binding.fullNameEt.setText(prevFullName);
                                    }

                                    if (snapshot.getData().get("phone") != null) {
                                        prevPhone = snapshot.getData().get("phone").toString();
                                        binding.phoneNumEt.setText(prevPhone);
                                    }

                                    binding.emailEt.setText(firebaseUser.getEmail());

                                    if (snapshot.getData().get("biography") != null) {
                                        prevBiography = snapshot.getData().get("biography").toString();
                                        binding.biographyEt.setText(prevBiography);
                                    }
                                    if (snapshot.getData().get("classStanding") != null) {
                                        prevClassStanding = snapshot.getData().get("classStanding").toString();
                                        binding.classSpinner.setSelection(getIndex(binding.classSpinner, prevClassStanding));
                                    }
                                    if (snapshot.getData().get("gender") != null) {
                                        prevGender = snapshot.getData().get("gender").toString();
                                        binding.genderSpinner.setSelection(getIndex(binding.genderSpinner, prevGender));
                                    }
                                    StorageReference pathReference = storageReference.child("images/" + firebaseUser.getUid());
                                    long MAX_BYTES = 1024 * 1024 * 10;
                                    pathReference.getBytes(MAX_BYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                        @Override
                                        public void onSuccess(byte[] bytes) {
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                            profilePic.setImageBitmap(bitmap);
                                        }
                                    });
                                } else {
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("fullName", "");
                                    user.put("phone", "");
                                    user.put("email", firebaseUser.getEmail());
                                    user.put("gender", "non-binary");
                                    user.put("classStanding", "freshman");
                                    user.put("classStandingNum", 1);
                                    user.put("biography", "");
                                    user.put("fullNameLower", "");
                                    db.collection("users").document(firebaseUser.getUid()).set(user);
                                    ArrayList<String> declinedIDs = new ArrayList<>();
                                    ArrayList<String> responsesList = new ArrayList<>();
                                    ArrayList<String> invitationsList = new ArrayList<>();
                                    db.collection("users").document(firebaseUser.getUid()).update("declinedInvitationsList", declinedIDs);
                                    db.collection("users").document(firebaseUser.getUid()).update("responsesList", responsesList);
                                    db.collection("users").document(firebaseUser.getUid()).update("invitationsList", invitationsList);
                                    binding.emailEt.setText(firebaseUser.getEmail());
                                }
                            }
                        }
                    });
        }
    }
    private int getIndex(Spinner spinner, String myString) {
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data != null && data.getData()!= null) {
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        progressDialog.setTitle("Uploading image...");
        progressDialog.show();

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        StorageReference ref = storageReference.child("images/" + firebaseUser.getUid());

        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image uploaded", Snackbar.LENGTH_LONG).show();
//                        imageChange = false;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        progressDialog.setMessage("Percentage: " + (int) progressPercent + "%");
                    }
                });
    }
}