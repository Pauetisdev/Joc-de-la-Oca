package com.example.jocdelaoca.models;


public class Jugador extends Persona {

    private String colorFitxa;
    private int casellaActual;

    private int ranking;

    //Constructor
    public Jugador (String name, String nickname, String colorFitxa){
        super(name, nickname);
        this.colorFitxa = colorFitxa;
        this.casellaActual = 0;
        this.ranking = 0;
    }

    // Getters
    public String getColorFitxa() {
        return colorFitxa;
    }

    public int getCasellaActual() {
        return casellaActual;
    }
    // Setters


    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public void setCasellaActual(int casellaActual) {
        this.casellaActual = casellaActual;
    }


    @Override
    public String toString() {
        return super.toString() +
                ", Jugador{" +
                "colorFitxa='" + colorFitxa + '\'' +
                ", casellaActual=" + casellaActual +
                ", ranking=" + ranking +
                '}';
    }
}
