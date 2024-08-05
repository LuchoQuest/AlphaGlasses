package com.example.android.smartglass;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class splash extends AppCompatActivity {

    // Duración del splash screen en milisegundos
    private static final int SPLASH_SCREEN_DURATION = 3000; // 3 segundos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Inicia un temporizador para abrir la actividad principal después de un tiempo
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Inicia la actividad principal
                Intent intent = new Intent(splash.this, MainActivity.class);
                startActivity(intent);
                // Cierra la actividad de splash para que el usuario no pueda volver a ella presionando "Atrás"
                finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }
}
