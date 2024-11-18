package com.example.odyssey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SigninActivity extends AppCompatActivity {

    private TextView signupLink;
    private Button loginButton;
    private SharedPreferences sharedPreferences;
    private TextInputLayout getEmail, getPassword;
    private TextInputEditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("authToken")) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_signin);

        loginButton=findViewById(R.id.loginButton);
        signupLink=findViewById(R.id.signupLink);
        getEmail = findViewById(R.id.getEmail);
        getPassword = findViewById(R.id.getPassword);

        emailEditText = (TextInputEditText) getEmail.getEditText();
        passwordEditText = (TextInputEditText) getPassword.getEditText();

        // HANDLING SIGNUP BUTTON
        signupLink.setOnClickListener(view -> startActivity(new Intent(this, SignupActivity.class)));

        // HANDLING SUBMIT BUTTON
        loginButton.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            getEmail.setError(email.isEmpty() ? "Email is required" : null);
            getPassword.setError(password.isEmpty() ? "Password is required" : null);

            if (!email.isEmpty() && !password.isEmpty()) loginUser(email, password);
        });

    }
    private void loginUser(String email, String password) {
        System.out.println(email + " "+ password);
    }
}