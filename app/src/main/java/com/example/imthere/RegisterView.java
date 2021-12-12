package com.example.imthere;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterView extends AppCompatActivity {

    FirebaseAuth fbAuth;

   private EditText fname,email,password,num;
   private Button regButton;
   private TextView login;
   private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        // Firebase Auth
        fbAuth = FirebaseAuth.getInstance();

        // Registration Info
        fname = findViewById(R.id.fname);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password1);
        num = findViewById(R.id.phoneNumber);

        // Clickables - Buttons & TextView
        regButton = findViewById(R.id.register);
        login = findViewById(R.id.login);

        // Progress Bar
        progressBar = findViewById(R.id.progressBar);

        /// Check if  User Logged in
        if(fbAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        regButton.setOnClickListener(v -> {
            String mail = email.getText().toString().trim();
            String pass = password.getText().toString().trim();
            // check if Email wad entered
            if(mail.isEmpty()){
                email.setError("Email is required");
                return;
            }
            // Check if password was entered
            if(pass.isEmpty()){
                password.setError("Password is required");
                return;
            }
            // Check password length
            if(pass.length() < 8){
                password.setError("Password must be greater 8");
                return;
            }
           progressBar.setVisibility(View.VISIBLE);

            fbAuth.createUserWithEmailAndPassword(mail,pass)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterView.this,"User in DB now",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            Toast.makeText(RegisterView.this,"Something went wrong" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        });

    login.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
    }

}