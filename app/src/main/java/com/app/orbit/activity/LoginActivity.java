package com.app.orbit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.orbit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText edtEmail, edtPass;
    AppCompatButton btnLogin;
    TextView txtForgotPass, btnRegister;

    String strEmail, strPass;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_signup);
        txtForgotPass = findViewById(R.id.btn_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        /*
        login button onclick
         */
        btnLogin.setOnClickListener(v -> {
            validateUsers();
        });

        /*
        register button onclick
         */
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUserToRegisterActivity();
            }
        });

        /*
        forgot password onclick
         */
        txtForgotPass.setOnClickListener(v -> {
            sendUserToResetPasswordActivity();
        });
    }

    /**
     * user input validation
     */
    private void validateUsers() {
        strEmail = edtEmail.getText().toString();
        strPass = edtPass.getText().toString();

        if (TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPass)) {
            Toast.makeText(this, "all fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
        if validation is successful
        login existing users
         */
        loginUsers();
    }

    /**
     * login existing users
     */
    private void loginUsers() {
        mAuth.signInWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                            sendUserToMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Intent to send user to another activity
     */

    /*
    after login -> send user to MainActivity
     */
    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
        finish();
    }

    /*
    click forgot password text -> send user to reset password activity
     */
    private void sendUserToResetPasswordActivity() {
        strEmail = edtEmail.getText().toString();

        Intent resetEmailIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
        /*
        get email from editText eg:"text@gmail.com" -> send it to ResetPasswordActivity
         */
        resetEmailIntent.putExtra("email", strEmail);
        startActivity(resetEmailIntent);
    }

    /*
    click register button -> send user to register activity
     */
    private void sendUserToRegisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        registerIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(registerIntent);
    }

}