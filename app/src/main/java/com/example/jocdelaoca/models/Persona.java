package com.example.jocdelaoca.models;

public class Persona {
    private String name;
    private String nickname;
    private int wins;

    public Persona(String name, String nickname){
        this.name = name;
        this.nickname = nickname;
        this.wins = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public int getWins() {
        return wins;
    }


    // Mètode per sumar 1 victoria al contador
    public void sumWin(){
        wins++;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", wins=" + wins +
                '}';
    }
}
