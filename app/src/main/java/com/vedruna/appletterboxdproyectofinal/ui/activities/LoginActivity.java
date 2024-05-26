package com.vedruna.appletterboxdproyectofinal.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vedruna.appletterboxdproyectofinal.R;

public class LoginActivity extends AppCompatActivity {
    private EditText userEdt, passEdt;
    private Button loginBtn;
    private TextView registerTextView; // Agrega esta línea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        initView();
    }

    private void initView() {
        userEdt = findViewById(R.id.editTextText);
        passEdt = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.loginBtn);
        registerTextView = findViewById(R.id.textView8); // Agrega esta línea

        loginBtn.setOnClickListener(v -> {
            if (userEdt.getText().toString().isEmpty() || passEdt.getText().toString().isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please Fill your user and password", Toast.LENGTH_SHORT).show();
            } else if (userEdt.getText().toString().equals("test") && passEdt.getText().toString().equals("test")) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "Your user and password is not correct", Toast.LENGTH_SHORT).show();
            }
        });

        // Agrega este bloque de código para manejar el clic en el texto "Regístrate ahora"
        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
