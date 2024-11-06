package com.example.it0608android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.it0608android.database.UserDB;

public class UpdatePasswordActivity extends AppCompatActivity {
    EditText edtNewPassword, edtConfirmPassword;
    Button btnUpdatePassword;
    Intent intent;
    Bundle bundle;
    private String email = null;
    private String username = null;
    UserDB userDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnUpdatePassword = findViewById(R.id.btnUpdatePassword);
        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null){
            email = bundle.getString("EMAIL_ACCOUNT", "");
            username = bundle.getString("USERNAME_ACCOUNT", "");
        }
        userDB = new UserDB(UpdatePasswordActivity.this);
        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = edtNewPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(newPassword)){
                    edtNewPassword.setError("New password can be not empty");
                    return;
                }
                if (!confirmPassword.equals(newPassword)){
                    edtConfirmPassword.setError("password not match");
                    return;
                }
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(username)){
                    Toast.makeText(UpdatePasswordActivity.this, "Co loi xay ra, vui long thu lai sau", Toast.LENGTH_SHORT).show();
                    return;
                }
                int update = userDB.updatePassword(username, email, newPassword);
                if (update == -1){
                    Toast.makeText(UpdatePasswordActivity.this, "He thong dang ban, quay lai sau", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentLogin = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                    finish(); // ko cho back lai
                }
            }
        });
    }
}
