package com.hfad.team32_homeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hfad.team32_homeshare.databinding.ActivityRegisterBinding;

import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    //ViewBinding
    private ActivityRegisterBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private ActionBar actionBar;

    private String email = "", password1 = "", password2 = "", fullName = "", phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // configure action bar, title, back button
        actionBar = getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Initialize FireBase auth
        firebaseAuth = FirebaseAuth.getInstance();

        // configure progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait.");
        progressDialog.setMessage("Creating your account...");
        progressDialog.setCanceledOnTouchOutside(false);

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed(); // go to previous activity when back button of action bar is pressed
//        return super.onSupportNavigateUp();
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void validateData() {
        // get data
        fullName = binding.fullNameEt.getText().toString().trim();
        email = binding.emailEt.getText().toString().trim();
        phone = binding.phoneNumEt.getText().toString().trim();
        password1 = binding.passwordEt.getText().toString().trim();
        password2 = binding.passwordConfirmEt.getText().toString().trim();

        if (TextUtils.isEmpty(fullName)) {
            binding.fullNameEt.setError("Name cannot be empty.");
        } else if (!validateFirstAndLastName(fullName)) {
            binding.fullNameEt.setError("Name must contain at least a first and last name.");
        } else if (TextUtils.isEmpty(phone)) {
            binding.phoneNumEt.setError("Phone cannot be empty.");
        } else if (!Patterns.PHONE.matcher(phone).matches()) {
            binding.phoneNumEt.setError("Invalid phone format");
        } else if (TextUtils.isEmpty(email)) {
            binding.emailEt.setError("Email cannot be empty.");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.setError("Invalid email format");
        } else if (TextUtils.isEmpty(password1)) {
            binding.passwordEt.setError("Enter password");
        } else if (!isValidPassword(password1)) {
            binding.passwordEt.setError("Password must be greater than 5 characters.");
        } else if (!validateMatchingPassword(password1, password2)) {
            binding.passwordConfirmEt.setError("Passwords must be equal to each other.");
        } else {
            firebaseSignUp();
        }
    }

    public static boolean validateNameFilled(String fullName) {
        return !TextUtils.isEmpty(fullName);
    }

    public static boolean validateFirstAndLastName(String fullName) {
        return fullName.trim().contains(" ");
    }

    public boolean validatePhoneFilled(String phone) {
        return !TextUtils.isEmpty(phone);
    }

    public static boolean validatePhone(String phone) {
        return Patterns.PHONE.matcher(phone).matches();
    }

    public static boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return password.trim().length() > 6;
    }

    public static boolean validateMatchingPassword(String password1, String password2) {
        return password1.trim().equals(password2.trim());
    }

    private void firebaseSignUp() {
        // show progress
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password1)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // signup success
                        progressDialog.dismiss();
                        // get user info
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Map<String, Object> user = new HashMap<>();
                        user.put("fullName", fullName);
                        user.put("phone", phone);
                        user.put("email", email);
                        user.put("gender", "non-binary");
                        user.put("classStanding", "freshman");
                        user.put("classStandingNum", 1);
                        user.put("biography", "");
                        user.put("fullNameLower", fullName.toLowerCase());
                        ArrayList<String> declinedIDs = new ArrayList<>();
                        user.put("declinedInvitationsList", declinedIDs);
                        ArrayList<String> responsesList = new ArrayList<>();
                        user.put("responsesList", responsesList);
                        ArrayList<String> invitationsList = new ArrayList<>();
                        user.put("invitationsList", invitationsList);
//                        User user = new User(email, phone, fullName);
//                        mDatabase.child("users").child(firebaseUser.getUid()).setValue(user);
                        db.collection("users").document(firebaseUser.getUid()).set(user);
//                        String email = firebaseUser.getEmail();
                        Toast.makeText(RegisterActivity.this, "Account created\n"+email+" ", Toast.LENGTH_SHORT).show();

                        // open profile activity
                        startActivity(new Intent(RegisterActivity.this, ProfileActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // signup failed
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}