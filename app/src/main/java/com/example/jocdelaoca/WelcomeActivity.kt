package com.example.jocdelaoca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome)

        val nextButton: Button = findViewById(R.id.btn_next_screen)

        nextButton.setOnClickListener {
            // Navega a la Pantalla 2: Configuración del Jugador 1
            val intent = Intent(this, SetupPlayer1Activity::class.java)
            startActivity(intent)
        }
    }
}