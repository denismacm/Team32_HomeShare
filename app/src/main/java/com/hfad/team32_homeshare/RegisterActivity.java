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
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.hfad.team32_homeshare.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    //ViewBinding
    private ActivityRegisterBinding binding;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private ActionBar actionBar;

    private String email = "", password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // configure action bar, title, back button
        actionBar = getSupportActionBar();
        actionBar.setTitle("Register");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // go to previous activity when back button of action bar is pressed
        return super.onSupportNavigateUp();
    }

    private void validateData() {
        // get data
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEt.setError("Invalid email format");
        } else if (TextUtils.isEmpty(password)) {
            binding.passwordEt.setError("Enter password");
        } else if (password.length() < 6) {
            binding.passwordEt.setError("Password must be greater than 5 characters.");
        } else {
            firebaseSignUp();
        }
    }

    private void firebaseSignUp() {
        // show progress
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        // signup success
                        progressDialog.dismiss();
                        // get user info
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();
                        Toast.makeText(RegisterActivity.this, "Account created\n"+email, Toast.LENGTH_SHORT).show();

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