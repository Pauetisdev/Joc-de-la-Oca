package com.example.jocdelaoca.logic;
import com.example.jocdelaoca.models.Jugador;
import com.example.jocdelaoca.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
public class Joc implements Resultat {
    private final Scanner scanner;
    private ArrayList<Jugador> jugadors;
    private final int puntuacioMax; // Un cop establerta la puntuació no cambia
    private Random rand = new Random();
    private List<Integer> ocaNumbers = List.of(1,5, 9, 14, 18, 23, 27, 32, 36, 41, 45, 50, 54, 59);

    // Constructor
    public Joc(){
        this.jugadors = new ArrayList<>();
        this.puntuacioMax = 63;
        this.scanner = new Scanner(System.in);
    }

    public void afegirJugadors(){
        System.out.print("Number of players (2-4): ");
        boolean continuar = false;
        int num = scanner.nextInt();
        scanner.nextLine();
        while (!continuar) {
            if (num < 2 || num > 4){
                System.out.println("Invalid number of players");
                System.out.print("\nEnter a number between 2 and 4: ");
                num = scanner.nextInt();
                scanner.nextLine();
            } else {
                continuar = true;
            }

        }

        for (int i = 0; i < num; i++){
            System.out.println("--Player " + (i+1) + " data--");
            System.out.print("Name: ");
            String name = scanner.nextLine();
            System.out.print("Nickname: ");
            String nickname = scanner.nextLine();
            System.out.print("Card Color: ");
            String CardColor = scanner.nextLine();

            // Instancia de classe jugador
            Jugador jugador = new Jugador(name, nickname, CardColor);
            // Afegir jugador a la llista
            jugadors.add(jugador);
        }
    }

    public int tirarDau(){
        return rand.nextInt(6)+1;
    }

    public void inOca(Jugador j) {
        System.out.println(j.getNickname() + " has landed on a goose square (" + j.getCasellaActual() + ")!");
        System.out.println("\nDe Oca a Oca i tiro perque em toca!");

        // Trobar la seguent oca
        int casellaActual = j.getCasellaActual();
        int seguentOca = -1; // centinella


        for (int oca : ocaNumbers) {
            if (oca > casellaActual) {
                seguentOca = oca;
                break;
            }
        }

        if (seguentOca != -1) {
            j.setCasellaActual(seguentOca);
            System.out.println("Moving to the next goose at square " + seguentOca);

            System.out.print("Press Enter to roll again...");
            scanner.nextLine();

            // Tornar a tirar el dau
            int tirada = tirarDau();
            System.out.println(j.getNickname() + " rolled a " + tirada);
            j.setCasellaActual(j.getCasellaActual() + tirada);
            System.out.println(j.getNickname() + " moves to square " + j.getCasellaActual());

            rebotSuperior(j);

            // Casella de la mort després del moviment
            if (casellaDeLaMort(j)) {
                return;
            }


            // Recursivitat per controlar si cau a una altre oca
            if (ocaNumbers.contains(j.getCasellaActual())) {
                inOca(j);
            }
        }
    }

    public void play() {
        boolean hiHaGuanyador = false;

        while (!hiHaGuanyador) {
            for (Jugador j : jugadors) {
                System.out.println("\nTurn: " + j.getNickname() + " (Current position: " + j.getCasellaActual() + ")");
                System.out.print("Press Enter to roll the dice...");
                scanner.nextLine();

                int tirada = tirarDau();

                System.out.println(j.getNickname() + " rolled a " + tirada);

                int posicioAnterior = j.getCasellaActual();
                j.setCasellaActual(j.getCasellaActual() + tirada);

                System.out.println(j.getNickname() + " moves from " + posicioAnterior + " to " + j.getCasellaActual());

                rebotSuperior(j);

                // FUTURE: WILL BE METHOD WITH A CASE
                // Comprovar si ha caigut en la casella de la mort
                if (casellaDeLaMort(j)) {
                    continue;
                }

                // Comprovar si s'ha caigut a una casella de la Oca
                if (ocaNumbers.contains(j.getCasellaActual())) {
                    inOca(j);
                }

                // Mirar si hi ha guanyador
                Jugador winner = getGuanyador(j);
                if (winner != null) {
                    System.out.println("WINNER! " + winner.getNickname() + " (" + winner.getName() + ") with " + winner.getColorFitxa() + " card have won the round!");
                    winner.sumWin();
                    hiHaGuanyador = true;
                    break;
                }
            }

            if (!hiHaGuanyador) {
                resumPunts();
            }
        }
    }

    private boolean casellaDeLaMort(Jugador j) {
        if (j.getCasellaActual() == 58) {
            System.out.println(j.getNickname() + " has fallen into the DEATH TRAP!");
            System.out.println("Return to the starting position");
            reiniciarPartida(false, j);
            return true; // ha caído en la muerte
        }
        return false; // sigue normal
    }


    public void resumPunts() {
        System.out.println("\n--- Player Scores Update ---");
        for (Jugador p : jugadors) {
            System.out.println(p.getNickname() + ", box:  " + p.getCasellaActual());
        }
        System.out.println("---------------------------");
    }

    public void reiniciarPartida(boolean allPlayers, Jugador jugador) {
        if (allPlayers) {
            for (Jugador j : jugadors) {
                j.setCasellaActual(0);
            }
        } else if (jugador != null) {
            jugador.setCasellaActual(0);
        }
    }


    @Override
    public Jugador getGuanyador(Jugador j) {
        if (j.getCasellaActual()== puntuacioMax){
            return j; // Retornar tot l'objecte
        }
        return null;
    }

    // Rebot casella superior a 63
    private void rebotSuperior(Jugador j) {
        if (j.getCasellaActual() > 63) {
            int casellaRebot = j.getCasellaActual() - 63;
            j.setCasellaActual(63 - casellaRebot);
            System.out.println(j.getNickname() + " exceeded square 63 and returns back to square " + j.getCasellaActual());
        }
    }

    public void Ranking() {

        ArrayList<Jugador> jugadoresOrdenados = new ArrayList<>(jugadors);
        jugadoresOrdenados.sort((j1, j2) -> Integer.compare(j2.getWins(), j1.getWins()));

        for (int i = 0; i < jugadoresOrdenados.size(); i++) {
            Jugador j = jugadoresOrdenados.get(i);
            j.setRanking(i + 1);
            System.out.println((i + 1) + ". " + j.getNickname() + " - " + j.getWins() + " wins");
        }
    }

    @Override
    public String toString() {
        return "Joc{" +
                "puntuacioMaxima=" + puntuacioMax +
                ", jugadors=" + jugadors +
                '}';
    }
}
