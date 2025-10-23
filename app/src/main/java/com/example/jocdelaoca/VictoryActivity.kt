package com.example.jocdelaoca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class VictoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_victory)

        // 1. RECUPERAR DATOS: Nombre del ganador
        val winnerName = intent.getStringExtra("WINNER_NAME") ?: "El Jugador X"

        val tvWinner: TextView = findViewById(R.id.tv_winner_name)
        val btnRepeat: Button = findViewById(R.id.btn_repeat)
        val btnStart: Button = findViewById(R.id.btn_start_new)

        // Actualiza el mensaje de felicitación
        tvWinner.text = winnerName

        // 2. Lógica de botones

        // Botón REPETIR: Vuelve al juego (Pantalla 4), asumiendo que GameActivity maneja el reset.
        btnRepeat.setOnClickListener {
            // Intent a GameActivity. Idealmente, pasaríamos los nombres de jugador
            // para que GameActivity sepa con quién continuar.
            val intent = Intent(this, GameActivity::class.java)
            // Ya que los nombres NO se pasan aquí, GameActivity.kt debe guardarlos de la primera vez.
            // Para simplificar: GameActivity deberá ser inteligente o se perderán los nombres.
            startActivity(intent)
            finish()
        }

        // Botón INICIAR (nueva partida): Regresar a Pantalla 1 (Welcome)
        btnStart.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            // Flags para limpiar el stack de Activities (simula volver al inicio limpio)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}