package com.example.jocdelaoca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SetupPlayer1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setup_player1)

        val nameEditText: EditText = findViewById(R.id.et_player_name)
        val nextButton: Button = findViewById(R.id.btn_next_player_setup)

        nextButton.setOnClickListener {
            val playerName1 = nameEditText.text.toString().trim()

            // Requisito: Validar que el nombre no esté vacío
            if (playerName1.isEmpty()) {
                nameEditText.error = "El nom del Jugador 1 no pot estar buit."
                return@setOnClickListener
            }

            // Navega a la Pantalla 3: Configuración del Jugador 2, pasando el nombre del Jugador 1
            val intent = Intent(this, SetupPlayer2Activity::class.java)
            intent.putExtra("PLAYER1_NAME", playerName1)
            startActivity(intent)
        }
    }
}