package com.app.orbit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.orbit.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    EditText edtEmail;
    String txtEmail;
    Button cancel, btnResetPass;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        edtEmail = findViewById(R.id.edt_email);
        btnResetPass = findViewById(R.id.btn_reset_pass);

        mAuth = FirebaseAuth.getInstance();

        Intent resetEmailIntent = getIntent();
        txtEmail = resetEmailIntent.getExtras().getString("email");
        edtEmail.setText(txtEmail);

        btnResetPass.setOnClickListener(v -> resetPassword());

    }

    private void resetPassword() {
        if (TextUtils.isEmpty(txtEmail)){
            Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.sendPasswordResetEmail(txtEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ResetPasswordActivity.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                    showAlert();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Error Occurred", Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ResetPasswordActivity.this, "Error Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Show alert
     **/
    private void showAlert() {
        /*
        will create a view of our custom dialog layout
         */
        View alertCustomDialog = LayoutInflater.from(ResetPasswordActivity.this).inflate(R.layout.dialog_reset, null);
        /*
        initialize alert builder.
         */
        AlertDialog.Builder alert = new AlertDialog.Builder(ResetPasswordActivity.this);
        /*
        set our custom alert dialog to tha alertdialog builder
         */
        alert.setView(alertCustomDialog);

        final AlertDialog dialog = alert.create();
        /*
        this line removed app bar from dialog and make it transparent
        and you see the image is like floating outside dialog box.
         */
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        /*
        show the dialog box
         */
        dialog.show();

        cancel = alertCustomDialog.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sendUserToLoginActivity();
            }
        });
    }

    private void sendUserToLoginActivity() {
        Intent loginIntent= new Intent(ResetPasswordActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(loginIntent);
    }
}