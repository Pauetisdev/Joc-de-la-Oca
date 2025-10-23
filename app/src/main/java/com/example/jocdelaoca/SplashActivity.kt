package com.example.jocdelaoca

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 5000 // 5 segundos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Usamos el layout activity_splash para mostrar la imagen.
        setContentView(R.layout.activity_splash)

        // Temporizador de 5 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            // Transición a la Pantalla 1 (Welcome)
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }
}