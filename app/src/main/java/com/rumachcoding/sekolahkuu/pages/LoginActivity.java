package com.rumachcoding.sekolahkuu.pages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rumachcoding.sekolahkuu.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = findViewById(R.id.et_username);
        final EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Login Validation

                String strUsername = etUsername.getText().toString();
                String strPassword = etPassword.getText().toString();

                if(!strUsername.equals("admin")){
                    etUsername.setError("Username Tidak Dikenal");
                    return;
                }
                if (!strPassword.equals("admin")){
                    etPassword.setError("Password Salah");
                    return;
                }

                //masuk aktiviti list
                Intent listIntent = new Intent(LoginActivity.this,ListActivity.class);
                startActivity(listIntent);

                finish();
            }
        });
    }
}
