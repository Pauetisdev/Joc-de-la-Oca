package com.example.jocdelaoca

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var player1Name: String
    private lateinit var player2Name: String
    private lateinit var tvPlayer1Score: TextView
    private lateinit var tvPlayer2Score: TextView // <-- CORREGIDO: Promovida a propiedad de clase
    private lateinit var tvMessage: TextView
    private lateinit var diceImageView: ImageView

    // Variables de estado del juego
    private var currentPlayer = 1 // 1 para Jugador 1, 2 para Jugador 2
    private var scorePlayer1 = 0
    private var scorePlayer2 = 0
    private val maxScore = 63 // La meta del juego

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)

        // 1. RECUPERAR DATOS: Nombres de los jugadores
        player1Name = intent.getStringExtra("PLAYER1_NAME") ?: "Jugador 1"
        player2Name = intent.getStringExtra("PLAYER2_NAME") ?: "Jugador 2"

        // 2. Encontrar Vistas
        val tvPlayer1Name: TextView = findViewById(R.id.tv_player1_name)
        val tvPlayer2Name: TextView = findViewById(R.id.tv_player2_name)
        // Eliminamos la declaración local de tvPlayer2Score aquí.
        val btnAbandonar: Button = findViewById(R.id.btn_abandonar)
        val btnReiniciar: Button = findViewById(R.id.btn_reiniciar)
        val btnJugar: Button = findViewById(R.id.btn_jugar)

        // Asignación a las propiedades de clase
        tvPlayer1Score = findViewById(R.id.tv_player1_score)
        tvPlayer2Score = findViewById(R.id.tv_player2_score) // <-- CORREGIDO: Asignación
        tvMessage = findViewById(R.id.tv_message)
        diceImageView = findViewById(R.id.iv_dice)

        // 3. Inicializar UI
        tvPlayer1Name.text = player1Name
        tvPlayer2Name.text = player2Name
        tvPlayer1Score.text = scorePlayer1.toString()
        tvPlayer2Score.text = scorePlayer2.toString()
        tvMessage.text = "Toca tirar a $player1Name"

        // 4. Configurar el botón JUGAR (Lógica del dado)
        btnJugar.setOnClickListener {
            // Lógica de la tirada
            val diceResult = rollDice()

            // CORREGIDO: Aseguramos que la función se llama con paréntesis: ${getCurrentPlayerName()}
            tvMessage.text = "${getCurrentPlayerName()} ha tirat un $diceResult"

            // Lógica de movimiento y comprobación de victoria
            updateScore(diceResult)

            if (scorePlayer1 >= maxScore || scorePlayer2 >= maxScore) {
                // Si hay ganador, navegamos a la Pantalla 5 (VictoryActivity)
                val winnerName = if (scorePlayer1 >= maxScore) player1Name else player2Name
                navigateToVictory(winnerName)
            } else {
                // Pasa el turno
                switchPlayer()
                tvMessage.append("\nAra toca a ${getCurrentPlayerName()}")
            }
        }

        // 5. Botones de control
        btnReiniciar.setOnClickListener {
            resetGame()
        }

        btnAbandonar.setOnClickListener {
            // Botón ABANDONAR: Regresar a la Pantalla 1 (WelcomeActivity)
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    // --- Métodos de Lógica del Juego ---

    private fun rollDice(): Int {
        val result = Random.nextInt(1, 6 + 1) // Genera entre 1 y 6
        updateDiceImage(result)
        return result
    }

    private fun updateDiceImage(result: Int) {
        val drawableId = when (result) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.ic_launcher_foreground
        }
        diceImageView.setImageResource(drawableId)
    }

    private fun updateScore(diceResult: Int) {
        if (currentPlayer == 1) {
            scorePlayer1 += diceResult
            tvPlayer1Score.text = scorePlayer1.toString()
            // Aquí iría la lógica de rebote y casillas especiales
            if (scorePlayer1 > maxScore) {
                // Lógica de rebote simple
                val overflow = scorePlayer1 - maxScore
                scorePlayer1 = maxScore - overflow
                tvPlayer1Score.text = scorePlayer1.toString()
                tvMessage.append("\n¡Rebot! Torna a la casella $scorePlayer1")
            }
        } else {
            scorePlayer2 += diceResult
            tvPlayer2Score.text = scorePlayer2.toString() // <-- CORREGIDO: tvPlayer2Score es accesible
            if (scorePlayer2 > maxScore) {
                val overflow = scorePlayer2 - maxScore
                scorePlayer2 = maxScore - overflow
                tvPlayer2Score.text = scorePlayer2.toString() // <-- CORREGIDO: tvPlayer2Score es accesible
                tvMessage.append("\n¡Rebot! Torna a la casella $scorePlayer2")
            }
        }
    }

    private fun switchPlayer() {
        currentPlayer = if (currentPlayer == 1) 2 else 1
        // Implementar indicador de turno (ej. color en el TextView) aquí
    }

    private fun getCurrentPlayerName(): String {
        return if (currentPlayer == 1) player1Name else player2Name
    }

    private fun navigateToVictory(winnerName: String) {
        val intent = Intent(this, VictoryActivity::class.java)
        intent.putExtra("WINNER_NAME", winnerName)
        startActivity(intent)
        finish()
    }

    private fun resetGame() {
        scorePlayer1 = 0
        scorePlayer2 = 0
        currentPlayer = 1
        tvPlayer1Score.text = "0"
        tvPlayer2Score.text = "0"
        tvMessage.text = "Partida reiniciada. Toca a $player1Name"
        // Reiniciar la imagen del dado a un valor neutral
        diceImageView.setImageResource(R.drawable.dice_1)
    }
}