package com.example.it0608android;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.it0608android.database.UserDB;
import com.example.it0608android.model.UserModel;

public class ForgetPasswordActivity extends AppCompatActivity {
    EditText edtEmail, edtUsername;
    Button btnSubmit;
    UserDB userDB;
    UserModel userModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        btnSubmit = findViewById(R.id.btnSubmit);
        userDB = new UserDB(ForgetPasswordActivity.this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAccount();
            }
        });
    }
    private void checkAccount(){
        String email = edtEmail.getText().toString().trim();
        String user = edtUsername.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            edtEmail.setError("Email can be not empty");
            return;
        }
        if (TextUtils.isEmpty(user)){
            edtUsername.setError("Username can be not empty");
            return;
        }
        boolean checkUser = userDB.checkUsernameEmail(user, 1);
        boolean checkEmail = userDB.checkUsernameEmail(email, 2);
        if (!checkUser){
            edtUsername.setError("Username not Exists");
            return;
        }
        if (!checkEmail){
            edtEmail.setError("Email not Exists");
            return;
        }
        Intent intent = new Intent(ForgetPasswordActivity.this, UpdatePasswordActivity.class);
        // send data sang UpdatePasswordActivity
        Bundle bundle = new Bundle();
        bundle.putString("EMAIL_ACCOUNT", email);
        bundle.putString("USERNAME_ACCOUNT", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
