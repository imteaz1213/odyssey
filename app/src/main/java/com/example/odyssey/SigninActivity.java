package com.example.odyssey;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {

    private Button loginButton;
    private TextView signupLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        loginButton=findViewById(R.id.loginButton);
        signupLink=findViewById(R.id.signupLink);

        loginButton.setOnClickListener(v -> {
            startActivity(new Intent(SigninActivity.this, HomeActivity.class));
        });

        signupLink.setOnClickListener(v -> {
            startActivity(new Intent(SigninActivity.this, SignupActivity.class));
        });

    }
}