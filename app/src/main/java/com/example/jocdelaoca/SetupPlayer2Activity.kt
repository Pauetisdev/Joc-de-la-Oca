package com.example.jocdelaoca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SetupPlayer2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setup_player2)

        // 1. RECUPERAR DATOS del Intent (Nombre del Jugador 1)
        val player1Name = intent.getStringExtra("PLAYER1_NAME")

        val nameEditText: EditText = findViewById(R.id.et_player_name)
        val nextButton: Button = findViewById(R.id.btn_next_player_setup)

        nextButton.setOnClickListener {
            val playerName2 = nameEditText.text.toString().trim()

            // Requisito: Validar que el nombre no esté vacío
            if (playerName2.isEmpty()) {
                nameEditText.error = "El nom del Jugador 2 no pot estar buit."
                return@setOnClickListener
            }

            // Navega a la Pantalla 4: Juego, pasando ambos nombres
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("PLAYER1_NAME", player1Name)
            intent.putExtra("PLAYER2_NAME", playerName2)
            startActivity(intent)
        }
    }
}