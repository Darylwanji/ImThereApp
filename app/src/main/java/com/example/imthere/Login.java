package com.example.imthere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    FirebaseAuth fbAuth;
    EditText email,password;
    Button signin;
    TextView regButton;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fbAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.Email);
        password = findViewById(R.id.password1);
        signin = findViewById(R.id.signin);

        progressBar = findViewById(R.id.progressBar);
        regButton = findViewById(R.id.register);

        if(fbAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        signin.setOnClickListener(v -> {
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();

            if(mail.isEmpty()){
                email.setError("Email is required");
                return;
            }
            if(pass.isEmpty()){
                password.setError("Password is required");
                return;
            }
            if(pass.length() < 8) {
                password.setError("Password must be >= 8");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);

            fbAuth.signInWithEmailAndPassword(mail,pass)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"User Signed in",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this,"Password or Email is incorrect" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        });

        regButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),RegisterView.class)));
    }
}