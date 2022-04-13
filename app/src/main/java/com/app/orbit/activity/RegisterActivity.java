package com.app.orbit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.orbit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText edtUsername, edtEmail, edtPass, edtConfirmPass;
    AppCompatButton btnSign;
    TextView btnLogin;

    String strUsername;
    String strEmail;
    String strPass;
    String strConfirmPass;

    /*
    Firebase
     */
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edt_username);
        edtEmail = findViewById(R.id.edt_email);
        edtPass = findViewById(R.id.edt_password);
        edtConfirmPass = findViewById(R.id.edt_confirm_password);
        btnSign = findViewById(R.id.btn_register);
        btnLogin = findViewById(R.id.btn_login);

        mAuth = FirebaseAuth.getInstance();

        /*
        Registration button click to register new user
         */
        btnSign.setOnClickListener(v -> {
            validateUser();
        });

        /*
        register button onclick
         */
        btnLogin.setOnClickListener(v -> {
            sendUserToLoginActivity();
        });
    }

    public void validateUser() {
        strUsername = edtUsername.getText().toString();
        strEmail = edtEmail.getText().toString();
        strPass = edtPass.getText().toString();
        strConfirmPass = edtConfirmPass.getText().toString();

        if (TextUtils.isEmpty(strUsername) || TextUtils.isEmpty(strEmail)
                || TextUtils.isEmpty(strPass) || TextUtils.isEmpty(strConfirmPass)){
            Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
        } else if (strPass.length() < 6){
            Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_SHORT).show();
        } else {
            registerUser(strUsername, strEmail, strPass);
        }
    }

    /**
     * Register new user
     * @param strUsername
     * @param strEmail
     * @param strPass
     */
    private void registerUser(String strUsername, String strEmail, String strPass) {
        mAuth.createUserWithEmailAndPassword(strEmail, strPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                            saveUserInfo(strUsername);
                            // if the user created intent to login activity
                            Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Registration failed!" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserInfo(String strUsername) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        map.put("username", strUsername);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Update the profile " +
                            "for better experience", Toast.LENGTH_SHORT ).show();
                }
            }
        });

    }


    /**
     * Intent to send user to another activity
     */

    /*
    click register button -> send user to register activity
     */
    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(loginIntent);
    }

}