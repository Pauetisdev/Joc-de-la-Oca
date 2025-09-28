package com.example.jocdelaoca.main

import com.example.jocdelaoca.logic.Joc
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    var stillPlaying = true

    println("Welcome!")
    val joc = Joc()
    joc.afegirJugadors()

    while (stillPlaying) {
        println("\n--- New game ---")
        joc.play()

        print("Play again? (Yes/No)")
        val response = scanner.next().trim().lowercase()

        if (response == "yes") {
            println("\nHere we go")
            joc.reiniciarPartida(true, null)
        } else {
            stillPlaying = false
        }
    }
    println("\n--GAMES SUMMARY--")
    println(joc.Ranking())
    println("\nSee you next time!")

    scanner.close()
}
