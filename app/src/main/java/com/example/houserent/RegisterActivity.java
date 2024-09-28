package com.example.houserent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterMobile,
        editTextRegisterPwd, editTextRegisterConfirmPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;

    private RadioButton radioButtonRegisterGenderSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Register");
        }

        Toast.makeText(RegisterActivity.this, "You can register now", Toast.LENGTH_SHORT).show();
        editTextRegisterFullName = findViewById(R.id.editText_register_full_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterMobile = findViewById(R.id.editText_register_phoneNumber);
        editTextRegisterPwd = findViewById(R.id.editText_register_password);
        editTextRegisterConfirmPwd = findViewById(R.id.editText_register_confirm_password);

        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedId);

                //obtain data from the user
                String fullName = editTextRegisterFullName.getText().toString();
                String email = editTextRegisterEmail.getText().toString();
                String mobile = editTextRegisterMobile.getText().toString();
                String password = editTextRegisterPwd.getText().toString();
                String confirmPassword = editTextRegisterConfirmPwd.getText().toString();
                String textGender;

                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(RegisterActivity.this, "Enter your full name", Toast.LENGTH_SHORT).show();
                    editTextRegisterFullName.setError("Full name is required");
                    editTextRegisterFullName.requestFocus();

                }else if(TextUtils.isEmpty(email)) {
                    Toast.makeText(RegisterActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    editTextRegisterEmail.setError("Enter a valid email");
                    editTextRegisterEmail.requestFocus();
                }else if(radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterActivity.this, "Select your Gender", Toast.LENGTH_SHORT).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required");
                    radioButtonRegisterGenderSelected.requestFocus();
                }else if(TextUtils.isEmpty(mobile)) {
                    Toast.makeText(RegisterActivity.this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Mobile number is required");
                    editTextRegisterMobile.requestFocus();
                }else if(editTextRegisterMobile.length()!=11){
                    Toast.makeText(RegisterActivity.this, "Enter a valid mobile number", Toast.LENGTH_SHORT).show();
                    editTextRegisterMobile.setError("Mobile number should be 11 digits!");
                    editTextRegisterMobile.requestFocus();
                }else if(TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password is required");
                    editTextRegisterPwd.requestFocus();
                }else if(password.length()<6) {
                    Toast.makeText(RegisterActivity.this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                    editTextRegisterPwd.setError("Password should be at least 6 characters");
                    editTextRegisterPwd.requestFocus();
                }else if(TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Confirm your password", Toast.LENGTH_SHORT).show();
                    editTextRegisterConfirmPwd.setError("Confirm your password");
                    editTextRegisterConfirmPwd.requestFocus();
                }else if(!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    editTextRegisterConfirmPwd.setError("Passwords do not match");
                    editTextRegisterConfirmPwd.requestFocus();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar = findViewById(R.id.progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(fullName, email, mobile, password, textGender);
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            private void registerUser(String fullName, String email, String mobile, String password, String textGender) {
                //register the user
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                    FirebaseUser firebaseUser = auth.getCurrentUser();

                                    firebaseUser.sendEmailVerification();

//                                    Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                    startActivity(intent);
//                                    finish();

                                }
                            }
                        });

            }
        });






    }
}