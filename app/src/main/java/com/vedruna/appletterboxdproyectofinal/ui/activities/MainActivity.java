package com.vedruna.appletterboxdproyectofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.vedruna.appletterboxdproyectofinal.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtener referencia a la BottomNavigationView y establecer la pestaña seleccionada por defecto
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        // Obtener el NavHostFragment y el NavController para la navegación entre fragmentos
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();

        // Configurar el listener de selección de elementos de la BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Navegar a los fragmentos correspondientes según el elemento seleccionado
            if (item.getItemId() == R.id.navigation_home) {
                navController.navigate(R.id.homeFragment);
            } else if (item.getItemId() == R.id.navigation_lists){
                navController.navigate(R.id.listsFragment);
            } else if (item.getItemId() == R.id.navigation_reviews){
                navController.navigate(R.id.reviewFragment);
            } else if (item.getItemId() == R.id.navigation_salir){
                navController.navigate(R.id.salirFragment);
            }
            return true;
        });
    }
}