package com.hfad.team32_homeshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hfad.team32_homeshare.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {

    private ActivityChangePasswordBinding binding;
    private ActionBar actionBar;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkUser();

        actionBar = getSupportActionBar();
        actionBar.setTitle("Change Password");
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait.");
        progressDialog.setTitle("Updating your password...");

        firebaseAuth = FirebaseAuth.getInstance();

        binding.updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate();
            }
        });
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed(); // go to previous activity when back button of action bar is pressed
//        return super.onSupportNavigateUp();
//    }

    public boolean onOptionsItemSelected(MenuItem item){
//        Intent myIntent = new Intent(getApplicationContext(), MyActivity.class);
        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
        return true;
    }

    private void validate() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String password1 = binding.passwordEt.getText().toString().trim();
        String password2 = binding.passwordConfirmEt.getText().toString().trim();

        if (TextUtils.isEmpty(password1)) {
            binding.passwordEt.setError("Please enter a new password");
        } else if (TextUtils.isEmpty(password2)) {
            binding.passwordEt.setError("Please enter a new password");
        } else if (!password1.equals(password2)) {
            binding.passwordConfirmEt.setError("Passwords do not match.");
        } else {
            progressDialog.show();
            firebaseUser.updatePassword(password1).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Snackbar.make(findViewById(android.R.id.content), "Password updated", Snackbar.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(ChangePasswordActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void checkUser() {
        // check if user is already logged in, if so then open profile activity

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            // user is already logged in
            startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
            finish();
        }
    }
}