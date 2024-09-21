package com.example.odyssey;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    private Spinner roleSpinner;
    private Button signupBtn;
    private TextView signinLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        roleSpinner = findViewById(R.id.roleSpinner);
        signupBtn = findViewById(R.id.signupBtn);
        signinLink = findViewById(R.id.signinLink);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRole = parent.getItemAtPosition(position).toString();
                Toast.makeText(SignupActivity.this, selectedRole, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        signupBtn.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
        });
        signinLink.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, SigninActivity.class));
        });


    }
}