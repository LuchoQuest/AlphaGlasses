package com.example.android.smartglass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class PantallaDispositivo extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pantalla_dispositivo);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_item_1:
                // Inicia la actividad CustomProcess
                intent = new Intent(this, CustomProcess.class);
                startActivity(intent);
                return true;
            case R.id.menu_item_2:
                // No es necesario iniciar la misma actividad actual
                return true;
            case R.id.menu_item_3:
                // Inicia la actividad PantallaLlamadas
                intent = new Intent(this, PantallaLlamadas.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }
}
