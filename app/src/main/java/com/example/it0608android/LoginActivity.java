package com.example.it0608android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
//import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.it0608android.database.UserDB;
import com.example.it0608android.model.UserModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnForgetPassword;
    TextView tvSignup;
    UserDB userDB;
    UserModel userModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linear_layout_demo_login);
        edtUsername = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);
        btnForgetPassword = findViewById(R.id.btnForget);

        btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentForget = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intentForget);
            }
        });

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        userDB = new UserDB(LoginActivity.this);
        checkLoginWithDatabase();
    }

    private void checkLoginWithDatabase(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUsername.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(user)){
                    edtUsername.setError("Username can be not empty");
                    return;
                }
                if (TextUtils.isEmpty(pass)){
                    edtPassword.setError("Password can be not empty");
                    return;
                }
                userModel = userDB.getInfoUser(user, pass); // lay du lieu tu database
                assert userModel != null;
                if (userModel.getUsername() != null){
                    // dang nhap thanh cong
                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("USERNAME_ACCOUNT", user);
                    bundle.putInt("ID_ACCOUNT", userModel.getId());
                    intent.putExtras(bundle);
                    startActivity(intent); // chuyen sang HomeActivity
                    finish();
                } else {
                    // dang nhap that bai
                    Toast.makeText(LoginActivity.this, "Account invalid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void checkLoginWithDataFile(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(username)){
                    edtUsername.setError("Username can not empty");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    edtPassword.setError("Password can not empty");
                    return;
                }
                // xu ly doc du lieu tu local storage
                try {
                    FileInputStream fileInputStream = openFileInput("account.txt");
                    int read = -1;
                    StringBuilder builder = new StringBuilder();
                    while ((read = fileInputStream.read()) != -1){
                        builder.append((char) read);
                    }
                    fileInputStream.close();
                    String[] infoAccount = null;
                    infoAccount = builder.toString().trim().split("\n");
                    boolean checkLogin = false;
                    for (int i = 0; i < infoAccount.length; i++){
                        String user = infoAccount[i].substring(0,infoAccount[i].indexOf("|"));
                        String pass = infoAccount[i].substring(infoAccount[i].indexOf("|")+1);
                        if (user.equals(username) && pass.equals(password)){
                            checkLogin = true;
                            break;
                        }
                    }
                    if(checkLogin){
                        // dang nhap thanh cong
                        // gui du lieu sang HomeActivity
                        // chuyen sang HomeActivity
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("USERNAME_ACCOUNT", username);
                        bundle.putInt("ID_ACCOUNT", 1);
                        intent.putExtras(bundle);
                        startActivity(intent); // chuyen sang HomeActivity
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Account Invalid", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
