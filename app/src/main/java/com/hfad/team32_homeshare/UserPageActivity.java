package com.hfad.team32_homeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hfad.team32_homeshare.databinding.ActivityUserPageBinding;

public class UserPageActivity extends AppCompatActivity {

    private ActionBar actionBar;

    private ActivityUserPageBinding binding;

    private String prevFullName;
    private String prevPhone;
    private String prevClassStanding;
    private String prevGender;
    private String prevBiography;

    private ProgressDialog pd;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private String userID;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        actionBar = getSupportActionBar();
        actionBar.setTitle("Viewing User Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);

        profilePic = binding.profilePic;

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        pd = new ProgressDialog(this);
        try {
            userID = getIntent().getStringExtra("userID");

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users").document(userID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        pd.setTitle("Fetching user profile...");
                        pd.show();
                        DocumentSnapshot ds = task.getResult();
                        if (ds.exists()) {
                            if (ds.getData().get("fullName") != null) {
                                String name = "<b>Name: </b>" + ds.getData().get("fullName").toString();
                                binding.userFullName.setText(Html.fromHtml(name));
                            }
                            if (ds.getData().get("phone") != null) {
                                String phone = "<b>Phone: </b>" + ds.getData().get("phone").toString();
                                binding.userPhoneNumber.setText(Html.fromHtml(phone));
                            }
                            if (ds.getData().get("biography") != null) {
                                String bio = "<b>Biography: </b>" + ds.getData().get("biography").toString();
                                binding.userBiography.setText(Html.fromHtml(bio));
                            }
                            if (ds.getData().get("classStanding") != null) {
                                String classStanding = "<b>Class Standing: </b>" + ds.getData().get("classStanding").toString();
                                binding.userClassStanding.setText(Html.fromHtml(classStanding));
                            }
                            if (ds.getData().get("gender") != null) {
                                String gender = "<b>Gender: </b>" + ds.getData().get("gender").toString();
                                binding.userGender.setText(Html.fromHtml(gender));
                            }

                            StorageReference pathReference = storageReference.child("images/" + userID);
                            long MAX_BYTES = 1024 * 1024 * 10;
                            pathReference.getBytes(MAX_BYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    profilePic.setImageBitmap(bitmap);
                                }
                            });
                        }
                        pd.dismiss();
                    }
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Fetching user page failed.", Toast.LENGTH_LONG).show();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), MyActivity.class);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        return true;
    }
}