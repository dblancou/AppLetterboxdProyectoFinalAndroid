package com.vedruna.appletterboxdproyectofinal.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.models.AuthResponse;
import com.vedruna.appletterboxdproyectofinal.models.RegisterRequest;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;
import com.vedruna.appletterboxdproyectofinal.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEdt, passwordEdt, confirmPasswordEdt;
    private Button registerBtn;
    private TextView loginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register);

        initView();
    }

    private void initView() {
        usernameEdt = findViewById(R.id.editTextUsername);
        passwordEdt = findViewById(R.id.editTextPassword);
        confirmPasswordEdt = findViewById(R.id.editTextConfirmPassword);
        registerBtn = findViewById(R.id.registerBtn);
        loginNow = findViewById(R.id.loginNow);

        registerBtn.setOnClickListener(v -> {
            String username = usernameEdt.getText().toString();
            String password = passwordEdt.getText().toString();
            String confirmPassword = confirmPasswordEdt.getText().toString();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                registerUser(username, password);
            }
        });

        loginNow.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser(String username, String password) {
        ApiService apiService = RetrofitClient.getApiService(this);
        RegisterRequest registerRequest = new RegisterRequest(username, password);

        apiService.registerUser(registerRequest).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    TokenManager.getInstance(RegisterActivity.this).saveToken(token);
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
