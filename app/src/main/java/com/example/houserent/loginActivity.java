package com.example.houserent;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    private EditText editTextLoginEmail, editTextLoginPwd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Login");
        }
        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPwd = findViewById(R.id.editText_login_password);
        progressBar = findViewById(R.id.progressBar);

        authProfile = FirebaseAuth.getInstance();

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(view -> {
            String email = editTextLoginEmail.getText().toString().trim();
            String password = editTextLoginPwd.getText().toString().trim();

            if(TextUtils.isEmpty(email)){
                Toast.makeText(loginActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                editTextLoginEmail.setError("Email is required");
                editTextLoginEmail.requestFocus();
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(loginActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                editTextLoginEmail.setError("Enter a valid email");
                editTextLoginEmail.requestFocus();
            }else if(TextUtils.isEmpty(password)) {
                Toast.makeText(loginActivity.this, "Password is required", Toast.LENGTH_SHORT).show();
                editTextLoginPwd.setError("Password is required");
                editTextLoginPwd.requestFocus();
            }else{
                loginUser(email, password);
            }
        }
        );
    }

    private void loginUser(String email, String password) {
        authProfile.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(loginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(loginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(loginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}