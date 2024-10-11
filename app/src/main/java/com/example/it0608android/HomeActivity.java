package com.example.it0608android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    TextView tvID, tvAccount;
    Button btnLogout;
    Intent intent;
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvID = findViewById(R.id.tvID);
        tvAccount = findViewById(R.id.tvAccount);
        btnLogout = findViewById(R.id.btnLogout);

        intent = getIntent();
        bundle = intent.getExtras();
        if (bundle != null) {
            String account = bundle.getString("USERNAME_ACCOUNT");
            int idAccount = bundle.getInt("ID_ACCOUNT");
            tvID.setText(String.valueOf(idAccount));
            tvAccount.setText(account);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle != null) {
                    intent.removeExtra("USERNAME_ACCOUNT");
                    intent.removeExtra("ID_ACCOUNT");
                }
                Intent intent1 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent1);
                finish();
            }
        });
    }
}
