package com.example.it0608android;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

//import java.io.File;
import com.example.it0608android.database.UserDB;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SignupActivity extends AppCompatActivity {
    TextView tvLogin;
    EditText edtUser, edtPassword, edtEmail, edtPhone, edtAddress;
    Button btnRegister, btnSubmitAccount;
    UserDB userDB;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tvLogin = findViewById(R.id.tvLogin);
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPass);
        btnRegister = findViewById(R.id.btnRegister);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtAddress = findViewById(R.id.edtAddress);
        btnSubmitAccount = findViewById(R.id.btnSubmitAccount);
        userDB = new UserDB(SignupActivity.this);

        btnSubmitAccount.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String address = edtAddress.getText().toString().trim();
                if (TextUtils.isEmpty(user)){
                    edtUser.setError("Username can not empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    edtPassword.setError("Password can not empty");
                    return;
                }
                if (TextUtils.isEmpty(email)){
                    edtEmail.setError("Email can not empty");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    edtPhone.setError("Phone can not empty");
                    return;
                }
                long insert = userDB.addNewUser(user, pass, email, phone, address);
                if (insert == -1){
                    Toast.makeText(SignupActivity.this, "signup Fail", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "signup successfully", Toast.LENGTH_SHORT).show();
                    // quay ve man hinh dang nhap
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //File file = null; // save data to file in local storage
                String user = edtUser.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(user)){
                    edtUser.setError("Username can not empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    edtPassword.setError("Password can not empty");
                    return;
                }
                FileOutputStream fileOutputStream = null;
                try {
                    user = user + "|";
                    //file = getFilesDir();
                    fileOutputStream = openFileOutput("account.txt", Context.MODE_APPEND);
                    fileOutputStream.write(user.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write(pass.getBytes(StandardCharsets.UTF_8));
                    fileOutputStream.write('\n');
                    edtUser.setText("");
                    edtPassword.setText("");
                    Toast.makeText(SignupActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                } catch (Exception ex){
                    ex.printStackTrace();
                } finally {
                    try {
                        assert fileOutputStream != null;
                        fileOutputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
