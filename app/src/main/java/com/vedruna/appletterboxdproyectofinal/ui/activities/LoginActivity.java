package com.vedruna.appletterboxdproyectofinal.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.models.LoginRequest;
import com.vedruna.appletterboxdproyectofinal.models.AuthResponse;
import com.vedruna.appletterboxdproyectofinal.network.ApiService;
import com.vedruna.appletterboxdproyectofinal.network.RetrofitClient;
import com.vedruna.appletterboxdproyectofinal.utils.TokenManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText userEdt, passEdt;
    private Button loginBtn;
    private TextView registerTextView;
    private ApiService apiService;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        apiService = RetrofitClient.getApiService(this);
        initView();

        // Limpiar el token al iniciar la actividad de inicio de sesión
        TokenManager.getInstance(LoginActivity.this).clearToken();
        Log.d(TAG, "Token cleared on app");

    }

    private void initView() {
        userEdt = findViewById(R.id.editTextText);
        passEdt = findViewById(R.id.editTextPassword);
        loginBtn = findViewById(R.id.loginBtn);
        registerTextView = findViewById(R.id.textView8);

        loginBtn.setOnClickListener(v -> {
            String username = userEdt.getText().toString().trim();
            String password = passEdt.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in your username and password", Toast.LENGTH_SHORT).show();
            } else {
                performLogin(username, password);
            }
        });

        registerTextView.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void performLogin(String username, String password) {
        // Clear any existing token before attempting to log in
        TokenManager.getInstance(LoginActivity.this).clearToken();
        Log.d(TAG, "Token cleared on app");

        LoginRequest loginRequest = new LoginRequest(username, password);
        Call<AuthResponse> call = apiService.loginUser(loginRequest);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String token = response.body().getToken();
                    Log.d(TAG, "Login successful. Token received: " + token);
                    TokenManager.getInstance(LoginActivity.this).saveToken(token);

                    // Log the saved token
                    Log.d(TAG, "Saved token: " + TokenManager.getInstance(LoginActivity.this).getToken());

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Response code: " + response.code());
                    Log.d(TAG, "Response message: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.d(TAG, "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "An error occurred: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}
