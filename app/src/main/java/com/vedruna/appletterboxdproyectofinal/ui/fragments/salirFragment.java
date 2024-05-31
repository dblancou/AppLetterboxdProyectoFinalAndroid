package com.vedruna.appletterboxdproyectofinal.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vedruna.appletterboxdproyectofinal.R;
import com.vedruna.appletterboxdproyectofinal.ui.activities.LoginActivity;
import com.vedruna.appletterboxdproyectofinal.utils.TokenManager;

public class salirFragment extends Fragment {

    private static final String TAG = "SalirFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salir, container, false);

        Button botonSalir = view.findViewById(R.id.botonSalir);

        botonSalir.setOnClickListener(v -> {
            // Clear the token on logout
            TokenManager.getInstance(getActivity()).clearToken();
            Log.d(TAG, "Token cleared on logout");

            // Redirect to login activity
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}
